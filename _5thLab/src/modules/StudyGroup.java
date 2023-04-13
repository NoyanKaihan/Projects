package modules;


import java.time.ZonedDateTime;

public class StudyGroup implements DataValidator, Comparable<StudyGroup> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private long expelledStudents; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name, Coordinates coordinates, ZonedDateTime zonedDateTime, long studentsCount
            , long expelledStudents, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = zonedDateTime;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public long getExpelledStudents() {
        return expelledStudents;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    @Override
    public String toString() {
        String s = "\n" +
                "\tId : " + id + "\n" +
                "   \tName : " + name + "\n" +
                "   \tCoordinate : " + coordinates + "\n" +
                "   \tCreationDate : " + creationDate + "\n" +
                "   \tStudent s count : " + studentsCount + "\n" +
                "   \tExpelled Students : " + expelledStudents + "\n";
        if (formOfEducation != null) {
            s += "   \tForm of education : " + formOfEducation + "\n";
        } else if (formOfEducation == null) {
            s += "   \tForm of education : " + '\n';
        }
        if (formOfEducation != null) {
            s += "   \tQuality of form of education : " + formOfEducation.quality + "\n";
        } else if (formOfEducation == null) {
            s += "  \tQuality of form of education :\n";
        }
        if (semesterEnum != null) {
            s += "   \tSemester : " + semesterEnum + "\n";
        } else if (semesterEnum == null) {
            s += "   \tSemester : " + "\n";
        }
        s += "   \tGroup Admin : " + groupAdmin;
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyGroup that = (StudyGroup) o;


        Integer i = id;
        Long s = studentsCount;
        if (expelledStudents != that.expelledStudents) return false;
        if (!i.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!coordinates.equals(that.coordinates)) return false;
        if (!creationDate.equals(that.creationDate)) return false;
        if (!s.equals(that.studentsCount)) return false;
        if (formOfEducation != that.formOfEducation) return false;
        return groupAdmin.equals(that.groupAdmin);
    }

    @Override
    public int hashCode() {
        Long l = studentsCount;
        Integer i = id;
        int result = i.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + coordinates.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + l.hashCode();
        result = 31 * result + (int) (expelledStudents ^ (expelledStudents >>> 32));
        result = 31 * result + formOfEducation.hashCode();
        result = 31 * result + semesterEnum.hashCode();
        result = 31 * result + groupAdmin.hashCode();
        return result;
    }

    @Override
    public boolean valid() {
        return id > 0
                && name != null && !name.equals("")
                && coordinates != null && coordinates.valid()
                && studentsCount > 0
                && expelledStudents > 0
                && groupAdmin != null && groupAdmin.valid();
    }

    @Override
    public int compareTo(StudyGroup o) {
        int result;
        result = Long.compare(studentsCount, o.getStudentsCount());
        return result;
    }

}
