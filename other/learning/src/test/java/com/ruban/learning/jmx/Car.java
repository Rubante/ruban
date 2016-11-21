package com.ruban.learning.jmx;

public class Car implements CarMBean {

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void drive(){
        System.out.println("your car color : " + color);
    }
}
