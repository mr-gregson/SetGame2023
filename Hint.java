import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hint {
    
    private List<Integer> selectedCards;
    private int hintsUsed;

    public Hint(List<Integer> selectedCards) {
        this.selectedCards = new ArrayList<>(selectedCards);
        this.hintsUsed = 0;
        Collections.shuffle(selectedCards);
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public int getHint(){
        if (hintsUsed < 2) 
            return selectedCards.get(hintsUsed++);
        return -1;
    }
}
