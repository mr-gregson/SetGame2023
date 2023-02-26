import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;

public class CardButton extends JButton{

    private static final Insets insets = new Insets(0,0,0,0);
    
    private ImageIcon imageIcon;
    private int index;
    
    public CardButton(MouseListener mouseListener,int index){
        this.imageIcon = new ImageIcon();
        this.index = index;
        setMargin(insets);
        setBackground(SetGame.BACKGROUND_COLOUR);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
        setSize(new Dimension(GamePanel.CARD_WIDTH,GamePanel.CARD_HEIGHT));
        setIcon(imageIcon);
        setDisabledIcon(imageIcon);
        addMouseListener(mouseListener);
    }

    public void update(BufferedImage image){
        imageIcon.setImage(image);
    }

    public int getIndex(){
        return index;
    }
}
