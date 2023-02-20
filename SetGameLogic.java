import java.util.ArrayList;
import java.util.List;

public class SetGameLogic {
    
    private Deck deck;
    private Card[] board;
    private boolean isExtended;

    public static final int BOARD_SIZE = 12;
    public static final int BOARD_SIZE_X = 15;

    public SetGameLogic() {
        this.deck = new Deck();
        this.board = new Card[BOARD_SIZE_X];
        startNewGame();
    }

    public void startNewGame(){
        deck.shuffle();
        isExtended = false;
        for (int i = 0; i < BOARD_SIZE; ++i) board[i] = deck.deal();
        for (int i = BOARD_SIZE; i < BOARD_SIZE_X; ++i) board[i] = null;
    }

    public Card cardAt(int n){
        if (n < 0 || n >= BOARD_SIZE_X || (!isExtended && n >= BOARD_SIZE)) return null;
        return board[n];
    }

    public void replaceCards(List<Integer> selectedCards){
        for (Integer i: selectedCards){
            board[i] = deck.isEmpty() ? null : deck.deal();
        }
    }

    public int cardsOnBoard(){
        return isExtended ? BOARD_SIZE_X : BOARD_SIZE;
    }

    public boolean isSet(List<Integer> selectedCards){
        int[] sums = sums(selectedCards);

        for (int i = 0; i < sums.length; ++i)
            if (sums[i] % 3 != 0) return false;

        return true;
    }

    public boolean hasSet(){
        List<Integer> selectedCards = new ArrayList<>();
        for (int i = 0; i < 3; ++i){
            selectedCards.add(0);
        }
        for (int i = 0; i < cardsOnBoard(); ++i) {
            selectedCards.set(0,i);
            for (int j = i + 1; j < cardsOnBoard(); ++j){
                selectedCards.set(1,j);
                for (int k = j + 1; j < cardsOnBoard(); ++k){
                    selectedCards.set(2,k);
                    if (isSet(selectedCards)) return true;
                }
            } 
        }
        return false;
    }



    public String notSetMessage(Card[] cards){
    
        return "Not a set";
    }

    // Utility methods

    private int[] sums(List<Integer> selectedCards){
        int[] sums = new int[4];
        for (int i = 0; i < sums.length; ++i) sums[i] = 0;

        for (Integer i: selectedCards){
            int[] attributes = cardAt(i).getAttributes();
            for (int j = 0; j < sums.length; ++j) sums[j] += attributes[j];
        }
        return sums;
    }
}
