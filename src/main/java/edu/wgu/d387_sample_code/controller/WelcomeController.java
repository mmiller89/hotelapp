package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.resource.WelcomeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.concurrent.*;


@CrossOrigin
@RestController
@RequestMapping("/resources")
public class WelcomeController {
    private ExecutorService welcomeExe = Executors.newFixedThreadPool(2);


    @RequestMapping("/greetings")
    public ResponseEntity<ArrayList<String>> returnWelcomeMessage() throws InterruptedException {

        ArrayList<String> languageDisplay = new ArrayList<>();
        Thread threadEn = new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                    WelcomeMessage english = new WelcomeMessage("en");
                    languageDisplay.add(english.getWelcomeMessage());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        Thread threadFr= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WelcomeMessage french = new WelcomeMessage("fr");
                    languageDisplay.add(french.getWelcomeMessage());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        threadEn.start();
        threadFr.start();

        try{
            threadEn.join();
            threadFr.join();
        } catch (Exception e){
            System.out.println("Error occured");
        }
        return ResponseEntity.ok(languageDisplay);

    }

}
