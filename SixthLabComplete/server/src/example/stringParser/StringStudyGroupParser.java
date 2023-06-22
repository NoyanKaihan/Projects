package example.stringParser;

import org.example.modules.*;

import java.time.ZonedDateTime;

public class StringStudyGroupParser {
    private String stringStudyGroup;
    public StringStudyGroupParser(String stringStudyGroup){
        this.stringStudyGroup = stringStudyGroup;
    }
    public StudyGroup parseStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();

        // Remove leading and trailing whitespaces
        stringStudyGroup = stringStudyGroup.trim();

        // Split the input string by new lines
        String[] lines = stringStudyGroup.split("\n");

        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "Id":
                    studyGroup.setId(Integer.parseInt(fieldValue));
                    break;
                case "Name":
                    studyGroup.setName(fieldValue);
                    break;
                case "Coordinate":
                    // Parse coordinates
                    Coordinates coordinates = parseCoordinates(fieldValue);
                    studyGroup.setCoordinates(coordinates);
                    break;
                case "CreationDate":
                    // Parse creation date
                    ZonedDateTime creationDate = ZonedDateTime.parse(fieldValue);
                    studyGroup.setCreationDate(creationDate);
                    break;
                case "Student s count":
                    studyGroup.setStudentsCount(Long.parseLong(fieldValue));
                    break;
                case "Expelled Students":
                    studyGroup.setExpelledStudents(Long.parseLong(fieldValue));
                    break;
                case "Form of education":
                    // Parse form of education
                    FormOfEducation formOfEducation = FormOfEducation.valueOf(fieldValue);
                    studyGroup.setFormOfEducation(formOfEducation);
                    break;
                case "Semester":
                    // Parse semester enum
                    Semester semester = Semester.valueOf(fieldValue);
                    studyGroup.setSemesterEnum(semester);
                    break;
                case "Group Admin":
                    // Parse group admin
                    Person groupAdmin = parsePerson(fieldValue);
                    studyGroup.setGroupAdmin(groupAdmin);
                    break;
            }
        }

        return studyGroup;
    }

    private  Coordinates parseCoordinates(String input) {
        Coordinates coordinates = new Coordinates();

        // Split the input string by new lines
        String[] lines = input.split("\n");

        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "x":
                    coordinates.setX(Long.parseLong(fieldValue));
                    break;
                case "y":
                    coordinates.setY(Long.parseLong(fieldValue));
                    break;
            }
        }

        return coordinates;
    }

    private Person parsePerson(String input) {
        Person person = new Person();

        // Split the input string by new lines
        String[] lines = input.split("\n");

        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "Name":
                    person.setName(fieldValue);
                    break;
                case "Weight":
                    person.setWeight(Double.parseDouble(fieldValue));
                    break;
                case "HairColor":
                    // Parse hair color
                    Color hairColor = Color.valueOf(fieldValue);
                    person.setHairColor(hairColor);
                    break;
                case "Nationality":
                    // Parse nationality
                    if (!fieldValue.equals("null")) {
                        Country nationality = Country.valueOf(fieldValue);
                        person.setNationality(nationality);
                    }
                    break;
                case "Location":
                    // Parse location
                    Location location = parseLocation(fieldValue);
                    person.setLocation(location);
                    break;
            }
        }

        return person;
    }

    private  Location parseLocation(String input) {
        Location location = new Location();

        // Split the input string by new lines
        String[] lines = input.split("\n");

        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "x":
                    location.setX(Long.parseLong(fieldValue));
                    break;
                case "y":
                    location.setY(Double.parseDouble(fieldValue));
                    break;
                case "Name":
                    location.setName(fieldValue);
                    break;
            }
        }

        return location;
    }
}
