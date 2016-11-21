package com.ruban.learning.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * 整数大小
 * 
 * @author ruban
 *
 */
public class IntegerTest {

    @Test
    public void equalsInt() {
        
        Integer i1 = 13;
        Integer i2 = 13;
        Assert.assertTrue(i1 == i2);
        
        Integer i3 = 1300;
        Integer i4 = 1300;
        Assert.assertFalse(i3 == i4);
    }
}
