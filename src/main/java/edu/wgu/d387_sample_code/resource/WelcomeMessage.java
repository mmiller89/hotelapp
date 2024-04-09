package edu.wgu.d387_sample_code.resource;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WelcomeMessage {
    Properties properties=new Properties();
    private String language;

    public WelcomeMessage(){}

    public WelcomeMessage(String language){
        this.language = language;
    }

    //en or fr
    public String getWelcomeMessage() throws IOException {

        InputStream stream = new ClassPathResource("translation_" + language + "_CA.properties").getInputStream();
        properties.load(stream);
        return properties.getProperty("welcome");
    }
}
