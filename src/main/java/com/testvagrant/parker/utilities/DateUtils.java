package com.testvagrant.parker.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String currentTimeShort()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
       return sdf.format(new Date().getTime());
    }
}
