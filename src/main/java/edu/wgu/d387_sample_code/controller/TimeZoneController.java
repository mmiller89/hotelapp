package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.resource.TimeCoversion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@CrossOrigin
@RestController
@RequestMapping("/timezone")
public class TimeZoneController {

    ZoneId zEastern = (ZoneId.of("America/New_York"));
    ZoneId zMountain = (ZoneId.of("America/Denver"));
    ZoneId zUTC = (ZoneId.of("UTC"));



    @RequestMapping("/utc")
    public String[] returnUTC() throws InterruptedException {
        TimeCoversion utc = new TimeCoversion();
        return utc.convertTimeZone(zUTC, "UTC Time");

    }

    @RequestMapping("/mountain")
    public String[] returnMountain() throws InterruptedException {
        TimeCoversion mountain = new TimeCoversion();
        return mountain.convertTimeZone(zMountain, "Mountain Time");

    }

    @RequestMapping("/eastern")
    public String[] returnEastern() throws InterruptedException {
        TimeCoversion eastern = new TimeCoversion();
        return eastern.convertTimeZone(zEastern, "Eastern Time");

    }

}
