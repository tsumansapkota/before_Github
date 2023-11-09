import javax.imageio.ImageIO;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
public class dice3 extends JPanel implements MouseListener {
boolean first=true, roll=true,tof=false, actper=false, gamy=false,gamb=false,gamr=true,gamg=false;
boolean ft=true,dispdice=false, movekey=false, fir=true, skip=true, rollstop=false, diceclick=false, rom=true, drawsix=false, gatticlick=false, mov=true ;
JFrame fram;
int selno=0, cout,color=0, clickno=0, coutdubli, scorey=0, scoreb=0, scorer=0, scoreg=0, sixes=0,colr=0, maxstack=0, winner=0;
int[] stepy= new int[4];
int[] stepb= new int[4];
int[] stepr= new int[4];
int[] stepg= new int[4];
int[] stacky= new int[4];
int[] stackb= new int[4];
int[] stackr= new int[4];
int[] stackg= new int[4];
int[] rx= new int[4];
int[] ry= new int[4];
int[] bx= new int[4];
int[] by= new int[4];
int[] gx= new int[4];
int[] gy= new int[4];
int[] yx= new int[4];
int[] yy= new int[4];
int yxs=383, yys=46,bxs=618, bys=361 ,rxs=289, rys=586 ,gxs=54 ,gys=271;

public static void main(String args[]) {
	dice3 dic=new dice3();
	JFrame fram= new JFrame();
	fram.setVisible(true);
	fram.setResizable(false);
	
	File file=new File("F:/Programmers/[Workspace]/Ludo/bin/bg1.jpg");
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
	fram.setVisible(true);
	fram.setSize(920,720);
	fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	dice3 dicr = new dice3();
	for(int ab=0;ab<4;ab++){
	dicr.stepy[ab]=0;
	dicr.stepb[ab]=0;
	dicr.stepr[ab]=0;
	dicr.stepg[ab]=0;
	dicr.stacky[ab]=0;
	dicr.stackb[ab]=0;
	dicr.stackr[ab]=0;
	dicr.stackg[ab]=0;}
	
	JPanel panel = new JPanel();
	
    panel.setLayout(new OverlayLayout(panel));
    panel.add(dicr);
    panel.add(new JLabel(bac));   
   fram.add(panel);
   fram.revalidate();
        
}


dice3(){
	if(stepy[0]==0){yx[ 0]= 500  ;	yy[ 0]=75;}
	if(stepy[1]==0){yx[ 1]= 590  ;	yy[ 1]= 75;}
	if(stepy[2]==0){yx[ 2]= 500  ;	yy[ 2]= 155;}
	if(stepy[3]==0){yx[ 3]=590 ;	yy[ 3]= 155;}
	if(stepr[0]==0){rx[ 0]= 77  ;ry[ 0]= 480 ;}
	if(stepr[1]==0){rx[ 1]= 167  ;ry[ 1]= 480;}
	if(stepr[2]==0){rx[ 2]= 77  ;ry[ 2]= 560;}
	if(stepr[3]==0){rx[ 3]= 167  ;ry[ 3]= 560;}
	if(stepg[0]==0){gx[ 0]= 77  ;gy[ 0]= 75 ;}
	if(stepg[1]==0){gx[ 1]= 167  ;gy[ 1]= 75;}
	if(stepg[2]==0){gx[ 2]= 77  ;gy[ 2]= 155;}
	if(stepg[3]==0){gx[ 3]= 167  ;gy[ 3]= 155;}
	if(stepb[0]==0){bx[ 0]= 500  ;by[ 0]=480;}
	if(stepb[1]==0){bx[ 1]= 590  ;by[ 1]= 480;}
	if(stepb[2]==0){bx[ 2]= 500  ;by[ 2]= 560;}
	if(stepb[3]==0){bx[ 3]=590 ;by[ 3]= 560;}
		  addMouseListener(this);
		    }
		
public void paintComponent (Graphics aa){
	super.paintComponent(aa);
	try
    {
        Thread.sleep(20);
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
	aa.setColor(Color.GRAY);
	File file=new File("F:/Programmers/[Workspace]/Ludo/src/b1.jpg");
	File filey=new File("F:/Programmers/[Workspace]/Ludo/src/yel48.png");
	File fileb=new File("F:/Programmers/[Workspace]/Ludo/src/blu48.png");
	File filer=new File("F:/Programmers/[Workspace]/Ludo/src/red48.png");
	File fileg=new File("F:/Programmers/[Workspace]/Ludo/src/gre48.png");
	ImageIcon bag = null;
	ImageIcon yel = null;
	ImageIcon blu = null;
	ImageIcon red = null;
	ImageIcon gre = null;
	ImageIcon ys = null;
	ImageIcon bs = null;
	ImageIcon rs = null;
	ImageIcon gs = null;
	int x=0, y=4;
	try {
		bag = new ImageIcon(ImageIO.read(file));
		yel = new ImageIcon(ImageIO.read(filey));
		blu= new ImageIcon(ImageIO.read(fileb));
		red = new ImageIcon(ImageIO.read(filer));
		gre = new ImageIcon(ImageIO.read(fileg));
		ys = new ImageIcon(ImageIO.read(new File("F:/Programmers/[Workspace]/Ludo/bin/sy.png")));
		bs = new ImageIcon(ImageIO.read(new File("F:/Programmers/[Workspace]/Ludo/bin/sb.png")));
		rs = new ImageIcon(ImageIO.read(new File("F:/Programmers/[Workspace]/Ludo/bin/sr.png")));
		gs = new ImageIcon(ImageIO.read(new File("F:/Programmers/[Workspace]/Ludo/bin/sg.png")));
		} catch (IOException e) {
		e.printStackTrace();
	}
	Image bgsc =bag.getImage().getScaledInstance(720, 720,Image.SCALE_DEFAULT);
	aa.drawImage(bgsc, 0, -20, 720, 720, null);
	Image yell =yel.getImage();
	Image blue =blu.getImage();
	Image redd =red.getImage();
	Image gree =gre.getImage();
	
	//steps 1 14 27 40
	
/*	for(int i=0; i<4; i++){
	aa.drawImage(yell, yx[i], yy[i], 48, 48, null);
	aa.drawImage(blue, bx[i], by[i], 48, 48, null);
	aa.drawImage(redd, rx[i], ry[i], 48, 48, null);
	aa.drawImage(gree, gx[i], gy[i], 48, 48, null);
	}*/
	for(int j=7; j>0; j--){
		
		for(int i=0; i<4; i++){
			if(stacky[i]==j)aa.drawImage(yell, yx[i]+x*j, yy[i]+y*j, 48, 48, null);
			if(stackb[i]==j)aa.drawImage(blue, bx[i]+x*j, by[i]+y*j, 48, 48, null);
			if(stackr[i]==j)aa.drawImage(redd, rx[i]+x*j, ry[i]+y*j, 48, 48, null);
			if(stackg[i]==j)aa.drawImage(gree, gx[i]+x*j, gy[i]+y*j, 48, 48, null);
		}
	}
	for(int i=0; i<4; i++){
		if(stacky[i]==0)aa.drawImage(yell, yx[i], yy[i], 48, 48, null);
		if(stackb[i]==0)aa.drawImage(blue, bx[i], by[i], 48, 48, null);
		if(stackr[i]==0)aa.drawImage(redd, rx[i], ry[i], 48, 48, null);
		if(stackg[i]==0)aa.drawImage(gree, gx[i], gy[i], 48, 48, null);
	}
	aa.drawImage(ys.getImage(),yxs-95 ,yys+45 , 50,50, null);
	aa.drawImage(bs.getImage(),bxs-49 ,bys-90 , 50,50, null);
	aa.drawImage(rs.getImage(),rxs+92 ,rys-45 , 50,50, null);
	aa.drawImage(gs.getImage(),gxs+46 ,gys+89 , 50,50, null);
	
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
	if(roll){cout= rand.nextInt(6)+1;
	cout= rand.nextInt(6)+1;
	cout= rand.nextInt(6)+1;
//	if(cout>2)cout=1;
//	else cout=1;
	cout=6;
	}  
	
	switch (cout){
	case 1:{if(!drawsix)aa.fillOval(260+550, 260-150, 30, 30);
	else{aa.fillOval(220+550, 220-150, 30, 30);
	aa.fillOval(220+550, 300-150, 30, 30);
	aa.fillOval(300+550, 220-150, 30, 30);
	aa.fillOval(300+550, 300-150, 30, 30);
	aa.fillOval(220+550, 260-150, 30, 30);
	aa.fillOval(300+550, 260-150, 30, 30);	}
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
	aa.setColor(Color.BLACK);
	aa.setFont(new Font("Counter-Strike", Font.BOLD, 40));
	if(dispdice){
	
	if(drawsix){aa.setFont(new Font("Segoe Print", Font.BOLD, 40));aa.drawString(String.format("3 SIX"), 780, 30);	}
	else aa.drawString(String.format("%d",cout), 800, 30);
	Image im=null;
	if(color==0){im=yell;}
	if(color==1){im=blue;}
	if(color==2){im=redd;}
	if(color==3){im=gree;}
	
	aa.drawImage(im, 800, 400, 48, 48, null);
	aa.setColor(Color.BLACK);
	aa.setFont(new Font("Segoe Print", Font.BOLD, 40));
	aa.drawString(String.format("Player"), 730, 400);
	if(!movekey)	aa.drawString(String.format("Roll"), 770, 500);	
	else aa.drawString(String.format("Move"), 770, 500);
}
	
	try
    {
        Thread.sleep(20);
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
	}


@Override
public void mouseClicked(MouseEvent arg0) {
		
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
	clickno++;
	coutdubli=cout;
	if (fir&&arg0.getX()>=750&&arg0.getY()>=50&&arg0.getX()<=950&&arg0.getY()<=250){repaint();fir=false;diceclick=true;}
	if(!movekey){
		int[] stps=new int[4];

		for(int no=0; no<4;no++){
			if(color==0){stps[no]=stepy[no];}
			if(color==1){stps[no]=stepb[no];}
			if(color==2){stps[no]=stepg[no];}
			if(color==3){stps[no]=stepr[no];}
			}
		if(!(stps[0]>0||stps[1]>0||stps[2]>0||stps[3]>0)||cout!=1||cout!=6){
			skip=false;}
		else skip=true;
		if(arg0.getX()>=750&&arg0.getY()>=50&&arg0.getX()<=950&&arg0.getY()<=250){
		roll=true;
		dispdice=false;
		diceclick=true;
		drawsix=false;
		Timer timer = new Timer (100, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent act) {
		repaint();
		}});
    if(roll)timer.start(); 
    if(!skip)movekey=true;
    rollstop=true;
    
	}
	
	}
	
	if(movekey){
		int[] x=new int[4];
		int[] y=new int[4];
		int[] stps=new int[4];
		mov=true;
		
		for(int no=0; no<4;no++){
			if(color==0){x[no]=yx[no];y[no]=yy[no];stps[no]=stepy[no];}
			if(color==1){x[no]=bx[no];y[no]=by[no];stps[no]=stepb[no];}
			if(color==2){x[no]=rx[no];y[no]=ry[no];stps[no]=stepr[no];}
			if(color==3){x[no]=gx[no];y[no]=gy[no];stps[no]=stepg[no];}
			}
//		if(stps[0]==0&&stps[1]==0&&stps[2]==0&&stps[3]==0&&cout!=1){
//			if(cout!=6){
//			if (color<4)color++;
//			else color=0;}
//			if (color==4)color=0;
//			movekey=false;
//			repaint();
//			System.out.println("...."+color+"not started");
//		}
		
		for(int but=0; but<4;but++){
			
			if(arg0.getX()+2>=x[but]&&arg0.getY()>=y[but]&&arg0.getX()<=x[but]+47&&arg0.getY()<=y[but]+45){
			//	System.out.println("you clicked");;
				Next move = new Next();
				int[] stacks=new int[4];
				for(int bu=0; bu<4;bu++){
				if(color==0){stacks[bu]=stacky[bu];}
				if(color==1){stacks[bu]=stackb[bu];}
				if(color==2){stacks[bu]=stackr[bu];}
				if(color==3){stacks[bu]=stackg[bu];}}
				boolean add=true, toft=false;		 selno=but;	
				tof=false;	
				int stepp=stps[but];
				for(int ab=1; ab<=48; ab+=5){
					for(int i=0; i<4; i++){
					if(stepy[i]==ab||stepb[i]==ab||stepr[i]==ab||stepg[i]==ab){tof=true;}
					if(stepp==ab)toft=true;}
				if(add){ab+=3;add=false;}else add=true;	
				}
				boolean trf=false, trfa=false;;
				for(int t=0;t<4;t++)if(t!=but)if(stps[but]==stps[t])trfa=true;
				add=true;					
				for(int ab=1; ab<=48; ab+=5){
					if(stps[but]==ab)trf=true;
				if(add){ab+=3;add=false;}else add=true;	}
				add=false;
				if (stps[but]==0&&cout==1){
					if(color==0){yx[but]=yxs;yy[but]=yys;stepy[but]++;stacks[but]=stacky[but];}
					if(color==1){bx[but]=bxs;by[but]=bys;stepb[but]++;stacks[but]=stackb[but];}
					if(color==2){rx[but]=rxs;ry[but]=rys;stepr[but]++;stacks[but]=stackr[but];}
					if(color==3){gx[but]=gxs;gy[but]=gys;stepg[but]++;stacks[but]=stackg[but];}
			//		System.out.println("...."+color+"executed...");
					movekey=false;
					gatticlick=true;
					sixes=0;
					int ab=1+color*13;
					for(int i=0;i<4;i++){
						if(stepy[i]==ab&&(color!=0||(color==0&&i!=but)))stacky[i]++;
						if((stepb[i]+13==ab||stepb[i]-39==ab)&&(color!=1||(color==1&&i!=but)))stackb[i]++;
						if((stepr[i]+26==ab||stepr[i]-26==ab)&&(color!=2||(color==2&&i!=but)))stackr[i]++;
						if((stepg[i]+39==ab||stepg[i]-13==ab)&&(color!=3||(color==3&&i!=but)))stackg[i]++;
		
						}
			//		tof=true;
					break;
				}
				else if(stps[but]!=0&&(stps[but]+cout)>57){
					System.out.println("this move is impossible");
				}
				//..............................
				else if(stps[but]!=0&&stps[but]<58&&stacks[but]!=0&&cout==1&&trf){									
				JDialog sm1 = new JDialog();
				JPanel jf= new JPanel();
				sm1.add(jf);
				sm1.setVisible(true);
				sm1.setBounds(arg0.getX()-50,arg0.getY()-50,100,200);
				sm1.addWindowListener(new WindowAdapter()
		        {
		            @Override
		            public void windowClosing(WindowEvent ee)
		            {
		                System.out.println("Window.2Closed");
		                movekey=true;
		                ee.getWindow().dispose();
		            }
		            @Override
		            public void windowDeactivated(WindowEvent ee)
		            {
		                System.out.println("Window.22Closed");
		                movekey=true;
		                sm1.dispose();
		            }
		        });
				for(int ii=0;ii<4;ii++)if (stacks[ii]>0){
			///////////.........
					
		//			if(stps[1]==stps[selno]&&stacks[1]>0)
					System.out.println(selno+""+stps[selno]);
					if(stps[0]==stps[selno]&&!(stacks[1]+1==stacks[0]&&stps[1]==stps[selno])&&!(stacks[2]+1==stacks[0]&&stps[2]==stps[selno])&&!(stacks[3]+1==stacks[0]&&stps[3]==stps[selno])){
						sm1.setVisible(true);
						JButton b1=new JButton("Rise 0");
						jf.add(b1);
						b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sm1.dispose();
				int ab=stepp+color*13;
				if(ab>52)ab-=52;
				for(int i=0;i<4;i++){
				
					if(stepy[i]==ab&&stacks[0]-stacky[i]==1){
						stacks[0]--;stacky[i]++;
						System.out.println("rising0");break;}
					if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[0]-stackb[i]==1){
						stacks[0]--;stackb[i]++;
						System.out.println("rising1");break;}
					if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[0]-stackr[i]==1){
						stacks[0]--;stackr[i]++;
						System.out.println("rising2");break;}
					if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[0]-stackg[i]==1){
						stacks[0]--;stackg[i]++;
						System.out.println("rising3");break;}}
				sm1.dispose();
				if(color==0)stacky[0]=stacks[0];
				if(color==1)stackb[0]=stacks[0];
				if(color==2)stackr[0]=stacks[0];
				if(color==3)stackg[0]=stacks[0];
			}});break;
					}
			////////.............
					
					if(stacks[1]>0&&stps[1]==stps[selno]&&!(stacks[0]+1==stacks[1]&&stps[0]==stps[selno])&&!(stacks[2]+1==stacks[1]&&stps[2]==stps[selno])&&!(stacks[3]+1==stacks[1]&&stps[3]==stps[selno])){
						sm1.setVisible(true);
						JButton b1=new JButton("Rise 1");
						jf.add(b1);
						b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sm1.dispose();
				int ab=stepp+color*13;
				if(ab>52)ab-=52;
				for(int i=0;i<4;i++){
				
					if(stepy[i]==ab&&stacks[1]-stacky[i]==1){
						stacks[1]--;stacky[i]++;
						System.out.println("rising0");break;}
					if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[1]-stackb[i]==1){
						stacks[1]--;stackb[i]++;
						System.out.println("rising1");break;}
					if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[1]-stackr[i]==1){
						stacks[1]--;stackr[i]++;
						System.out.println("rising2");break;}
					if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[1]-stackg[i]==1){
						stacks[1]--;stackg[i]++;
						System.out.println("rising3");break;}}
				sm1.dispose();
				if(color==0)stacky[1]=stacks[1];
				if(color==1)stackb[1]=stacks[1];
				if(color==2)stackr[1]=stacks[1];
				if(color==3)stackg[1]=stacks[1];
			}});break;
					}
	///////////////////........
					if(stacks[2]>0&&stps[2]==stps[selno]&&!(stacks[1]+1==stacks[2]&&stps[1]==stps[selno])&&!(stacks[0]+1==stacks[2]&&stps[0]==stps[selno])&&!(stacks[3]+1==stacks[2]&&stps[3]==stps[selno])){
						sm1.setVisible(true);
						JButton b1=new JButton("Rise 2");
						jf.add(b1);
						b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sm1.dispose();
				int ab=stepp+color*13;
				if(ab>52)ab-=52;
				for(int i=0;i<4;i++){
				
					if(stepy[i]==ab&&stacks[2]-stacky[i]==1){
						stacks[2]--;stacky[i]++;
						System.out.println("rising0");break;}
					if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[2]-stackb[i]==1){
						stacks[2]--;stackb[i]++;
						System.out.println("rising1");break;}
					if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[2]-stackr[i]==1){
						stacks[2]--;stackr[i]++;
						System.out.println("rising2");break;}
					if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[2]-stackg[i]==1){
						stacks[2]--;stackg[i]++;
						System.out.println("rising3");break;}}
				sm1.dispose();
				if(color==0)stacky[2]=stacks[2];
				if(color==1)stackb[2]=stacks[2];
				if(color==2)stackr[2]=stacks[2];
				if(color==3)stackg[2]=stacks[2];
			}});
					}
			////////.............
					if(stacks[3]>0&&stps[3]==stps[selno]&&!(stacks[1]+1==stacks[3]&&stps[1]==stps[selno])&&!(stacks[2]+1==stacks[3]&&stps[2]==stps[selno])&&!(stacks[0]+1==stacks[3]&&stps[0]==stps[selno])){
						sm1.setVisible(true);
						JButton b1=new JButton("Rise 3");
						jf.add(b1);
						b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sm1.dispose();
				int ab=stepp+color*13;
				if(ab>52)ab-=52;
				for(int i=0;i<4;i++){
				
					if(stepy[i]==ab&&stacks[3]-stacky[i]==1){
						stacks[3]--;stacky[i]++;
						System.out.println("rising0");break;}
					if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[3]-stackb[i]==1){
						stacks[3]--;stackb[i]++;
						System.out.println("rising1");break;}
					if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[3]-stackr[i]==1){
						stacks[3]--;stackr[i]++;
						System.out.println("rising2");break;}
					if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[3]-stackg[i]==1){
						stacks[3]--;stackg[i]++;
						System.out.println("rising3");break;}}
				sm1.dispose();
				if(color==0)stacky[3]=stacks[3];
				if(color==1)stackb[3]=stacks[3];
				if(color==2)stackr[3]=stacks[3];
				if(color==3)stackg[3]=stacks[3];
			}});
					}
			////////.............

					break;
				}
				
			}
				
				
				else if(stps[but]!=0&&stps[but]<58&&stacks[but]==0){
					System.out.print(stacks[0]);
					System.out.print(stacks[1]);
					System.out.print(stacks[2]);
					System.out.print(stacks[3]);
					trfa=false;
					trf=false;
					for(int t=0;t<4;t++)if(t!=but)if(stps[but]==stps[t])trfa=true;
					add=true;					
					for(int ab=1; ab<=48; ab+=5){
						if(stps[but]==ab)trf=true;
					if(add){ab+=3;add=false;}else add=true;	}
					add=false;
				if((stacks[1]!=0||stacks[2]!=0||stacks[3]!=0||stacks[0]!=0)&&cout==1&&stacks[but]==0&&trfa&&trf){
							mov=false;
							JDialog chose = new JDialog();
							chose.setVisible(true);
							chose.setBounds(arg0.getX()-50,arg0.getY()-50, 100, 200);
							chose.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							JPanel pan1 = new JPanel();
							JButton b1 = new JButton("MOVE");
							b1.setBounds(0,0,100, 100);
							pan1.add(b1);
							pan1.setSize(100, 40);
							chose.add(pan1);
							chose.addWindowListener(new WindowAdapter()
					        {
					            @Override
					            public void windowClosing(WindowEvent e)
					            {
					                System.out.println("Window1Closed");
					                movekey=true;
					                e.getWindow().dispose();
					            }
					            @Override
					            public void windowDeactivated(WindowEvent e)
					            {
					                System.out.println("Window11Closed");
					                if(!actper)movekey=true;
					                actper=false;
					                e.getWindow().dispose();
					            }
					        });
							b1.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									actper=true;
									move.next(selno);
									chose.dispose();
									int ab=stepp+color*13;
									if(ab>52)ab-=52;
									for(int i=0;i<4;i++){
										if(stepy[i]==ab&&(color!=0||(color==0&&i!=selno)))stacky[i]--;
										if((stepb[i]+13==ab||stepb[i]-39==ab)&&(color!=1||(color==1&&i!=selno)))stackb[i]--;
										if((stepr[i]+26==ab||stepr[i]-26==ab)&&(color!=2||(color==2&&i!=selno)))stackr[i]--;
										if((stepg[i]+39==ab||stepg[i]-13==ab)&&(color!=3||(color==3&&i!=selno)))stackg[i]--;
								}
									movekey=false;
								}});
							
							JButton b2 = new JButton("RISE");
							b2.setBounds(00, 100,100,30);
							pan1.add(b2);
							b2.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									////..
									chose.dispose();
									JDialog sm = new JDialog();
									JPanel jf= new JPanel();
									sm.add(jf);
									sm.setBounds(arg0.getX()-50,arg0.getY()-50,100,200);
									sm.addWindowListener(new WindowAdapter()
							        {
							            @Override
							            public void windowClosing(WindowEvent e)
							            {
							                System.out.println("Window2Closed");
							                movekey=true;
							                e.getWindow().dispose();
							            }
							            @Override
							            public void windowDeactivated(WindowEvent e)
							            {
							                System.out.println("Window22Closed");
							                if(!actper)movekey=true;
							                actper=false;
							                e.getWindow().dispose();
							            }
							        });
									for(int ii=0;ii<4;ii++)if (stacks[ii]>0){
								///////////.........replace these all condition from above
										if(stps[0]==stps[selno]&&!(stacks[1]+1==stacks[0]&&stps[1]==stps[selno])&&!(stacks[2]+1==stacks[0]&&stps[2]==stps[selno])&&!(stacks[3]+1==stacks[0]&&stps[3]==stps[selno])){
											sm.setVisible(true);
											JButton b1=new JButton("Rise 0");
											jf.add(b1);
											b1.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									actper=true;
									sm.dispose();
									movekey=false;
									int ab=stepp+color*13;
									if(ab>52)ab-=52;
									for(int i=0;i<4;i++){
								
										if(stepy[i]==ab&&stacks[0]-stacky[i]==1){
											stacks[0]--;stacky[i]++;
											System.out.println("rising0");break;}
										if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[0]-stackb[i]==1){
											stacks[0]--;stackb[i]++;
											System.out.println("rising1");break;}
										if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[0]-stackr[i]==1){
											stacks[0]--;stackr[i]++;
											System.out.println("rising2");break;}
										if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[0]-stackg[i]==1){
											stacks[0]--;stackg[i]++;
											System.out.println("rising3");break;}}
									
									if(color==0)stacky[0]=stacks[0];
									if(color==1)stackb[0]=stacks[0];
									if(color==2)stackr[0]=stacks[0];
									if(color==3)stackg[0]=stacks[0];
								}});
										}
								////////.............
										if(stacks[1]>0&&stps[1]==stps[selno]&&!(stacks[0]+1==stacks[1]&&stps[0]==stps[selno])&&!(stacks[2]+1==stacks[1]&&stps[2]==stps[selno])&&!(stacks[3]+1==stacks[1]&&stps[3]==stps[selno])){
												sm.setVisible(true);
											JButton b1=new JButton("Rise 1");
											jf.add(b1);
											b1.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									actper=true;sm.dispose();
									movekey=false;
									int ab=stepp+color*13;
									if(ab>52)ab-=52;
									for(int i=0;i<4;i++){
									
										if(stepy[i]==ab&&stacks[1]-stacky[i]==1){
											stacks[1]--;stacky[i]++;
											System.out.println("rising0");break;}
										if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[1]-stackb[i]==1){
											stacks[1]--;stackb[i]++;
											System.out.println("rising1");break;}
										if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[1]-stackr[i]==1){
											stacks[1]--;stackr[i]++;
											System.out.println("rising2");break;}
										if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[1]-stackg[i]==1){
											stacks[1]--;stackg[i]++;
											System.out.println("rising3");break;}}
									
									if(color==0)stacky[1]=stacks[1];
									if(color==1)stackb[1]=stacks[1];
									if(color==2)stackr[1]=stacks[1];
									if(color==3)stackg[1]=stacks[1];
								}});
										}
						///////////////////........
										if(stacks[2]>0&&stps[2]==stps[selno]&&!(stacks[1]+1==stacks[2]&&stps[1]==stps[selno])&&!(stacks[0]+1==stacks[2]&&stps[0]==stps[selno])&&!(stacks[3]+1==stacks[2]&&stps[3]==stps[selno])){
												sm.setVisible(true);
											JButton b1=new JButton("Rise 2");
											jf.add(b1);
											b1.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									actper=true;sm.dispose();
									movekey=false;
									int ab=stepp+color*13;
									if(ab>52)ab-=52;
									for(int i=0;i<4;i++){
									
										if(stepy[i]==ab&&stacks[2]-stacky[i]==1){
											stacks[2]--;stacky[i]++;
											System.out.println("rising0");break;}
										if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[2]-stackb[i]==1){
											stacks[2]--;stackb[i]++;
											System.out.println("rising1");break;}
										if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[2]-stackr[i]==1){
											stacks[2]--;stackr[i]++;
											System.out.println("rising2");break;}
										if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[2]-stackg[i]==1){
											stacks[2]--;stackg[i]++;
											System.out.println("rising3");break;}}
									
									if(color==0)stacky[2]=stacks[2];
									if(color==1)stackb[2]=stacks[2];
									if(color==2)stackr[2]=stacks[2];
									if(color==3)stackg[2]=stacks[2];
								}});
										}
								////////.............
										if(stacks[3]>0&&stps[3]==stps[selno]&&!(stacks[1]+1==stacks[3]&&stps[1]==stps[selno])&&!(stacks[2]+1==stacks[3]&&stps[2]==stps[selno])&&!(stacks[0]+1==stacks[3]&&stps[0]==stps[selno])){
												sm.setVisible(true);
											JButton b1=new JButton("Rise 3");
											jf.add(b1);
											b1.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e){
									actper=true;sm.dispose();
									movekey=false;
									int ab=stepp+color*13;
									if(ab>52)ab-=52;
									for(int i=0;i<4;i++){
									
										if(stepy[i]==ab&&stacks[3]-stacky[i]==1){
											stacks[3]--;stacky[i]++;
											System.out.println("rising0");break;}
										if((stepb[i]+13==ab||stepb[i]-39==ab)&&stacks[3]-stackb[i]==1){
											stacks[3]--;stackb[i]++;
											System.out.println("rising1");break;}
										if((stepr[i]+26==ab||stepr[i]-26==ab)&&stacks[3]-stackr[i]==1){
											stacks[3]--;stackr[i]++;
											System.out.println("rising2");break;}
										if((stepg[i]+39==ab||stepg[i]-13==ab)&&stacks[3]-stackg[i]==1){
											stacks[3]--;stackg[i]++;
											System.out.println("rising3");break;}}
									
									if(color==0)stacky[3]=stacks[3];
									if(color==1)stackb[3]=stacks[3];
									if(color==2)stackr[3]=stacks[3];
									if(color==3)stackg[3]=stacks[3];
								}});
										}
								////////.............

										break;
									}					
								}});
							if(actper)break;
				}	
				
				else if(cout==1){move.next(but);}
				else if(cout==2){move.next(but);move.next(but);}
				else if(cout==3){move.next(but);move.next(but);move.next(but);}
				else if(cout==4){move.next(but);move.next(but);move.next(but);move.next(but);}
				else if(cout==5){move.next(but);move.next(but);move.next(but);move.next(but);move.next(but);}
				else if(cout==6){move.next(but);move.next(but);move.next(but);move.next(but);move.next(but);move.next(but);}
	//			System.out.println("...."+color+"executed.");
				if(stepy[0]==57&&stepy[1]==57&&stepy[2]==57&&stepy[3]==57&&!gamy){gamy=true;winner=winner*10+1;}
				if(stepb[0]==57&&stepb[1]==57&&stepb[2]==57&&stepb[3]==57&&!gamb){gamb=true;winner=winner*10+2;}
				if(stepr[0]==57&&stepr[1]==57&&stepr[2]==57&&stepr[3]==57&&!gamr){gamr=true;winner=winner*10+3;}
				if(stepg[0]==57&&stepg[1]==57&&stepg[2]==57&&stepg[3]==57&&!gamg){gamg=true;winner=winner*10+4;}
	
				selno=but;
				movekey=false;
				sixes=0;
				gatticlick=true;
				if(tof){
				int st=0;
				if(color==0){st=stepy[but];}
				else if(color==1){st=stepb[but];}
				else if(color==2){st=stepr[but];}
				else if(color==3){st=stepg[but];}
				System.out.print("ST+"+stepp+"."+st+",");
				tof=false;
				add=true;
				for(int ab=1; ab<=48; ab+=5){
					if(st==ab)tof=true;
				if(add){ab+=3;add=false;}else add=true;	
				}
				
				if((stepp-st)!=0&&tof){
					int ab=st+color*13;
					if(ab>52)ab-=52;
					for(int i=0;i<4;i++){
					if(stepy[i]==ab&&(color!=0||(color==0&&i!=but)))stacky[i]++;
					if((stepb[i]+13==ab||stepb[i]-39==ab)&&(color!=1||(color==1&&i!=but)))stackb[i]++;
					if((stepr[i]+26==ab||stepr[i]-26==ab)&&(color!=2||(color==2&&i!=but)))stackr[i]++;
					if((stepg[i]+39==ab||stepg[i]-13==ab)&&(color!=3||(color==3&&i!=but)))stackg[i]++;
	
					}
				}else System.out.print("and not");}
				
				if(toft){
					int st=0;
					if(color==0){st=stepy[but];}
					else if(color==1){st=stepb[but];}
					else if(color==2){st=stepr[but];}
					else if(color==3){st=stepg[but];}
					System.out.print("unstack..");
					toft=false;
					int ab=stepp+color*13;
					if(ab>52)ab-=52;
					if((st-stepp)!=0){
						for(int i=0;i<4;i++){
							if(stepy[i]==ab&&(color!=0||(color==0&&i!=but)))stacky[i]--;
							if((stepb[i]+13==ab||stepb[i]-39==ab)&&(color!=1||(color==1&&i!=but)))stackb[i]--;
							if((stepr[i]+26==ab||stepr[i]-26==ab)&&(color!=2||(color==2&&i!=but)))stackr[i]--;
							if((stepg[i]+39==ab||stepg[i]-13==ab)&&(color!=3||(color==3&&i!=but)))stackg[i]--;
					}
				}
				}
	/*			 add=true;					
					for(int ab=1; ab<=48; ab+=5){
							if(stps[but]==ab){
								System.out.print("..sss..");
									for(int i=0; i<4; i++)
										{if(stepy[i]==ab&&(colr!=0||(colr==0&&i!=selno)))stacky[i]--;if(stepb[i]==ab&&(colr!=1||(colr==1&&i!=selno)))stackb[i]--;if(stepr[i]==ab&&(colr!=2||(colr==2&&i!=selno)))stackr[i]--;if(stepg[i]==ab&&(colr!=3||(colr==3&&i!=selno)))stackg[i]--;}
				}
				if(add){ab+=3;add=false;}else add=true;	
				
				}
					for(int ab=0; ab<4; ab++){
					if(stacky[ab]<0)stacky[ab]=0;
					if(stackb[ab]<0)stackb[ab]=0;
					if(stackr[ab]<0)stackr[ab]=0;
					if(stackg[ab]<0)stackg[ab]=0;}	*/
			//		if(color==0)stacky[but]=0;
			//		if(color==1)stackb[but]=0;
			//		if(color==2)stackr[but]=0;
			//		if(color==3)stackg[but]=0;
				tof=false;
				if(cout!=1 && cout != 6){
					if (color<4)color++;
					else color=0;
					if (color==4)color=0;}
				break;
			
				}	
			
				
				
					
				
		/*		if(cout!=1&&cout!=6){
					colr=color-1;
				}else{colr=color;}
				if(colr<0)colr=3;
				for(int no=0; no<4;no++){
					if(stps[no]==1||stps[no]==9||stps[no]==22||stps[no]==35||stps[no]==48||stps[no]==1||stps[no]==13||stps[no]==26||stps[no]==39)tof=true;}
					
			if(tof){
					boolean add=true;
				System.out.print("..>star<.."+color+selno+"\n");
					int[] stey = new int[4];	
					int[] steb = new int[4];
					int[] ster = new int[4];
					int[] steg = new int[4];
					for(int aa=0; aa<4; aa++){
						stey[aa]=stacky[aa];
						steb[aa]=stackb[aa];
						ster[aa]=stackr[aa];
						steg[aa]=stackg[aa];
					}

					if(color==1){stps[selno]+=13;if(stps[selno]>52)stps[selno]-=52;}
					if(color==2){stps[selno]+=26;if(stps[selno]>52)stps[selno]-=52;}
					if(color==3){stps[selno]+=39;if(stps[selno]>52)stps[selno]-=52;}
					
					for(int ab=1; ab<=48; ab+=5){
							if(stps[selno]==ab){
								System.out.print(".ok.");
									for(int i=0; i<4; i++)
										{if(stepy[i]==ab&&(colr!=0||(colr==0&&i!=selno))&&(stey[i]-stacky[i])==0)stacky[i]++;if((stepb[i]+13==ab||stepb[i]-39==ab)&&(colr!=1||(colr==1&&i!=selno))&&(steb[i]-stackb[i])==0)stackb[i]++;if((stepr[i]+26==ab||stepr[i]-26==ab)&&(colr!=2||(colr==2&&i!=selno))&&(ster[i]-stackr[i])==0)stackr[i]++;if((stepg[i]-13==ab||stepg[i]+39==ab)&&(colr!=3||(colr==3&&i!=selno))&&(steg[i]-stackg[i])==0)stackg[i]++;}
				}
				if(add){ab+=3;add=false;}else add=true;	
							}tof=false;}*/
		
	}
		
	System.out.println(stepy[0]+" "+stepy[1]+" "+stepy[2]+" "+stepy[3]+" "+scorey);
	System.out.println(stepb[0]+" "+stepb[1]+" "+stepb[2]+" "+stepb[3]+" "+scoreb);
	System.out.println(stepr[0]+" "+stepr[1]+" "+stepr[2]+" "+stepr[3]+" "+scorer);
	System.out.println(stepg[0]+" "+stepg[1]+" "+stepg[2]+" "+stepg[3]+" "+scoreg);
			
	}}
	
	
}
@Override
public void mouseReleased(MouseEvent arg0) {
	int[] x=new int[4];
	int[] y=new int[4];
	int[] stps=new int[4];
	int[] stacks=new int[4];
	boolean[] not=new boolean[4];
	not[1]=false;
	not[2]=false;
	not[3]=false;
	not[0]=false;
	dispdice=true;
	roll=false;
	if(winner>100){
		int win3=winner % 10;
		winner=winner/10;
		int win2=winner %10;
		winner = winner/10;
		int win1 = winner;
		System.out.printf("first %d  second %d  third %d",win1,win2, win3);
		JDialog jjj = new JDialog();
		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		JLabel l3 = new JLabel();
		l1.setText(String.format("THE WINNER IS: %d\n", win1));
		l1.setText(String.format("Second WINNER IS: %d\n", win2));
		l1.setText(String.format("Third WINNER IS: %d\n", win3));
		
		jjj.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jjj.add(l1);
		jjj.add(l2);
		jjj.add(l3);
	}
	for(int no=0; no<4;no++){
		if(color==0){x[no]=yx[no];y[no]=yy[no];stps[no]=stepy[no];stacks[no]=stacky[no];}
		if(color==1){x[no]=bx[no];y[no]=by[no];stps[no]=stepb[no];stacks[no]=stackb[no];}
		if(color==2){x[no]=rx[no];y[no]=ry[no];stps[no]=stepr[no];stacks[no]=stackr[no];}
		if(color==3){x[no]=gx[no];y[no]=gy[no];stps[no]=stepg[no];stacks[no]=stackg[no];}
		if(stps[no]==0||(stps[no]+cout)>57||stacks[no]>0){not[no]=true;}
	}
//	System.out.println("///"+color);
	for(int no=0; no<4;no++){
		if(stps[no]==1||stps[no]==9||stps[no]==22||stps[no]==35||stps[no]==48||stps[no]==1||stps[no]==13||stps[no]==26||stps[no]==39)tof=true;}
	if(diceclick){
		if(sixes==3){sixes=0;}
		if(cout==6){sixes++;System.out.println("HAH!!!");}
		if(sixes==3){cout=1;drawsix=true;System.out.println("3SIX");}

	
//	if((stps[0]==0&&stps[1]==0&&stps[2]==0&&stps[3]==0&&cout!=1)||((stps[0]==0&&stps[1]==0&&stps[2]==0&&stps[3]>=50&&cout!=1&&(stps[3]+cout)>57)||(stps[0]==0&&stps[1]==0&&stps[2]>=50&&stps[3]==0&&cout!=1&&(stps[2]+cout)>57)||(stps[0]==0&&stps[1]>=50&&stps[2]==0&&stps[3]==0&&cout!=1&&(stps[2]+cout)>57)||(stps[0]>=50&&stps[1]==0&&stps[2]==0&&stps[3]==0&&cout!=1&&(stps[0]+cout)>57))){
	if(not[0]&&not[1]&&not[2]&&not[3]&&cout!=1){
		System.out.println("...."+color+"skipped");
		if(cout!=6){
		if (color<4)color++;
		else color=0;}
		if (color==4)color=0;
		movekey=false;
		
	}
//	else if((stps[0]==0&&stps[1]==0&&stps[2]==0&&stps[3]>=50&&cout!=1&&(stps[3]+cout)>57)||(stps[0]==0&&stps[1]==0&&stps[2]>=50&&stps[3]==0&&cout!=1&&(stps[3]+cout)>57)||(stps[0]==0&&stps[1]>=50&&stps[2]==0&&stps[3]==0&&cout!=1&&(stps[3]+cout)>57)||(stps[0]>=50&&stps[1]==0&&stps[2]==0&&stps[3]==0&&cout!=1&&(stps[3]+cout)>57)){
//		System.out.println("...."+color+"skipped");
//		if(cout!=6){
//		if (color<4)color++;
//		else color=0;}
//		if (color==4)color=0;
//		movekey=false;
//	}
	

	diceclick=false;
	}
	if(cout!=1&&cout!=6){
		colr=color-1;
	}else{colr=color;}
	if(colr<0)colr=3;
//	System.out.print("..."+selno+colr+"...\n");
	if(!movekey){
	//cut the other player if not in safe location;
	if(colr==0){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepy[i]!=0&&stepy[i]<52&&stepy[i]!=9&&stepy[i]!=22&&stepy[i]!=35&&stepy[i]!=48&&stepy[i]!=1&&stepy[i]!=14&&stepy[i]!=27&&stepy[i]!=40){if(stepy[i]==stepb[j]+13)stepb[j]=0;if(stepy[i]==stepr[j]+26)stepr[j]=0;if(stepy[i]==stepg[j]+39)stepg[j]=0;}}}}
	if(colr==1){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepb[i]!=0&&stepb[i]<52&&stepb[i]!=9&&stepb[i]!=22&&stepb[i]!=35&&stepb[i]!=48&&stepb[i]!=1&&stepb[i]!=14&&stepb[i]!=27&&stepb[i]!=40){if(stepb[i]==stepr[j]+13)stepr[j]=0;if(stepb[i]==stepg[j]+26)stepg[j]=0;if(stepb[i]==stepy[j]+39)stepy[j]=0;}}}}
	if(colr==2){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepr[i]!=0&&stepr[i]<52&&stepr[i]!=9&&stepr[i]!=22&&stepr[i]!=35&&stepr[i]!=48&&stepr[i]!=1&&stepr[i]!=14&&stepr[i]!=27&&stepr[i]!=40){if(stepr[i]==stepg[j]+13)stepg[j]=0;if(stepr[i]==stepy[j]+26)stepy[j]=0;if(stepr[i]==stepb[j]+39)stepb[j]=0;}}}}
	if(colr==3){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepg[i]!=0&&stepg[i]<52&&stepg[i]!=9&&stepg[i]!=22&&stepg[i]!=35&&stepg[i]!=48&&stepg[i]!=1&&stepg[i]!=14&&stepg[i]!=27&&stepg[i]!=40){if(stepg[i]==stepy[j]+13)stepy[j]=0;if(stepg[i]==stepb[j]+26)stepb[j]=0;if(stepg[i]==stepr[j]+39)stepr[j]=0;}}}}
	if(colr==0){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepy[i]!=0&&stepy[i]<52&&stepy[i]!=9&&stepy[i]!=22&&stepy[i]!=35&&stepy[i]!=48&&stepy[i]!=1&&stepy[i]!=14&&stepy[i]!=27&&stepy[i]!=40){if(stepy[i]==stepb[j]+13)stepb[j]=0;if(stepy[i]==stepr[j]-26)stepr[j]=0;if(stepy[i]==stepg[j]-13)stepg[j]=0;}}}}
	if(colr==1){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepb[i]!=0&&stepb[i]<52&&stepb[i]!=9&&stepb[i]!=22&&stepb[i]!=35&&stepb[i]!=48&&stepb[i]!=1&&stepb[i]!=14&&stepb[i]!=27&&stepb[i]!=40){if(stepb[i]==stepr[j]-39)stepr[j]=0;if(stepb[i]==stepg[j]-26)stepg[j]=0;if(stepb[i]==stepy[j]-13)stepy[j]=0;}}}}
	if(colr==2){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepr[i]!=0&&stepr[i]<52&&stepr[i]!=9&&stepr[i]!=22&&stepr[i]!=35&&stepr[i]!=48&&stepr[i]!=1&&stepr[i]!=14&&stepr[i]!=27&&stepr[i]!=40){if(stepr[i]==stepg[j]-39)stepg[j]=0;if(stepr[i]==stepy[j]-26)stepy[j]=0;if(stepr[i]==stepb[j]-13)stepb[j]=0;}}}}
	if(colr==3){for(int i=0; i<4; i++){for(int j=0; j<4; j++){if(stepg[i]!=0&&stepg[i]<52&&stepg[i]!=9&&stepg[i]!=22&&stepg[i]!=35&&stepg[i]!=48&&stepg[i]!=1&&stepg[i]!=14&&stepg[i]!=27&&stepg[i]!=40){if(stepg[i]==stepy[j]-39)stepy[j]=0;if(stepg[i]==stepb[j]-26)stepb[j]=0;if(stepg[i]==stepr[j]-13)stepr[j]=0;}}}}
	
	}
	/*		if(!movekey){if(tof){
		boolean add=true;
	System.out.print("..>star<.."+color+selno+"\n");
		int[] stey = new int[4];	
		int[] steb = new int[4];
		int[] ster = new int[4];
		int[] steg = new int[4];
		for(int aa=0; aa<4; aa++){
			stey[aa]=stacky[aa];
			steb[aa]=stackb[aa];
			ster[aa]=stackr[aa];
			steg[aa]=stackg[aa];
		}
//		for(int ab=0; ab<4; ab++){
//			steb[ab]=stepb[ab]+13;
//			if(steb[ab]>52)steb[ab]-=52;
//			ster[ab]=stepr[ab]+26;
//			if(ster[ab]>52)ster[ab]-=52;
//			steg[ab]=stepg[ab]+39;
//			if(steg[ab]>52)steg[ab]-=52;
//			System.out.print(steb[ab]+" "+ster[ab]+" "+steg[ab]+",,");
//		}
	System.out.print("\n");
		if(colr==1){stps[selno]+=13;if(stps[selno]>52)stps[selno]-=52;}
		if(colr==2){stps[selno]+=26;if(stps[selno]>52)stps[selno]-=52;}
		if(colr==3){stps[selno]+=39;if(stps[selno]>52)stps[selno]-=52;}
		for(int ab=1; ab<=48; ab+=5){
				if(stps[selno]==ab){
					System.out.print(".ok.");
						for(int i=0; i<4; i++)
							{if(stepy[i]==ab&&(colr!=0||(colr==0&&i!=selno)))stacky[i]++;if((stepb[i]+13==ab||stepb[i]-39==ab)&&(colr!=1||(colr==1&&i!=selno)))stackb[i]++;if((stepr[i]+26==ab||stepr[i]-26==ab)&&(colr!=2||(colr==2&&i!=selno)))stackr[i]++;if((stepg[i]-13==ab||stepg[i]+39==ab)&&(colr!=3||(colr==3&&i!=selno)))stackg[i]++;}
	}
	if(add){ab+=3;add=false;}else add=true;	
				}tof=false;}gatticlick=false;} */
	if(gamy&&color==0)color++;
	if(gamb&&color==1)color++;
	if(gamr&&color==2)color++;
	if(gamg&&color==3)color++;
	
	
	if(stepy[0]==0){yx[ 0]= 500  ;	yy[ 0]=75;}
	if(stepy[1]==0){yx[ 1]= 590  ;	yy[ 1]= 75;}
	if(stepy[2]==0){yx[ 2]= 500  ;	yy[ 2]= 155;}
	if(stepy[3]==0){yx[ 3]=590 ;	yy[ 3]= 155;}
	if(stepr[0]==0){rx[ 0]= 77  ;ry[ 0]= 480 ;}
	if(stepr[1]==0){rx[ 1]= 167  ;ry[ 1]= 480;}
	if(stepr[2]==0){rx[ 2]= 77  ;ry[ 2]= 560;}
	if(stepr[3]==0){rx[ 3]= 167  ;ry[ 3]= 560;}
	if(stepg[0]==0){gx[ 0]= 77  ;gy[ 0]= 75 ;}
	if(stepg[1]==0){gx[ 1]= 167  ;gy[ 1]= 75;}
	if(stepg[2]==0){gx[ 2]= 77  ;gy[ 2]= 155;}
	if(stepg[3]==0){gx[ 3]= 167  ;gy[ 3]= 155;}
	if(stepb[0]==0){bx[ 0]= 500  ;by[ 0]=480;}
	if(stepb[1]==0){bx[ 1]= 590  ;by[ 1]= 480;}
	if(stepb[2]==0){bx[ 2]= 500  ;by[ 2]= 560;}
	if(stepb[3]==0){bx[ 3]=590 ;by[ 3]= 560;}

	repaint();
/*	System.out.print(stacky[0]+"-"+stacky[1]+"-"+stacky[2]+"-"+stacky[3]+"::");
	System.out.print(stackb[0]+"-"+stackb[1]+"-"+stackb[2]+"-"+stackb[3]+"::");
	System.out.print(stackr[0]+"-"+stackr[1]+"-"+stackr[2]+"-"+stackr[3]+"::");
	System.out.print(stackg[0]+"-"+stackg[1]+"-"+stackg[2]+"-"+stackg[3]+"\n");
	*/
}
	
public class Next{
	public void next(int no){
	//	x+=47;
	//	y+=45;
	//yellow 383 46,618 361 ,289 586 ,524 271  
	//383,1 383,226 // 430,271 665,271 665,361 430,361 // 383,406 383,631 289,631 289,406 // 242,361 7,361 7,271 242,271 // 289,226 289,1	
		int x=0;
		int y=0;
		int stepsel = 0;
		if(color==0){x=yx[no];y=yy[no];stepsel=stepy[no];}
		if(color==1){x=bx[no];y=by[no];stepsel=stepb[no];}
		if(color==2){x=rx[no];y=ry[no];stepsel=stepr[no];}
		if(color==3){x=gx[no];y=gy[no];stepsel=stepg[no];}
		
		if(stepsel>0&&stepsel<51){
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
		if(color==0){yx[no]=x;yy[no]=y;stepy[no]++;}
		if(color==1){bx[no]=x;by[no]=y;stepb[no]++;}
		if(color==2){rx[no]=x;ry[no]=y;stepr[no]++;}
		if(color==3){gx[no]=x;gy[no]=y;stepg[no]++;}
		
		}
		else if(stepsel>50&&(stepsel+coutdubli)<58){	
			if(color==0){y+=45;}
			if(color==2){y-=45;}
			if(color==1){x-=47;}
			if(color==4){x+=47;}
			coutdubli--;
			System.out.println("last step");
			if(color==0){yx[no]=x;yy[no]=y;stepy[no]++;
			if(stepy[no]==57)scorey++;
				}
			if(color==1){bx[no]=x;by[no]=y;stepb[no]++;
			if(stepb[no]==57)scoreb++;}
			if(color==2){rx[no]=x;ry[no]=y;stepr[no]++;
			if(stepr[no]==57)scorer++;}
			if(color==3){gx[no]=x;gy[no]=y;stepg[no]++;
			if(stepg[no]==57)scoreg++;}
			
		}
		
		
		
		
		
	}
	}
}
