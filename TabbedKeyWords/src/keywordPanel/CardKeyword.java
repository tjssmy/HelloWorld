package keywordPanel;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.ArrayList;

public class CardKeyword implements ItemListener {
	JPanel cards; //a panel that uses CardLayout

	private static JPanel paraEntry(String Name,
			final Object data, keywordData.ParaType Type) {
		JPanel Entry = new JPanel();
		Entry.setPreferredSize (new Dimension(350, 50));
		Entry.setLayout(new GridLayout(1,2));
		JButton JB = new JButton(Name);
		JB.setText(Name);
		Entry.add(JB);
		
		switch(Type) {
		
		case Double: {
			final Double D;
			final JTextField F = new JTextField(10);
			Entry.add(F);
			
			F.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					D = Double.parseDouble(F.getText())	;		
				}
				
			});
			
			break;
		}
		case EnumType: {
			break;
		}
		case File: {
			break;
		}
		case Integer: {
			Entry.add(JB);
			Entry.add(new JTextField(10));

			break;
		}
		case ListOfDoubles: {
			break;
		}
		case String: {
			Entry.add(JB);
			Entry.add(new JTextField(10));

			break;
		}
		default:
			break;
		}

		return Entry;
	}

	
	public JPanel CardList (int n, 
			ArrayList<keywordData.ParaData> paras ) {
		int j;
		//Create the "cards".
		JPanel card = new JPanel();
		//    	card.setPreferredSize (new Dimension(350, 800));
		card.setLayout(new GridLayout(n,1));
		
		for (j = 0; j < n; j++) {
//			card.add(new JButton();
			keywordData.ParaData P = paras.get(j);
			JPanel entry = paraEntry(P.Name,P.Data,P.Type);
			card.add(entry);
		}
		return card;
	}


	public void addKeyWordPanel(Container pane, 
			keywordData kData) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		int j;
		
		JComboBox cb = new JComboBox((kData.KeyNames.toArray()));
		cb.setEditable(false);
		cb.addItemListener(this);
		cb.setPreferredSize(new Dimension(200,20));
		comboBoxPane.add(cb);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		
		for (j = 0; j < kData.numKeys; j++) {
			keywordData.KeyData K = kData.Keys.get(j);
			ArrayList<keywordData.ParaData> P = K.Paras;
			int nParas = K.numParas;
			
			JPanel card = CardList(nParas,P);
			cards.add(card,K.Name);
		}
			
		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);
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


	/**
	 * Create the GUI and show it.  For thread safety,vg
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createKeyWordPanel() {
		//Create and set up the window.
		JFrame frame = new JFrame("CardLayoutDemo");
		
//		frame.setPreferredSize(new Dimension(350,800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		keywordData kData = new keywordData("file");
		kData.testKeyFile();
		
		//Create and set up the content pane.
		CardKeyword demo = new CardKeyword();

		demo.addKeyWordPanel(frame.getContentPane(),kData);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
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

