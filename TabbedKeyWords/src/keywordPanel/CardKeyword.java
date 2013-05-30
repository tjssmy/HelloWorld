package keywordPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
//import java.util.ArrayList;

public class CardKeyword implements ItemListener {
	JPanel cards; //a panel that uses CardLayout
	static keywordData kData;
	
	public static class ParseData {
		ParseStatus Status;
		Object Data;
		
		public ParseData()
		{
			Status = ParseStatus.ParseUnknown;
			Data = null;
		}
	}
	
	private enum ParseStatus {
		ParseOk,ParseFail,ParseUnknown
	}
	private static ParseData DoubleCheckParse(String S, Double Def)
	{
		Double D;
		ParseData ParseRet = new ParseData();
		try {
		    D = Double.parseDouble(S);
		    ParseRet.Data = (Object) D;
		    ParseRet.Status = ParseStatus.ParseOk;
		    
		    return ParseRet;
		} catch (NumberFormatException e) {
			String M = "Bad double input:  " + S;
		    JOptionPane.showMessageDialog(null,M);
		    
		    ParseRet.Data = (Object) Def;
		    ParseRet.Status = ParseStatus.ParseFail;
		    
		    return ParseRet;
		}
	}
	
	private static ParseData IntegerCheckParse(String S, Integer Def)
	{
		Integer I;
		ParseData ParseRet = new ParseData();
		try {
		    I = Integer.parseInt(S);
		    ParseRet.Data = (Object) I;
		    ParseRet.Status = ParseStatus.ParseOk;
		    
		    return ParseRet;
		} catch (NumberFormatException e) {
			String M = "Bad integer input:  " + S;
		    JOptionPane.showMessageDialog(null,M);
		    I = Def;
		    
		    ParseRet.Data = (Object) Def;
		    ParseRet.Status = ParseStatus.ParseFail;
		    
		    return ParseRet;
		}
	}
	
	private JPanel paraEntry(final int k, final int p) {
		
		keywordPanel.keywordData.KeyData K = kData.Keys.get(k); 
		keywordPanel.keywordData.ParaData P = K.Paras.get(p);
		keywordPanel.keywordData.ParaType Type = P.Type;
		Object data = P.Data;
		String Name = P.Name;
		
		JPanel Entry = new JPanel();
		Entry.setPreferredSize (new Dimension(350, 50));
		Entry.setLayout(new GridLayout(1,2));
		JButton JB = new JButton(Name);
		JB.setText(Name);
		Entry.add(JB);

		switch(Type) {

		case Double: {
			
			final Double D = (Double)data;
			final JTextField F = new JTextField(10);
			
			Entry.add(F);
			F.setText(D.toString());
			
			F.addActionListener(new ActionListener()
			{
				@Override				
				public void actionPerformed(ActionEvent arg0) {
					Double DD;
					ParseData P =  DoubleCheckParse(F.getText(),D);
					DD = (Double) P.Data;
					kData.SetDoubleData(k,p,DD);
					F.setText(DD.toString());
					if (P.Status == ParseStatus.ParseOk) F.setForeground(Color.red);
					Double Dt = (Double)(kData.Keys.get(k)).Paras.get(p).Data;
					System.out.printf("double: %f %f\n",DD,Dt);
			}});

			break;
		}
		case EnumType: {
			break;
		}
		case File: {
			break;
		}
		case Integer: {
			
			final Integer I = (Integer)data;
			final JTextField F = new JTextField(10);
			
			Entry.add(F);
			F.setText(I.toString());
			
			F.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Integer II = I;
					ParseData P = IntegerCheckParse(F.getText(),I)	;
					II = (Integer) P.Data;
					kData.SetIntegerData(k,p,II);
					F.setText(II.toString());
					if (P.Status == ParseStatus.ParseOk) F.setForeground(Color.red);
					Integer It = (Integer)(kData.Keys.get(k)).Paras.get(p).Data;
					System.out.printf("int: %d %d\n",II,It);
			}});
			break;
		}
		case ListOfDoubles: {
			break;
		}
		case String: {
		
			final String S = (String)data;
			final JTextField F = new JTextField(10);
			
			Entry.add(F);
			F.setText(S);
			
			F.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String SS = F.getText();
					kData.SetStringData(k,p,SS);
					F.setForeground(Color.red);
					String St = (String)(kData.Keys.get(k)).Paras.get(p).Data;
					System.out.printf("string: %s %s\n",SS,St);
			}});


			break;
		}
		default:
			break;
		}

		return Entry;
	}


	public JPanel CardList (int k) {
		int j;
		//Create the "cards".
		JPanel card = new JPanel();
		//    	card.setPreferredSize (new Dimension(350, 800));
		int n = kData.numKeys;
		card.setLayout(new GridLayout(n,1));
//		
		
		for (j = 0; j < n; j++) {
			JPanel entry = paraEntry(k,j);
			card.add(entry);
		}
		return card;
	}


	public void addKeyWordPanel(Container pane) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		int k;

		JComboBox cb = new JComboBox((kData.KeyNames.toArray()));
		cb.setEditable(false);
		cb.addItemListener(this);
		cb.setPreferredSize(new Dimension(200,20));
		comboBoxPane.add(cb);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());

		for (k = 0; k < kData.numKeys; k++) {
			JPanel card = CardList(k);
			keywordPanel.keywordData.KeyData K = kData.Keys.get(k); 
			cards.add(card,K.Name);
		}

		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);
	}

	public void addBottomButtonsl(Container pane) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel Buttons = new JPanel(); 
		Buttons.setLayout(new GridLayout(1,2));
		
		JButton WriteBut = new JButton("Write File");
		JButton ExitBut = new JButton("Exit");
		
		ExitBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
		}});

		WriteBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				kData.writeKeyFile(kData.OutFile);
		}});

		
		Buttons.add(WriteBut);
		Buttons.add(ExitBut);
		
		pane.add(Buttons, BorderLayout.PAGE_END);
	}

	
	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, (String)evt.getItem());
	}


	public static class KeyData {
		String Name;
		String Paras[];
		int num;

		public KeyData(String name, String Pnames[], int n)
		{
			Name = name;
			Paras = Pnames;
			num = n;
		}
	}


	private static void createKeyWordPanel() {
		//Create and set up the window.
		JFrame frame = new JFrame("CardLayoutDemo");

		//		frame.setPreferredSize(new Dimension(350,800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		kData = new keywordData("Outfile.run","RunConfig.conf");
		kData.testKeyFile();

		//Create and set up the content pane.
		CardKeyword demo = new CardKeyword();

		demo.addKeyWordPanel(frame.getContentPane());
		demo.addBottomButtonsl(frame.getContentPane());
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		/**
		 * Create the GUI and show it.  For thread safety,vg
		 * this method should be invoked from the
		 * event dispatch thread.
		 */
		
		/* Use an appropriate Look and Feel */
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createKeyWordPanel();
			}
		});
	}
}

