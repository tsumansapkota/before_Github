import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.event.ActionEvent;
public class Snakeclasskk extends JFrame implements KeyListener{

    int[] x = new int[800] ;
    int[] y = new int[800];
    int bb=0;
    int aa=10;
    int ud=0;
    int lr=1;
    public int ranx, rany, score=0, snakelen=5;
    boolean gamover=false;

    
public static void main(String[] args) {
        Snakeclasskk fram = new Snakeclasskk();
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fram.setSize(500, 520);
        fram.setResizable(false);
        fram.setVisible(true);
        
        
}

Snakeclasskk() {
	 super("Circle");
	 JButton b1= new JButton("Start");
	 JPanel pan1=new JPanel();
	 Random rand = new Random();
//	 int ranno = rand.nextInt(480) + 1;
	 x[0]=150;
	 y[0]=50;
	 for(int a=1; a<snakelen; a++){
		 x[a]=x[a-1]-10;
		 y[a]=50;
	 }
	ranx = rand.nextInt(47)*10+10;
	rany = rand.nextInt(47)*10+30;
	 
	 Timer timer = new Timer(100, new ActionListener() {
		 
		 @Override
         public void actionPerformed(ActionEvent e) {
        	 for(int a=snakelen-1; a>0; a--){
        		 x[a]=x[a-1];
        		 y[a]=y[a-1];
        		
        	 }
        	
        	 x[0]+=aa;
    		 y[0]+=bb;
    		 if(x[0]<10 ){x[0]=480;aa=-10;}
    		 if(y[0]<30){y[0]=500;bb=-10;}
    		 if(x[0]>=490){x[0]=10;aa=10;}
    		 if(y[0]>=510){y[0]=30;bb=10;}
    		 
    		 
    		 //after eating
    		 if(x[0]==ranx && y[0]==rany){
    			 int match;
    			do{
    				match=0;
    			 	ranx = rand.nextInt(47)*10+10;
    				rany = rand.nextInt(47)*10+30;
    				for(int ab=0; ab<snakelen; ab++){
    				if(ranx==x[ab] && rany==y[ab]){match=1;break;}
    				 }
    				
    		 }while(match==1);
    			
    			score++;
				snakelen++;
    		 } ///yaha samma eating;;
    		 
    		//snake touching body
 			for(int ab=1; ab<snakelen; ab++){
    			 if(x[0]==x[ab] &&y[0]==y[ab]){
    				 gamover=true;
    			//	 dispose();
    			 }
    		 }
    		 repaint();
         }
     });
     
	 addKeyListener(this);
	// if(x[0]<10 || y[0]<30 ||x[0]>490 ||y[0]>510){
	//		removeAll();
	//		JLabel go = new JLabel("Game Over");
	//		add(go); }
	 
	 if(!gamover)timer.start();
     
			 
    
    }

    
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fill3DRect(10, 30, 480, 480, true);
        if(!gamover){
            g.setColor(Color.ORANGE);
            for(int ab=0; ab<snakelen; ab++){
            g.fillOval(x[ab], y[ab], 10, 10);
            }
            g.setColor(Color.GREEN);
            g.fillOval(ranx, rany, 10, 10);
            }
            else{
            	g.setColor(Color.RED);
            	g.drawString(String.format("GAME OVER... :("), 200, 200);
            	g.drawString(String.format("Your Score is:%d",score), 200, 220);
            	try{
            	Thread.sleep(5000);
            	dispose();
            	}
            	catch (InterruptedException e) {
        			e.printStackTrace();
        		}
            	}
       
     //   for(int ab=0; ab<999999; ab++){}
        
  
    }
   
    	@Override
        public void keyPressed(KeyEvent ee) {
    		if (ee.getKeyCode() == KeyEvent.VK_UP && ud==0){
    			bb=-10;
    			ud=1;
    			lr=0;
    			aa=0;
    		}
    		else if(ee.getKeyCode() == KeyEvent.VK_DOWN && ud==0){
    			bb=10;
    			ud=1;
    			lr=0;
    			aa=0;
    		}
    		else if(ee.getKeyCode() == KeyEvent.VK_LEFT && lr==0){
    			aa=-10;
    			bb=0;
    			lr=1;
    			ud=0;
    		}
    		else if(ee.getKeyCode() == KeyEvent.VK_RIGHT && lr==0){
    			aa=10;
    			bb=0;
    			lr=1;
    			ud=0;
    		}
        }

		@Override
		public void keyReleased(KeyEvent ff) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent gg) {
			// TODO Auto-generated method stub
			
		}
    
}