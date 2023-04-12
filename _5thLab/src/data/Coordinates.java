package data;


import java.util.Objects;

public class Coordinates implements DataValidator {
    private Long x; //Поле не может быть null
    private long y; //Максимальное значение поля: 790
    public Coordinates (Long x,long y){
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        Long y1 = y;
        return Float.compare(that.x, x) == 0 && y1.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return  "\n"+
                "  \t\tx : "+x+"\n"+
                "  \t\ty : "+y;

    }

    @Override
    public boolean valid() {
        return x != null
                && y<=790;
    }
}
