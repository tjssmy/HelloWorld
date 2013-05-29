package keywordPanel;

import java.util.ArrayList;

public class keywordData {
	String FileName;
	
	public keywordData(String fname){
		this.FileName = fname;
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
	
	int numKeys;
	ArrayList<KeyData> Keys = new ArrayList<KeyData>();
	ArrayList<String>  KeyNames = new ArrayList<String>();
	
	public static void readKeyFile(){
//		keywordData Keys = new keywordData(FileName);
//		return Keys;
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
		
		this.KeyNames.add("Growth");
		this.Keys.add(new KeyData("Growth",Paras,NumParas));
		this.KeyNames.add("GlView");
		this.Keys.add(new KeyData("GlView",Paras,NumParas));
		this.KeyNames.add("Main");
		this.Keys.add(new KeyData("Main",Paras,NumParas));
		this.KeyNames.add("Mob");
		this.Keys.add(new KeyData("Mob",Paras,NumParas));
		this.KeyNames.add("Substrate");
		this.Keys.add(new KeyData("Substrate",Paras,NumParas));
		
	}
	
}