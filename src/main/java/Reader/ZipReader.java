package Reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

/**
 * This class about reading files in zipped directory.
 * @author Á¶¿µÂù
 *
 */
public class ZipReader {
	
	String input;	//-i
	String output;	// -o
	String output2; // -o2
	boolean help;	//-h

	
	/**
	 * It create ZipReader instance and implement run method and based on args.
	 * @param args
	 */
	public static void main(String[] args) {
		ZipReader zipReader = new ZipReader();
		zipReader.run(args);
		
	}
	
	/**
	 * This method for reading excel file based input options.
	 * @param args
	 */
	private void run(String[] args) {
		//String path = args[0];
		
		Options options = createOptions();


		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
		}
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		readFileInZip(input);
		
	}

	/**
	 * This method take path, get data in the excel file and write it to result file by writeAFile method.
	 * @param path
	 */
	public void readFileInZip(String path) {
		ZipFile zipFile;
		String resultPath = output;
		String resultPath2 = output2;
		ArrayList<String> temp = new ArrayList<String>();
		int count =0;
		try {
			zipFile = new ZipFile(path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	
		    	ZipArchiveEntry entry = entries.nextElement();
		        InputStream stream = zipFile.getInputStream(entry);
		    
		        ExcelReader myReader = new ExcelReader();
		        
		        for(String value:myReader.getData(stream)) {
		        	System.out.println(value);
		        	temp.add(value);
		        }
		        if(count == 0)
		        	Utils.writeAFile(temp, resultPath);
		        else if(count == 1)
		        	Utils.writeAFile(temp, resultPath2);
		        
		        count ++;
		    }
		    
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method help you to get the excel file path and result file path.
	 * @param options
	 * @param args
	 * @return
	 */
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			output2 = cmd.getOptionValue("o2");
			help = cmd.hasOption("h");


		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}      
	
	/**
	 * This method print the message about each options.
	 * @return
	 */
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an first output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		options.addOption(Option.builder("o2").longOpt("output2")
				.desc("Set an second output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}
	/**
	 * This method print out the help message when you need help about using options.
	 * @param options
	 */
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

}
