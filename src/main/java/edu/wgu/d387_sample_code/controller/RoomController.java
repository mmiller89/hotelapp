package edu.wgu.d387_sample_code.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @DeleteMapping()
    public String deleteRoomById(@PathVariable("id") Long roomId){
        return "Deleted.";
    }

}
