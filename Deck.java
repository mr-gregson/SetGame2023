import java.util.ArrayList;
import java.util.List;

public class Deck {
    
    private List<Card> deck;
    private int size = 0;

    public Deck(){
        deck = new ArrayList<>();
        for (Fill fill: Fill.values()){
            for (Count count: Count.values()){
                for (Shape shape: Shape.values()){
                    for (Colour colour: Colour.values()){
                        deck.add(new Card(colour, shape, count, fill));
                    }
                }
            }
        }
        size = deck.size();
        shuffle();
    }

    public void shuffle(){
        for (int i = deck.size() - 1; i > 0; --i){
            int j = (int) (Math.random() * (i+1));
            Card temp = deck.get(i);
            deck.set(i,deck.get(j));
            deck.set(j,temp);
        }
        size = deck.size();
    }

    public Card deal(){
        if (isEmpty()){
            return null;
        }
        return deck.get(--size);
    }

    public int size(){
        return size;
    }

    public Card dealCard(Card card){
        if (isEmpty()){
            return null;
        }
        int i = deck.indexOf(card);
        if (i == -1 || i >= size){
            return null;
        }
        Card temp = deck.get(i);
        deck.set(i,deck.get(size-1));
        deck.set(size-1,temp);
        return deck.get(--size);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args){
        Deck d = new Deck();
        Card c = d.deal();
        System.out.println(c.getColour());
        System.out.println(c.getShape());
        System.out.println(c.getCount());
        System.out.println(c.getFill());
        System.out.println(c);
    }
}
