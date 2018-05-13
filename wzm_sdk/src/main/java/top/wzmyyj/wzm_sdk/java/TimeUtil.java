package top.wzmyyj.wzm_sdk.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wzm on 2018/4/26 0026.
 */

public class TimeUtil {


    public static String changeToString(long l, String format) {
        if (l == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(new Date(l));
        return s;
    }

    public static long changeToLong(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long l = 0;
        try {
            l = sdf.parse(time).getTime();
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return l;
    }


    public static String getEasyTime(long l) {
        Date d = new Date();
        String s;
        //一天内
        if (Math.abs(d.getTime() - l) < 24 * 60 * 60 * 1000) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            s = sdf.format(new Date(l));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            s = sdf.format(new Date(l));
        }
        return s;
    }
}
