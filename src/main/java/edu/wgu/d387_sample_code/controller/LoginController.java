package edu.wgu.d387_sample_code.controller;
import edu.wgu.d387_sample_code.entity.AdditionEntity;
import edu.wgu.d387_sample_code.entity.ReservationEntity;
import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.model.response.ReservationResponse;
import edu.wgu.d387_sample_code.repository.AdditionRepository;
import edu.wgu.d387_sample_code.repository.ReservationRepository;
import edu.wgu.d387_sample_code.repository.RoomRepository;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;
    private Login loginAttempt;

    @Autowired
    ConversionService conversionService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    AdditionRepository additionRepository;

    @RequestMapping(value = "/credentials")
    public UsersEntity validateLogin(@RequestBody Login login) {
        loginAttempt = login;

        Iterable<UsersEntity> itr = usersRepository.findAll();

        for (UsersEntity usr : itr) {
            if (loginAttempt.getUserName().equals(usr.getUserName()) && loginAttempt.getPassword().equals(usr.getPassword())) {
                return usr;
            }
        }

        return null;
    }

    @RequestMapping(value = "/getallusers")
    public List<UsersEntity> getAllUsers() {


        Iterable<UsersEntity> itr = usersRepository.findAll();
        List<UsersEntity> usrs = new ArrayList<>();

        for (UsersEntity usr : itr) {
            usrs.add(usr);
        }

        return usrs;
    }

    @RequestMapping(value = "/getallreservations")
    public List<ReservationEntity> getAllReserves() {


        Iterable<ReservationEntity> itr = reservationRepository.findAll();
        List<ReservationEntity> reserves = new ArrayList<>();

        for (ReservationEntity rsv : itr) {
            reserves.add(rsv);
        }

        return reserves;
    }

    @RequestMapping(value = "/getallrooms")
    public List<RoomEntity> getAllRooms() {


        Iterable<RoomEntity> itr = roomRepository.findAll();
        List<RoomEntity> roomz = new ArrayList<>();

        for (RoomEntity room : itr) {
            roomz.add(room);
        }

        return roomz;
    }

    @RequestMapping(value = "/getalladditions")
    public List<AdditionEntity> getAllAdditions() {


        Iterable<AdditionEntity> itr = additionRepository.findAll();
        List<AdditionEntity> adds = new ArrayList<>();

        for (AdditionEntity add : itr) {
            adds.add(add);
        }
        return adds;
    }

    @RequestMapping(value = "/save")
    public ResponseEntity<UsersEntity> update(@RequestBody User user){



        int rewards = Integer.parseInt(user.getRewards());
        Optional<UsersEntity> usearch = usersRepository.findById(user.getId());
        UsersEntity usersEntity = null;


        if (usearch.isPresent()){
            usersEntity = usearch.get();
            usersEntity.setRewardsPoints(rewards);
            usersRepository.save(usersEntity);
        } else {
            return null;
        }

        return new ResponseEntity<>(usersEntity, HttpStatus.CREATED);

    }


    public static class Login {
        private String userName;
        private String password;

        public Login(){}

        public Login(String userName, String password){
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class User{

        private Long id;
        private String userName;
        private String rewards;

        public User(){}

        public User(Long id, String userName, String rewards){
            this.id = id;
            this.userName = userName;
            this.rewards = rewards;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}


