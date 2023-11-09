import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.*;


public class frame extends JFrame{

	private JLabel level;
	private JTextField write1;
	private JButton b1;
	private JButton b2;
	private JCheckBox boldbox;
	private JRadioButton f12;
	private Font f2;
	private Font f4;
	private Font f8;
	private JRadioButton f14;
	private JRadioButton f18;
	private ButtonGroup bgrp;
	public frame()
	{
		super("Telephone Diary");
		setLayout(new FlowLayout());
		level = new JLabel("Choose a item");
		level.setToolTipText("Only one");
		add(level);
		
		f12=new JRadioButton("Font 12", false);
		f14=new JRadioButton("Font 14", false);
		f18=new JRadioButton("Font 18", false);
		add(f12);
		add(f14);
		add(f18);
		bgrp= new ButtonGroup();
		bgrp.add(f12);
		bgrp.add(f14);
		bgrp.add(f18);
		
		
		write1= new JTextField("1-5",10);
		write1.setFont(new Font("Serif",Font.PLAIN, 16));
		add(write1);
		
		handler1 handler= new handler1();
		write1.addActionListener(handler);
		
		b1= new JButton("Click regular");
		add(b1);
		
		Icon ic = new ImageIcon(getClass().getResource("im1.png"));
		Icon id = new ImageIcon(getClass().getResource("im2.png"));
			
		b2=new JButton("With icon",ic);
		b2.setRolloverIcon(id);
		add(b2);
		
		handler2 handl2r = new handler2(); 
		b1.addActionListener(handl2r);
		b2.addActionListener(handl2r);
		
		boldbox = new JCheckBox("Bold");
		add(boldbox);
		
		handler3 handl3r = new handler3();
		boldbox.addItemListener(handl3r);
		
//		handler4 handl4r = new handler4();
//		f12.addItemListener(handl4r);
//		f14.addItemListener(handl4r);
//		f18.addItemListener(handl4r);
		
		f2=new Font("Serif", Font.PLAIN,12);
		f4=new Font("Serif", Font.PLAIN,14);
		f8=new Font("Serif", Font.PLAIN,18);
		
		f12.addItemListener(new handler4(f2));
		f14.addItemListener(new handler4(f4));
		f18.addItemListener(new handler4(f8));
	}
	
private class handler1 implements ActionListener{
	
	public void actionPerformed(ActionEvent event){
		String string="";
		if (event.getSource()==write1)
			string=String.format("You have entered : %s", event.getActionCommand());
		JOptionPane.showMessageDialog(null, string);
	
	
	
	}
	
}

private class handler2 implements ActionListener{
	
	public void actionPerformed(ActionEvent event){
		JOptionPane.showMessageDialog(null, String.format("You have entered : %s", event.getActionCommand()));
	
	
	
	}
	
}
private class handler3 implements ItemListener{
	
	public void itemStateChanged(ItemEvent event){
		Font font=null;
		if(boldbox.isSelected()){
			font = new Font("Serif", Font.BOLD,14);
		}
		else{
		 	font = new Font("Serif", Font.PLAIN,14);
		}
	write1.setFont(font);
		
	}
	
}
private class handler4 implements ItemListener{
	
	private Font font;
//	public void itemStateChanged(ItemEvent event){
//		Font font=null;
//		if(event.getSource()==f12){
//			font = new Font("Serif", Font.BOLD,12);
//		}
//		else if(event.getSource()==f14){
//		 	font = new Font("Serif", Font.PLAIN,14);
//		}
//		else if(event.getSource()==f18){
//		 	font = new Font("Serif", Font.PLAIN,18);
//		}
//	write1.setFont(font);
//	
//	}
	public handler4(Font f){
		font=f;
	}
	public void itemStateChanged(ItemEvent event){
		write1.setFont(font);
	}
	 
}
	
}
