import javafx.geometry.Bounds;
import jdk.management.resource.internal.UnassignedContext;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import com.sun.javafx.css.Rule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameJpanel extends JPanel implements KeyListener{
    byte[] led= new byte[8];
    byte sign=0;
    byte[] xx=new byte[50];
    byte[] yy=new byte[50];
    byte length=4;
    byte urdl=2;
    boolean buttonclicked=false;
    boolean[][] ledon=new boolean[8][8];
    public short[] car= new short[]{3, 6};
    public short[] pos= new short[]{car[0], car[1]};
    boolean gameover,startcar=false;
    Random random= new Random();
    final2 serial;
    Thread gameRun;
    boolean gamemenu=true;
    int gamemenuno=0;
    boolean disposable=false;
    int score;
    int hardness=500;
    boolean mute=true;
	boolean firstclick=false;
    boolean button1=false, button2=false;
    byte rx=0,ry=0;
    boolean eaten;
    JDialog jDialog;

    private void randomGenerate(){
    	boolean newvehicle=true;
    	boolean nodown=true;
    	
        if(ledon[car[0]][car[1]-1]||ledon[car[0]+1][car[1]-1])gameover=true;
        for(int a=7;a>0;a--){
            for(int b=0;b<8;b++){
                //    if(!(car[0]>=b&&car[0]<=b+1&&car[1]>=a&&car[1]<=a+1))
                ledon[b][a] = ledon[b][a-1];
                ledon[b][a-1]=false;
            }}
        
        //length of vehicle
        for(int a=0;a<8;a++){
        	if(ledon[a][1]&&ledon[a+((a>6)?0:1)][1]&&a<7){
        		if(!ledon[a][2]&&!ledon[(a>6)?0:1+1][2]&&a<7){
        			ledon[a][0]=ledon[a+1][0]=true;
        			newvehicle=false;
        		//	System.out.println("must make a car");
        	 		}
        		else{
        			if(!ledon[a][3]&&!ledon[(a>6)?0:1+1][3]&&a<7)
        			if(random.nextBoolean()){ 
        		    ledon[a][0]=ledon[a+1][0]=true;
        			newvehicle=false;
        		//	System.out.println("making car long");
        			}
        		}
        		a++;
        	}
        	else if(ledon[a][1]){
        		if(random.nextBoolean()&&!(ledon[a][2]&&ledon[a][3])){
        			ledon[a][0]=true;
        			newvehicle=false;
            	}
            }
        }
        int rano = random.nextInt(8);
        while(!rule(rano)){
        	rano=random.nextInt(8);
        }
        
        //car position
        //if any vehicle is just down from it
        if(newvehicle){
        	
        	 for(int a=0;a<8;a++){
        		 if(ledon[a][1]){nodown=false;}
        	 }
        }
        if(newvehicle&&nodown){
        	hardness-=hardness/100;
        	if(hardness<100)hardness=100;
        	System.out.println(hardness+ " " + score);
        	score+=6-hardness/100;
        	System.out.println(score);
        	
        ledon[rano][0]=true;
        
        //car width
        int size=random.nextInt(3)-1;
        if(rano+size>7||rano+size<0)size=0;
        ledon[(rano+size)][0]=true;}

    }

    GameJpanel() throws InterruptedException{
    	
		startMenu();
        gameRun=new Thread(new Runnable() {
            @Override
            public void run() {
                long time;
                while(!startcar){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					}
                while(true){
                	int delaytime=100;
                	time=System.currentTimeMillis();
                	while(gamemenu){
                		if(System.currentTimeMillis()-time<delaytime){ 
                        	try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        	continue;}
                        time=System.currentTimeMillis();
                        if(gamemenuno==0){
                        	delaytime=300;
                        	menuButton();}
                        else if(gamemenuno==1){
                        	delaytime=300;
                        	gameCar();
                        }
                        else if(gamemenuno==2){
                        	delaytime=300;
                        	gameSnake();
                        }
                        else if(gamemenuno==3){
                        	delaytime=300;
                        	gameOfthrones();
                        }
                        repaint();
                	}
                	
                    time=System.currentTimeMillis();
                    while(System.currentTimeMillis()-time<hardness){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}
                    mute=false;
                   if(gamemenuno==1){ 
                	randomGenerate();
                    buildcar();
                    }
                   else if(gamemenuno==2){
                	   buildSnake();
                	   repaint();
                   }
                   if(gameover) whenOver();
                }
            }
        });
        gameRun.start();
        

        serial=new final2();

        //serial
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				serial.selectPort(new String("COM2"));
				System.out.println("Port selected here");
				if(!serial.portOpen()){
					System.out.println("Failed to open port");
					return;}
				new Thread(new Runnable() {
					@Override
					public void run() {
						int count1=0,count2=0;
						long ctime1=0,ctime2=0;
						serial.startPortReceiving();
						while(true){
							int extradelay=0;
						while(serial.incoming()){
							String data=serial.getStringData();
							System.out.println("Out--"+data);
							
							if(!gameover)extradelay=0;
							if(!gameover&&gamemenuno==2)extradelay=(hardness-100);
							if(gamemenu&&(data.compareToIgnoreCase("1")==0)){
								if(count1==0){
									extradelay=500;
									ctime1=System.currentTimeMillis();
									for(int y=0; y<8; y++){
							            for(int x=0; x<8; x++){
							            	ledon[x][y]=false;
							            }}
									if(gamemenuno==0)mute=mute?false:true;
									else{
										sign=0;
										gamemenu=false;
										gameover=false;
										if(disposable)jDialog.dispose();
										disposable=false;
									}
									}
								count1++;
							}
							if(gamemenu&&(data.compareToIgnoreCase("2")==0)){
								if(count1==0){
									extradelay=500;
									ctime1=System.currentTimeMillis();
									for(int y=0; y<8; y++){
							            for(int x=0; x<8; x++){
							            	ledon[x][y]=false;
							            }}
									gamemenuno++;
									if(gamemenuno>3)gamemenuno=0;
									}
								count1++;
							}
								
							if (data.compareToIgnoreCase("1")==0&&car[0]>0&&!gameover){
								if(count1==0){keyLeft();ctime1=System.currentTimeMillis();}
								count1++;
								
							}
							if (data.compareToIgnoreCase("2")==0&&car[0]<6&&!gameover){
								if(count2==0){keyRight();ctime2=System.currentTimeMillis();}
								count2++;
							}
							if(System.currentTimeMillis()-ctime1>(100+extradelay)) count1=0;
							if(System.currentTimeMillis()-ctime2>(100+extradelay)) count2=0;
						
							System.out.println(gamemenuno+"  "+mute);
						}
				
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						}				
					}
				}).start();
				
				serial.startPortSending();
				startcar=true;
			}
		}).start();
        for(int a=0;a<5;a++){
        	xx[a]=(byte) (4-a);
        	yy[a]=2;
        }
    repaint();	
    }

//////////////////////////////////////////////////////////////////////
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        led=new byte[]{0,0,0,0,0,0,0,0};
        g.setColor(Color.white);
        // System.out.println(car[0]+" "+car[1]);
        //  g.draw3DRect(10,10,20,20,true);
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                if (ledon[x][y]){
                	g.setColor(Color.red);
                	///for serial data sending
                	led[y]|=(1<<x);
                }
                             
                else {
                	g.setColor(Color.white);
                	//for serial data sending
                	led[y]|=(0<<x);
                }
                g.fill3DRect(10+x*30,10+y*30,20,20,true); }
     //   System.out.println(led[y]);
        }
        g.setColor(Color.BLACK);
        g.drawString(String.format("Score : %d", score), 20, 265);
        if(startcar){			
            try {
            	//serial.send.write(new Byte((byte) 0));
            serial.send.write(led);
            if(!mute)serial.send.write(sign);
            else serial.send.write(0);
            if(sign==2||sign==3)sign=0;
            } catch (IOException e) {e.printStackTrace();}
              }
    }

    public void buildcar(){
        ledon[car[0]][car[1]]=ledon[car[0]][car[1]+1]=ledon[car[0]+1][car[1]]=ledon[car[0]+1][car[1]+1]=true;
        repaint();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if(gamemenu&&e.getKeyCode()==KeyEvent.VK_LEFT){
    		for(int y=0; y<8; y++){
	            for(int x=0; x<8; x++){
	            	ledon[x][y]=false;
	            }}
			if(gamemenuno==0)mute=mute?false:true;
			else{
				sign=0;
				gamemenu=false;
				gameover=false;
				if(disposable)jDialog.dispose();
				disposable=false;
			}
		}
    	if(gamemenu&&e.getKeyCode()==KeyEvent.VK_RIGHT){
    		for(int y=0; y<8; y++){
	            for(int x=0; x<8; x++){
	            	ledon[x][y]=false;
	            }}
			gamemenuno++;
			if(gamemenuno>3)gamemenuno=0;
		}
        if(gameover)return;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT&&car[0]<6){
            keyRight();
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT&&car[0]>0){
            keyLeft();
        }
    }
    private void keyLeft(){
    if(gamemenuno==1){	
    	if(ledon[car[0]-1][car[1]]||ledon[car[0]-1][car[1]+1])gameover=true;
        car[0]--;
        buildcar();
        ledon[car[0]+2][car[1]]=ledon[car[0]+2][car[1]+1]=false;
        sign=2;
    }
    else if(gamemenuno==2&&!buttonclicked){
    	buttonclicked=true;
    	urdl--;
    	if(urdl<1)urdl=4;
    }
    }
    private void keyRight(){
    if(gamemenuno==1){	
    	if(ledon[car[0]+2][car[1]]||ledon[car[0]+2][car[1]+1])gameover=true;
        car[0]++;
        buildcar();
        ledon[car[0]-1][car[1]]=ledon[car[0]-1][car[1]+1]=false;
        sign=3;
    }
    else if(gamemenuno==2&&!buttonclicked){
    	buttonclicked=true;
    	urdl++;
    	if(urdl>4)urdl=1;
    	
    }
    
    }
    

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
private void buildSnake(){
	
	ledon[xx[length-1]][yy[length-1]]=false;
	for(int a=length-1;a>0; a--){
	xx[a]=xx[a-1];
	yy[a]=yy[a-1];
	}
	if(urdl==2)xx[0]++;
	else if(urdl==3)yy[0]++;
	else if(urdl==4)xx[0]--;
	else if(urdl==1)yy[0]--;
	
	
	if(xx[0]>7)xx[0]=0;
	else if(xx[0]<0)xx[0]=7;
	if(yy[0]>7)yy[0]=0;
	if(yy[0]<0)yy[0]=7;
	
	for(int a=0;a<length;a++){
		ledon[xx[a]][yy[a]]=true;
	}
	buttonclicked=false;
	
	for(int a=0;a<length;a++){
		for(int b=0;b<length;b++){
			if(a!=b&&xx[a]==xx[b]&&yy[a]==yy[b]){
				gameover=true;
				return;
			}
		}
	}
	sign=0;
	if(xx[0]==rx&&yy[0]==ry){
		hardness=hardness-hardness/20;
		score=score+(5-hardness/100)*4;
		System.out.println(score);
		length++;
		sign=4;
	while(true){
	rx=(byte) random.nextInt(4);
	ry=(byte) random.nextInt(4);
	if (!ledon[rx][ry])break;
	}}
	
	ledon[rx][ry]=true;
	
	/*for(int a=0;a<8;a++){
		for(int b=0;b<8;b++){
			
		}
	}*/
	
	System.out.println(score);
	
	
	
	
	
	
	
	
	
}
private void whenOver(){
    System.out.println("game over");
    if(gamemenuno==1){
    for (int i = 0; i < 7; i++) {
        ledon[car[0]][car[1]]=ledon[car[0]][car[1]+1]=ledon[car[0]+1][car[1]]=ledon[car[0]+1][car[1]+1]=ledon[car[0]][car[1]]?false:true;
        sign=(byte) (ledon[car[0]][car[1]]?2:3);
        repaint();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    		}///upto here if menu1
    else if(gamemenuno==2){
    //	sign=5;
    	for (int i = 0; i < 7; i++) {
    		for(int a=0;a<length;a++){
    			ledon[xx[a]][yy[a]]=ledon[xx[a]][yy[a]]?false:true;
    		}
            repaint();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    try {
    serial.send.write(new byte[]{0,0,0,0,0,0,0,0,0});;     } catch (IOException e) {e.printStackTrace();
    	}
    for(int y=0; y<8; y++){
        for(int x=0; x<8; x++){
        	ledon[x][y]=false;
        }}
    highScore();
    mute=true;
    startMenu();
    
    
}

private void startMenu(){
	resetValues();
	sign=1;
	repaint();
	gamemenuno=0;
	//gameRun.start();
}

private boolean rule(int rano){
	
	//check if there is any space to side of car
	short x=car[0],y=car[1];
	ledon[car[0]][car[1]]=ledon[car[0]][car[1]+1]=ledon[car[0]+1][car[1]]=ledon[car[0]+1][car[1]+1]=false;
	//ledon[x][y]=ledon[x+1][y]=ledon[x][y+1]=ledon[x+1][y+1]=false;
	for(int a=0;a<2000;a++){
		int rand=random.nextInt(100);
		if(rand<21)rand=0;
		else if(rand<81)rand=1;
		else rand=2;
		switch (rand) {
		case 0:
			for(int i=0;i<random.nextInt(6)+1;i++)
			if(x>0&&!ledon[x-(x>0?1:0)][y]&&!ledon[x-(x>0?1:0)][y+1]){
				//way to left	
				x--;
				}
			break;
		case 1:
			for(int i=0;i<random.nextInt(6)+1;i++)
				if(x<6&&!ledon[x+(x<6?2:0)][y]&&!ledon[x+(x<6?2:0)][y+1]){
				//way to right
				x++;
				}
			break;
		case 2:
			for(int i=0;i<random.nextInt(3)+1;i++)
				if(!ledon[x][y-1]&&!ledon[x+1][y-1]){
				//way to top	
				y--;
				if(y<1)break;
				}
			break;
		default:
			break;
		};
		/*if(x>0&&!ledon[x-(x>0?1:0)][y]&&!ledon[x-(x>0?1:0)][y+1]){
			//way to left	
			x--;
			}
		if(x<6&&!ledon[x+(x<6?2:0)][y]&&!ledon[x+(x<6?2:0)][y+1]){
			//way to right
			x++;
			}
		if(!ledon[x][y-1]&&!ledon[x+1][y-1]){
			//way to top	
			y--;
			}*/
		
		
	//	System.out.println(rand+": "+x +"  "+y);	
		if (y<1) break;
	}
	//System.out.println("test over");
	ledon[car[0]][car[1]]=ledon[car[0]][car[1]+1]=ledon[car[0]+1][car[1]]=ledon[car[0]+1][car[1]+1]=true;
	if(x!=rano&&x+1!=rano) return true;
	return false;

}
private void resetValues(){
	car= new short[]{3, 6};
	gameover=true;
	gamemenu=true;
	sign=0;
	hardness=500;
	score=0;
	length=4;
	urdl=2;
	 for(int a=0;a<5;a++){
     	xx[a]=(byte) (4-a);
     	yy[a]=2;
     }
	
	for(int y=0; y<8; y++){
        for(int x=0; x<8; x++){
	ledon[x][y]=false;}}
}
private void menuButton(){
	for(int y=0; y<4; y++){
        int x=3;
	ledon[x][y]=true;}
	ledon[2][5]=ledon[3][5]=ledon[4][5] =true;
	for(int y=6; y<8; y++){
        for(int x=0; x<7; x++){
	ledon[x][y]=true;}}
	if(ledon[3][4]){
    	ledon[2][3]=ledon[3][4]=ledon[4][3]=false;
    	ledon[2][2]=ledon[3][3]=ledon[4][2]=true;
    }
    else{
    	ledon[2][2]=ledon[4][2]=false;
    	ledon[2][3]=ledon[3][4]=ledon[4][3]=true;
    }
}
private void gameSnake(){
	for(int x=2; x<5; x++){
	   	ledon[x][6]=true;
	   	ledon[x][4]=true;
	   	ledon[x][2]=true;
	}
	ledon[5][5]=ledon[2][4]=ledon[1][3]=true;
	ledon[5][2]=(ledon[5][2])?false:true;
	
}
private void gameCar(){
	
		ledon[1][7]=ledon[2][7]=ledon[5][7]=ledon[6][7]=true;
	for(int x=1; x<5; x++){
	   	ledon[x][3]=true;}
	for(int x=0; x<6; x++){
    	ledon[x][4]=true;}
	for(int y=5; y<7; y++){
        for(int x=0; x<8; x++){
	ledon[x][y]=true;}}
}
private void gameOfthrones(){
	mute=false;
}

private void highScore(){
	int tempscore=score;
	int tempgamemenuno=gamemenuno;
	firstclick=true;
	JPanel jPanel=new JPanel();
	JTextField jTextField=new JTextField();
	jTextField.setSize(200,20);
	jTextField.setText("                                            ");
	JButton jButton=new JButton("Done");
	JTextArea jTextArea=new JTextArea();
	jTextArea.setSize(50,50);
	jTextArea.setEditable(false);
	jTextArea.setText(String.format("%d", score));
	jPanel.add(jTextField);
	jPanel.add(jButton);
	jPanel.add(jTextArea);
	jPanel.setVisible(true);
	disposable=true;
	jDialog=new JDialog();
	jDialog.setSize(200,200);
	jDialog.setVisible(true);
	
	jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	jDialog.add(jPanel);
	jTextField.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(firstclick)jTextField.setText(" ");
			firstclick=false;
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				String name = jTextField.getText();
				FileWriter fil;
				try {
					fil = new FileWriter(new File("D:\\highscore.doc"),true);
					if(tempgamemenuno==1)fil.append(name+"  :  "+tempscore+"  :car "+"\n");
					if(tempgamemenuno==2)fil.append(name+"  :  "+tempscore+"  :snake "+"\n");
				    fil.close();
				} catch (IOException e) {
					e.printStackTrace();
				}			
				
				
				jDialog.dispose();
			}
			
		}
	});
	jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = jTextField.getText();
			FileWriter fil;
			try {
				fil = new FileWriter(new File("C:\\Users\\Hey There\\Desktop\\highscore.doc"),true);
				if(tempgamemenuno==1)fil.append(name+"  :  "+tempscore+"  :car "+"\n");
				if(tempgamemenuno==2)fil.append(name+"  :  "+tempscore+"  :snake "+"\n");
			    fil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
			
			jDialog.dispose();
		}
	});;
	
}
}
