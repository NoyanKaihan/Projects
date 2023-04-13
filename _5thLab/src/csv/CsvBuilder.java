package csv;

import modules.Person;
import modules.StudyGroup;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for building Csv formatted String out of Object
 */
public class CsvBuilder {
    private PriorityQueue<StudyGroup> collection;
    private static final String CSV_SEPARATOR = ",";

    public CsvBuilder(PriorityQueue<StudyGroup> collection) {
        this.collection = collection;
    }

    /**
     * Method for building CSV formatted String
     *
     * @return String containing CSV formatted String
     */
    public String build() {
        AtomicReference<String> csv = new AtomicReference<>("");
        String column = "Id,Name,Coordinates:x,Coordinates:y,CreationDate,StudentCount,ExpelledStudents" +
                ",FormOfEducation,Semester,GroupAdmin:Name,weight,hairColor," +
                "Nationality,Location:x,Location:y,LocationName" + "\n";
        collection.forEach(studyGroup -> {
            StringBuffer oneLine = new StringBuffer("");
            oneLine.append(studyGroup.getId());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getName());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(new NestedObjectBuilder().nestedCoordinate(studyGroup.getCoordinates().getX(), studyGroup.getCoordinates().getY()));
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getCreationDate());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getStudentsCount());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getExpelledStudents());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getFormOfEducation() == null ? "" : studyGroup.getFormOfEducation());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(studyGroup.getSemesterEnum() == null ? "" : studyGroup.getSemesterEnum());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(new NestedObjectBuilder().nestedPerson(studyGroup.getGroupAdmin()));
            if (studyGroup.getId() > 1)
                csv.set(csv.get() + "\n");
            if (csv.get() != null)
                csv.set(csv.get() + oneLine);
        });
        return column + csv.get();
    }

    /**
     * Class for converting nested Objects to CSV
     */
    private class NestedObjectBuilder {
        /**
         * Method for building CSV String of Coordinates object
         *
         * @param x
         * @param y
         * @return Csv formatted String
         */
        public String nestedCoordinate(Long x, Long y) {
            return x + CSV_SEPARATOR + y;
        }

        /**
         * Method for building CSV String of Person object
         *
         * @param person
         * @return Csv formatted String
         */
        public String nestedPerson(Person person) {
            String s = person.getName() == null ? "" : person.getName();
            s += CSV_SEPARATOR;
            s += person.getWeight() < 0 ? "" : person.getWeight();
            s += CSV_SEPARATOR;
            s += person.getHairColor() == null ? "" : person.getHairColor();
            s += CSV_SEPARATOR;
            s += person.getNationality() == null ? "" : person.getNationality();
            s += CSV_SEPARATOR;
            s += nestedLocation(person.getLocation().getX(), person.getLocation().getY(), person.getLocation().getName());
            return s;
        }

        /**
         * Method for building CSV String of Location object
         *
         * @param x
         * @param y
         * @param name
         * @return CSV formatted String
         */
        private String nestedLocation(long x, Double y, String name) {
            return x + CSV_SEPARATOR + y + CSV_SEPARATOR + name;
        }
    }
}
