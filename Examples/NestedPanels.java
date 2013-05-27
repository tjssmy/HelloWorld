//********************************************************************
//  NestedPanels.java       Author: Lewis/Loftus
//
//  Demonstrates a basic componenet hierarchy.
//********************************************************************

import java.awt.*;
import javax.swing.*;

public class NestedPanels
{
   //-----------------------------------------------------------------
   //  Presents two colored panels nested within a third.
   //-----------------------------------------------------------------
   
	private static JPanel Panel1(int i1, int i2, Color C)
	{
	      JPanel subPanel1 = new JPanel();
	      subPanel1.setPreferredSize (new Dimension(150, 100));
	      subPanel1.setBackground (C);
	      JLabel label1 = new JLabel ("One");
	      subPanel1.add (label1);
	      
	      return subPanel1;
	}
	
	public static void main (String[] args)
   {
      JFrame frame = new JFrame ("Nested Panels");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      JPanel subPanel1,subPanel2;
      
      subPanel1 = Panel1(150,100,Color.green);
      subPanel2 = Panel1(150,100,Color.red);
      

      // Set up primary panel
      JPanel primary = new JPanel();
      primary.setBackground (Color.blue);
      primary.add (subPanel1);
      primary.add (subPanel2);

      frame.getContentPane().add(primary);
      frame.pack();
      frame.setVisible(true);
   }
}
