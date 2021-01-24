package ua.com.Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static boolean compareDatesToTheDay(final Date firstDate, final Date secondDate) {
        if (firstDate.getYear() != secondDate.getYear()) {
            return false;
        }
        if (firstDate.getMonth() != secondDate.getMonth()) {
            return false;
        }
        if (firstDate.getDay() != secondDate.getDay()) {
            return false;
        }
        return true;
    }

    public static Date convertLocalDateToDate(final LocalDate localDate)
    {
        return  Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertDateToLocalDate(final Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date findYesterdayDate()
    {
        Date date = new Date();
        LocalDate localDate = DateUtil.convertDateToLocalDate(date);
        localDate = localDate.minusDays(1);
        return DateUtil.convertLocalDateToDate(localDate);
    }
    public static Date findTomorrowDate()
    {
        Date date = new Date();
        LocalDate localDate = DateUtil.convertDateToLocalDate(date);
        localDate = localDate.plusDays(1);
        return DateUtil.convertLocalDateToDate(localDate);
    }


    public static String toStringDate(final Date date) {
        return date.toString().substring(0, 10);
    }



}
