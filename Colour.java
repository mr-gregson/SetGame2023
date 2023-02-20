public enum Colour implements Attribute {
    RED,
    GREEN,
    BLUE;

    public int getValue() {
        return ordinal();
    }

    public String toString(){
        return name().toLowerCase();
    }
}
