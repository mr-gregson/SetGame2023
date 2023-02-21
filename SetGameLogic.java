import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetGameLogic {
    
    private Deck deck;
    private Card[] board;
    private boolean isExtended;
    private int score;
    // Game Panel Dimensions
    
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
        this.score = 0;
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

    public List<Integer> getSet(){
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
                    if (isSet(selectedCards)) return selectedCards;
                }
            } 
        }
        return null;
    }

    public boolean hasSet(){
        return getSet() != null;
    }

    public void extendBoard(){
        if (isExtended) return;
        Card thirdCard = null;
        while (thirdCard == null){
            int[] indeces = randomPair();
            Card card1 = cardAt(indeces[0]);
            Card card2 = cardAt(indeces[1]);
            thirdCard = completeSet(card1, card2);
        }
        List<Card> extensionCards = new ArrayList<>();
        extensionCards.add(thirdCard);
        extensionCards.add(deck.deal());
        extensionCards.add(deck.deal());
        Collections.shuffle(extensionCards);
        for (int i = BOARD_SIZE; i < BOARD_SIZE_X; ++i) board[i] = extensionCards.get(i - BOARD_SIZE);
        isExtended = true;
    }

    public boolean isExtended(){
        return isExtended;
    }

    public void collapseBoard(List<Integer> selectedCards){
        if (!isExtended) return;
        for (Integer i : selectedCards){
            if (i >= BOARD_SIZE) board[i] = null;
            else{
                int k = BOARD_SIZE;
                while (board[k] == null) ++k;  
                board[i] = board[k]; 
                board[k] = null; 
            }
        }
        isExtended = false;
    }

    public String notSetMessage(Card[] cards){
    
        return "Not a set";
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points){
        score = Math.max(score + points, 0);
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

    private Card completeSet(Card card1, Card card2){
        int[] attributes = new int[4];
        for (int i = 0; i < attributes.length; ++i){
            attributes[i] = (6 - card1.getAttributes()[i] - card2.getAttributes()[i]) % 3;
        }
        return new Card(Colour.values()[attributes[0]], Shape.values()[attributes[1]], 
                        Count.values()[attributes[2]], Fill.values()[attributes[3]]);
    }

    private int[] randomPair(){
        int[] pair = new int[2];
        pair[0] = (int) (Math.random() * BOARD_SIZE);
        pair[1] = (int) (Math.random() * BOARD_SIZE);
        while (pair[0] == pair[1]) pair[1] = (int) (Math.random() * BOARD_SIZE_X);
        return pair;
    }
}
