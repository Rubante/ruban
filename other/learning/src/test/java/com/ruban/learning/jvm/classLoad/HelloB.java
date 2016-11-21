package com.ruban.learning.jvm.classLoad;

public class HelloB extends HelloA {

    static {
        System.out.println("Hello B1!");
    }
    {
        System.out.println("Hello B2!");
    }

    public static void main(String[] args) {
        HelloB helloB = new HelloB();
    }
}
