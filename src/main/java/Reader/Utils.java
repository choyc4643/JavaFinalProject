package Reader;

import java.util.ArrayList;
//import java.io.IOException;
import java.io.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;

public class Utils {

   
   public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		//String addLine = "";
		//int count = 1;
		
		try (CSVPrinter printer = new CSVPrinter(new FileWriter(targetFileName), CSVFormat.DEFAULT)) {
		    for(String line:lines) {
		    	//if(count%5 == 0) {
		    		//count = 0;
		    		String[] aline = line.split("\t");
		    		if(aline.length < 5) {
		    			String aline2 = "\t";
		    			aline2 += line;
		    			String alines2[] = aline2.split("\t");
		    			printer.printRecord(alines2);
		    		}
		    		else {
		    			printer.printRecord(aline);
		    		}
	
		    		//addLine = "";
		    	}
		    	
		    	//addLine += line + "//";
		    	//count++;
		    	//printer.printRecord(line);*/
		    	
		    	//addLine += line + "//";
		   // }
		    
		    //String[] aline = addLine.split("//");
		    
		    
		    System.out.println("done.");
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
	}
}