package tclParsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class tclList {

	ArrayList<Object> topList = new ArrayList<Object>();
	
	private void TclListReadError(String Msg)
	{
		Msg = String.format("Bad Keyword in file: %s\n",Msg);
		System.exit(1);
	};
	
	public void ReadTclListFromFile(String File)
	{
		BufferedReader br = null;
		File file = null;
		
		try {
			String sCurrentLine;
			
			br = new BufferedReader(new FileReader(file));
			
			while ((sCurrentLine = br.readLine()) != null)
			{
				sCurrentLine = sCurrentLine.replace("}"," } ");
				sCurrentLine = sCurrentLine.replace("{"," { ");
				
				
			}
			
		}
		catch ( IOException e ) 
		{
			TclListReadError("");
		}
		
	}
	
}
