import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

    public final static int CARD_PANEL_WIDTH = 595;
    public final static int BUTTON_PANEL_WIDTH = 150;
    public final static int PANEL_HEIGHT = 470;
    

    public final static int CARD_TOP_X = 57;
    public static final int CARD_TOP_EX = 20;
    public final static int CARD_TOP_Y = 20;

    public static final int CARD_WIDTH = 95;
    public static final int CARD_HEIGHT = 130;
    public static final int CARD_INC_X = 115;
    public static final int CARD_INC_Y = 150;

    public static final Color BACKGROUND_COLOUR = new Color(Integer.parseInt("008000",16));

    private Point[] cardLocations;
    private Point[] cardLocations_EX;
    private boolean extended;
    private SetGameLogic board = new SetGameLogic();

    public GamePanel(){
        setPreferredSize(new Dimension(CARD_PANEL_WIDTH + BUTTON_PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOUR);
        cardLocations = new Point[12];
        cardLocations_EX = new Point[15];
        extended = false;

        for (int i = 0; i < cardLocations.length; ++i){
            int x = CARD_TOP_X + (i/3) * CARD_INC_X;
            int y = CARD_TOP_Y + (i%3) * CARD_INC_Y;
            cardLocations[i] = new Point(x,y);
        }
        for (int i = 0; i < cardLocations_EX.length; ++i){
            int x = CARD_TOP_EX + (i/3) * CARD_INC_X;
            int y = CARD_TOP_Y + (i%3) * CARD_INC_Y;
            cardLocations_EX[i] = new Point(x,y);
        }
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
