package clientDataManager;

import connection.Request;
import models.Color;
import models.Product;
import models.*;

import java.time.*;
import java.util.Scanner;

public class FileInputManager extends InputManager {
    private Scanner input;

    public FileInputManager(String path) {
        input = new Scanner(new FileManager(path).read());
    }

    public Scanner getInput() {
        return input;
    }

    @Override
    public String readNameOfProduct() {
        return input.nextLine();
    }

    @Override
    public Integer readXCoordinate() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public Integer readYCoordinate() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public int readPrice() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public String readPartNumber() {
        return input.nextLine();
    }

    public float readManufactureCost() {
        try {
            return Float.parseFloat(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public UnitOfMeasure readUnitOfMeasure() {
        try {
            return UnitOfMeasure.valueOf(input.nextLine().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public String readOwnerName() {
        return input.nextLine();
    }

    @Override
    public ZonedDateTime readPersonBirthday() {
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
                throw new RuntimeException();
            }
        }
    }

    @Override
    public long readPersonHeight() {
        try {
            return Long.parseLong(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public Color readPersonHairColor() {
        try {
            return Color.valueOf(input.nextLine().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public long readLocationX() {
        try {
            return Long.parseLong(input.nextLine());
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public double readLocationY() {
        try {
            String y1 = input.nextLine();
            double y = Double.parseDouble(y1);
            return y;
        } catch (NumberFormatException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public String readLocationName() {
        String name;
        name = input.nextLine();
        return name;
    }

    public Coordinates readCoordinates() {
        return new Coordinates(readXCoordinate(), readYCoordinate());
    }

    public Location readPersonLocation() {
        return new Location(readLocationX(), readLocationY(), readLocationName());
    }

    public Person readPerson() {
        return new Person(readOwnerName(), readPersonBirthday(), readPersonHeight(), readPersonHairColor(), readPersonLocation());
    }

    public Product readProduct() {
        return new Product(readNameOfProduct(), readCoordinates(), readPrice(), readPartNumber(), readManufactureCost(), readUnitOfMeasure(), readPerson());
    }

    public Request readCommand() {
        String command = input.nextLine();
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new Request(command, argument, product);
    }
}
