import javax.swing.*;
import java.awt.*;
public class dicemain extends JFrame {
boolean first=true;
JLayeredPane jlp;


	public static void main(String args[]){
		dicemain bo1 = new dicemain();
		bo1.setVisible(true);
		
		
	}
	dicemain(){
		setSize(920,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	  //  pack();
	    ImageIcon bg = new ImageIcon(getClass().getResource("b1.jpg"));
	    Image bgsc =bg.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	    ImageIcon bac = new ImageIcon(bgsc);
	//    if(first){ setContentPane(new JLabel(bac, JLabel.LEFT));first=false;}
	    Container cbg;
	    cbg=getContentPane();
	    cbg.setLayout(null);
	    JLabel jlc = new JLabel(bac);
        jlc.setBounds(100, 100, 48, 48); // x, y, width, height
        cbg.add(jlc);
	    
	    window start = new window();
	    JLabel label = new JLabel(bac);
	    label.add(start);
	    getContentPane();
	    start.setOpaque(false);
	  	getContentPane().add(start);
	    }
	public class window extends JPanel{
		window(){
			JButton b1 = new JButton("aaa");
			getContentPane();
			add(b1);
		}
		
		public void paintComponent (Graphics aa){
			super.paintComponent(aa);
			aa.setColor(Color.GRAY);
			
			aa.fillRect(100, 100, 200, 300);
			aa.setColor(Color.GRAY);
			
			aa.fillArc(200+550, 200-150, 50, 50, 90, 90);
			aa.fillArc(200+550, 200-150, 50, 50, 90, 90);
			aa.fillArc(300+550, 200-150, 50, 50, 0, 90);
			aa.fillArc(200+550, 300-150, 50, 50, 180, 90);
			aa.fillArc(300+550, 300-150, 50, 50, 270, 90);
			
			aa.drawLine(225+550, 200-150, 325+550, 200-150);
			aa.drawLine(350+550, 225-150, 350+550, 325-150);
			aa.drawLine(325+550, 350-150, 225+550, 350-150);
			aa.drawLine(200+550, 325-150, 200+550, 225-150);
			
			aa.fillRect(220+550, 200-150,105,25);
			aa.fillRect(220+550, 325-150,105,25);
			aa.fillRect(200+550, 225-150,25,105);
			aa.fillRect(325+550, 225-150,25,105);
			aa.fillRect(220+550, 225-150,110,100);
		}
	}
}
