package keywordPanel;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class keywordData {

	String OutFile, InFile;
	String ConfigFile;

	int numKeys;
	ArrayList<KeyData> Keys = new ArrayList<KeyData>();
	ArrayList<KeyData> ActiveKeys = new ArrayList<KeyData>();
	
	ArrayList<String>  KeyNames = new ArrayList<String>();
	ArrayList<String>  ActiveKeyNames = new ArrayList<String>();

	
	public keywordData(String OutputFile,String ConfigFile){
		this.OutFile = OutputFile;
		this.ConfigFile = ConfigFile;
	}

	public enum ParaType {
		Double,Integer,String,File,ListOfDoubles,EnumType
	}

	public enum GuiKeyStatus {
		optional,required
	}
	
	// Each Keyword data is this
	public static class KeyData {
		String Name;
		ArrayList<ParaData> Paras = new ArrayList<ParaData>();
		int numParas;
		int Modified = 0;
		GuiKeyStatus Status;
		public KeyData(String name, ArrayList<ParaData> paras, int n, GuiKeyStatus status)
		{
			Name = name;
			Paras = paras;
			numParas = n;
			Status = status;
		
		}
		
		public String toString() // override so combo box knows what to call it
		{
			return Name; 
		}
		
	}
	
	public static class ParaData {
		String Name;
		ParaType Type;
		Object Data;
		ArrayList<String> ParaTypeList;
		int Modified = 0;
		
		public ParaData(String name, ParaType type, Object data, ArrayList<String> paraTypeList)
		{
			Name = name;
			Type = type;
			Data = data;
			ParaTypeList = paraTypeList;
		}
	}

	private static ParaData CopyPara(ParaData P)
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
			for (String s : List)
				List.add(new String(s));

			data = (Object) new String((String)P.Data); // default
		}
		
		ParaData Pr = new ParaData(Name,P.Type,data,List);
		return Pr;
	}
	
	public static KeyData KeyCopy(KeyData K)
	{
		String Name = new String(K.Name);
		int NumParas = K.numParas;
		ArrayList<ParaData> Paras = new ArrayList<ParaData>();
		
		for (ParaData p : K.Paras)
			Paras.add(CopyPara(p));
		
		KeyData Kr = new KeyData(Name,Paras,NumParas,K.Status);
		return Kr;
	}

	// used for ActionListeners

	public void SetDoubleData(int k, int p, Double D) {
		(Keys.get(k)).Paras.get(p).Data = (Object) D;
		(Keys.get(k)).Paras.get(p).Modified = 1;
		(Keys.get(k)).Modified = 1;
	}

	public void SetIntegerData(int k, int p, Integer I) {
		(Keys.get(k)).Paras.get(p).Data = (Object) I;
		(Keys.get(k)).Paras.get(p).Modified = 1;
		(Keys.get(k)).Modified = 1;
	}

	public void SetStringData(int k, int p, String S) {
		(Keys.get(k)).Paras.get(p).Data = (Object) S;
		(Keys.get(k)).Paras.get(p).Modified = 1;
		(Keys.get(k)).Modified = 1;
	}
	
	public void SetEnumTypeData(int k, int p, int I) {
		(Keys.get(k)).Paras.get(p).Data = 
				(Object) (Keys.get(k)).Paras.get(p).ParaTypeList.get(I);
		(Keys.get(k)).Paras.get(p).Modified = 1;
		(Keys.get(k)).Modified = 1;
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
						else if (ParaTy.equals("{")) // begining of def type list 
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
							ParaData Pd = new ParaData(ParaName,PType,(Object)data,ParaTypeList);
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
					String M = "Bad para line";
					JOptionPane.showMessageDialog(null,M);
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
		
		for(KeyData K : this.Keys) {
			if (K.Status == GuiKeyStatus.required) {
				this.ActiveKeys.add(KeyCopy(K));
				this.ActiveKeyNames.add(new String(K.Name));
			}
				
		}
		
	}

	public void readKeyFile(String InFile){
	
	}
	
	public void writeKeyFile(String OutFile){
		int k,p;
		String Ps = new String();
		try {
			File file = new File(OutFile);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			for (k = 0; k < numKeys; k++) {
				KeyData Key = Keys.get(k);
				
				if (Key.Modified == 0) continue;
				
				output.write(Keys.get(k).Name);
				
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

	public void testKeyFile(){
		//		keywordData Keys = new keywordData("FileName");
		this.numKeys = 5;

		ArrayList<ParaData> Paras = new ArrayList<ParaData>();

		int NumParas = 6;

		Double D1 = 50.0;
		ParaData Pd = new ParaData("Name1",ParaType.Double,(Object)D1,null);
		Paras.add(Pd);

		Double D2 = 150.0;
		Pd = new ParaData("Name2",ParaType.Double,(Object)D2,null);
		Paras.add(Pd);

		Integer I1 = 15;
		Pd = new ParaData("Name3",ParaType.Integer,(Object)I1,null);
		Paras.add(Pd);

		Integer I2 = 115;
		Pd = new ParaData("Name4",ParaType.Integer,(Object)I2,null);
		Paras.add(Pd);

		String S1 = new String("A string");
		Pd = new ParaData("Name1",ParaType.String,(Object)S1,null);
		Paras.add(Pd);

		String S2 = new String("A second string");
		Pd = new ParaData("Name1",ParaType.String,(Object)S2,null);
		Paras.add(Pd);
		
		ArrayList<ParaData> Paras2 = new ArrayList<ParaData>();

		int NumParas2 = 6;

		Double D12 = 50.0;
		ParaData Pd2 = new ParaData("Name1",ParaType.Double,(Object)D12,null);
		Paras2.add(Pd2);

		Double D22 = 150.0;
		Pd2 = new ParaData("Name2",ParaType.Double,(Object)D22,null);
		Paras2.add(Pd2);

		Integer I12 = 15;
		Pd2 = new ParaData("Name3",ParaType.Integer,(Object)I12,null);
		Paras2.add(Pd2);

		Integer I22 = 115;
		Pd2 = new ParaData("Name4",ParaType.Integer,(Object)I22,null);
		Paras2.add(Pd2);

		String S12 = new String("A string");
		Pd2 = new ParaData("Name1",ParaType.String,(Object)S12,null);
		Paras2.add(Pd2);

		String S22 = new String("A second string");
		Pd2 = new ParaData("Name1",ParaType.String,(Object)S22,null);
		Paras2.add(Pd2);

		this.KeyNames.add("Growth");
		this.Keys.add(new KeyData("Growth",Paras,NumParas,GuiKeyStatus.required));
		this.KeyNames.add("GlView");
		this.Keys.add(new KeyData("GlView",Paras2,NumParas2,GuiKeyStatus.optional));
		this.KeyNames.add("Main");
		this.Keys.add(new KeyData("Main",Paras,NumParas,GuiKeyStatus.optional));
		this.KeyNames.add("Mob");
		this.Keys.add(new KeyData("Mob",Paras,NumParas,GuiKeyStatus.optional));
		this.KeyNames.add("Substrate");
		this.Keys.add(new KeyData("Substrate",Paras2,NumParas2,GuiKeyStatus.optional));

	}

}
