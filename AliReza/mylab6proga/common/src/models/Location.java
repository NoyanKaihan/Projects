package models;

import java.io.Serializable;

public class Location implements Serializable {
    private long x;
    private double y;
    private String name; //Поле может быть null
    public Location(){}
    public Location(long x,double y,String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\tx : " + x +
                "\n\t\ty : " + y +
                "\n\t\tname : " + name +
                "\n\t}";
    }
}
