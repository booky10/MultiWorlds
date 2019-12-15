package tk.t11e.util;
// Created by booky10 in MultiWorlds (16:22 15.12.19)

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getStringTime() {
        return getStringTime(System.currentTimeMillis());
    }

    public static String getStringTime(long time) {
        return new SimpleDateFormat("HH:mm:ss+dd-MM-yyyy").format(new Date(time));
    }
}