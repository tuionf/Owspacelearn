package com.example.tuionf.owspacelearn.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class TimeUtil {
    public static long getCurrentSeconds(){
        long ls = System.currentTimeMillis()/1000;
        return ls;
    }

    public static String getDate(String formate){
        String str = new SimpleDateFormat(formate, Locale.ENGLISH).format(new Date());
        return str;
    }

    public static String[] getCalendarShowTime(String paramString)
    {
        try {
            long l = Long.valueOf(paramString);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(1000L * l);
            return getCalendarShowTime(localCalendar.getTimeInMillis());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getCalendarShowTime(long paramLong)
    {
        String[] localObject;
        String str = new SimpleDateFormat("yyyy:MMM:d", Locale.ENGLISH).format(new Date(paramLong));
        try
        {
            String[] arrayOfString = str.split(":");
            localObject = arrayOfString;
            if ((localObject != null) && (localObject.length == 3));
            return localObject;
        }
        catch (Exception localException)
        {
            while (true)
                localException.printStackTrace();
        }
    }
}
