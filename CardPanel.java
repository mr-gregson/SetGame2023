import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JPanel;

import java.awt.Dimension;


public class CardPanel extends JPanel {

    CardButton[] cardButtons;

    private JPanel[] columns;

    public static final int CARD_PANEL_WIDTH = 1190;

    public CardPanel(CardButton[] cardButtons) {
        this.columns = new JPanel[5];
        setBackground(SetGame.BACKGROUND_COLOUR);
        setPreferredSize(new Dimension(CardPanel.CARD_PANEL_WIDTH,GamePanel.PANEL_HEIGHT));

        for (int i = 0; i < columns.length; ++i) {
            columns[i] = new JPanel();
            columns[i].setLayout(new BoxLayout(columns[i], BoxLayout.Y_AXIS));
            columns[i].setBackground(SetGame.BACKGROUND_COLOUR);
            columns[i].add(Box.createRigidArea(new Dimension(0, GamePanel.CARD_INC_Y)));
            for (int j = i * 3; j < (i + 1) * 3; ++j) {
                columns[i].add(cardButtons[j]);
                columns[i].add(Box.createRigidArea(new Dimension(0, GamePanel.CARD_INC_X)));
            }
            add(Box.createHorizontalStrut(GamePanel.CARD_INC_X));
            add(columns[i]);
            
        }
        add(Box.createHorizontalStrut(GamePanel.CARD_INC_X));
        columns[4].setVisible(false);

    }

    public void extend() {
        columns[4].setVisible(true);
        repaint();
    }

    public void collapse() {
        columns[4].setVisible(false);
        repaint();
    }
}
