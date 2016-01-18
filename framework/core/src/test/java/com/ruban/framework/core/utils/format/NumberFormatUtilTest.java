package com.ruban.framework.core.utils.format;

import org.junit.Assert;
import org.junit.Test;

public class NumberFormatUtilTest {

    @Test
    public void format() {
        String result = PercentUtil.fmtPercent("0.75");
        Assert.assertEquals("format error ", "75.00%", result);
        System.out.println(result);
    }
}
