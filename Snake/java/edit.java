import java.awt.FlowLayout;
import java.io.*;
import java.util.Scanner;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class edit implements ActionListener{
	private JDialog dia2;
	private JTextField w1;
	private JTextField w2;
	private JTextField w3;
	private JPanel tp2;


	public void actionPerformed(ActionEvent event) {
		

		dia2 = new JDialog();
        dia2.setSize(300,300);
        dia2.setTitle("Record Window");
      	dia2.setVisible(true);
//startNEWwindoeFROMhere			
		w1 = new JTextField("Enter Name Here",20);
		w2 = new JTextField("Enter Addres Here",20);
		w3 = new JTextField("Enter Phone No Here",14);
		w1.setFont(new Font("Serif",Font.PLAIN, 16));
		w2.setFont(new Font("Serif",Font.PLAIN, 16));
		w3.setFont(new Font("Serif",Font.PLAIN, 16));
		
		tp2.add(w1);
		tp2.add(w2);
		tp2.add(w3);

			}
}
