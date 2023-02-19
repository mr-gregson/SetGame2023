import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public static final int CARD_PANEL_WIDTH = 1190;
    public static final int BUTTON_PANEL_WIDTH = 130;
    public static final int PANEL_HEIGHT = 940;

    public static final int CARD_TOP_X = 114;
    public static final int CARD_TOP_EX = 40;
    public static final int CARD_TOP_Y = 40;

    public static final int CARD_WIDTH = 190;
    public static final int CARD_HEIGHT = 262;
    public static final int CARD_INC_X = 20;
    public static final int CARD_INC_Y = 40;

    public static final int BUTTON_HEIGHT = 60;
    public static final int BUTTON_WIDTH = 130;
    public static final int SCORE_LABEL_HEIGHT = 60;
    public static final int LABEL_WIDTH = 110;

    public static final int LABEL_MARGIN = 40;

    public static final String BLANK_PATH_STRING = "img/blank.png";

    private boolean[] isSelected;
    private List<Integer> selectedCards;
    private CardButton[] cardButtons;
    private int score;
    private boolean extended;
    private CardPanel cardPanel;
    private JPanel buttonPanel;
    private JButton noSetButton;
    private JButton hintButton;
    private JButton newGameButton;
    private MouseListener mouseListener;
    private SetGameLogic board;
    private int numberOfCards;

    public GamePanel(SetGameLogic board) {
        this.board = board;
        this.isSelected = new boolean[15];
        this.numberOfCards = SetGame.BOARD_SIZE_X;
        this.selectedCards = new ArrayList<>();
        this.score = 0;

        setBackground(SetGame.BACKGROUND_COLOUR);
        setPreferredSize(new Dimension(CARD_PANEL_WIDTH + BUTTON_PANEL_WIDTH + LABEL_MARGIN, PANEL_HEIGHT));

        setName("GamePanel");

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof CardButton)
                    cardAction(e);
                else if (component instanceof JButton)
                    buttonAction(e);
            }
        };

        cardButtons = new CardButton[SetGame.BOARD_SIZE_X];

        for (int i = 0; i < cardButtons.length; ++i) {
            CardButton cardButton = new CardButton(mouseListener, i);
            cardButtons[i] = cardButton;
        }

        cardPanel = new CardPanel(cardButtons);
        cardPanel.setPreferredSize(new Dimension(CARD_PANEL_WIDTH, PANEL_HEIGHT));

        updateCards();

        buttonPanel = new JPanel();
        buttonPanel.setBackground(SetGame.BACKGROUND_COLOUR);
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, PANEL_HEIGHT));

        add(cardPanel);
        add(buttonPanel);

        extended = false;

        noSetButton = new JButton();
        initButton(noSetButton, "No Set");
        buttonPanel.add(noSetButton);

        hintButton = new JButton();
        initButton(hintButton, "Hint");
        buttonPanel.add(hintButton);

        newGameButton = new JButton();
        initButton(newGameButton, "New Game");
        buttonPanel.add(newGameButton);

        addMouseListener(mouseListener);
    }

    private void initButton(JButton button, String text) {
        button.setName(text);
        button.setFont(SetGame.font);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setVisible(true);
        button.setEnabled(true);
        button.setText(text);
        button.addMouseListener(mouseListener);
    }

    private void updateCards() {
        for (int i = 0; i < numberOfCards; ++i) {
            if (board.cardAt(i) == null) 
                cardButtons[i].update(readImage(BLANK_PATH_STRING));   
            else
                cardButtons[i].update(readImage(String.format(board.cardAt(i).toString(), isSelected[i]?"S":"")));            
        }
        repaint();
    }

    private void buttonAction(MouseEvent e) {
        String name = e.getComponent().getName();
        if (name.equals("No Set")){
            cardPanel.extend();
            System.out.println("extend");
        }
         
        if (name.equals("Hint")) cardPanel.collapse();
        repaint();
    }

    private void cardAction(MouseEvent e) {
        CardButton cardButton = (CardButton) e.getComponent();
        int i = cardButton.getIndex();
        isSelected[i] = !isSelected[i];
        if (isSelected[i]){
            selectedCards.add(i);
            if (selectedCards.size() == 3 && board.isSet(selectedCards)) {
                score += 1;
                System.out.println("SET");
                board.replaceCards(selectedCards);
                for (Integer j: selectedCards) 
                    isSelected[j] = false;
                selectedCards.clear();                
            }
        }
        else
            selectedCards.remove(Integer.valueOf(i));
        
        updateCards();
    }

    private static BufferedImage readImage(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
