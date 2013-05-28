package keywordPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Keywords extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final String gapList[] = {"0", "10", "15", "20"};
	final static int maxGap = 20;
	JComboBox horGapComboBox;
	JComboBox verGapComboBox;
	JButton applyButton = new JButton("Apply gaps");
	GridLayout experimentLayout = new GridLayout(0,2);

	public Keywords(String name) {
		super(name);
		setResizable(false);
	}

	public void initGaps() {
		horGapComboBox = new JComboBox(gapList);
		verGapComboBox = new JComboBox(gapList);
	}

	public JPanel keywordPanel(final Container pane) {
		initGaps();
		final JPanel keywordsPanel = new JPanel();
		keywordsPanel.setLayout(new GridLayout(5,1));
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(2,3));

		//Set up components preferred size
		JButton b = new JButton("Just fake button");
		Dimension buttonSize = b.getPreferredSize();
		keywordsPanel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+maxGap,
				(int)(buttonSize.getHeight() * 3.5)+maxGap * 2));

		//Add buttons to experiment with Grid Layout
		keywordsPanel.add(new JButton("Button 1"));
		keywordsPanel.add(new JButton("Button 2"));
		keywordsPanel.add(new JButton("Button 3"));
		keywordsPanel.add(new JButton("Long-Named Button 4"));
		keywordsPanel.add(new JButton("5"));

		//Add controls to set up horizontal and vertical gaps
		controls.add(new Label("Horizontal gap:"));
		controls.add(new Label("Vertical gap:"));
		controls.add(new Label(" "));
		controls.add(horGapComboBox);
		controls.add(verGapComboBox);
		controls.add(applyButton);

		//Process the Apply gaps button press
		applyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Get the horizontal gap value
				String horGap = (String)horGapComboBox.getSelectedItem();
				//Get the vertical gap value
				String verGap = (String)verGapComboBox.getSelectedItem();
				//Set up the horizontal gap value
				experimentLayout.setHgap(Integer.parseInt(horGap));
				//Set up the vertical gap value
				experimentLayout.setVgap(Integer.parseInt(verGap));
				//Set up the layout of the buttons
				experimentLayout.layoutContainer(keywordsPanel);
			}
		});
		pane.add(keywordsPanel, BorderLayout.NORTH);
		pane.add(new JSeparator(), BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
		return keywordsPanel;
	}

	

	public JPanel parameterPanel(final Container pane) {
		
		final JPanel paraPanel = new JPanel();
		paraPanel.setLayout(new GridLayout(5,1));

		//Set up components preferred size
		JButton b = new JButton("Just fake button");
		Dimension buttonSize = b.getPreferredSize();
		paraPanel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+maxGap,
				(int)(buttonSize.getHeight() * 3.5)+maxGap * 2));

		//Add buttons to experiment with Grid Layout
		paraPanel.add(new JButton("Button 1"));
		paraPanel.add(new JButton("Button 2"));
		paraPanel.add(new JButton("Button 3"));
		paraPanel.add(new JButton("Long-Named Button 4"));
		paraPanel.add(new JButton("5"));

		//
		//Process the Apply gaps button press
		applyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Get the horizontal gap value
				String horGap = (String)horGapComboBox.getSelectedItem();
				//Get the vertical gap value
				String verGap = (String)verGapComboBox.getSelectedItem();
				//Set up the horizontal gap value
				experimentLayout.setHgap(Integer.parseInt(horGap));
				//Set up the vertical gap value
				experimentLayout.setVgap(Integer.parseInt(verGap));
				//Set up the layout of the buttons
				experimentLayout.layoutContainer(paraPanel);
			}
		});
		
		pane.add(paraPanel, BorderLayout.NORTH);
		return paraPanel;
	}


	private static JPanel Panel1(int i1, int i2, String Name, Color C)
	{
		JPanel subPanel1 = new JPanel();
		subPanel1.setPreferredSize (new Dimension(i1, i2));
		subPanel1.setBackground (C);
		JLabel label1 = new JLabel (Name);
		subPanel1.add (label1);

		return subPanel1;
	}
	
	private static JPanel CardPanel(int i1, int i2, String Name, Color C)
	{
		JPanel subPanel1 = new JPanel(new CardLayout());
//		JPanel subPanel1 = new JPanel();
		subPanel1.setPreferredSize (new Dimension(i1, i2));
		subPanel1.setBackground (C);
//		JLabel label1 = new JLabel (Name);
//		subPanel1.add (label1);

		return subPanel1;
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method is invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		//		Keywords frame = new Keywords("Keywords");
		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		//Set up the content pane.
		//		frame.addComponentsToPane(frame.getContentPane());
		//		//Display the window.
		//		frame.pack();
		//		frame.setVisible(true);

		Keywords frame = new Keywords("Keywords");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set up the content pane.
		//		frame.keywordPanel(frame.getContentPane());
		//		frame.addFirstPane(frame.getContentPane(),frame);
		//Display the window.

		JPanel subPanel1,subPanel2; 
		subPanel1 = Panel1(350,800,"Keywords",Color.green);
		frame.keywordPanel(subPanel1);
		
		subPanel2 = CardPanel(350,800,"Parameters",Color.red);		
		frame.parameterPanel(subPanel2);


		// Set up primary panel
		JPanel primary = new JPanel();
		//		primary.setBackground (Color.blue);
		primary.add (subPanel1);
		primary.add (subPanel2);

		frame.getContentPane().add(primary);
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
				createAndShowGUI();
			}
		});
	}


}
