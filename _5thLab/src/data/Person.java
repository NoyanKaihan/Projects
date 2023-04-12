package data;

import java.util.Objects;

public class Person implements DataValidator,Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double weight; //Значение поля должно быть больше 0
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null
    public Person (String name,double weight,Color hairColor,Country nationality,Location location){
        this.name = name;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
       String s =  "\n"+
                "\t\tName : "+name+"\n"+
                "   \t\tWeight : "+weight+"\n";
                if(hairColor!=null) {
                    s+="   \t\tHair Color : " + hairColor + "\n";
                }else if(hairColor == null){
                    s+="   \t\tHair Color : " +"\n";
                }
                s+="   \t\tNationality : "+nationality+"\n"+
                "   \t\tLocation : "+location;
                return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (weight != person.weight) return false;
        if (!name.equals(person.name)) return false;
        if (hairColor != person.hairColor) return false;
        if (nationality != person.nationality) return false;
        return Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int)weight;
        result = 31 * result + (hairColor != null ? hairColor.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public boolean valid() {
        return name!=null && !name.equals("")
                && weight > 0
                && hairColor!=null
                && location.valid();
    }

    @Override
    public int compareTo(Person o) {
        int result;
        result = Double.compare(getWeight(),o.getWeight());
        return result;
    }
}
