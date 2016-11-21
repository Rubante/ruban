package com.ruban.learning.jvm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//-Dcom.sun.management.jmxremote.port=9999
//-Dcom.sun.management.jmxremote.authenticate=false
//-Dcom.sun.management.jmxremote.ssl=false
//-XX:MaxPermSize=10m    最大永久代2m
public class PermSizeTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext a = new ClassPathXmlApplicationContext();
        
        File file = new File("");
        
        List<String> result = new ArrayList<String>();
        int i = 10000000;
        while (true) {
            i = i + 1;

            result.add(("abc"+i*i).intern());
            System.out.println(i*i);
        }
    }

}
