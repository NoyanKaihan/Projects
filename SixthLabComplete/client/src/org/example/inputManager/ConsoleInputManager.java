package org.example.inputManager;

import org.example.doubleColon.Question;
import org.example.exceptions.EmptyStringException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.*;

import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Class for inputting from Console
 */
public class ConsoleInputManager extends InputManager {
    public ConsoleInputManager() {
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
    }

    @Override
    public String readName() throws InvalidDataException {
        return new Question<String>("Enter name :"
                , super::readName).getAnswer();
    }

    @Override
    public Long readX() {
        return new Question<Long>("Enter x : "
                , super::readX).getAnswer();
    }

    @Override
    public long readY() {
        return new Question<Long>("Enter y : "
                , super::readY).getAnswer();
    }

    @Override
    public Coordinates readCoordinates() {
        return new Question<Coordinates>("Enter coordinates : "
                , super::readCoordinates).getAnswer();
    }
    @Override
    public ZonedDateTime readCreationDate() {
        return new Question<ZonedDateTime>("Time has already been fixed "
                , super::readCreationDate).getAnswer();
    }

    @Override
    public long readStudentsCount() {
        return new Question<Long>("Enter Student s count : "
                , super::readStudentsCount).getAnswer();
    }

    @Override
    public long readExpelledStudents() {
        return new Question<Long>("Enter Expelled Students : "
                , super::readExpelledStudents).getAnswer();
    }

    @Override
    public FormOfEducation readFormOfEducation() throws InvalidDataException {
        return new Question<FormOfEducation>("Enter form of education(DISTANCE_EDUCATION , FULL_TIME_EDUCATION, EVENING_CLASSES)"
                , super::readFormOfEducation).getAnswer();
    }

    @Override
    public Semester readSemester() {
        return new Question<Semester>("Enter semester (FIRST, THIRD, FOURTH, SIXTH): "
                , super::readSemester).getAnswer();
    }

    @Override
    public String readAdminName() throws EmptyStringException {
        return new Question<String>("Enter name of Group admin : "
                , super::readAdminName).getAnswer();
    }

    @Override
    public double readWeight() {
        return new Question<Double>("Enter group admin 's weight : "
                , super::readWeight).getAnswer();
    }

    @Override
    public Color readHairColor() {
        return new Question<Color>("Enter hair color of group Admin  (GREEN, RED, BLACK, WHITE) : "
                , super::readHairColor).getAnswer();
    }

    @Override
    public Country readCountry() {
        return new Question<Country>("Enter Country of group Admin (VATICAN, THAILAND, SOUTH_KOREA, NORTH_KOREA, JAPAN) : "
                , super::readCountry).getAnswer();
    }

    @Override
    public long readLX() {
        return new Question<Long>("Enter Location 's x : "
                , super::readLX).getAnswer();
    }

    @Override
    public Double readLY() {
        return new Question<Double>("Enter Location 's y : "
                , super::readLY).getAnswer();
    }

    @Override
    public String readLName() {
        return new Question<String>("Enter Location Name : ",
                super::readLName).getAnswer();
    }

    @Override
    public Location readLocation() throws InvalidDataException {
        return new Location(readLX(), readLY(), readLName());
    }

    @Override
    public Person readPerson() throws InvalidDataException {
        return new Person(readAdminName(), readWeight(), readHairColor(),
                readCountry(), readLocation());
    }

    @Override
    public StudyGroup readStudyGroup() throws InvalidDataException {
        return new StudyGroup(readName(), readCoordinates(), readCreationDate()
                , readStudentsCount(), readExpelledStudents(), readFormOfEducation(), readSemester(), readPerson());
    }
}
