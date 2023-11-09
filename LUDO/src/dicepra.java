import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
public class dicepra extends JPanel implements MouseListener {
boolean first=true, roll=true;
boolean ft=true,dispdice1=false;
JLayeredPane jlp;
int cout;
int[] stepy= new int[4];
int[] stepb= new int[4];
int[] stepr= new int[4];
int[] stepg= new int[4];
int[] rx= new int[4];
int[] ry= new int[4];
int[] bx= new int[4];
int[] by= new int[4];
int[] gx= new int[4];
int[] gy= new int[4];
int[] yx= new int[4];
int[] yy= new int[4];

public static void main(String args[]) {
	
	JFrame fram= new JFrame();
	fram.setVisible(true);
	
	File file=new File("F:/Programmers/[Workspace]/Ludo/bin/b1.jpg");
	ImageIcon bag = null;
	try {
		bag = new ImageIcon(ImageIO.read(file));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	Image bgsc =bag.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	ImageIcon bac = new ImageIcon(bgsc);
//	fram.setContentPane(new JLabel(bac,JLabel.LEFT));
//	fram.setVisible(true);
	fram.setSize(920,720);
//	fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	dicepra dicr = new dicepra();
	if(dicr.ft){
	    dicr.yx[0]=383;
	    dicr.yy[0]=1+45;
	  dicr.ft=false;}
	
	JPanel panel = new JPanel();
	
    panel.setLayout(new OverlayLayout(panel));
    panel.add(dicr);
    panel.add(new JLabel(bac));   
    fram.add(panel);
    fram.revalidate();
        
}


dicepra(){
		
		  repaint();
		  addMouseListener(this);
		    }
		
public void paintComponent (Graphics aa){
	super.paintComponent(aa);
	aa.setColor(Color.GRAY);
	File file=new File("F:/Programmers/[Workspace]/Ludo/bin/b1.jpg");
	ImageIcon bag = null;
	try {
		bag = new ImageIcon(ImageIO.read(file));
	} catch (IOException e) {
		e.printStackTrace();
	}
	Image bgsc =bag.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	aa.drawImage(bgsc, 0, 0, 720, 720, null);
	
	
	aa.fillArc(200, 200, 100, 100, 90, 90);
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
	

	aa.fillOval(220+550, 220-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	aa.fillOval(300+550, 300-150, 30, 30);

//	ImageIcon yel = new ImageIcon(getClass().getResource("yel48.png"));
//	Image image = yel.getImage();
//	aa.drawImage(image, 33, 200, null);
	
	
	Random rand=new Random();
//	 
	aa.setColor(Color.GREEN);
	//aa.fillOval(260, 260, 30, 30);
	if(roll){cout= rand.nextInt(5)+1;}  
	
	switch (cout){
	case 1:{aa.fillOval(260+550, 260-150, 30, 30);
	break;}
	case 2:{aa.fillOval(220+550, 260-150, 30, 30);
	aa.fillOval(300+550, 260-150, 30, 30);	
	break;}
	case 3:{aa.fillOval(260+550, 260-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	break;}
	case 4:{aa.fillOval(220+550, 220-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	aa.fillOval(300+550, 300-150, 30, 30);
	break;}
	case 5:{aa.fillOval(260+550, 260-150, 30, 30);
	aa.fillOval(220+550, 220-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	aa.fillOval(300+550, 300-150, 30, 30);
	break;}
	case 6:{aa.fillOval(220+550, 220-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	aa.fillOval(300+550, 300-150, 30, 30);
	aa.fillOval(220+550, 260-150, 30, 30);
	aa.fillOval(300+550, 260-150, 30, 30);	
	break;}}
	aa.setFont(new Font("TimesRoman", Font.BOLD, 25));
	if(dispdice){aa.drawString(String.format("You Got: %d",cout), 770, 500);}
	
aa.dispose();		
}


@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mousePressed(MouseEvent arg0) {
	roll=true;
	dispdice=false;

		Timer timer = new Timer (100, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent act) {
		repaint();
		}});
    if(roll)timer.start(); 
}
@Override
public void mouseReleased(MouseEvent arg0) {
	dispdice=true;
	roll=false;
	repaint();
	
}
	
}

//////////////
else if((stps[but]+cout)>50&&(cout+stps[but])<=57){
	for(int ab=0; ab<cout; ab++){
	if(stps[but]>50){	
	if(color==0){yy[but]+=45;stepy[but]++;}
	if(color==3){ry[but]-=45;stepb[but]++;}
	if(color==2){bx[but]-=47;stepr[but]++;}
	if(color==4){gx[but]+=47;stepg[but]++;}
	System.out.println("last step");
	}
	else{move.next(but);
	stps[but]++;
	System.out.println("second last step");}
	
	}

