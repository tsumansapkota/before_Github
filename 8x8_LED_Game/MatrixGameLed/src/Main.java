import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        JFrame jFrame=new JFrame("The game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setSize(265,300);
        jFrame.setResizable(false);
        GameJpanel gameJpanel=new GameJpanel();
        jFrame.add(gameJpanel);
        jFrame.addKeyListener(gameJpanel);
    }
}
