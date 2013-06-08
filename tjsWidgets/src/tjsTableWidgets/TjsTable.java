package tjsTableWidgets;
/*
 * TableDemo.java requires no other files.
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/** 
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TjsTable extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
	public TjsTableModel model;
	
    public TjsTable(String[] columnNames, Object data[][], Container parentCont) {
        super(new GridLayout(1,0));
        model = new TjsTableModel(columnNames,data);
        final JTable table = new JTable(model);
        final int Cols = table.getColumnCount();
        int Csize = Cols*75;
        
        table.setPreferredScrollableViewportSize(new Dimension(Csize, 120));
        table.setToolTipText("Double click to edit cell. Right click to add/del rows");
        
        table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if (e.getButton() == MouseEvent.BUTTON1) return;
				
				Object[] options = { "Add Row", "Delete Row", "Cancel" };
				
				int ret = JOptionPane.showOptionDialog(table,null, "Add/Delete",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
						null, options, options[2]);

				
				TjsTableModel model = (TjsTableModel) table.getModel();
				int[] rows = table.getSelectedRows();

				
				if (ret == 1)
				{
					for(int i=0;i<rows.length;i++){
						model.removeRow(rows[i]-i);
					}
				}
				else if ( ret == 0)
				{
					if (rows.length == 0) {
						Object[] NewRow = model.DuplicateRowData(table.getRowCount());
						model.insertRow(table.getRowCount()+1,NewRow); // put at end
					}
					else 
					{
						Object[] NewRow = model.DuplicateRowData(rows[0]);
						model.insertRow(rows[0]+1,NewRow);
					}
				}
				
				table.validate();
			}});
	
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public Object[][] getTableData () {
	    int nRow = model.getRowCount(), nCol = model.getColumnCount();
	    Object[][] tableData = new Object[nRow][nCol];
	    for (int i = 0 ; i < nRow ; i++)
	        for (int j = 0 ; j < nCol ; j++)
	            tableData[i][j] = model.getValueAt(i,j);
	    return tableData;
	}
	
    
    public class TjsTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames; 		
		private Object[][] data;
		
		public TjsTableModel(String[] cNames, Object d[][])
		{
			columnNames = cNames;
			data = d;
		}

		

		private Object[] DuplicateRowData(int row)
		{
			int c = getColumnCount();
			Object[] newRow = new Object[c];
			for (int j=0; j < c; j++)
			{
				if (data[row][j] instanceof Double)
					newRow[j] = new Double(0.0);
				else if (data[row][j] instanceof Integer)
					newRow[j] = new Integer(0);
				else if (data[row][j] instanceof String)
					newRow[j] = new String("");
				else if (data[row][j] instanceof Boolean)
					newRow[j] = new Boolean(false);
				else data[row][j] = null;
			}
			return newRow;
		}
		
		public void insertRow(int row, Object[] rowData) 
		{
			int i,j;
			int r = getRowCount() + 1; // add row
			int c = getColumnCount();
			
			Object[][] NewData = new Object[r][c];
			
			for (i=0; i < r; i++)
			{
				for (j=0; j < c; j++)
					if (i < row)
					{
						NewData[i][j] = data[i][j];
					}
					else if (i == row)
					{
						NewData[i][j] = rowData[j];
					}
					else
					{
						NewData[i][j] = data[i-1][j];
					}
			}
			
			data = NewData;
			fireTableRowsInserted(row, row);
		}

			
		public void removeRow(int row) {  
			int i,j;
			int r = getRowCount(); 
			int c = getColumnCount();
			
			Object[][] NewData = new Object[r][c];
			
			for (i=0; i < r; i++)
			{
				for (j=0; j < c; j++)
					if (i < row)
					{
						NewData[i][j] = data[i][j];
					}
					else if (i == row);
					else
					{
						NewData[i-1][j] = data[i-1][j];
					}
			}
			
			data = NewData;
			fireTableRowsDeleted(row,row);  
		}  
		    
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class<? extends Object> getColumnClass(int c) {
            return (Class<? extends Object>) getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
//            if (col < 2) {
//                return false;
//            } else {
//                return true;
//            }
        	return true;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	
//		String[] columnNames = {"First Name",
//                                        "Last Name",
//                                        "Sport",
//                                        "# of Years",
//                                        "Vegetarian"};
//        Object[][] data = {
//            {"Mary", "Campione",
//             "Snowboarding", new Double(5), new Boolean(false)},
//            {"Alison", "Huml",
//             "Rowing", new Double(3), new Boolean(true)},
//            {"Kathy", "Walrath",
//             "Knitting", new Double(2), new Boolean(false)},
//            {"Sharon", "Zakhour",
//             "Speed reading", new Double(20), new Boolean(true)},
//            {"Philip", "Milne",
//             "Pool", new Double(10), new Boolean(false)},
//            {"Isaac", "Rabinovitch", 
//                "Nitpicking", new Double(1000), new Boolean(false)}
//        };
//        
        
        String[]  columnNames = {"first", "second","third", "forth"};
        
//        Object[][] data = {
//        		{new Double(1), new Double(2)},
//        		{new Double(3), new Double(4)},
//        };
        
		String D1 = new String("one");
		Double D2 = new Double(2.0);
		String D3 = new String("two");
		Double D4 = new Double(4.0);
		
        Object[][] data = new Object[3][4];
        
        data[0][0] = D1;
        data[0][1] = D2;
        data[0][2] = new Integer(0);
        data[0][3] =  new Boolean(false);
        
        data[1][0] = D3;
        data[1][1] = D4;
        data[1][2] = new Integer(0);
        data[1][3] =  new Boolean(false);
        
        data[2][0] = D3;
        data[2][1] = D4;
        data[2][2] = new Integer(0);
        data[2][3] =  new Boolean(false);
        
        
        
		
        //Create and set up the content pane.
        TjsTable newContentPane = new TjsTable(columnNames,data,frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
