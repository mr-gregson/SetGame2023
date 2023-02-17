import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GamePanel extends JPanel{

    public static final  int CARD_PANEL_WIDTH = 1190;
    public static final int BUTTON_PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 940;
    

    public static final int CARD_TOP_X = 114;
    public static final int CARD_TOP_EX = 40;
    public static final int CARD_TOP_Y = 40;

    public static final int CARD_WIDTH = 190;
    public static final int CARD_HEIGHT = 260;
    public static final int CARD_INC_X = 230;
    public static final int CARD_INC_Y = 300;

    public static final int BUTTON_HEIGHT = 60;
    public static final int BUTTON_WIDTH = 130;
    public static final int SCORE_LABEL_HEIGHT = 60;
    public static final int LABEL_WIDTH = 110;

    public static final int LABEL_MARGIN = 20;

    private Point[] cardLocations;
    private boolean extended;
    private JButton noSetButton;
    private JButton hintButton;
    private JButton newGameButton;
    private SetGameLogic board = new SetGameLogic();

    public GamePanel(){
        setPreferredSize(new Dimension(CARD_PANEL_WIDTH + BUTTON_PANEL_WIDTH, PANEL_HEIGHT));
    
        cardLocations = new Point[15];

        extended = false;

        noSetButton = new JButton();
        initButton(noSetButton, "No Set");
        add(noSetButton,0,0)
        hintButton = new JButton();
        newGameButton = new JButton();

        for (int i = 0; i < cardLocations.length; ++i){
            int x = CARD_TOP_X + (i/3) * CARD_INC_X;
            int y = CARD_TOP_Y + (i%3) * CARD_INC_Y;
            cardLocations[i] = new Point(x,y);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component component = e.getComponent();
            }
        });
    }

    private void initButton(JButton button, String text){
        button.setFont(SetGame.font);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setText(text);
    }

    public void paintComponent(Graphics g){
        
        int numberOfCards = extended ? 15 : 12;
        for (int i = 0; i < numberOfCards; ++i){
            BufferedImage cardImage = readImage(String.format(board.cardAt(i).toString(),""));
            g.drawImage(cardImage, (int) cardLocations[i].getX(), (int) cardLocations[i].getY(), CARD_WIDTH, CARD_HEIGHT, null);
        }

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
