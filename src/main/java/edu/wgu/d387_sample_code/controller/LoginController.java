package edu.wgu.d387_sample_code.controller;
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
            if (loginAttempt.getUserName().equals(usr.getUserName()) && loginAttempt.getPassword().equals(usr.getPassword())) {
                return usr;
            }
        }

        return null;
    }

    @RequestMapping(value = "/save")
    public UsersEntity updateRewards(@RequestBody User user){

        int rewards = Integer.parseInt(user.getRewards());

        Iterable<UsersEntity> itr = usersRepository.findAll();

        for (UsersEntity usr : itr) {
            if (usr.getUserName().equals(user.getUserName())){
                usr.setRewardsPoints(rewards);
                usersRepository.save(usr);
                return usr;
            }
        } return null;
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

        private String userName;
        private String rewards;

        public User(){}

        public User(String userName, String rewards){
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
    }
}


