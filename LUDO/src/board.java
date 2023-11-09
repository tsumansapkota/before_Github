import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.ActionEvent;
public class board extends JFrame implements MouseListener{
int cout;
Random rand;
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
JLabel ye1,ye2,ye3,ye4, xy;
boolean dispdice=false, roll=false, firsttime=true;
int yxs=383, yys=46,bxs=618, bys=361 ,rxs=289, rys=586 ,gxs=524 ,gys=271;
private Image i;
private Graphics doubleG;
int tim=100;


	public static void main(String args[]) throws IOException{
		board bo1 = new board();
		bo1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bo1.setVisible(true);
	//	bo1.setResizable(false);
		bo1.setSize(920,720);
		
		JFrame fram= new JFrame();
		fram.setVisible(false);
		fram.dispose();
		
		File file=new File("F:/Programmers/[Workspace]/Ludo/bin/b1.jpg");
		ImageIcon bag = new ImageIcon(ImageIO.read(file));
		Image bgsc =bag.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	    ImageIcon bac = new ImageIcon(bgsc);
		fram.setContentPane(new JLabel(bac,JLabel.LEFT));
		fram.setVisible(false);
		fram.setSize(920,720);
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	board(){
	    setLayout(new BorderLayout());
	    //load icon image
	    ImageIcon bg = new ImageIcon(getClass().getResource("b1.jpg"));
	    //resize image
	    Image bgsc =bg.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	    //make image --> icon image
	    ImageIcon bac = new ImageIcon(bgsc);
	  //  setContentPane(new JLabel(bac, JLabel.LEFT));
	   
	    
	    
	    
	    if(firsttime){
	    yx[0]=383;
	    yy[0]=1+45;
	    firsttime=false;}
	    //yellow 383 46,618 361 ,289 586 ,524 271  
	  //383,1 383,226 430,271 430,271 665,271 665,361 430,361 383,406 383,631 289,631 289,406 242,361 7,361 7,271 242,271 289,226 289,1
	    
	    ImageIcon yel = new ImageIcon(getClass().getResource("yel48.png"));
	  	Container y1,y2,y3,y4;
	  	y1=getContentPane();
	  	y2=getContentPane();
	  	y3=getContentPane();
	  	y4=getContentPane();
        y1.setLayout(null);
        y2.setLayout(null);
        y3.setLayout(null);
        y4.setLayout(null);
        JLabel ye1 = new JLabel(yel);
        ye1.setBounds(yx[0], yy[0], 48, 48); // x, y, width, height
        y1.add(ye1);;
        
        JLabel ye2 = new JLabel(yel);
        ye2.setBounds(yx[1], yy[1], 48, 48); // x, y, width, height
        y1.add(ye2);;
        
        JLabel ye3 = new JLabel(yel);
        ye3.setBounds(yx[2], yy[2], 48, 48); // x, y, width, height
        y3.add(ye3);;
        
        JLabel ye4 = new JLabel(yel);
        ye4.setBounds(yx[3], yy[3], 48, 48); // x, y, width, height
        y4.add(ye4);;
               
        ye1.addMouseListener(new MouseListener() {
          public void mouseClicked(MouseEvent me) {
        	  System.out.printf("\nx = %d y = %d",yx[0],yy[0]);
        	  Next kk = new Next();
              kk.Next(0);
              System.out.printf("\nx = %d y = %d",yx[0],yy[0]);
              validate();
              repaint();
             
              
          }
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
        });
        ye2.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
              Next kk = new Next();
              kk.Next(1);
             
            }
  		@Override
  		public void mouseEntered(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseExited(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mousePressed(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseReleased(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
          });
        ye3.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
            	Next kk = new Next();
                kk.Next(2);
            }
  		@Override
  		public void mouseEntered(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseExited(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mousePressed(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseReleased(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
          });
        ye4.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
            	Next kk = new Next();
                kk.Next(3);
            }
  		@Override
  		public void mouseEntered(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseExited(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mousePressed(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		@Override
  		public void mouseReleased(MouseEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
          });
        
        
		//add(new JLabel(new ImageIcon("b1.jpg")));
	    rand = new Random();
		cout= rand.nextInt(5)+1; 
	    
	    addMouseListener(this);
	   
		
	}
	@Override
	public void update(Graphics aa){
		if (i==null){
			i=createImage(this.getSize().width,this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		aa.drawImage(i, 0, 0, this);
	}
	//upto here constructor
	public void paint (Graphics aa){
		super.paint(aa);
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
		
		ImageIcon yel = new ImageIcon(getClass().getResource("yel48.png"));
		Image image = yel.getImage();
		
		aa.drawImage(image, yx[0], yy[0], null);
		
		if(roll){
		cout= this.rand.nextInt(5)+1;  
		}   
		aa.setColor(Color.GREEN);
		//aa.fillOval(260, 260, 30, 30);
		
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

		
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		roll=true;
		dispdice=false;

	//		Timer timer = new Timer (tim, new ActionListener(){
	//		@Override
	//		public void actionPerformed(ActionEvent act) {
			repaint();
	//		}});
	 //   if(roll)timer.start(); 
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		dispdice=true;
		roll=false;
		tim=1000;
		repaint();
		
		
		
	}
	public class Next{
	public void Next(int no){
	//	x+=47;
	//	y+=45;
	//yellow 383 46,618 361 ,289 586 ,524 271  
	//383,1 383,226 // 430,271 665,271 665,361 430,361 // 383,406 383,631 289,631 289,406 // 242,361 7,361 7,271 242,271 // 289,226 289,1	
		int x=yx[no],y=yy[no];
		if(x==383&&y==226){x+=47;y+=45;}
		else if(x==430&&y==361){x-=47;y+=45;}
		else if(x==289&&y==406){x-=47;y-=45;}
		else if(x==242&&y==271){x+=47;y-=45;}
		else if(x==383&&y>=1&&y!=631){y+=45;}
		else if(x<=665&&y==361&&x!=7){x-=47;}
		else if(x==665&&y>=271){y+=45;}
		else if(x>=430&&y==271){x+=47;}
		//else if(x==383&&y>=406){y+=45;}
		else if(x==289&&y<=631&&y>1){y-=45;}
		else if(x<=383&&y==631){x-=47;}
		else if(x>=7&&y==271){x+=47;}
		else if(x==7&&y<=361){y-=45;}
		//else if(x<=242&&y==361){x-=47;}
		else if(x>=289&&y==1){x+=47;}
		
		System.out.printf("\nNext executed");
		yx[no]=x;
		yy[no]=y;
		
		
		
	}
	}
}
