import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class SnakePract extends JFrame {

    int x = 00;
    int y = 00;
    
public static void main(String[] args) {
        SnakePract fram = new SnakePract();
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fram.setSize(500, 500);
        fram.setVisible(true);
        
}

SnakePract() {
	 super("Circle");
	 JButton b1= new JButton("Left");
	 JButton b2= new JButton("Right");
	 JButton b3= new JButton("Up");
	 JButton b4= new JButton("Down");
	 JPanel pan1=new JPanel();
	 JPanel pan2=new JPanel();
			 
     pan1.add(b1);
     pan1.add(b2);
     pan1.add(b3);
     pan1.add(b4);
     	b1.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				    x-=10;
        	               // y+=10;
        	                repaint();
        	            
        			}	}	);
     	b2.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				    x+=10;
        	               // y+=10;
        	                repaint();
        	            
        			}	}	);
     	b3.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				  //  x-=10;
        	               y-=10;
        	                repaint();
        	            
        			}	}	);
     	b4.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				   // x-=10;
        	                y+=10;
        	                repaint();
        	            
        			}	}	);
        add(pan1);
    }

    
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(x, y, 100, 100);
    }

  
}