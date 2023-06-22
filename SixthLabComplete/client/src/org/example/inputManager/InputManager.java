package org.example.inputManager;

import org.example.connection.Request;
import org.example.connection.RequestMessage;
import org.example.console.ConsoleColor;
import org.example.exceptions.EmptyStringException;
import org.example.exceptions.InvalidDataException;
import org.example.exceptions.InvalidNumberException;
import org.example.modules.*;

import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Class for inputting
 */
public abstract class InputManager {
    private Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
        scanner.useDelimiter("\n");
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String readName() throws InvalidDataException {
        String s = scanner.nextLine().trim();
        if (s.equals("")) {
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Name can't be empty :(" + ConsoleColor.RESET);
        }
        return s;
    }

    public Long readX() throws InvalidDataException {
        long x;
        try {
            x = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid coordinate x :(" + ConsoleColor.RESET);
        }
        if (Float.isInfinite(x) || Float.isNaN(x))
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "invalid long value :(" + ConsoleColor.RESET);
        return x;
    }

    public long readY() throws InvalidNumberException {
        long y;
        try {
            y = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "Invalid coordinate y :(" + ConsoleColor.RESET);
        }
        if (y > 790)
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "must be less than 790 :(" + ConsoleColor.RESET);
        return y;
    }

    public Coordinates readCoordinates() throws InvalidDataException {
        long x = readX();
        long y = readY();
        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    public ZonedDateTime readCreationDate() {
        return ZonedDateTime.now();
    }

    public long readStudentsCount() throws InvalidNumberException {
        long studentCount;
        try {
            studentCount = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "Invalid students Count :(" + ConsoleColor.RESET);
        }
        if (studentCount < 0)
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "must be greater than 0 :(" + ConsoleColor.RESET);
        return studentCount;
    }

    public long readExpelledStudents() throws InvalidDataException {
        long expelledStudents;
        try {
            expelledStudents = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "Invalid expelled students :(" + ConsoleColor.RESET);
        }
        if (expelledStudents < 0)
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "must be greater than 0 :(" + ConsoleColor.RESET);
        return expelledStudents;
    }

    public FormOfEducation readFormOfEducation() throws InvalidDataException {
        String formOfEducation;
        formOfEducation = scanner.nextLine().trim();
        if (formOfEducation.equals("")) {
            return null;
        }
        try {
            FormOfEducation.valueOf(formOfEducation);
            return FormOfEducation.valueOf(formOfEducation);
        } catch (IllegalArgumentException exception) {
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid form of education :(" + ConsoleColor.RESET);
        }
    }

    public Semester readSemester() throws InvalidDataException {
        String semester = scanner.nextLine().trim();
        if (semester.equals("")) {
            return null;
        } else {
            try {
                return Semester.valueOf(semester);
            } catch (IllegalArgumentException exception) {
                throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid semester :(" + ConsoleColor.RESET);
            }
        }
    }

    public String readAdminName() throws EmptyStringException {
        String s = scanner.nextLine().trim();
        if (s.equals("")) {
            throw new EmptyStringException(ConsoleColor.RED_BACKGROUND + "Name can't be empty :(" + ConsoleColor.RESET);
        }
        return s;
    }

    public double readWeight() throws NumberFormatException, InvalidDataException {
        double weight;
        try {
            weight = Double.valueOf(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid weight :(" + ConsoleColor.RESET);
        }
        if (weight < 0)
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "must be greater than 0 :(" + ConsoleColor.RESET);
        return weight;
    }

    public Color readHairColor() throws InvalidDataException {
        String hairColor;
        hairColor = scanner.nextLine().trim();
        try {
            return Color.valueOf(hairColor.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid hair color :(" + ConsoleColor.RESET);
        }
    }

    public Country readCountry() throws InvalidDataException {
        String country = scanner.nextLine().trim();
        if (country.equals("")) {
            return null;
        } else {
            try {
                return Country.valueOf(country.toUpperCase());
            } catch (IllegalArgumentException exception) {
                throw new InvalidDataException(ConsoleColor.RED_BACKGROUND + "Invalid country :(" + ConsoleColor.RESET);
            }
        }
    }

    public Double readLY() throws InvalidDataException {
        double y;
        try {
            y = Double.valueOf(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "Invalid location y :(" + ConsoleColor.RESET);
        }
        return y;
    }

    public long readLX() throws InvalidNumberException {
        long x = 0;
        try {
            x = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new InvalidNumberException(ConsoleColor.RED_BACKGROUND + "Invalid location x :(" + ConsoleColor.RESET);
        }
        return x;
    }

    public String readLName() {
        String s = scanner.nextLine().trim();
        return s;
    }

    public Location readLocation() throws InvalidDataException {
        double y = readLY();
        long x = readLX();
        String name;
        name = readLName();
        Location location = new Location(x, y, name);
        return location;
    }

    public Person readPerson() throws InvalidDataException {
        String name;
        double weight;
        Color hairColor;
        Country nationality;
        Location location;
        name = readAdminName();
        weight = readWeight();
        hairColor = readHairColor();
        nationality = readCountry();
        location = readLocation();
        Person person = new Person(name, weight, hairColor, nationality, location);
        return person;
    }

    public StudyGroup readStudyGroup() throws InvalidDataException {
        String name = readName();
        Coordinates coordinates = readCoordinates();
        long studentsCount = readStudentsCount();
        long expelledStudents = readExpelledStudents();
        FormOfEducation formOfEducation = readFormOfEducation();
        Semester semester = readSemester();
        Person person = readPerson();
        StudyGroup studyGroup = new StudyGroup(name, coordinates, readCreationDate(),
                studentsCount, expelledStudents, formOfEducation, semester, person);
        return studyGroup;
    }

    public Request readCommand(){
        String command = scanner.nextLine();
        String argument = null;
        StudyGroup studyGroup = null;
        if (command.contains(" ")) { //if command has argument
            String[] arr = command.split(" ", 2);
            command = arr[0];
            argument = arr[1];
        }
        if (command.equals("add") || command.equals("remove_greater") || command.equals("add_if_max") || command.equals("update")) {
            try {
                studyGroup = readStudyGroup();
            } catch (InvalidDataException e) {
                System.out.println(e.getMessage());
            }
        }
        return new RequestMessage(command,argument,studyGroup);
    }
}
