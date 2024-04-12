package org.dsi.todospringboot.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampConverter {
    public static String convertTimestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }
}
