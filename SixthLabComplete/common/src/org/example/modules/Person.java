package org.example.modules;

import java.io.Serializable;
import java.util.Objects;

public class Person implements DataValidator, Comparable<Person>, Serializable {
    private String name; //Field can't be null, String can't be empty
    private double weight; //Field 's value should be greater than 0
    private Color hairColor; //Field can't be null
    private Country nationality; //Field can be null
    private Location location; //Field can be null

    public Person(){}
    public Person(String name, double weight, Color hairColor, Country nationality, Location location) {
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
        String s = "\n" +
                "\t\tName : " + name + "\n" +
                "   \t\tWeight : " + weight + "\n" +
                "   \t\tHairColor : " + hairColor + "\n";
        if (nationality != null) {
            s += "   \t\tNationality : " + nationality + "\n";
        } else if (nationality == null) {
            s += "   \t\tNationality : " + "\n";
        }
        s += "   \t\tLocation : " + location;
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
        result = 31 * result + (int) weight;
        result = 31 * result + (hairColor != null ? hairColor.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public boolean valid() {
        return name != null && !name.equals("")
                && weight > 0
                && hairColor != null
                && location.valid();
    }

    @Override
    public int compareTo(Person o) {
        int result;
        result = Double.compare(getWeight(), o.getWeight());
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
