import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SetGame extends JFrame{

    private SetGameLogic board;
    
    private JPanel gamePanel;

    public static final Color BACKGROUND_COLOUR = new Color(Integer.parseInt("008000",16));
    public static final Font font = new Font(Font.SANS_SERIF,Font.BOLD,28);
    
    public SetGame(SetGameLogic board) {
        this.board = board;
        intializeGUI();
        
        setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void intializeGUI(){
        setBackground(BACKGROUND_COLOUR);
        gamePanel = new GamePanel(board);
        
        add(gamePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        SetGameLogic board = new SetGameLogic();
        try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		}
		catch (Exception e) {}

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new SetGame(board);
            }
        });
    }

    




    


}
