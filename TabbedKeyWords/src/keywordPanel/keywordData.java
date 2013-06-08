package keywordPanel;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class keywordData {

	String OutFile, InFile;
	String ConfigFile;

	int numKeys;
	ArrayList<KeyData> Keys = new ArrayList<KeyData>();
	ArrayList<KeyData> ActiveKeys = new ArrayList<KeyData>();

	ArrayList<String>  KeyNames = new ArrayList<String>();
	ArrayList<String>  ActiveKeyNames = new ArrayList<String>();

	KeyData RemoveKeywordDummy;

	public keywordData(String ConfigFile){
		this.ConfigFile = ConfigFile;
		RemoveKeywordDummy = new KeyData(new String("Remove Keyword"),null,0,null);
	}

	public enum ParaType {
		Double,Integer,String,File,List,EnumType
	}
	public enum ParaListType {
		Undef,Double,Integer,String
	}

	
	public enum GuiKeyStatus {
		optional,required
	}

	// Each Keyword data is this
	public class KeyData {
		JPanel Card;
		String Name,GUIName;
		ArrayList<ParaData> Paras = new ArrayList<ParaData>();
		int numParas,numIns=0;
		int Modified = 0;
		GuiKeyStatus Status;
		public KeyData(String name, ArrayList<ParaData> paras, int n, GuiKeyStatus status)
		{
			Name = name;
			GUIName = name; // default is the same
			Paras = paras;
			numParas = n;
			Status = status;

		}

		public String toString() // override so combo box knows what to call it
		{
			return GUIName; 
		}

	}

	public class ParaData {
		String Name;
		ParaType Type;
		Object Data;
		ArrayList<String> ParaTypeList;
		ParaListType ListType;
		int ListDim=0,ListNum=0;
		int Modified = 0;

		public ParaData(String name, ParaType type, Object data,
				ArrayList<String> paraTypeList, 
				int PListdim, ParaListType PListType,int PListNum)
		{
			Name = name;
			Type = type;
			Data = data;
			ParaTypeList = paraTypeList;
			ListDim = PListdim;
			ListType = PListType;
			ListNum = PListNum;
		}
	}

	private ParaData CopyPara(ParaData P)
	{
		String Name = new String(P.Name);
		Object data = null;
		ArrayList<String> List = new ArrayList<String>();

		if (P.Type == ParaType.Double)
			data = (Object) new Double((Double) P.Data);
		else if (P.Type == ParaType.Integer)
			data = (Object) new Integer((Integer) P.Data);				
		else if (P.Type == ParaType.String)
			data = (Object) new String((String) P.Data);
		else if (P.Type == ParaType.EnumType)
		{
			for (String s : P.ParaTypeList)
				List.add(new String(s));

			data = (Object) new String((String)P.Data); // default
		}
		else if (P.Type == ParaType.List)
		{
			if (P.ListDim == 1 && P.ListType == ParaListType.Double)
			{
				Double[][] D = new Double[1][1];
				D[0][0] = new Double(0.0);
				data = (Object)D;
			} 
			else if (P.ListDim == 1 && P.ListType == ParaListType.Integer)
			{
				Integer[][] I = new Integer[1][1];
				I[0][0] = new Integer(0);
				data = (Object)I;
			}

			
		}
		
		ParaData Pr = new ParaData(Name,P.Type,data,List,P.ListDim,P.ListType,P.ListNum);
		Pr.Modified = P.Modified;

		return Pr;
	}

	public KeyData KeyCopy(KeyData K)
	{
		String Name = new String(K.Name);
		int NumParas = K.numParas;
		ArrayList<ParaData> Paras = new ArrayList<ParaData>();

		for (ParaData p : K.Paras)
			Paras.add(CopyPara(p));

		KeyData Kr = new KeyData(Name,Paras,NumParas,K.Status);
		Kr.Modified = K.Modified;

		K.numIns++;

		return Kr;
	}

	// used for ActionListeners

	public void SetDoubleData(int k, int p, Double D) {
		(ActiveKeys.get(k)).Paras.get(p).Data = (Object) D;
		(ActiveKeys.get(k)).Paras.get(p).Modified = 1;
		(ActiveKeys.get(k)).Modified = 1;
	}

	public void SetIntegerData(int k, int p, Integer I) {
		(ActiveKeys.get(k)).Paras.get(p).Data = (Object) I;
		(ActiveKeys.get(k)).Paras.get(p).Modified = 1;
		(ActiveKeys.get(k)).Modified = 1;
	}

	public void SetStringData(int k, int p, String S) {
		(ActiveKeys.get(k)).Paras.get(p).Data = (Object) S;
		(ActiveKeys.get(k)).Paras.get(p).Modified = 1;
		(ActiveKeys.get(k)).Modified = 1;
	}

	public void SetEnumTypeData(int k, int p, int I) {
		(ActiveKeys.get(k)).Paras.get(p).Data = 
				(Object) (ActiveKeys.get(k)).Paras.get(p).ParaTypeList.get(I);
		(ActiveKeys.get(k)).Paras.get(p).Modified = 1;
		(ActiveKeys.get(k)).Modified = 1;
	}

	public void SetListData(int k, int p, JButton B)
	{
//		ParaData P = ActiveKeys.get(k).Paras.get(p);
		
//		if (P.ListType == ParaListType.Double && P.ListDim == 3)
			SetDouble3Data(k,p,B);
			
			
			
	}

	
	public void SetDouble3Data(int k, int p, JButton B)
	{
//		
		ParaData P = ActiveKeys.get(k).Paras.get(p);
		
		final int Cols = P.ListDim;
//		int Rows = P.ListNum;
		
		Object[] columnNames = new String[Cols];
		
		columnNames[0] = "1";
		if (Cols >= 2) columnNames[1] = "2";
		if (Cols == 3) columnNames[2] = "3";
		
		Object[][] data = (Object[][]) P.Data;
		int CSize = Cols*50;
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		final JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(CSize, 200));
		table.setFillsViewportHeight(true);
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		Object D = dtm.getValueAt(0, 0);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if (e.getButton() == MouseEvent.BUTTON1) return;
				
				Object[] options = { "Add Row", "Delete Row" };
				int ret = JOptionPane.showOptionDialog(table, "Make A Choice", "Add/Delete",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int[] rows = table.getSelectedRows();

				
				if (ret == 1)
				{
					for(int i=0;i<rows.length;i++){
						model.removeRow(rows[i]-i);
					}
				}
				else // ret == 0
				{
					Double[]  NewRow = new Double[Cols];
					NewRow[0] = new Double(0.0);
					
					if ( Cols == 2) {
						NewRow[1] = new Double(0.0);
					}
					else if (Cols == 3) {
						NewRow[2] = new Double(0.0);
					}
					
					if (rows.length == 0) {
						model.insertRow(table.getRowCount()+1,NewRow); // put at end
					} else 
					{
						model.insertRow(rows[0]+1,NewRow);
					}
				}
			}});
		JScrollPane Jp = new JScrollPane(table);

		int result = JOptionPane.showConfirmDialog(B,Jp, "List Values (right click Add/Remove)",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if(result == 0)
		{
			System.out.println("ok");

			int numRows = table.getRowCount();
			int numCols = table.getColumnCount();
						
			Object Data[][] = getTableData(table);
			ActiveKeys.get(k).Paras.get(p).Data = Data;
			
			System.out.println("Value of data: ");
			for (int i=0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j=0; j < numCols; j++) {
					System.out.print("  " + Data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");

		}
		else
		{
			System.out.println("cancel");
		}

	}
	
	public void LoadActiveKeys()
	{
		for(KeyData K : this.Keys) {
			if (K.Status == GuiKeyStatus.required) {
				this.ActiveKeys.add(KeyCopy(K));
				this.ActiveKeyNames.add(new String(K.Name));
			}
		}
	}

	public void readConfigKeyFile() {
		int k,p,n;
		String toks[],KeyName,ParaName,ParaTy;
		ParaType PType = null;
		GuiKeyStatus GuiStatus;

		BufferedReader br = null;
		KeyData Key;
		Object data = null;

		int NumParas;
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader(ConfigFile));

			sCurrentLine = br.readLine();
			toks = sCurrentLine.split(" ");

			try {
				this.numKeys = Integer.parseInt(toks[1]);
			} catch (NumberFormatException e) {
				String M = "Bad Keyword Number";
				JOptionPane.showMessageDialog(null,M);
				System.exit(1);
			}

			for (k = 1; k <= this.numKeys; k++)
			{
				sCurrentLine = br.readLine();
				toks = sCurrentLine.split(" ");
				if (toks.length == 5)
				{
					n = Integer.parseInt(toks[0]);
					if (n != k) break;

					if (toks[2].equals("GuiOptional"))
						GuiStatus = GuiKeyStatus.optional;
					else GuiStatus = GuiKeyStatus.required;

					NumParas = Integer.parseInt(toks[4]);
					KeyName = new String(toks[1]);

					ArrayList<ParaData> Paras = new ArrayList<ParaData>();

					for (p = 1; p <= NumParas; p++)
					{
						int PListDim=0;
						int PListNum=0;
						ParaListType PListType =ParaListType.Undef;
						sCurrentLine = br.readLine();
						toks = sCurrentLine.split(" ");
						n = Integer.parseInt(toks[0]);
						if (n != p) break;

						PType = null;

						ParaName = new String(toks[1]);
						ParaTy = toks[2]; 

						ArrayList <String> ParaTypeList = new ArrayList<String>();

						if (ParaTy.equals("double")) 
						{
							data = (Object) Double.parseDouble(toks[3]);
							PType = ParaType.Double;
						}
						else if (ParaTy.equals("int")) 
						{
							data = (Object) Integer.parseInt(toks[3]);
							PType = ParaType.Integer;
						}
						else if (ParaTy.equals("char*")) 
						{
							data = (Object) new String(" ");
							PType = ParaType.String;
						}
						else if (ParaTy.equals("GSList*")) 
						{
							PType = ParaType.List;
							
							if (toks[3].equals("double"))
							{
								PListDim = 1;
								PListType = ParaListType.Double;
								Double[][] D = new Double[1][1];
								D[0][0] = new Double(0.0);
								PListNum = 1;
								data = (Object)D;
							} 
							else if (toks[3].equals("double2"))
							{
								PListDim = 2;
								PListType = ParaListType.Double;
							}
							else if (toks[3].equals("double3"))
							{
								PListDim = 3;
								PListType = ParaListType.Double;
							}
							else if (toks[3].equals("int"))
							{
								PListDim = 1;
								PListType = ParaListType.Integer;
								Integer[][] I = new Integer[1][1];
								I[1][1] = new Integer(0);
								PListNum = 1;
								data = (Object)I;
							} 
							else if (toks[3].equals("int2"))
							{
								PListDim = 2;
								PListType = ParaListType.Integer;
							}
							else if (toks[3].equals("int3"))
							{
								PListDim = 3;
								PListType = ParaListType.Integer;
							}
							else if (toks[3].equals("char*"))
							{
								PListDim = 3;
								PListType = ParaListType.String;
							}
							
							
							
						}
						else if (ParaTy.equals("{")) // beginning of defined type list 
						{
							int s=3;
							PType = ParaType.EnumType;

							while (!toks[s].equals("}"))
							{
								String Etype = new String(toks[s]);
								ParaTypeList.add(Etype);
								s++;
							}

							data = (Object) new String(toks[s+1]); // default

						}

						if (PType != null)
						{
							ParaData Pd = new ParaData(ParaName,PType,(Object)data,
									ParaTypeList,PListDim,PListType,PListNum);
							Paras.add(Pd);
						}
					}

					this.KeyNames.add(KeyName);

					NumParas = Paras.size(); // we've skipped over ones we can't handle yet
					Key = new KeyData(KeyName,Paras,NumParas,GuiStatus);
					this.Keys.add(Key);
				}
				else 
				{
					JOptionPane.showMessageDialog(null,new String("Bad Para Line"));
					System.exit(1);
				}
			}					
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	

	}

	private KeyData FindKeyFromName(String Name)
	{

		for (KeyData K : Keys)
		{
			if (K.Name.equals(Name))
				return K;
		}
		return null;
	}

	private ParaData FindParaFromName(ArrayList<ParaData> PList, String Name)
	{

		for (ParaData P : PList)
		{
			if (P.Name.equals(Name))
				return P;
		}
		return null;
	}

	private ArrayList<String> ReFormQStrings(String toks[])
	{
		ArrayList<String> NewToks = new ArrayList<String>();
				
		int i = 0;
		while (i < toks.length)
		{
			String t = toks[i];
			int l = t.length();
			
			if (l == 0) { i++; continue;}
			
			if ((t.charAt(0) == '\'') &&  (t.charAt(l-1) == '\''))
			{
				NewToks.add(t.substring(1,l- 1));
				i++;
			}
			else if ((t.charAt(0) == '\"') &&  (t.charAt(l-1) == '\"'))
			{
				NewToks.add(t.substring(1,l- 1));
				i++;
			}
			else if (t.charAt(0) == '\'')
			{
				String Tok2 = new String(t.substring(1,l));
				i++;
				int foundEnd = 0;
				while (i < toks.length)
				{
					String tn = toks[i];
					if (tn.charAt(tn.length()-1) == '\'')
					{
						Tok2 = Tok2 + " " + tn.substring(0,tn.length()- 1);
						foundEnd = 1;
						i++;
						break;
					}
					else 
					{
						Tok2 = Tok2 + tn;
						i++;
					}

				}
				if (foundEnd != 1) return null;

				NewToks.add(Tok2);
			}
			else if (t.charAt(0) == '\"')
			{
				String Tok2 = new String(t.substring(1,l));
				i++;
				int foundEnd = 0;
				while (i < toks.length)
				{
					

					String tn = toks[i];
					if (tn.charAt(tn.length()-1) == '\"')
					{
						Tok2 = Tok2 +  " " + tn.substring(0,tn.length()- 1);
						foundEnd = 1;
						i++;
						break;
					}
					else 
					{
						Tok2 = Tok2 + tn;
						i++;
					}

					if (foundEnd != 1) return null;

					NewToks.add(Tok2);
				}
			}
			else 
			{
				NewToks.add(toks[i]);
				i++;
			}

		}

		return NewToks;
	}

	static ArrayList<ParaData>  ReplacePara(ArrayList<ParaData> PList, ParaData P)
	{
		for(int i=0; i < PList.size();i ++)
		{
			if (PList.get(i).Name.equals(P.Name))
			{
				PList.set(i, P);
				return PList;
			}
		}
		
		return null;
	}
	
	public void readKeyFile(){

		String origToks[];
		ArrayList<String> toks = new ArrayList<String>();
		BufferedReader br = null;
		JFileChooser fc;
		File file = null;
		KeyData Key;

		fc = new JFileChooser(System.getProperty("user.dir"));
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			System.exit(0);
		}

		try {
			String sCurrentLine;
			String keyName;

			br = new BufferedReader(new FileReader(file));


			while ((sCurrentLine = br.readLine()) != null)
			{
				origToks = sCurrentLine.split("\\s+");
				

				keyName = origToks[0];

				if (keyName.length() == 0) continue;
				if (keyName.charAt(0) == '*') continue;

				toks = ReFormQStrings(origToks);
				
				Key = FindKeyFromName(keyName);

				if (Key != null) {
					KeyData NewKey = KeyCopy(Key);
					NewKey.Modified = 1;

					ArrayList<ParaData> PList = NewKey.Paras;

					int n = toks.size()-1;
					int i=1;			
					while ( i < n)
					{
						String paraName,Data;
						paraName = toks.get(i);

						if (paraName.contains("="))
						{
							String ParaEq[] = paraName.split("=");
							paraName = ParaEq[0];
							Data = ParaEq[1];
							i++;
						}
						else 
						{
							i = i + 2; //Advance over equals
							Data = toks.get(i);
							i++;
						}

						ParaData P = FindParaFromName(PList,paraName);

						if (P != null)
						{
							P.Modified = 1;
							if (P.Type == ParaType.Double)
							{
								try {
									Double D = Double.parseDouble(Data);
									P.Data = (Object) D;
								}
								catch (NumberFormatException e) 
								{
									JOptionPane.showMessageDialog(null,new String("Bad double in file"));
									System.exit(1);	
								}
							}
							else if (P.Type == ParaType.Integer)
							{
								try {
									Integer I = Integer.parseInt(Data);
									P.Data = (Object) I;
								}
								catch (NumberFormatException e) 
								{
									JOptionPane.showMessageDialog(null,new String("Bad Integer in file"));
									System.exit(1);	
								}
							}
							else if  (P.Type == ParaType.EnumType || P.Type == ParaType.String)
							{
								try {
									String S = new String(Data);
									P.Data = (Object) S;
								}
								catch (NumberFormatException e) 
								{
									JOptionPane.showMessageDialog(null,new String("Bad String in file"));
									System.exit(1);	
								}
							}

							else 
							{
								JOptionPane.showMessageDialog(null,new String("Bad Keyword in file"));
								System.exit(1);
							}
							
//							ReplacePara(PList,P);
						}
						else 
						{
							JOptionPane.showMessageDialog(null,new String("Bad parameter in file"));
							System.exit(1);
						}
					}

					ActiveKeys.add(NewKey);
				}
				else{
					JOptionPane.showMessageDialog(null,new String("Bad Keyword in file"));
					System.exit(1);
				}

			}

		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void writeKeyFile(){
		int k,p;
		String Ps = new String();
		File file = null;
		
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		
		int returnVal = fc.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			System.exit(0);
		}

		try {

			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			for (k = 0; k < ActiveKeys.size(); k++) {
				KeyData Key = ActiveKeys.get(k);

				if (Key.Modified == 0) continue;

				output.write(ActiveKeys.get(k).Name);

				Ps = "  ";

				for (p = 0; p < Key.numParas; p++) {

					ParaData Para = Key.Paras.get(p);
					ParaType T = Para.Type;

					if (Para.Modified == 1)
					{
						if (T == ParaType.Double) {
							Ps = Ps + String.format(" %s = %f ",Para.Name,(Double)Para.Data);	
						} else if (T == ParaType.Integer) {
							Ps =  Ps + String.format(" %s = %d ",Para.Name,(Integer)Para.Data);	
						} else if (T == ParaType.String) {
							String S = new String((String)Para.Data);
							S = S.trim();
							Ps =  Ps + String.format(" %s = '%s' ",Para.Name,S);	
						}
						else if (T == ParaType.EnumType) {
							Ps =  Ps + String.format(" %s = %s ",Para.Name,(String)Para.Data);	
						}
					}
				}
				Ps = Ps + "\n";
				output.write(Ps);
			}
			output.close();	
		} catch ( IOException e ) {
			e.printStackTrace();
		}

	}

//	public void testKeyFile(){
//		//		keywordData Keys = new keywordData("FileName");
//		this.numKeys = 5;
//
//		ArrayList<ParaData> Paras = new ArrayList<ParaData>();
//
//		int NumParas = 6;
//
//		Double D1 = 50.0;
//		ParaData Pd = new ParaData("Name1",ParaType.Double,(Object)D1,null);
//		Paras.add(Pd);
//
//		Double D2 = 150.0;
//		Pd = new ParaData("Name2",ParaType.Double,(Object)D2,null);
//		Paras.add(Pd);
//
//		Integer I1 = 15;
//		Pd = new ParaData("Name3",ParaType.Integer,(Object)I1,null);
//		Paras.add(Pd);
//
//		Integer I2 = 115;
//		Pd = new ParaData("Name4",ParaType.Integer,(Object)I2,null);
//		Paras.add(Pd);
//
//		String S1 = new String("A string");
//		Pd = new ParaData("Name1",ParaType.String,(Object)S1,null);
//		Paras.add(Pd);
//
//		String S2 = new String("A second string");
//		Pd = new ParaData("Name1",ParaType.String,(Object)S2,null);
//		Paras.add(Pd);
//
//		ArrayList<ParaData> Paras2 = new ArrayList<ParaData>();
//
//		int NumParas2 = 6;
//
//		Double D12 = 50.0;
//		ParaData Pd2 = new ParaData("Name1",ParaType.Double,(Object)D12,null);
//		Paras2.add(Pd2);
//
//		Double D22 = 150.0;
//		Pd2 = new ParaData("Name2",ParaType.Double,(Object)D22,null);
//		Paras2.add(Pd2);
//
//		Integer I12 = 15;
//		Pd2 = new ParaData("Name3",ParaType.Integer,(Object)I12,null);
//		Paras2.add(Pd2);
//
//		Integer I22 = 115;
//		Pd2 = new ParaData("Name4",ParaType.Integer,(Object)I22,null);
//		Paras2.add(Pd2);
//
//		String S12 = new String("A string");
//		Pd2 = new ParaData("Name1",ParaType.String,(Object)S12,null);
//		Paras2.add(Pd2);
//
//		String S22 = new String("A second string");
//		Pd2 = new ParaData("Name1",ParaType.String,(Object)S22,null);
//		Paras2.add(Pd2);
//
//		this.KeyNames.add("Growth");
//		this.Keys.add(new KeyData("Growth",Paras,NumParas,GuiKeyStatus.required));
//		this.KeyNames.add("GlView");
//		this.Keys.add(new KeyData("GlView",Paras2,NumParas2,GuiKeyStatus.optional));
//		this.KeyNames.add("Main");
//		this.Keys.add(new KeyData("Main",Paras,NumParas,GuiKeyStatus.optional));
//		this.KeyNames.add("Mob");
//		this.Keys.add(new KeyData("Mob",Paras,NumParas,GuiKeyStatus.optional));
//		this.KeyNames.add("Substrate");
//		this.Keys.add(new KeyData("Substrate",Paras2,NumParas2,GuiKeyStatus.optional));
//
//	}

}
