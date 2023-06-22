package org.example.modules;


import java.io.Serializable;
import java.util.Objects;

public class Location implements DataValidator, Serializable {
    private long x;
    private Double y; //Field can't be null
    private String name; //Field can't be null
    public Location(){}
    public Location(long x, Double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "\n" +
                "   \t\t\tx : " + x + "\n" +
                "   \t\t\ty : " + y + "\n" +
                "   \t\t\tName : " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.x, x) != 0) return false;
        if (y != location.y) return false;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        int y1 = y.intValue();
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (y1 ^ (y1 >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean valid() {
        return y != null
                && name != null;
    }
}
