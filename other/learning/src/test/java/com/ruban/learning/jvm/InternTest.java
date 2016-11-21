package com.ruban.learning.jvm;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * When the intern method is invoked, if the pool already contains a string
 * equal to this String object as determined by the equals(Object) method, then
 * the string from the pool is returned. Otherwise, this String object is added
 * to the pool and a reference to this String object is returned.
 * 
 * @author ruban
 *
 */
public class InternTest {

    @Test
    public void testIntern() {
        // sb.intern(),如果字符串常量池中存在该字符串【计算机】,则返回常量池中的那个，依据是equal方法，如果没有则添加到常量池中
        String str = new StringBuilder().append("计算").append("机器").toString();
        Assert.assertTrue(str.intern() == str);

        // 【计算机】经过上步操作，已经加入，因此intern返回的是常量池中的那个，跟StringBuilder的这个新建的不是一个
        String str2 = new StringBuilder().append("计算").append("机器").toString();
        Assert.assertFalse(str2.intern() == str2);

        // 该处校验，字符串是一个整体
        String str3 = new StringBuilder().append("计算").append("机").toString();
        Assert.assertTrue(str3.intern() == str3);
    }

    @Test
    public void testIntern2() {
        // 基本类型，类，接口的限定符，常量值，放在常量池中
        String str1 = new StringBuilder().append("ja").append("va").toString();
        Assert.assertFalse(str1.intern() == str1);

        // 该处模拟一些常见字符串，像方法的名称的描述已经在常量池中
        String str2 = new StringBuilder().append("test").append("Intern2").toString();
        Assert.assertFalse(str2.intern() == str2);

    }

}
