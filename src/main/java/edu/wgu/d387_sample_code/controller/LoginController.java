package edu.wgu.d387_sample_code.controller;


import com.mysql.cj.log.Log;
import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;
    private Login loginAttempt;

    @RequestMapping(value = "/credentials")
    public UsersEntity validateLogin(@RequestBody Login login) {
        loginAttempt = login;

        Iterable<UsersEntity> itr = usersRepository.findAll();

        for (UsersEntity usr : itr) {
            if (loginAttempt.getUsername().equals(usr.getUserName()) && loginAttempt.getPassword().equals(usr.getPassword())) {
                return usr;
            }
        }

        return null;
    }

    @RequestMapping("/save")
    public String updateRewards(@RequestBody UsersEntity user){
        return "test test";
    }


    public static class Login {
        private String username;
        private String password;

        public Login(){}

        public Login(String username, String password){
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}


