package clientDataManager;

import connection.Request;
import models.Color;
import models.Product;
import models.UnitOfMeasure;

import java.time.ZonedDateTime;

public abstract class InputManager {

    public Integer readXCoordinate;

    public  abstract String readNameOfProduct();

    public abstract Integer readXCoordinate();

    public abstract Integer readYCoordinate();

    public abstract int readPrice();

    public abstract String readPartNumber();

    public abstract UnitOfMeasure readUnitOfMeasure();

    public abstract String readOwnerName();

    public abstract ZonedDateTime readPersonBirthday();

    public abstract long readPersonHeight();

    public abstract Color readPersonHairColor();

    public abstract long readLocationX();

    public abstract double readLocationY();

    public abstract String readLocationName();
    public abstract Product readProduct();
    public abstract Request readCommand();
}
