package com.ruban.framework.core.utils.format;

import org.junit.Assert;
import org.junit.Test;

public class FormatUtilTest {

    @Test
    public void format() {
        String result = FormatUtil.fmtPercent("0.75");
        Assert.assertEquals("format error ", "75.00%", result);
        System.out.println(result);
    }
    
    @Test
    public void percent(){
        String result = FormatUtil.fmtMicrometer("11111111.2222");
        System.out.println(result);
        
        result = FormatUtil.fmtMicrometerByScale(88888666.222222222, 2);
        System.out.println(result);
    }
}
