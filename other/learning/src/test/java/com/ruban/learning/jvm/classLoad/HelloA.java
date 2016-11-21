package com.ruban.learning.jvm.classLoad;

public class HelloA {

    static {
        System.out.println("Hello A1!");
    }
    {
        System.out.println("Hello A2!");
    }
}
