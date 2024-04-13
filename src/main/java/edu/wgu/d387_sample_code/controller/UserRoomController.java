package edu.wgu.d387_sample_code.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRoomController {

    @RequestMapping("/display")
    public List<String> displayBookedRooms(){
        List<String> list = new ArrayList<>();

        return list;
    }
}
