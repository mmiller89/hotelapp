package edu.wgu.d387_sample_code.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeCoversion {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("h:mm a");
    LocalDateTime localDateTime= LocalDate.now().atTime(11, 0);
    ZoneId zoneId=ZoneId.systemDefault();
    ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

    public String[] convertTimeZone(ZoneId timeZone, String display){
        ZonedDateTime zonedDateTimeArea = zonedDateTime.withZoneSameInstant(timeZone);
        LocalDateTime localDateTimeArea = zonedDateTimeArea.toLocalDateTime();
        String[] timeArr = new String[2];
        timeArr[1] = display;
        timeArr[0] = localDateTimeArea.format(dateFormat);
        return timeArr;
    }


}
