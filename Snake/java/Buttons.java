import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.*;

import java.awt.event.*;

public class Buttons extends JFrame {

private JButton b1;
private JButton b2;
private JButton b3;
private JButton b4;
private JButton bsend;
private JTextField w1;
private JTextField w2;
private JTextField w3;
private JLabel[] datas;
private JPanel textpanel,tp2;
private JTextArea textarea1;
private JFrame frame1;
private JDialog mydialog,dia2;
private String nam, add, pho;
private int phon;
private double value;


public Buttons(){
	super("Telephone Diary");
	setLayout(new FlowLayout());
	
	b1= new JButton("View Records");
	b2= new JButton("Add Records");
	b3= new JButton("Edit Records");
	b4= new JButton("Delete Records");
	add(b1);
	add(b2);
	add(b3);
	add(b4);
	b1.setForeground(Color.red);
	b1.setMargin(new Insets(5,1,5,1));
//	b1.setBackground(Color.BLACK); 
	b2.setForeground(Color.green);
	b2.setMargin(new Insets(5,1,5,1));
	b3.setForeground(Color.blue);
	b3.setMargin(new Insets(5,1,5,1));
	b4.setForeground(Color.orange);
	b4.setMargin(new Insets(5,1,5,1));
	
	Handler1 handler1 = new Handler1(); 
	b1.addActionListener(handler1);
	b2.addActionListener(handler1);
	b3.addActionListener(handler1);
	b4.addActionListener(handler1);
	
//	b1.addActionListener(
//			new ActionListener(){
//				public void actionPerformed(ActionEvent event){
//						
//					}	}	);
	
	
}
private class Handler1 implements ActionListener{
	
	public void actionPerformed(ActionEvent event){
		
		mydialog = new JDialog();
        mydialog.setSize(260,300);
        textpanel = new JPanel();
      //  mydialog.setSize(mydialog.getPreferredSize());
		if (event.getSource()==b1){
		
		textarea1 = new JTextArea();
		textpanel.add(textarea1);
		
        mydialog.setTitle("Record Window");
       
        mydialog.setVisible(true);
        mydialog.add(textpanel);
        
        textarea1.append("Name\t Address\t PhoneNo\n\n");
		
		try{
			DataInputStream inp =new DataInputStream(new FileInputStream("test.txt"));
			BufferedReader imp = new BufferedReader(new InputStreamReader(inp));
			String rea ;
			int k=0,l=0;
			
			while((rea = imp.readLine())!=null)
			{
			k++;
			textarea1.append(rea+"\t");
			rea = imp.readLine();
			textarea1.append(rea+"\t");
			rea = imp.readLine();
			textarea1.append(rea+"\n");
			if(k==3){ System.out.println(); k=0;}
			}       
			imp.close(); 	
			
	
		}
		catch(Exception e){
			System.out.println("the file reading is incomplete");
		}
		//add(textpanel);
		}
		else if (event.getSource()==b2){
			w1 = new JTextField("Enter Name Here",20);
			w2 = new JTextField("Enter Addres Here",20);
			w3 = new JTextField("Enter Phone No Here",14);
			w1.setFont(new Font("Serif",Font.PLAIN, 16));
			w2.setFont(new Font("Serif",Font.PLAIN, 16));
			w3.setFont(new Font("Serif",Font.PLAIN, 16));
			w1.addMouseListener(
					new MouseAdapter(){
						public void mouseClicked(MouseEvent eve){
							w1.setText("");
						}
					}
					);
			w2.addMouseListener(
					new MouseAdapter(){
						public void mouseClicked(MouseEvent eve){
							w2.setText("");
						}
					}
					);
			w3.addMouseListener(
					new MouseAdapter(){
						public void mouseClicked(MouseEvent eve){
							w3.setText("");
						}
					}
					);
			
			mydialog.setTitle("Add Record Window");
		    mydialog.setVisible(true);
			textpanel.add(w1);
			textpanel.add(w2);
			textpanel.add(w3);
			mydialog.add(textpanel);
			bsend = new JButton("Submit & Exit");
			textpanel.add(bsend);
			nam="";
			add="";
			pho="";
			
			bsend.addActionListener(
					new ActionListener(){
					public void actionPerformed(ActionEvent event){
						
						//System.out.println("enter the name :");
						//System.out.println("enter the address: ");
						//System.out.println("enter the phone number: ");
						
						nam=w1.getText();
						add=w2.getText();
						pho=w3.getText();
					//	phon=Integer.parseInt(pho);
						try{
							FileWriter fil = new FileWriter(new File("test.txt"),true);			
							
							fil.write(nam+"\n");
							fil.write(add+"\n");
							fil.write(pho+"\n");
							//System.out.println("Do you want to add more records? (Y/N)");
						    fil.close();
							
					
						}
						catch(Exception e){
							System.out.println("the file writing is incomplete");
						}
						mydialog.dispose();
							}	}	 );
			

			
			
			
			
			
		}
		else if (event.getSource()==b3){
		
				textarea1 = new JTextArea();
				textpanel.add(textarea1);
				
		        mydialog.setTitle("Edit Record Window");
		       
		        mydialog.setVisible(true);
		        mydialog.add(textpanel);
		        
		        textarea1.append("Name\t Address\t PhoneNo\n\n");
				
			try{
								
				File filr=new File("test.txt"); 
				File filw=new File("testt.txt"); 
				DataInputStream inp =new DataInputStream(new FileInputStream(filr));
				DataInputStream inp2 =new DataInputStream(new FileInputStream(filr));
				BufferedReader imp = new BufferedReader(new InputStreamReader(inp));
				BufferedReader imp2 = new BufferedReader(new InputStreamReader(inp2));
			//	System.out.println("enter the name to edit: ");
				filw.createNewFile();
				String rea;
				int k=0,l=0;
				
				while((rea = imp.readLine())!=null)
				{
				k++;
				l++;
				textarea1.append(l+" : "+rea+"\t");
				rea = imp.readLine();
				textarea1.append(rea+"\t");
				rea = imp.readLine();
				textarea1.append(rea+"\n");
				if(k==3){ System.out.println(); k=0;}
				}       
				inp.close();
				imp.close();
				SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1000, 1);
			    JSpinner spinner = new JSpinner(model);
				textpanel.add(spinner);
				bsend = new JButton("Submit & Exit");
				textpanel.add(bsend);
				
				
				
				bsend.addActionListener(
						new ActionListener(){
						public void actionPerformed(ActionEvent evet){
						//	String no = w1.getText();
							value = model.getNumber().doubleValue();
						//	TDiary valls = new TDiary();
						//	valls.retu(value);
							
							
							textpanel.removeAll();
							mydialog.removeAll();
							mydialog.dispose();
							dia2 = new JDialog();
							tp2 = new JPanel();
							dia2.setTitle("Edit the values of selected record");
					        dia2.setSize(300,300);
							
							
							dia2.setVisible(true);
				//startNEWwindoeFROMhere			
							w1 = new JTextField("Enter Name Here",20);
							w2 = new JTextField("Enter Addres Here",20);
							w3 = new JTextField("Enter Phone No Here",14);
							w1.setFont(new Font("Serif",Font.PLAIN, 16));
							w2.setFont(new Font("Serif",Font.PLAIN, 16));
							w3.setFont(new Font("Serif",Font.PLAIN, 16));
							w1.addMouseListener(
									new MouseAdapter(){
										public void mouseClicked(MouseEvent eve){
											w1.setText("");
										}
									}
									);
							w2.addMouseListener(
									new MouseAdapter(){
										public void mouseClicked(MouseEvent eve){
											w2.setText("");
										}
									}
									);
							w3.addMouseListener(
									new MouseAdapter(){
										public void mouseClicked(MouseEvent eve){
											w3.setText("");
										}
									}
									);
							
							
							tp2.add(w1);
							tp2.add(w2);
							tp2.add(w3);
							
							bsend = new JButton("Submit & Exit");
							tp2.add(bsend);
							nam="";
							add="";
							pho="";
							dia2.add(tp2);
							
							bsend.addActionListener(
									new ActionListener(){
									public void actionPerformed(ActionEvent event){
										
										//System.out.println("enter the name :");
										//System.out.println("enter the address: ");
										//System.out.println("enter the phone number: ");
										
										nam=w1.getText();
										add=w2.getText();
										pho=w3.getText();
									//	phon=Integer.parseInt(pho);
										String rea;
										int k=1;
									
										System.out.println(value);
									//	int edo=Integer.valueOf(edno);
										try{
											FileWriter fil = new FileWriter(new File("testt.txt"));
											while((rea = imp2.readLine())!=null){
																		
											if ((int)value==k){
												System.out.println("\t"+rea);
												rea = imp2.readLine();
												System.out.println("\t"+rea);
												rea = imp2.readLine();
												System.out.println("\t"+rea);
												fil.write(nam+"\n");
												fil.write(add+"\n");
												fil.write(pho+"\n");
											}	
											else{
												System.out.println(rea);
												fil.write(rea+"\n");
												rea=imp2.readLine();
												System.out.println(rea);
												fil.write(rea+"\n");
												rea=imp2.readLine();
												System.out.println(rea);
												fil.write(rea+"\n");
											}
											k++;
											}
											imp2.close(); 
											fil.close();		
											filr.delete();
											filw.renameTo(filr);
											
									
										}
										catch(Exception e){
											System.out.println("the file editing is incomplete");
										}
										dia2.dispose();
											}	}	 );
					
								}	}	 );
				
				
		
			}
			catch(Exception e){
				System.out.println("the file editing is incomplete");
			}
		
		
		}
		else if (event.getSource()==b4){

			textarea1 = new JTextArea();
			textpanel.add(textarea1);
			
	        mydialog.setTitle("Edit Record Window");
	       
	        mydialog.setVisible(true);
	        mydialog.add(textpanel);
	        
	        textarea1.append("Name\t Address\t PhoneNo\n\n");
			
		try{
							
			File filr=new File("test.txt"); 
			File filw=new File("testt.txt"); 
			DataInputStream inp =new DataInputStream(new FileInputStream(filr));
			DataInputStream inp2 =new DataInputStream(new FileInputStream(filr));
			BufferedReader imp = new BufferedReader(new InputStreamReader(inp));
			BufferedReader imp2 = new BufferedReader(new InputStreamReader(inp2));
		//	System.out.println("enter the name to edit: ");
			filw.createNewFile();
			String rea;
			int k=0,l=0;
			
			while((rea = imp.readLine())!=null)
			{
			k++;
			l++;
			textarea1.append(l+" : "+rea+"\t");
			rea = imp.readLine();
			textarea1.append(rea+"\t");
			rea = imp.readLine();
			textarea1.append(rea+"\n");
			if(k==3){ System.out.println(); k=0;}
			}       
			inp.close();
			imp.close();
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1000, 1);
		    JSpinner spinner = new JSpinner(model);
			textpanel.add(spinner);
			bsend = new JButton("Submit & Exit");
			textpanel.add(bsend);
			
			
			
			bsend.addActionListener(
					new ActionListener(){
					public void actionPerformed(ActionEvent evet){
					//	String no = w1.getText();
						value = model.getNumber().doubleValue();
					//	TDiary valls = new TDiary();
					//	valls.retu(value);
						
						
						textpanel.removeAll();
						mydialog.removeAll();
						mydialog.dispose();
						
									String rea;
									int k=1;
								
									System.out.println(value);
								//	int edo=Integer.valueOf(edno);
									try{
										FileWriter fil = new FileWriter(new File("testt.txt"));
										while((rea = imp2.readLine())!=null){
																	
										if ((int)value==k){
											System.out.println("\t"+rea);
											rea = imp2.readLine();
											System.out.println("\t"+rea);
											rea = imp2.readLine();
											System.out.println("\t"+rea);
											}	
										else{
											System.out.println(rea);
											fil.write(rea+"\n");
											rea=imp2.readLine();
											System.out.println(rea);
											fil.write(rea+"\n");
											rea=imp2.readLine();
											System.out.println(rea);
											fil.write(rea+"\n");
										}
										k++;
										}
										imp2.close(); 
										fil.close();		
										filr.delete();
										filw.renameTo(filr);
										
								
									}
									catch(Exception e){
										System.out.println("the file editing is incomplete");
									}
										
				
							}	}	 );
			
			
	
		}
		catch(Exception e){
			System.out.println("the file editing is incomplete");
		}
	
	
			
			
			
		}
		}
		


	
	}
	
	
}


