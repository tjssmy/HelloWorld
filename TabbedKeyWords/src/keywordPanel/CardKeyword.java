package keywordPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
//import java.util.ArrayList;
//import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CardKeyword implements ItemListener {
	JPanel activeKeyPanel; //a panel that contains the activeKeyword cards
	JPanel keyfilePanel;   //a panel to contain the comboboxes and cards
	keywordData kData;
	
	public CardKeyword(String confFile) {

		kData = new keywordData(confFile);
		kData.readConfigKeyFile();

	}
	
	public class ParseData {
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
	

	private ParseData DoubleCheckParse(String S, Double Def)
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
	
	private ParseData IntegerCheckParse(String S, Integer Def)
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
			
		keywordPanel.keywordData.KeyData K = kData.ActiveKeys.get(k); 
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
					Double Dt = (Double)(kData.ActiveKeys.get(k)).Paras.get(p).Data;
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
					Integer It = (Integer)(kData.ActiveKeys.get(k)).Paras.get(p).Data;
					System.out.printf("int: %d %d\n",II,It);
			}});
			break;
		}
		case List: {
			final JButton B = new JButton("List Values");
			Entry.add(B);
			B.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanel Jp = new JPanel();
					
//					http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
					
					Jp.add(new JLabel("here"));
					int result = JOptionPane.showConfirmDialog(B, Jp, "List Values",
				            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);	
			}});
			
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
					String St = (String)(kData.ActiveKeys.get(k)).Paras.get(p).Data;
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
		
		
		
		keywordPanel.keywordData.KeyData K = kData.ActiveKeys.get(k);
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
		comboBoxPane.setLayout(new GridLayout(2,2));
		
		JLabel L1 = new JLabel("Add a Keyword");
		JLabel L2 = new JLabel("Active Keywords");
		L1.setHorizontalAlignment( SwingConstants.CENTER );
		L2.setHorizontalAlignment( SwingConstants.CENTER );
		comboBoxPane.add(L1);
		comboBoxPane.add(L2);
		
		int k;

		ArrayList<String> ANames = new ArrayList<String>();
		
		ANames.addAll(kData.ActiveKeyNames);
		
		keywordData.KeyData DummyKey = kData.RemoveKeywordDummy;
		
		kData.ActiveKeys.add(DummyKey);	
		
		final JComboBox cb = new JComboBox(kData.ActiveKeys.toArray());
		cb.setEditable(false);
		cb.addItemListener(this);
		cb.setPreferredSize(new Dimension(150,20));
		
		JComboBox cbDef = new JComboBox((kData.Keys.toArray()));
		cbDef.setEditable(false);
		cbDef.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox CB = (JComboBox) e.getSource();
				keywordData.KeyData K = (keywordData.KeyData) CB.getSelectedItem();
				keywordData.KeyData KNew = kData.KeyCopy(K);
				int k = kData.ActiveKeys.size()-1; // last index (dummy key)
				
				kData.ActiveKeys.add(k,KNew); // insert and push last element to end
				kData.ActiveKeyNames.add(new String(K.Name));
				
				// seems you need unique names to link to cards.
				Integer I = new Integer(K.numIns);
				String KName = new String(KNew.Name + "(" + I.toString() + ")");
				KNew.GUIName = KName;
				cb.insertItemAt(KNew, cb.getItemCount()-1);
				
				JPanel card = CardList(k);
				activeKeyPanel.add(card,KName);
		        System.out.println("Selected: " + KName);	
			}
		});
		
		comboBoxPane.add(cbDef);
		comboBoxPane.add(cb);		
		
		//Create the panel that contains the "cards".
		activeKeyPanel = new JPanel(new CardLayout());

		for (k = 0; k < kData.ActiveKeys.size(); k++) {
			JPanel card = CardList(k);
			keywordPanel.keywordData.KeyData K = kData.ActiveKeys.get(k); 
			activeKeyPanel.add(card,K.Name);
		}

		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(activeKeyPanel, BorderLayout.CENTER);
		comboBoxPane.setVisible(true);
		pane.setVisible(true);
	}

	//  card/combo event handler -- finds card of specific name
	// this is too global for an override I think. Should be in a sub-class??
	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(activeKeyPanel.getLayout());
		
		keywordData.KeyData Key = (keywordData.KeyData)evt.getItem();
		String ItemStr = Key.GUIName;
				
		if (evt.getStateChange() == ItemEvent.SELECTED)
		{
			if (ItemStr.equals("Remove Keyword"))
			{
				java.util.List<keywordPanel.keywordData.KeyData> Keys = kData.ActiveKeys.subList(0, kData.ActiveKeys.size()-1);
				Object[] options = (Object[]) Keys.toArray();

				JComboBox cb = (JComboBox) evt.getSource();

				keywordData.KeyData K = (keywordData.KeyData)JOptionPane.showInputDialog(
						null, "Pick keyword\n", "Customized Dialog",
						JOptionPane.PLAIN_MESSAGE, null,options,null);

				cb.removeItem(K);
				kData.ActiveKeys.remove(K);

				cb.setSelectedIndex(0); // set back to 1st key
			}
			else
			{
				cl.show(activeKeyPanel, ItemStr);
			}
		}
	}


	public void addBottomButtons(final Container pane) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel Buttons = new JPanel(); 
		Buttons.setLayout(new GridLayout(1,3));
		
		JButton ReadBut = new JButton("Read File");
		JButton NewBut = new JButton("New File");
		JButton WriteBut = new JButton("Write File");
		JButton ExitBut = new JButton("Exit");
		
//		forCards.add(new JLabel("here is space3"));
		
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
				kData.writeKeyFile();
		}});

		ReadBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				kData.readKeyFile();
				keyfilePanel.removeAll();
				addKeyWordPanel(keyfilePanel);
				keyfilePanel.validate();
				keyfilePanel.repaint();
		}});

		NewBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				keyfilePanel.removeAll();
				kData.LoadActiveKeys();
				addKeyWordPanel(keyfilePanel);
				keyfilePanel.validate();
				keyfilePanel.repaint();
			
		}});
		
		Buttons.add(NewBut);
		Buttons.add(ReadBut);
		Buttons.add(WriteBut);
		Buttons.add(ExitBut);
		
		pane.add(Buttons, BorderLayout.PAGE_END);
	}
	
	private void CreateKeywordCards(JPanel mainPanel)
	{
		keyfilePanel = new JPanel();
		keyfilePanel.setPreferredSize(new Dimension(350,800));
		mainPanel.add(keyfilePanel);
		//	keywordCards.addKeyWordPanel(ForCards);
		//	keywordCards.addKeyWordPanel(frame.getContentPane());
		addBottomButtons(mainPanel);

		//
//		forCards.add(new JLabel("here is space2"));
	}
	
 	private static void createKeyWordPanel(String[] args) {
		//Create and set up the window.
 		String confFile=null;
 		
		if (args.length == 2 && args[0].equals("-c"))
		{
			confFile = args[1];
		}
		else 
		{
			System.out.println("Command Line args bad\n");
			System.exit(0);
		}
				
		JFrame frame = new JFrame("Input File");
		JPanel J0 = new JPanel();
		JPanel J1 = new JPanel(new BorderLayout());
		JPanel J2 = new JPanel(new BorderLayout());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		CardKeyword keywordCards = new CardKeyword(confFile);
		keywordCards.CreateKeywordCards(J1);
		J0.add(J1);
		
		CardKeyword keywordCards2 = new CardKeyword(confFile);
		keywordCards2.CreateKeywordCards(J2);
		J0.add(J2);
		
//		Display the window.
		frame.add(J0);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(final String[] args) {
		
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
				createKeyWordPanel(args);
			}
		});
	}
}

