public class Card {
    
    private Colour colour;
    private Shape shape;
    private Count count;
    private Fill fill;
    private String s;

    /**
     * @param colour
     * @param shape
     * @param count
     * @param fill
     */

    public Card(Colour colour, Shape shape, Count count, Fill fill) {
        this.colour = colour;
        this.shape = shape;
        this.count = count;
        this.fill = fill;

        this.s = "";
        for (int i: getAttributes()) this.s = Integer.toString(i) + this.s;
    }

    public Colour getColour() {
        return colour;
    }

    public Shape getShape() {
        return shape;
    }

    public Count getCount() {
        return count;
    }

    public Fill getFill() {
        return fill;
    }

    public int[] getAttributes(){
        int[] attributes = new int[4];
        attributes[0] = colour.ordinal();
        attributes[1] = shape.ordinal();
        attributes[2] = count.ordinal();
        attributes[3] = fill.ordinal();

        return attributes;
    }

    @Override
    public String toString(){
        return this.s;
    }
}
