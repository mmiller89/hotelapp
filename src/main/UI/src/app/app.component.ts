import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';
import {Location, LocationStrategy} from "@angular/common";


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
  private additionsURL: string = this.baseURL + '/resources/additions'
  private welcomeMessageURL:string = this.baseURL + "/resources/greetings";
  private timeZoneURL: string = this.baseURL + "/timezone/";
  public sentences!: string[];
  public easternTime: string = "";
  public mountainTime: string = "";
  public utcTime: string = "";
  public tableScene: number = 0;
  public scene: number = 0;


  public loggedIn: boolean = false;
  public user: any;
  public reservationDisplayToggle: number = 0;
  public additionsAvailable!: boolean;
  public additions! : Addition[];


  public submitted!:boolean;
  roomsearch! : FormGroup;

  rooms! : Room[];
  reservations: Reservation[] = [];


  request!:ReserveRoomRequest;
  currentCheckInVal!:string;
  currentCheckOutVal!:string;

//For Database Reports //
  users: DataReportUser[] = [];
  reserves: DataReportReservation[] = [];
  roomData: DataReportRoom[] = [];
  additionData: DataReportAdditions[] = [];



    ngOnInit(){
      this.roomsearch= new FormGroup({
        checkin: new FormControl(' '),
        checkout: new FormControl(' ')
      });



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

      this.getAdditions().subscribe(add => {
        this.additions = add;
        console.log(this.additions);
      })


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

  setTableState(n: number){
      this.tableScene = n;
  }

  createReport(){
      this.createUserReport();
      this.createReservationReport();
      this.createRoomReport();
      this.createAdditionReport();
      this.scene = 4;

  }

  createUserReport(){
    this.httpClient.get(this.baseURL + "/login/getallusers",{responseType: 'json'}).subscribe(res =>{
      let obLength = Object.keys(res).length;
      if (obLength > 0){
        for (let i = 0; i < obLength; i++){
          // @ts-ignore
          let id = res[i]['id'];
          // @ts-ignore
          let name = res[i]['userName']
          // @ts-ignore
          let password = res[i]['password']
          // @ts-ignore
          let rewards = res[i]['rewardsPoints']

          let reservedRes: string[] = []
          // @ts-ignore
          for (let x = 0; x < res[i]['reservationEntities'].length; x++){
            // @ts-ignore
            reservedRes[x] = res[i]['reservationEntities'][x]['id'];
          }

          this.users[i] = new DataReportUser(id, name, password, rewards, reservedRes)
        }
      }
    });
  }

  // id: string;
  // checkin: string;
  // checkout: string;
  // attachedRoomNumber: string;

  createReservationReport(){
    this.httpClient.get(this.baseURL + "/login/getallreservations",{responseType: 'json'}).subscribe(res =>{
      let obLength = Object.keys(res).length;
      if (obLength > 0){
        for (let i = 0; i < obLength; i++){
          // @ts-ignore
          let id = res[i]['id'];
          // @ts-ignore
          let checkin = res[i]['checkin']
          // @ts-ignore
          let checkout = res[i]['checkout']
          // @ts-ignore
          let roomNumber = res[i]['roomEntity']['roomNumber']

          this.reserves[i] = new DataReportReservation(id, checkin, checkout, roomNumber)
        }
      }
    });
  }

  createRoomReport(){
    this.httpClient.get(this.baseURL + "/login/getallrooms",{responseType: 'json'}).subscribe(res =>{
      let obLength = Object.keys(res).length;
      console.log(res)
      if (obLength > 0){
        for (let i = 0; i < obLength; i++){
          // @ts-ignore
          let id = res[i]['id'];
          // @ts-ignore
          let roomNumber = res[i]['roomNumber']
          // @ts-ignore
          let price = res[i]['price']
          // @ts-ignore
          let reservedAdditions: string[] = []

          // @ts-ignore
          if (res[i]['additionEntities'].length > 0){
            // @ts-ignore
            for (let x = 0; x < res[i]['additionEntities'].length; x++){
              // @ts-ignore
              reservedAdditions[x] = res[i]['additionEntities'][x]['name'];
            }
          }
          // @ts-ignore
          this.roomData[i] = new DataReportRoom(id, roomNumber, price, reservedAdditions)
        }
      }
    });
  }

  createAdditionReport(){
    this.httpClient.get(this.baseURL + "/login/getalladditions",{responseType: 'json'}).subscribe(res =>{
      let obLength = Object.keys(res).length;
      console.log(res)
      if (obLength > 0){
        for (let i = 0; i < obLength; i++){
          // @ts-ignore
          let id = res[i]['id'];
          // @ts-ignore
          let name = res[i]['name']

          // @ts-ignore
          let description = res[i]['description']
          // @ts-ignore
          let price = res[i]['price']

          this.additionData[i] = new DataReportAdditions(id, name, description, price)
        }
      }
    });
  }

  displayUserReservations(){
      this.reservations = []
    return this.httpClient.get(this.getUrl + "reservationlist/" + this.user.id, {responseType: 'json'}).subscribe(res => {
      console.log("THE SERVER RESPONSE");
      console.log(res);

     let obLength = Object.keys(res).length;
     console.log(obLength)
     let additionArr: Addition[] = [];


      if (obLength > 0){
          for (let i = 0; i < Object.keys(res).length; i++){
            // @ts-ignore
            let checkin = res[i]['checkin'];
            // @ts-ignore
            let checkout = res[i]['checkout'];
            // @ts-ignore
            let resId = res[i]['id'];
            // @ts-ignore
            let price = res[i]['roomEntity']['price'];
            // @ts-ignore
            let roomNumber = res[i]['roomEntity']['roomNumber'];
            // @ts-ignore
            let roomD = res[i]['roomEntity']['id'];

            // @ts-ignore
            additionArr = res[i]['roomEntity']['additionEntities'];

            let breakSwitch = true;
            let pool = true;
            let sheets = true;
            for (let i = 0; i < additionArr.length; i++){

              if (additionArr[i].name.includes("Breakfast in Bed")){
                breakSwitch = false;
              }
              if (additionArr[i].name.includes("Premium Bed Sheets")){
                sheets = false;
              }
              if (additionArr[i].name.includes("VIP Pool Pass")){
                pool = false;
              }
            }

            this.reservations[i] = new Reservation(this.user.id, resId, roomD, checkin, checkout, roomNumber, price, additionArr, breakSwitch, sheets, pool);
        }


      }
      this.reservationDisplayToggle = 1;

    })

    }

    logAdditions(value: any, reservation: Reservation, cost: number){

      let additionEntity = null;

      for (let s of this.additions) {
        if (s.name == value.target.name) {
          additionEntity = s;
        }
      }

      let checked = value.target.checked;
      if (checked && additionEntity != null){
        reservation.additions.push(additionEntity);
        console.log(reservation.additions)
        reservation.cashOut += cost;
      }
      else if (additionEntity != null) {
        const index = reservation.additions.indexOf(additionEntity);
        if (index != -1){
          reservation.additions.splice(index, 1);
          console.log(reservation.additions)
          reservation.cashOut -= cost;
        }
      }
    }


    buyAdditions(value: string, reserve: Reservation){ //THIS MUST BE LOGGED TO DATABASE IF SUCCESSFUL
      if (value == "cash"){
        this.updateReservation(reserve);
        alert("Successful!")
        this.displayUserReservations();
      }
      else if (value == "rewards" && this.user.rewards >= reserve.cashOut){
        this.updateReservation(reserve);
        this.user.rewards -= reserve.cashOut;
        reserve.cashOut = 0;
        this.saveUserInfo()
        alert("Thank you, points applied to purchase.")
        this.displayUserReservations();
      } else {
        alert("Not enough points.")
      }

    }

    updateReservation(reserve: Reservation){
      //take the data from this.reservations and post it to the back end.
      this.httpClient.put(this.postUrl, reserve)
        .subscribe(res => {
          console.log("THIS IS THE USER AFTER POSTING WITH USER ID " + this.user.id);
          console.log(res);
        })

    }

    deleteReservation(reservation: Reservation){ //THIS MUST BE LOGGED TO DATABASE

      let id = parseInt(reservation.reservationId)
      this.httpClient.delete(this.postUrl + "/" + id).subscribe()

      let index;

      for (let res of this.reserves){
        if (res.id == reservation.reservationId){
          index = this.reserves.indexOf(res);
          this.reserves.splice(index, 1);
        }
      }

     index = this.reservations.indexOf(reservation);
      this.reservations.splice(index, 1);
    }

    //TOMORROWS OBJECTIVE -> REPOPULATE ROOMS AFTER DELETING RESERVATION, CLEAN UP CSS, WORK ON DIAGRAMS.


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

  logout(){
    if (this.loggedIn){
      this.saveUserInfo();
      this.reservationDisplayToggle = 0;
      this.scene = 0;
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


  getAdditions(): Observable<any> {
    return this.httpClient.get(this.additionsURL, {responseType: 'json'});
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
      //this.rooms=[]
      this.reservationDisplayToggle = 0;
      this.getAll().subscribe(

        rooms => {

          if (rooms == null){
            alert("Please ensure check-in date is before check-out date!")
          } else {
            console.log(rooms[0]);
            this.rooms=<Room[]>Object.values(rooms)[0];
          }

        }


      );
    }




    reserveRoom(value:string, method: string, price: string){
      this.request = new ReserveRoomRequest(value, this.user.id, this.currentCheckInVal, this.currentCheckOutVal);

      const roomPrice = parseInt(price);

      this.createReservation(this.request);

      if (method == "money"){
        alert("Rerouting to payment information page! (Not Actual)")
      }
      else if (method == "points" && this.user.rewards >= roomPrice){
        alert("Paying with points!")
        this.user.subtractPoints(roomPrice)
        this.saveUserInfo()
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

export class Reservation{ //This is used for displaying the information visually on the page.
  userId: string;
  reservationId: string;
  roomId: string;
  checkin: string;
  checkout: string;
  roomNumber: string;
  roomPrice: string;
  additions: Addition[] = [];
  cashOut: number = 0;
  breakSwitch: boolean;
  towelSwitch: boolean;
  poolSwitch: boolean;


  constructor(userId: string, reservationId: string, roomId: string,checkin: string, checkout: string, roomNumber: string,
              roomPrice: string, additions: Addition[], breakSwitch: boolean, towelSwitch: boolean, poolSwitch: boolean){
    this.userId = userId;
    this.reservationId = reservationId;
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
    this.roomNumber = roomNumber;
    this.roomPrice = roomPrice;
    this.additions = additions;
    this.breakSwitch = breakSwitch;
    this.towelSwitch = towelSwitch;
    this.poolSwitch = poolSwitch;
  }
}

export class Addition {
  description: string;
  id: string;
  name: string;
  cost: number;

  constructor(id: string, name: string, description: string, cost: number) {
    this.id = id;
    this.description = description;
    this.name = name;
    this.cost = cost;
  }
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
  additions: string[] = [];

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


//Special Classes used for displaying data reports
export class DataReportUser{
  id: string;
  userName: string;
  password: string;
  rewards: string;
  reservedRes: string[]


  constructor(id: string, userName: string, password: string, rewards: string, reservedRes: string[]) {
    this.id = id;
    this.userName = userName;
    this.rewards = rewards;
    this.password = password;
    this.reservedRes = reservedRes;
  }
}

export class DataReportReservation{
  id: string;
  checkin: string;
  checkout: string;
  attachedRoomNumber: string;


  constructor(id: string, checkin: string, checkout: string, attachedRoomNumber: string) {
    this.id = id;
    this.checkin = checkin;
    this.checkout = checkout;
    this.attachedRoomNumber = attachedRoomNumber;
  }
}

export class DataReportRoom{
  id: string;
  roomNumber: string;
  price: string;
  attachedAdditions: string[];




  constructor(id: string, roomNumber: string, price: string, attachedAdditions: string[]) {
    this.id = id;
    this.roomNumber = roomNumber;
    this.price = price;
    this.attachedAdditions = attachedAdditions;
  }
}

export class DataReportAdditions{
  id: string;
  name: string;
  description: string;
  price: string;



  constructor(id: string, name: string,description: string, price: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }
}

