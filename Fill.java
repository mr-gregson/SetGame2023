public enum Fill implements Attribute {
    UNSHADED,
    PINSTRIPE,
    SOLID;

    public int getValue() {
        return ordinal();
    }

    public String toString(){
        return name().toLowerCase();
    }
}
