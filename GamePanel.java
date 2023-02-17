import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GamePanel extends JPanel{

    public static final  int CARD_PANEL_WIDTH = 1190;
    public static final int BUTTON_PANEL_WIDTH = 130;
    public static final int PANEL_HEIGHT = 940;
    

    public static final int CARD_TOP_X = 114;
    public static final int CARD_TOP_EX = 40;
    public static final int CARD_TOP_Y = 40;

    public static final int CARD_WIDTH = 190;
    public static final int CARD_HEIGHT = 262;
    public static final int CARD_INC_X = 230;
    public static final int CARD_INC_Y = 300;

    public static final int BUTTON_HEIGHT = 60;
    public static final int BUTTON_WIDTH = 130;
    public static final int SCORE_LABEL_HEIGHT = 60;
    public static final int LABEL_WIDTH = 110;

    public static final int LABEL_MARGIN = 20;

    private Point[] cardLocations;
    private JLabel[] cardImageIcons;
    private boolean extended;
    private JPanel cardPanel;
    private JPanel buttonPanel;
    private JButton noSetButton;
    private JButton hintButton;
    private JButton newGameButton;
    private SetGameLogic board;

    public GamePanel(SetGameLogic board){
        this.board = board;

        //setPreferredSize(new Dimension(CARD_PANEL_WIDTH+BUTTON_PANEL_WIDTH, PANEL_HEIGHT));
        
        cardPanel = new JPanel(new GridLayout(3,4,40,40));
        cardPanel.setBackground(SetGame.BACKGROUND_COLOUR);
        cardPanel.setPreferredSize(new Dimension(CARD_PANEL_WIDTH,PANEL_HEIGHT));
    

        buttonPanel = new JPanel();
        buttonPanel.setBackground(SetGame.BACKGROUND_COLOUR);
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH,PANEL_HEIGHT));

        add(cardPanel);
        add(buttonPanel);

        System.out.println("1");
        
        cardLocations = new Point[15];

        extended = false;

        noSetButton = new JButton();
        initButton(noSetButton, "No Set");
        buttonPanel.add(noSetButton);
                
        hintButton = new JButton();
        initButton(hintButton, "Hint");
        buttonPanel.add(hintButton);

        newGameButton = new JButton();
        initButton(newGameButton,"New Game");
        buttonPanel.add(newGameButton);

        System.out.println("1");

        cardImageIcons = new JLabel[12];

        for (int i = 0; i < cardLocations.length; ++i){
            int x = CARD_TOP_X + (i/3) * CARD_INC_X;
            int y = CARD_TOP_Y + (i%3) * CARD_INC_Y;
            cardLocations[i] = new Point(x,y);
        }

        for (int i = 0; i < cardImageIcons.length; ++i){
            cardImageIcons[i] = new JLabel();
            cardImageIcons[i].setPreferredSize(new Dimension(CARD_WIDTH,CARD_HEIGHT));
            ImageIcon icon = new ImageIcon(readImage(String.format(board.cardAt(i).toString(),"")));
            cardImageIcons[i].setIcon(icon);
            
            cardPanel.add(cardImageIcons[i]);

        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component component = e.getComponent();
                System.out.println(component.getName());
                
            }
        });
    }

    private void initButton(JButton button, String text){
        button.setName(text);
        button.setFont(SetGame.font);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setVisible(true);
        button.setEnabled(true);
        button.setText(text);
    }


    private static BufferedImage readImage(String filename){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new FileInputStream(filename));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return image;
	}

}
