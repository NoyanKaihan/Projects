package models;

import java.beans.Transient;
import java.io.Serializable;

public class Coordinates implements Serializable{

    private  Integer x; //Поле не может быть null
    private  Integer y; //Значение поля должно быть больше -802, Поле не может быть null
    public Coordinates(){}
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    /**
     * Coordinate display
     */
    @Override
    public String toString() {
        return "coordinates : {" +
                "\n\tx : " + x +
                "\n\ty : " + y +
                "\n}";
    }

    public boolean validate() {
        return x != 0 && y != 0 && y > -802;
    }
}
