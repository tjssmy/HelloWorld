package keywordPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
//import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CardKeyword implements ItemListener {
	JPanel cards; //a panel that uses CardLayout
	static keywordData kData;
	static String ConfFile;
	
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
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		
		if (k == 7 && p == 13)
		{
			System.out.println("here");
		}
		keywordPanel.keywordData.KeyData K = kData.Keys.get(k); 
		keywordPanel.keywordData.ParaData P = K.Paras.get(p);
		keywordPanel.keywordData.ParaType Type = P.Type;
		Object data = P.Data;
		String Name = P.Name;
		ArrayList<String> List = P.ParaTypeList;
		
		JPanel Entry = new JPanel();
		Entry.setPreferredSize (new Dimension(350, 50));
		Entry.setLayout(new GridLayout(1,2));
		JLabel JL = new JLabel(Name);
		JL.setText(Name);
		JL.setBorder(new EmptyBorder(5, 15, 5, 15));
		Entry.add(JL);

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
			
			JComboBox ListBox = new JComboBox(List.toArray());
			Entry.add(ListBox);
			int def = P.ParaTypeList.indexOf((String)P.Data);
			
			ListBox.setSelectedIndex(def);
			
			ListBox.addActionListener(new ActionListener()
			{
				@Override				
				public void actionPerformed(ActionEvent arg0) {
					JComboBox Box = (JComboBox)arg0.getSource();
					int i = Box.getSelectedIndex();
					kData.SetEnumTypeData(k,p,i);
					Box.setForeground(Color.red);
			}});
			
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
		int j,t;
		//Create the "cards".
		JPanel card = new JPanel();
		card.setPreferredSize (new Dimension(350, 800));
		
		JTabbedPane JT = new JTabbedPane();
		
		
		
		keywordPanel.keywordData.KeyData K = kData.Keys.get(k);
		int n = K.numParas;
		
		
//		
		int NumTabs = n/15+1;
		
		for (t = 0; t < NumTabs; t++)
		{
			int start,end;
			JPanel T1 = new JPanel();
			T1.setLayout(new GridLayout(n,1));
			
			start = t*15;
			end = start+15;
			
			if (end > n) end = n;
			
			for (j = start; j < end; j++) {
				JPanel entry = paraEntry(k,j);
				T1.add(entry);
			}

			JT.addTab(String.format("Tab %d",t+1),null,T1,"Does nothing");
		}
		
		card.add(JT);
		return card;
	}

	
	public void addKeyWordPanel(Container pane) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		int k;

		
		final JComboBox cbA = new JComboBox((kData.ActiveKeyNames.toArray()));
//		JComboBox cb = new JComboBox((kData.Keys.toArray()));
//		cb.setEditable(false);
//		cb.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JComboBox CB = (JComboBox) e.getSource();
//				keywordData.KeyData K = (keywordData.KeyData) CB.getSelectedItem();
//				kData.ActiveKeys.add(keywordData.KeyCopy(K));
//				kData.ActiveKeyNames.add(new String(K.Name));
//				cbA.addItem(new String(K.Name));
//				
//				int k = kData.ActiveKeys.size()-1;
//				JPanel card = CardList(k);
//				cards.add(card,kData.ActiveKeys.get(k).Name);
////				String S = new String((String)(is.getSelectedObjects()[0]));
//		        System.out.println("Selected: " + K.Name);	
//			}
//		});
//		
		
		cbA.setEditable(false);
		cbA.addItemListener(this);
		cbA.setPreferredSize(new Dimension(150,20));
		
//		cb.setPreferredSize(new Dimension(150,20));
		
//		comboBoxPane.add(cb);
		comboBoxPane.add(cbA);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());

		for (k = 0; k < kData.ActiveKeys.size(); k++) {
			JPanel card = CardList(k);
			cards.add(card,kData.ActiveKeys.get(k).Name);
		}

		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);
	}

	public void addBottomButtonsl(Container pane) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel Buttons = new JPanel(); 
		Buttons.setLayout(new GridLayout(1,3));
		
		JButton ReadBut = new JButton("Read File");
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

		ReadBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				kData.readConfigKeyFile();
			
		}});

		Buttons.add(ReadBut);
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

		kData = new keywordData("Outfile.run",ConfFile);
		
		kData.readConfigKeyFile();
//		kData.testKeyFile();

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

		
		if (args.length == 2 && args[0].equals("-c"))
		{
			ConfFile = args[1];
		}
		else 
		{
			System.out.println("Command Line args bad\n");
			System.exit(0);
		}
				
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createKeyWordPanel();
			}
		});
	}
}

