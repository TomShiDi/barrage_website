package barrage.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static String toChinaNormal(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:MM:ss");
        return simpleDateFormat.format(date);
    }
}
