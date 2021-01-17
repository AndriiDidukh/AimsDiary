package ua.com.Utils;

import java.util.Date;

public class DateUtil {

    public static boolean compareDateToCurrentDateToTheDay(final Date date) {
        Date currentDate = new Date();
        if (currentDate.getYear() != date.getYear()) {
            return false;
        }
        if (currentDate.getMonth() != date.getMonth()) {
            return false;
        }
        if (currentDate.getDay() != date.getDay()) {
            return false;
        }
        return true;
    }
}
