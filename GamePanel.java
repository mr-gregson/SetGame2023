
/**
 * 
 * @author mr-gregson
 * @version 1.0
 */
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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {

    public static final int BUTTON_PANEL_WIDTH = 200;
    public static final int PANEL_HEIGHT = 940;

    public static final int CARD_WIDTH = 190;
    public static final int CARD_HEIGHT = 262;
    public static final int CARD_INC_X = 20;
    public static final int CARD_INC_Y = 40;

    public static final int BUTTON_HEIGHT = 120;
    public static final int BUTTON_WIDTH = BUTTON_PANEL_WIDTH;
    public static final int SCORE_LABEL_HEIGHT = BUTTON_HEIGHT;
    public static final int MESSAGE_LABEL_HEIGHT = 200;
    public static final int LABEL_WIDTH = BUTTON_PANEL_WIDTH;

    public static final int LABEL_MARGIN = 40;

    public static final String BLANK_PATH_STRING = "img/blank.png";
    public static final String SCORE_LABEL_STRING = "Score: %2d";

    /**
     * Read an image file if <code>filename</code>, return null otherwise
     * 
     * @param filename
     * @return
     */
    private static BufferedImage readImage(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private boolean[] isSelected;
    private List<Integer> selectedCards;
    private CardButton[] cardButtons;
    private CardPanel cardPanel;
    private JPanel buttonPanel;
    private JButton noSetButton;
    private JButton hintButton;
    private JButton newGameButton;
    private JLabel scoreLabel;
    private JLabel messageLabel;
    private MouseListener mouseListener;
    private SetGameLogic board;

    private Hint hint;

    private int numberOfCards;

    public GamePanel(SetGameLogic board) {
        // initialize instance variables
        this.board = board;
        this.numberOfCards = SetGameLogic.BOARD_SIZE_X;
        this.isSelected = new boolean[SetGameLogic.BOARD_SIZE_X];
        this.selectedCards = new ArrayList<>();
        

        // mouse listener will be used for all buttons and cards
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

        // card buttons are initialized and added to the card panel
        cardButtons = new CardButton[SetGameLogic.BOARD_SIZE_X];

        for (int i = 0; i < cardButtons.length; ++i) {
            CardButton cardButton = new CardButton(mouseListener, i);
            cardButtons[i] = cardButton;
        }
        updateCards();

        this.hint = board.getHint();
        cardPanel = new CardPanel(cardButtons);
        cardPanel.setPreferredSize(new Dimension(CardPanel.CARD_PANEL_WIDTH, PANEL_HEIGHT));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(SetGame.BACKGROUND_COLOUR);
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, PANEL_HEIGHT));

        scoreLabel = initLabel(String.format(SCORE_LABEL_STRING, 0), SCORE_LABEL_HEIGHT);
        messageLabel = initLabel("", MESSAGE_LABEL_HEIGHT);

        noSetButton = initButton("No Set");
        hintButton = initButton("Hint");
        newGameButton = initButton("New Game");

        setName("GamePanel");
        setBackground(SetGame.BACKGROUND_COLOUR);
        setPreferredSize(new Dimension(CardPanel.CARD_PANEL_WIDTH + BUTTON_PANEL_WIDTH + LABEL_MARGIN, PANEL_HEIGHT));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, LABEL_MARGIN)));
        buttonPanel.add(scoreLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, LABEL_MARGIN)));
        buttonPanel.add(noSetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, LABEL_MARGIN)));
        buttonPanel.add(hintButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, LABEL_MARGIN)));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, LABEL_MARGIN)));
        buttonPanel.add(messageLabel);

        add(cardPanel);
        add(buttonPanel);
    }

    private JButton initButton(String text) {
        JButton button = new JButton();
        button.setName(text);
        button.setFont(SetGame.font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setVisible(true);
        button.setEnabled(true);
        button.setText(text);
        button.addMouseListener(mouseListener);

        return button;
    }

    private JLabel initLabel(String text, int height) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(SetGame.font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, height));
        label.setVisible(true);
        label.setText(text);

        return label;
    }

    private void updateCards() {
        for (int i = 0; i < numberOfCards; ++i) {
            if (board.cardAt(i) == null)
                cardButtons[i].update(readImage(BLANK_PATH_STRING));
            else
                cardButtons[i].update(readImage(String.format(board.cardAt(i).toString(), isSelected[i] ? "S" : "")));
        }
        repaint();
    }

    private void updateMessage(String message) {
        messageLabel.setText(message);
    }

    private void updateScore(int points) {
        board.updateScore(points);
        scoreLabel.setText(String.format(SCORE_LABEL_STRING, board.getScore()));
    }

    private void buttonAction(MouseEvent e) {
        String name = e.getComponent().getName();
        if (name.equals("No Set")) {
            if (board.hasSet()) {
                updateScore(-1);
                updateMessage("There is a set. Try again!");
            } else {
                updateScore(2);
                updateMessage("No Set!");
                noSetButton.setBorderPainted(false);
                board.extendBoard();
                cardPanel.extend();
            }
        } else if (name.equals("Hint")) {
            if (hint == null) {
                noSetButton.setBorderPainted(true);
            } else {
                int i = hint.getHint();
                if (i != -1) {
                    if (!isSelected[i])
                        selectedCards.add(Integer.valueOf(i));
                    isSelected[i] = true;
                    cardButtons[i].setEnabled(false);
                }
            }
        } else if (name.equals("New Game")) {
            board.startNewGame();
            cardPanel.collapse();
            updateScore(0);
            updateMessage("");
        }
        updateCards();
        repaint();
    }

    private void cardAction(MouseEvent e) {
        CardButton cardButton = (CardButton) e.getComponent();
        int i = cardButton.getIndex();
        if (board.cardAt(i) == null)
            return;
        isSelected[i] = !isSelected[i];
        updateMessage("");

        if (isSelected[i])
            selectedCards.add(i);
        else
            selectedCards.remove(Integer.valueOf(i));
        if (selectedCards.size() == 3) {
            if (board.isSet(selectedCards)) {
                updateScore(1);
                updateMessage("Set!");
                if (board.isExtended()) {
                    board.collapseBoard(selectedCards);
                    cardPanel.collapse();
                } else
                    board.replaceCards(selectedCards);
            } else {
                updateScore(-1);
                updateMessage("Not a set");
            }
            for (Integer j : selectedCards) {
                isSelected[j] = false;
                cardButtons[j].setEnabled(true);
            }
            selectedCards.clear();
        }
        hint = board.getHint();
        updateCards();
    }
}
