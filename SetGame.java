
import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SetGame extends JFrame{

    // Game Panel Dimensions

    
    public static final int BUTTON_HEIGHT = 60;
    public static final int BUTTON_WIDTH = 130;
    public static final int SCORE_LABEL_HEIGHT = 60;
    public static final int LABEL_WIDTH = 110;

    public static final int LABEL_MARGIN = 20;


    
    private static final String BLANK_PATH_STRING = "/img/blank.png";

    private SetGameLogic board;
    
    private int score;

    private JPanel gamePanel;

    private JButton noSetButton;
    private JButton hintButton;
    private JButton newGameButton;
    
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
       
        score = 0;
        

        gamePanel = new GamePanel();
        
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
