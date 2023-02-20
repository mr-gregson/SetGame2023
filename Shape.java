public enum Shape implements Attribute {
    DIAMOND,
    OVAL,
    SQUIGGLE;

    public int getValue() {
        return ordinal();
    }   

    public String toString(){
        return name().toLowerCase();
    }
}
