package org.example.modules;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class StudyGroup implements DataValidator, Comparable<StudyGroup>, Serializable {
    private int id; //Field 's value should be greater than 0, value should be unique, value of this field should be generated automatically
    private String name; //Field can't be null, String can't be null
    private Coordinates coordinates; //Field can't be null
    private ZonedDateTime creationDate; //Field can't be null, value should be generated automatically
    private long studentsCount; //Field 's value should be greater than 0
    private long expelledStudents; //Field 's value should be greater than 0
    private FormOfEducation formOfEducation; //Field can be null
    private Semester semesterEnum; //Field can be null
    private Person groupAdmin; //Field can't be null

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

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }
}
