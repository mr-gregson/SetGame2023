public enum Count implements Attribute {
    ONE,
    TWO,
    THREE;

    public int getValue() {
        return ordinal();
    }

    public String toString(){
        return name().toLowerCase();
    }
}
