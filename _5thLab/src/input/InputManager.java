package input;

import commands.CommandWrapper;
import console.ConsoleColor;
import data.*;
import exceptions.DataException;
import exceptions.NoSuchDataException;

import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Class for inputting
 */
public abstract class InputManager {
    private Scanner scanner;
    public InputManager(Scanner scanner){
        this.scanner = scanner;
        scanner.useDelimiter("\n");
    }
    public Scanner getScanner(){
        return scanner;
    }
    public String readName()throws DataException{
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Name can't be empty :("+ConsoleColor.RESET);
        }
        return s;
    }
    public Long readX ()throws DataException{
        long x;
        try{
            x = Long.parseLong(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid coordinate x :("+ConsoleColor.RESET);
        }
        if (Float.isInfinite(x) || Float.isNaN(x)) throw new DataException(ConsoleColor.RED_BACKGROUND+"invalid long value :("+ConsoleColor.RESET);
        return x;
    }
    public long readY()throws DataException{
        long y;
        try{
            y = Long.parseLong(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid coordinate y :("+ConsoleColor.RESET);
        }
        if (y>790) throw new DataException(ConsoleColor.RED_BACKGROUND+"must be less than 790 :("+ConsoleColor.RESET);
        return y;
    }
    public Coordinates readCoordinates()throws DataException{
        long x = readX();
        long y = readY();
        Coordinates coordinates = new Coordinates(x,y);
        return coordinates;
    }
    public ZonedDateTime readCreationDate(){
        return ZonedDateTime.now();
    }
    public long readStudentsCount()throws DataException{
        long studentCount;
        try{
            studentCount = Long.parseLong(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid students Count :("+ConsoleColor.RESET);
        }
        if (studentCount<0) throw new DataException(ConsoleColor.RED_BACKGROUND+"must be greater than 0 :("+ConsoleColor.RESET);
        return studentCount;
    }
    public long readExpelledStudents()throws DataException{
        long expelledStudents;
        try{
            expelledStudents = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException exception){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid expelled students :("+ConsoleColor.RESET);
        }if(expelledStudents<0)throw new DataException(ConsoleColor.RED_BACKGROUND+"must be greater than 0 :("+ConsoleColor.RESET);
        return expelledStudents;
    }
    public FormOfEducation readFormOfEducation ()throws DataException{
        String formOfEducation;
        formOfEducation = scanner.nextLine().trim();
        if(formOfEducation.equals("")){
            return null;
        }
        try {
            FormOfEducation.valueOf(formOfEducation);
            return FormOfEducation.valueOf(formOfEducation);
        }catch (IllegalArgumentException exception){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid form of education :("+ConsoleColor.RESET);
        }
    }
    public Semester readSemester()throws DataException{
        String semester = scanner.nextLine().trim();
        if(semester.equals("")){
            return null;
        }else {
            try {
                return Semester.valueOf(semester);
            } catch (IllegalArgumentException exception) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "Invalid semester :(" + ConsoleColor.RESET);
            }
        }
    }
    public String readAdminName()throws DataException{
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Name can't be empty :("+ConsoleColor.RESET);
        }
        return s;
    }
    public double readWeight ()throws NumberFormatException,DataException{
        double weight;
        try{
            weight = Double.valueOf(scanner.nextLine());
        }catch (NumberFormatException numberFormatException){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid weight :("+ConsoleColor.RESET);
        }if(weight<0)throw new DataException(ConsoleColor.RED_BACKGROUND+"must be greater than 0 :("+ConsoleColor.RESET);
        return weight;
    }
    public Color readHairColor()throws DataException{
        String hairColor;
        hairColor = scanner.nextLine().trim();
            try {
                return Color.valueOf(hairColor.toUpperCase());
            } catch (IllegalArgumentException exception) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "Invalid hair color :(" + ConsoleColor.RESET);
            }
    }
    public Country readCountry()throws DataException{
        String country= scanner.nextLine().trim();
        if(country.equals("")){
            return null;
        }else {
            try {
                return Country.valueOf(country.toUpperCase());
            } catch (IllegalArgumentException exception) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "Invalid country :(" + ConsoleColor.RESET);
            }
        }
    }
    public Double readLY ()throws DataException{
        double y;
        try{
            y = Double.valueOf(scanner.nextLine());
        }catch (NumberFormatException exception){
            throw new NoSuchDataException(ConsoleColor.RED_BACKGROUND+"Invalid location y :("+ConsoleColor.RESET);
        }
        return y;
    }
    public long readLX ()throws DataException{
        long x =0;
        try{
            x = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException exception){
            throw new DataException(ConsoleColor.RED_BACKGROUND+"Invalid location x :("+ConsoleColor.RESET);
        }
        return x;
    }
    public String readLName(){
        String s = scanner.nextLine().trim();
        return s;
    }

    public Location readLocation()throws DataException{
        double y = readLY();
        long x = readLX();
        String name;
        name = readLName();
        Location location = new Location(x,y,name);
        return location;
    }
    public Person readPerson()throws DataException{
        String name;
        double weight;
        Color hairColor;
        Country nationality;
        Location location;
            name = readAdminName();
            weight= readWeight();
            hairColor = readHairColor();
            nationality = readCountry();
            location = readLocation();
        Person person = new Person(name,weight,hairColor,nationality,location);
        return person;
    }
    public StudyGroup readStudyGroup()throws DataException{
        String name = readName();
        Coordinates coordinates = readCoordinates();
        long studentsCount = readStudentsCount();
        long expelledStudents = readExpelledStudents();
        FormOfEducation formOfEducation = readFormOfEducation();
        Semester semester = readSemester();
        Person person = readPerson();
        StudyGroup studyGroup = new StudyGroup(name,coordinates,readCreationDate(),
                studentsCount,expelledStudents,formOfEducation,semester,person);
        return studyGroup;
    }
    public CommandWrapper readCommand(){
        String cmd = scanner.nextLine();
        if (cmd.contains(" ")){ //if command has argument
            String arr [] = cmd.split(" ",2);
            cmd = arr[0];
            String arg = arr[1];
            return new CommandWrapper(cmd,arg);
        } else {
            return new CommandWrapper(cmd);
        }
    }
}
