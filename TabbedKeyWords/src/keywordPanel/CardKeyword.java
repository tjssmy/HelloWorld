package keywordPanel;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class CardKeyword implements ItemListener {
	JPanel cards; //a panel that uses CardLayout

	private static JPanel paraEntry(String Name) {
		JPanel Entry = new JPanel();
		Entry.setPreferredSize (new Dimension(350, 50));
		Entry.setLayout(new GridLayout(1,2));
		JButton JB = new JButton(Name);
		JB.setText(Name);
		Entry.add(JB);
		Entry.add(new JTextField(10));
		return Entry;
	}

	
	public JPanel CardList (int n, String names[]) {
		int j;
		//Create the "cards".
		JPanel card = new JPanel();
		//    	card.setPreferredSize (new Dimension(350, 800));
		card.setLayout(new GridLayout(n,1));
		
		for (j = 0; j < n; j++) {
//			card.add(new JButton();
			JPanel entry = paraEntry(names[j]);
			card.add(entry);
		}
		return card;
	}


	public void addKeyWordPanel(Container pane, 
			int nKeys, String Keys[], ArrayList<KeyData> KeyParas) {
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		int j;
		
		JComboBox cb = new JComboBox(Keys);
		cb.setEditable(false);
		cb.addItemListener(this);
		cb.setPreferredSize(new Dimension(200,20));
		comboBoxPane.add(cb);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		
		for (j = 0; j < nKeys; j++) {
			KeyData K = KeyParas.get(j);
			int nParas = K.num;
			String name = K.Name;
			String names[] = K.Paras;
			
			JPanel card = CardList(nParas,names);
			cards.add(card,name);
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
		int j;
		//Create and set up the window.
		JFrame frame = new JFrame("CardLayoutDemo");
		
//		frame.setPreferredSize(new Dimension(350,800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		keywordData kData = new keywordData("file");
		
		//Create and set up the content pane.
		CardKeyword demo = new CardKeyword();
		int numKeys=5;
		String Keys[] = {"key 1","key 2","key 3","key 4","key 5"};
		String Paras[][] = {{"para1","para2d","para3","pac3ra4","para5"},
				{"para5","para2","pardda3","para4","padra5"},
				{"para5","parad2","para3","parac4","para5"},
				{"para1","para2","para3","para4","pdarad5"},
				{"para3","pardda2","para3","para4","padra5"}};
		ArrayList<KeyData>  KeyParas = new ArrayList<KeyData>();
		
		for (j=0; j< numKeys; j++)
		{
			KeyParas.add(new KeyData(Keys[j],Paras[j],5));
		}

		demo.addKeyWordPanel(frame.getContentPane(),numKeys,Keys,KeyParas);

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

