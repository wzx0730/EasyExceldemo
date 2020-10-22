package com.wzx.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    private static DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar cld = Calendar.getInstance();
    private static String today;
    private static String yesterday;


    static {
        today = dft.format(cld.getTime());
        cld.add(Calendar.DAY_OF_MONTH,-1 );
        yesterday =dft.format(cld.getTime());
    }

    public static String getCurrentTime() {
        return today;
    }

    public static String getYesterDayTime() {
        return yesterday;
    }


}
