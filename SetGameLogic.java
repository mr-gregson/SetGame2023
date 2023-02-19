import java.util.List;

public class SetGameLogic {
    
    private Deck deck;
    private Card[] board;
    private boolean extraCards;

    public static final int BOARD_SIZE = 12;
    public static final int BOARD_SIZE_X = 15;

    public SetGameLogic() {
        this.deck = new Deck();
        this.board = new Card[BOARD_SIZE_X];
        startNewGame();
    }

    public void startNewGame(){
        deck.shuffle();
        extraCards = false;
        for (int i = 0; i < BOARD_SIZE; ++i) board[i] = deck.deal();
        for (int i = BOARD_SIZE; i < BOARD_SIZE_X; ++i) board[i] = null;
    }

    public Card cardAt(int n){
        if (n < 0 || n >= BOARD_SIZE_X || (!extraCards && n >= BOARD_SIZE)) return null;
        return board[n];
    }

    public void replaceCards(List<Integer> selectedCards){
        for (Integer i: selectedCards){
            board[i] = deck.isEmpty() ? null : deck.deal();
        }
    }

    public boolean isSet(List<Integer> selectedCards){
        return true;
    }

    public String notSetMessage(Card[] cards){
        int[][] attributes = new int[3][];
        int[] mods;

        for (int i = 0; i < attributes.length; ++i){
            attributes[i] = cards[i].getAttributes();
        }
        mods = addColsMod3(attributes);
        return "";
    }

    // Utility methods

    private static boolean all(boolean[] bs, int start){
        if (start >= bs.length) return true;
        if (!bs[start]) return false;
        return all(bs, start+1);
    }

    private static int sum(int[] ns, int start){
        if (start >= ns.length) return 0;
        return ns[start] + sum(ns,start+1);
    }

    private int[] addColsMod3(int[][] matrix){
        int[] mods = new int[matrix[0].length];
        for (int i = 0; i < mods.length; ++i){
            mods[i] = sum(matrix[i],0);
        }
        return mods;
    }
}
