package csv;

import console.ConsoleColor;
import modules.*;
import exceptions.DataException;
import exceptions.DataParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for parsing StudyGroup
 */
public class StudyGroupParser {
    private StudyGroup studyGroup;

    public StudyGroupParser() {
    }

    /**
     * Method for parsing String into StudyGroup object
     *
     * @param object
     * @return StudyGroup object
     * @throws DataParseException
     */
    public StudyGroup objectParse(String object) throws DataParseException, DataException {

        /**
         * ArrayList for parsing
         */
        List<String> dt = new ArrayList<>(Arrays.asList(object.split(",")));

        /**
         * ZonedDateTime parsing
         */
        ZonedDateTime zonedDateTime = null;
        try {
            zonedDateTime = zonedDateTimeParser(dt.get(4));
        } catch (DataParseException exception) {
            throw new DataParseException(exception.getMessage());
        }
        /**
         * Nested Coordinates parsing
         */
        Coordinates coordinates = coordinatesParser(dt.get(2), dt.get(3));

        /**
         * Nested Person parsing
         */
        Person person = null;
        try {
            person = personParser(dt.get(9), dt.get(10), dt.get(11), dt.get(12), dt.get(13), dt.get(14), dt.get(15));
        } catch (DataParseException exception) {
            throw new DataParseException(exception.getMessage());
        }

        /**
         * StudyGroup parsing
         */
        try {
            studyGroup = studyGroupParse(dt.get(0), dt.get(1), coordinates, zonedDateTime, dt.get(5), dt.get(6), dt.get(7), dt.get(8), person);
        } catch (DataParseException exception) {
            throw new DataParseException(exception.getMessage());
        }
        return studyGroup;
    }

    /**
     * Nested Coordinates Parsing
     *
     * @param x1
     * @param y1
     * @return Coordinates Object
     */
    private Coordinates coordinatesParser(String x1, String y1) {
        long x = 0;
        try {
            x = Long.parseLong(x1);
        } catch (NumberFormatException exception) {
            System.out.print(ConsoleColor.RED_BACKGROUND + "Invalid coordinate x :(" + ConsoleColor.RESET + "\n");
        }
        long y = 0;
        try {
            y = Long.parseLong(y1);
        } catch (NumberFormatException exception) {
            System.out.print(ConsoleColor.RED_BACKGROUND + "Invalid coordinate y :(" + ConsoleColor.RESET + "'\n");
        }
        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    /**
     * Nested ZonedDateTime Parsing
     *
     * @param zoneDateTime
     * @return ZonedDateTime Object
     * @throws DataParseException
     */
    private ZonedDateTime zonedDateTimeParser(String zoneDateTime) throws DataParseException {
        try {
            String[] parse = zoneDateTime.split("[T]");
            LocalDate localDate = LocalDate.parse(parse[0]);
            String[] parse1 = parse[1].split("[+]");
            LocalTime localTime = LocalTime.parse(parse1[0]);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, zoneId);
            return zonedDateTime;
        } catch (DateTimeParseException exception) {
            throw new DataParseException(ConsoleColor.RED_BACKGROUND + " Invalid date :(" + ConsoleColor.RESET + "\n");
        }
    }

    /**
     * Nested Person Parsing
     *
     * @param name
     * @param weight
     * @param hairColor
     * @param nationality
     * @param locationX
     * @param locationY
     * @param locationName
     * @return Person Object
     */
    private Person personParser(String name, String weight, String hairColor, String nationality, String locationX, String locationY, String locationName) throws DataParseException {

        Location location = null;
        try {
            location = locationParser(locationX, locationY, locationName);
        } catch (DataParseException dataParseException) {
            throw new DataParseException(dataParseException.getMessage());
        }
        Person person = null;
        try {
            if (!hairColor.equals("") && nationality.equals("")) {
                person = new Person(name, personWeight(weight), Color.valueOf(hairColor), null, location);
                return person;
            }
            if (hairColor.equals("") && nationality.equals("")) {
                person = new Person(name, personWeight(weight), null, null, location);
                return person;
            } else if (!hairColor.equals("") && !nationality.equals("")) {
                person = new Person(name, personWeight(weight), Color.valueOf(hairColor), Country.valueOf(nationality), location);
                return person;
            } else {
                return person;
            }
        } catch (DataParseException exception) {
            throw new DataParseException(exception.getMessage());
        }
    }

    /**
     * Person weight parsing
     *
     * @param weight
     * @return
     * @throws DataParseException
     */
    private double personWeight(String weight) throws DataParseException {
        double pW = 0;
        try {
            pW = Double.parseDouble(weight);
            return pW;
        } catch (NumberFormatException exception) {
            throw new DataParseException(ConsoleColor.RED_BACKGROUND + "Invalid Person weight :(" + ConsoleColor.RESET + "\n");
        }
    }

    /**
     * Nested Person Nested Location Parsing
     *
     * @param locationX
     * @param locationY
     * @param locationName
     * @return Location Object
     */
    private Location locationParser(String locationX, String locationY, String locationName) throws DataParseException {
        Location location = null;
        try {
            location = new Location(locationXParser(locationX), locationYParser(locationY), locationName);
            return location;
        } catch (DataParseException exception) {
            throw new DataParseException(exception.getMessage());
        }
    }

    /**
     * Location X parsing
     *
     * @param lx1
     * @return long x
     * @throws DataParseException
     */
    private long locationXParser(String lx1) throws DataParseException {
        long lx = 0;
        try {
            lx = Long.parseLong(lx1);
            return lx;
        } catch (NumberFormatException exception) {
            throw new DataParseException(ConsoleColor.RED_BACKGROUND + "Invalid location x :(" + ConsoleColor.RESET + "\n");
        }
    }

    /**
     * Location Y parsing
     *
     * @param ly1
     * @return double y
     * @throws DataParseException
     */
    private double locationYParser(String ly1) throws DataParseException {
        double ly = 0;
        try {
            ly = Double.parseDouble(ly1);
            return ly;
        } catch (NumberFormatException exception) {
            throw new DataParseException(ConsoleColor.RED_BACKGROUND + "Invalid location y :(" + ConsoleColor.RESET + "\n");
        }
    }

    /**
     * StudyGroup Parsing
     *
     * @param id
     * @param name
     * @param coordinates
     * @param zonedDateTime
     * @param studentsCount
     * @param expelledStudents
     * @param formOfEducation
     * @param semester
     * @param person
     * @return StudyGroup object
     */
    private StudyGroup studyGroupParse(String id, String name, Coordinates coordinates, ZonedDateTime zonedDateTime, String studentsCount, String expelledStudents, String formOfEducation, String semester, Person person) throws DataParseException {
        if (!formOfEducation.equals("") && !semester.equals("")) {
            studyGroup = new StudyGroup(name, coordinates, zonedDateTime, studyGroupStudentCount(studentsCount), studyGroupExpelledStudents(expelledStudents), FormOfEducation.valueOf(formOfEducation), Semester.valueOf(semester), person);
            studyGroup.setId(idParsing(id));
            return studyGroup;
        }
        if (formOfEducation.equals("") && !semester.equals("")) {
            studyGroup = new StudyGroup(name, coordinates, zonedDateTime, studyGroupStudentCount(studentsCount), studyGroupExpelledStudents(expelledStudents), null, Semester.valueOf(semester), person);
            studyGroup.setId(idParsing(id));
            return studyGroup;
        }
        if (semester.equals("") && !formOfEducation.equals("")) {
            studyGroup = new StudyGroup(name, coordinates, zonedDateTime, studyGroupStudentCount(studentsCount), studyGroupExpelledStudents(expelledStudents), FormOfEducation.valueOf(formOfEducation), null, person);
            studyGroup.setId(idParsing(id));
            return studyGroup;
        } else if (formOfEducation.equals("") && semester.equals("")) {
            studyGroup = new StudyGroup(name, coordinates, zonedDateTime, studyGroupStudentCount(studentsCount), studyGroupExpelledStudents(expelledStudents), null, null, person);
            studyGroup.setId(idParsing(id));
            return studyGroup;
        } else {
            return null;
        }
    }

    /**
     * StudyGroup Id parsing
     *
     * @param id
     * @return int id
     */
    private Integer idParsing(String id) {
        int id1 = 0;
        try {
            id1 = Integer.parseInt(id);
        } catch (NumberFormatException exception) {
            System.out.print(ConsoleColor.RED_BACKGROUND + "Invalid Id :(" + ConsoleColor.RESET + "\n");
        }
        return id1;
    }

    /**
     * StudyGroup Student count parsing
     *
     * @param studentsCount
     * @return long studentsCount
     */
    private long studyGroupStudentCount(String studentsCount) {
        long sC = 0;
        try {
            sC = Long.parseLong(studentsCount);
        } catch (NumberFormatException exception) {
            System.out.print(ConsoleColor.RED_BACKGROUND + "Invalid StudentsCount :(" + ConsoleColor.RESET + "\n");
        }
        return sC;
    }

    /**
     * StudyGroup Expelled Students parsing
     *
     * @param expelledStudents
     * @return long expelledStudents
     */
    private long studyGroupExpelledStudents(String expelledStudents) {
        long eS = 0;
        try {
            eS = Long.parseLong(expelledStudents);
        } catch (NumberFormatException exception) {
            System.out.print(ConsoleColor.RED_BACKGROUND + "Invalid expelled students :(" + ConsoleColor.RESET + "\n");
        }
        return eS;
    }
}



