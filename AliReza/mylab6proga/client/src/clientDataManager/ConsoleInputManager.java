package clientDataManager;


import connection.Request;
import exceptions.InvalidDataException;
import managers.CollectionManager;
import models.*;

import java.time.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInputManager extends InputManager {
    private final Scanner input;
    private final CollectionManager collectionManager;

    public ConsoleInputManager() {
        collectionManager = null;
        input = new Scanner(System.in);
    }

    public ConsoleInputManager(CollectionManager collectionManager) {
        input = new Scanner(System.in);
        this.collectionManager = collectionManager;
    }

    @Override
    public String readNameOfProduct() {
        String name;
        for (; ; ) {
            System.out.println("Enter name of the product (should not be null) : ");
            name = input.nextLine();
            if (!name.equals("")) break;
            else System.err.println("Name can't be empty");
        }
        return name;
    }

    @Override
    public Integer readXCoordinate() {
        String x1;
        int x;
        for (; ; ) {
            System.out.println("Enter coordinate x (Should not be null ): ");
            x1 = input.nextLine();
            if (!x1.equals("")) {
                try {
                    x = Integer.parseInt(x1);
                    break;
                } catch (NumberFormatException exception) {
                    System.err.println("should be a number !!!");
                }
            } else System.err.println("can't be empty !!!");
        }
        return x;
    }

    @Override
    public Integer readYCoordinate() {
        String y1;
        int y;
        for (; ; ) {
            System.out.println("Enter coordinate y (Must be greater than -802 and can't be null ): ");
            y1 = input.nextLine();
            if (!y1.equals("")) {
                try {
                    y = Integer.parseInt(y1);
                    if (-802 > y) {
                        System.err.println("should be greater than -802");
                        continue;
                    }
                    break;
                } catch (NumberFormatException exception) {
                    System.err.println("should be a number !!!");
                }
            } else System.err.println("can't be empty !!!");
        }
        return y;
    }

    @Override
    public int readPrice() {
        String number;
        int price = 0;
        for (; ; ) {
            System.out.println("Enter price of the product in DOLLARS $ (should be greater than 0) : ");
            number = input.nextLine();
            try {
                price = Integer.parseInt(number);
                if (price <= 0) {
                    System.err.println("should be greater than 0 !!!");
                } else break;
            } catch (NumberFormatException exception) {
                System.err.println("should be a number !!!");
            }
        }
        return price;
    }

    @Override
    public String readPartNumber() {
        String partNumber;
        for (; ; ) {
            System.out.println("Enter a String as partNumber of the product: ");
            partNumber = input.nextLine();
            if (!partNumber.equals("")) break;
            else System.err.println("can't be empty!!!");
        }
        return partNumber;
    }

    public float readManufactureCost() {
        String number;
        float cost = 0.0f;
        for (; ; ) {
            System.out.println("Enter a Float as a manufactureCost in DOLLARS $ : ");
            number = input.nextLine();
            try {
                cost = Float.parseFloat(number);
                return cost;
            } catch (NumberFormatException exception) {
                System.err.println("should be a number !!!");
            }
        }
    }

    @Override
    public UnitOfMeasure readUnitOfMeasure() {
        UnitOfMeasure unitOfMeasure = null;
        String unit;
        for (; ; ) {
            try {
                System.out.println("Enter unit of Measure (METERS, CENTIMETERS, SQUARE_METERS, LITERS, GRAMS) : ");
                unit = input.nextLine();
                if (unit.equals("")) return null;
                if (!unit.equalsIgnoreCase("METERS") && !unit.equalsIgnoreCase("CENTIMETERS") && !unit.equalsIgnoreCase("SQUARE_METERS") && !unit.equalsIgnoreCase("LITERS") && !unit.equalsIgnoreCase("GRAMS"))
                    throw new IllegalArgumentException("Invalid Input, try again !!!");
                else {
                    unitOfMeasure = UnitOfMeasure.valueOf(unit.toUpperCase());
                    return unitOfMeasure;
                }
            } catch (IllegalArgumentException exception) {
                System.err.println(exception.getMessage());
            }
        }

    }

    @Override
    public String readOwnerName() {
        String name;
        for (; ; ) {
            System.out.println("Enter name of owner (should not be null) : ");
            name = input.nextLine();
            if (!name.equals("")) break;
            else System.err.println("Name can't be empty");
        }
        return name;
    }
@Override
    public ZonedDateTime readPersonBirthday() {
        for (; ; ) {
            System.out.println("Enter owner's birthday in format YYYY-MM-DD (can be empty) : ");
            String birthday = input.nextLine();
            if (birthday.equals("")) return null;
            else {
                String[] spilit = birthday.split("-");
                try {
                    int year = Integer.parseInt(spilit[0]);
                    int month = Integer.parseInt(spilit[1]);
                    int day = Integer.parseInt(spilit[2]);
                    LocalDate localDate = LocalDate.of(year, month, day);
                    LocalTime localTime = LocalTime.now();
                    ZoneId zoneId = ZoneId.systemDefault();
                    return ZonedDateTime.of(LocalDateTime.of(localDate, localTime), zoneId);
                } catch (NumberFormatException | IndexOutOfBoundsException | DateTimeException exception) {
                    System.err.println("Invalid BirthDay!!!");
                }

            }
        }
    }
@Override
    public long readPersonHeight() {
        String number;
        long height;
        for (; ; ) {
            System.out.println("Enter owner's height into cm: ");
            number = input.nextLine();
            if (number.equals("")) {
                System.err.println("can't be empty");
                continue;
            }
            try {
                height = Long.parseLong(number);
                if (height <= 0) System.err.println("should be greater than 0 ");
                else return height;
            } catch (NumberFormatException exception) {
                System.err.println("should be number ");
            }
        }
    }
@Override
    public Color readPersonHairColor() {
        String hairColor;
        for (; ; ) {
            System.out.println("Enter owner's hair Color (GREEN, RED, BLACK, ORANGE, BROWN) : ");
            hairColor = input.nextLine();
            if (hairColor.equals("")) return null;
            else {
                try {
                    if (!hairColor.equalsIgnoreCase("green") && !hairColor.equalsIgnoreCase("red") && !hairColor.equalsIgnoreCase("black") && !hairColor.equalsIgnoreCase("orange") && !hairColor.equalsIgnoreCase("brown")) {
                        System.err.println("Invalid hair color !!!");
                    } else {
                        return Color.valueOf(hairColor.toUpperCase());
                    }
                } catch (IllegalArgumentException exception) {
                    System.err.println("Invalid hair color !!!");
                }
            }
        }
    }
@Override
    public long readLocationX() {
        for (; ; ) {
            System.out.println("Enter owner's location x : ");
            String number = input.nextLine();
            if (number.equals("")) {
                System.err.println("can't be empty");
                continue;
            }
            try {
                return Long.parseLong(number);
            } catch (NumberFormatException exception) {
                System.err.println("Invalid location x !!!");
            }
        }
    }
@Override
    public double readLocationY() {
        for (; ; ) {
            System.out.println("Enter owner's location y : ");
            String number = input.nextLine();
            if (number.equals("")) {
                System.err.println("can't be empty");
                continue;
            }
            try {
                return Double.parseDouble(number);
            } catch (NumberFormatException exception) {
                System.err.println("Invalid location y !!!");
            }
        }
    }
@Override
    public String readLocationName() {
        System.out.println("Enter owner's location name (can be empty): ");
        String locationName = input.nextLine();
        if (locationName.equals("")) return null;
        else {
            return locationName;
        }
    }

    public Coordinates readCoordinates() {
        return new Coordinates(readXCoordinate(), readYCoordinate());
    }

    public Location readPersonLocation() {
        return new Location(readLocationX(), readLocationY(), readLocationName());
    }

    public Person readPerson() {
        for (; ; ) {
            System.out.println("Do you want add an owner? (\"yes\" or \"no\")");
            String answer = input.nextLine();
            if (answer.equals("yes"))
                return new Person(readOwnerName(), readPersonBirthday(), readPersonHeight(), readPersonHairColor(), readPersonLocation());
            else if (answer.equals("no")) return null;
            else System.err.println("answer is not valid, try again !!!\n");
        }
    }

    public Product readProduct() {
        Product p =new Product(readNameOfProduct(), readCoordinates(), readPrice(), readPartNumber(), readManufactureCost(), readUnitOfMeasure(), readPerson());
        System.out.println("\t\t\t\tproduct = " + p+"\n\n");
        return p;
    }


    public Request readCommand() {
        String command = new Scanner(System.in).nextLine();
        String argument = null;
        Product product = null;
        if (command.contains(" ")) { //if command has argument
            String[] arr = command.split("\s", 2);
            command = arr[0];
            argument = arr[1].trim();
        }
        if (command.equals("insert") || command.equals("replace_if_lowe") || command.equals("update")) {
            try {
                product = readProduct();
                System.out.println("\nInputted Product:");
                System.out.println(product.toStringWitOutID());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new Request(command, argument, product);
    }
}
