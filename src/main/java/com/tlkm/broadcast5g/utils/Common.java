package com.tlkm.broadcast5g.utils;

import java.util.Calendar;
import java.util.Date;

public class Common {

    public static int FAILED = 2;
    public static int SUCCESS = 1;
    public static int QUEUE = 0;


    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


}
