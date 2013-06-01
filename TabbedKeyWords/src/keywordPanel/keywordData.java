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
	ArrayList<String>  KeyNames = new ArrayList<String>();

	public keywordData(String OutputFile,String ConfigFile){
		this.OutFile = OutputFile;
		this.ConfigFile = ConfigFile;
	}

	public enum ParaType {
		Double,Integer,String,File,ListOfDoubles,EnumType
	}

	public static class ParaData {
		String Name;
		ParaType Type;
		Object Data;

		public ParaData(String name, ParaType type, Object data)
		{
			Name = name;
			Type = type;
			Data = data;
		}
	}


	// Each Keyword data is this
	public static class KeyData {
		String Name;
		ArrayList<ParaData> Paras = new ArrayList<ParaData>();
		int numParas;

		public KeyData(String name, ArrayList<ParaData> paras, int n)
		{
			Name = name;
			Paras = paras;
			numParas = n;
		}
	}

	// used for ActionListeners

	public void SetDoubleData(int k, int p, Double D) {
		(Keys.get(k)).Paras.get(p).Data = (Object) D;
	}

	public void SetIntegerData(int k, int p, Integer I) {
		(Keys.get(k)).Paras.get(p).Data = (Object) I;
	}

	public void SetStringData(int k, int p, String S) {
		(Keys.get(k)).Paras.get(p).Data = (Object) S;
	}


	public void readConfigKeyFile() {
		int l,k,p,n;
		String toks[],KeyName,ParaName,ParaTy;
		ParaType PType = null;
		BufferedReader br = null;
		KeyData Key;
		Object data = null;
		
		int NumParas;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(ConfigFile));
			
			l = 0;
			
			l++;
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
				if (toks.length == 4)
				{
					n = Integer.parseInt(toks[0]);
					if (n != k) break;
					
					NumParas = Integer.parseInt(toks[3]);
					KeyName = new String(toks[1]);
					
					ArrayList<ParaData> Paras = new ArrayList<ParaData>();
					
					for (p = 1; p <= NumParas; p++)
					{
						sCurrentLine = br.readLine();
						toks = sCurrentLine.split(" ");
						n = Integer.parseInt(toks[0]);
						if (n != p) break;
						
						ParaName = new String(toks[1]);
						ParaTy = toks[2]; 
						
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

						if (PType != null)
						{
							ParaData Pd = new ParaData(ParaName,PType,(Object)data);
							Paras.add(Pd);
						}
					}
					
					this.KeyNames.add(KeyName);
					
					Key = new KeyData(KeyName,Paras,NumParas);
					this.Keys.add(Key);
				}
				else 
				{
					String M = "Bad para line";
					JOptionPane.showMessageDialog(null,M);
					System.exit(1);
				}
			}
			
			while ((sCurrentLine = br.readLine()) != null) {
				l++;
				
			
				
				toks = sCurrentLine.split(" ");
				
				if (l == 1)
				{
					
				}
				else
				{
					
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
				output.write(Keys.get(k).Name);
				
				Ps = "  ";
				
				for (p = 0; p < Key.numParas; p++) {

					ParaData Para = Key.Paras.get(p);
					ParaType T = Para.Type;

					if (T == ParaType.Double) {
						Ps = Ps + String.format(" %s = %f ",Para.Name,(Double)Para.Data);	
					} else if (T == ParaType.Integer) {
						Ps =  Ps + String.format(" %s = %d ",Para.Name,(Integer)Para.Data);	
					} else if (T == ParaType.String) {
						Ps =  Ps + String.format(" %s = '%s' ",Para.Name,(String)Para.Data);	
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
		ParaData Pd = new ParaData("Name1",ParaType.Double,(Object)D1);
		Paras.add(Pd);

		Double D2 = 150.0;
		Pd = new ParaData("Name2",ParaType.Double,(Object)D2);
		Paras.add(Pd);

		Integer I1 = 15;
		Pd = new ParaData("Name3",ParaType.Integer,(Object)I1);
		Paras.add(Pd);

		Integer I2 = 115;
		Pd = new ParaData("Name4",ParaType.Integer,(Object)I2);
		Paras.add(Pd);

		String S1 = new String("A string");
		Pd = new ParaData("Name1",ParaType.String,(Object)S1);
		Paras.add(Pd);

		String S2 = new String("A second string");
		Pd = new ParaData("Name1",ParaType.String,(Object)S2);
		Paras.add(Pd);
		
		ArrayList<ParaData> Paras2 = new ArrayList<ParaData>();

		int NumParas2 = 6;

		Double D12 = 50.0;
		ParaData Pd2 = new ParaData("Name1",ParaType.Double,(Object)D12);
		Paras2.add(Pd2);

		Double D22 = 150.0;
		Pd2 = new ParaData("Name2",ParaType.Double,(Object)D22);
		Paras2.add(Pd2);

		Integer I12 = 15;
		Pd2 = new ParaData("Name3",ParaType.Integer,(Object)I12);
		Paras2.add(Pd2);

		Integer I22 = 115;
		Pd2 = new ParaData("Name4",ParaType.Integer,(Object)I22);
		Paras2.add(Pd2);

		String S12 = new String("A string");
		Pd2 = new ParaData("Name1",ParaType.String,(Object)S12);
		Paras2.add(Pd2);

		String S22 = new String("A second string");
		Pd2 = new ParaData("Name1",ParaType.String,(Object)S22);
		Paras2.add(Pd2);

		this.KeyNames.add("Growth");
		this.Keys.add(new KeyData("Growth",Paras,NumParas));
		this.KeyNames.add("GlView");
		this.Keys.add(new KeyData("GlView",Paras2,NumParas2));
		this.KeyNames.add("Main");
		this.Keys.add(new KeyData("Main",Paras,NumParas));
		this.KeyNames.add("Mob");
		this.Keys.add(new KeyData("Mob",Paras,NumParas));
		this.KeyNames.add("Substrate");
		this.Keys.add(new KeyData("Substrate",Paras2,NumParas2));

	}

}
