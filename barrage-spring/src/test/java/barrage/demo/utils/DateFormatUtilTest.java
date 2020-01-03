package barrage.demo.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateFormatUtilTest {

    @Test
    public void toChinaNormal() {
        System.out.println(DateFormatUtil.toChinaNormal(new Date()));
    }
}