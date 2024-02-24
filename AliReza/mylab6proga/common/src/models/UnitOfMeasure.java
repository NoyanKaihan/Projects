package models;

import java.io.Serializable;

public enum UnitOfMeasure implements Serializable {
    METERS(1),
    CENTIMETERS(2),
    SQUARE_METERS(3),
    LITERS(4),
    GRAMS(5);
    private final int value;
    UnitOfMeasure(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
