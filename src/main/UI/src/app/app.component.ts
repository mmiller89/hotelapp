import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient, HttpResponse,HttpHeaders} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Location, LocationStrategy } from "@angular/common";
import {map} from "rxjs/operators";





@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{



  constructor(private httpClient:HttpClient, private location: Location, private locationStrategy: LocationStrategy){}



  // private baseURL:string='http://localhost:8080';
  private baseURL:string=this.location.path();

  private getUrl:string = this.baseURL + '/room/reservation/v1/';
  private postUrl:string = this.baseURL + '/room/reservation/v1';
  private welcomeMessageURL:string = this.baseURL + "/resources/greetings";
  private timeZoneURL: string = this.baseURL + "/timezone/";
  public sentences!: string[];
  public easternTime: string = "";
  public mountainTime: string = "";
  public utcTime: string = "";

  public scene: number = 0;

  //Temporary initial values
  public loggedIn: boolean = false;
  public user: any;
  //Temporary initial values

  public submitted!:boolean;
  roomsearch! : FormGroup;

  rooms! : Room[];

  request!:ReserveRoomRequest;
  currentCheckInVal!:string;
  currentCheckOutVal!:string;

    ngOnInit(){
      this.roomsearch= new FormGroup({
        checkin: new FormControl(' '),
        checkout: new FormControl(' ')
      });

 //     this.rooms=ROOMS;

      this.getWelcome().subscribe(
        sentence => {
          this.sentences! = sentence;
        }
      )

      this.getEastern().subscribe(
        time => {
          this.easternTime! = time[0];
        }
      )

      this.getMountain().subscribe(
        time => {
          this.mountainTime! = time[0];
        }
      )

      this.getUTC().subscribe(
        time => {
          this.utcTime! = time[0];
        }
      )



      const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });
  }

  loginService = new FormGroup({
    userName: new FormControl(""),
    password: new FormControl("")
  })



  setScene(n: number): void {
      this.scene = n;
  }


  displayUserReservations(){
    return this.httpClient.get(this.getUrl + "reservationlist/" + this.user.id, {responseType: 'json'}).subscribe(res => console.log(res))
  }

  validateLogin(){
    let user = this.loginService.value.userName!;
    let password = this.loginService.value.password!;
    let login = new Login(user, password);
    JSON.stringify(login)
    this.httpClient.put(this.baseURL + "/login/credentials", login).subscribe(res => {
      if (res) {
        console.log(res)
        this.loggedIn = true;
        // @ts-ignore
        let rewards = parseInt(res['rewardsPoints'])
        // @ts-ignore
        this.user = new User(res['id'],res['userName'], rewards)
      }
    })
  }

  //
  //temporary testing method - remove before full launch.
  addOrSub(num: Number){
      if (num == 0){
        this.user.addPoints(50)
      } else { this.user.subtractPoints(50) }
  }
  //temporary testing method - remove before full launch.
  //

  logout(){
    if (this.loggedIn){
      this.saveUserInfo();
      this.loggedIn = false;
      this.user = null;
    }
  }

  // User data front end
  // id: string;
  // userName: string;
  // rewards: number;
  saveUserInfo(){
    let user = new User(this.user.id, this.user.userName, this.user.rewards)
    JSON.stringify(user)
    this.httpClient.put(this.baseURL + "/login/save", user).subscribe(res => console.log(res))
  }


  getWelcome(): Observable<any> {
    return this.httpClient.get(this.welcomeMessageURL, {responseType: 'json'});
  }

  getEastern(): Observable<any> {
    return this.httpClient.get(this.timeZoneURL + "eastern", {responseType:'json'});
  }
  getMountain(): Observable<any> {
    return this.httpClient.get(this.timeZoneURL + "mountain", {responseType:'json'});
  }
  getUTC(): Observable<any> {
    return this.httpClient.get(this.timeZoneURL + "utc", {responseType:'json'});
  }


    onSubmit({value,valid}:{value:Roomsearch,valid:boolean}){
      this.getAll().subscribe(

        rooms => {
          console.log(Object.values(rooms)[0]);
          this.rooms=<Room[]>Object.values(rooms)[0];
        }


      );
    }




    reserveRoom(value:string, method: string, price: string){
      this.request = new ReserveRoomRequest(value, this.user.id, this.currentCheckInVal, this.currentCheckOutVal);

      const roomPrice = parseInt(price);

      this.createReservation(this.request);

      console.log(roomPrice);
      console.log(this.user.rewards)

      if (method == "money"){
        alert("Rerouting to payment information page! (Not Actual)")
      }
      else if (method == "points" && this.user.rewards >= roomPrice){
        alert("Paying with points!")
        this.user.subtractPoints(roomPrice)
      } else {
        alert("You don't have enough points, reverting to normal payment!")
      }

      this.onSubmit(this.roomsearch)
    }



    createReservation(body:ReserveRoomRequest) {
      let bodyString = JSON.stringify(body); // Stringify payload
      let headers = new Headers({'Content-Type': 'application/json'}); // ... Set content type to JSON
     // let options = new RequestOptions({headers: headers}); // Create a request option

     const options = {
      headers: new HttpHeaders().append('key', 'value'),
    }

      this.httpClient.post(this.postUrl, body, options)
        .subscribe(res => console.log(res));
    }

  /*mapRoom(response:HttpResponse<any>): Room[]{
    return response.body;
  }*/


    getAll(): Observable<any> {


       return this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin='+ this.currentCheckInVal + '&checkout='+this.currentCheckOutVal, {responseType: 'json'});
    }






  }





export interface Roomsearch{
    checkin:string;
    checkout:string;
  }




export interface Room{
  id:string;
  roomNumber:string;
  price:string;
  links:string;

}

export class User{
  id: string;
  userName: string;
  rewards: number;


  constructor(id: string, userName: string, rewards: number) {
    this.id = id;
    this.userName = userName;
    this.rewards = rewards;
  }

  addPoints(points: number){
    this.rewards += points;
  }
  subtractPoints(points: number){
    this.rewards -= points;
  }
}

export class Login{
  userName:string;
  password:string;

  constructor(userName: string, password: string) {
    this.userName = userName;
    this.password = password;
  }
}

export class ReserveRoomRequest {
  roomId:string;
  userId:string;
  checkin:string;
  checkout:string;

  constructor(roomId:string,
              userId: string,
              checkin:string,
              checkout:string) {

    this.roomId = roomId;
    this.userId = userId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

/*
var ROOMS: Room[]=[
  {
  "id": "13932123",
  "roomNumber" : "409",
  "price" :"20",
  "links" : ""
},
{
  "id": "139324444",
  "roomNumber" : "509",
  "price" :"30",
  "links" : ""
},
{
  "id": "139324888",
  "roomNumber" : "609",
  "price" :"40",
  "links" : ""
}
] */

