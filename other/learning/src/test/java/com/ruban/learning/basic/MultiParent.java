package com.ruban.learning.basic;

import org.junit.Test;

public class MultiParent {

    @Test
    public void testFatherAndSun() {
        Father sun = new Son();

        if (sun instanceof Father) {
            System.out.println("i'm father");
        }

        if (sun instanceof Son) {
            System.out.println("i'm son");
        }
        
        sun.sayHello();
        
        sun.say(sun);
    }
}

class Father {
    public void sayHello() {
        System.out.println("Father hello!");
    }
    
    public void say(Father father){
        System.out.println("Say Father!");
    }
    
    public void say(Son son){
        System.out.println("Say Son");
    }
}

class Son extends Father {

    @Override
    public void sayHello() {
        System.out.println("Son hello!");
    }
}