import javax.swing.JFrame;

public class GameFrame extends JFrame{
    

    GameFrame(){

        
        
        this.setTitle("SNAKE GAME");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new GamePanel());
        //this.setSize(500,500);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
