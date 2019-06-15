package Reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
    
    
    public ArrayList<String> getData(InputStream is) {
    ArrayList<String> values = new ArrayList<String>();
    
    try (InputStream inp = is) {
 
    Workbook wb = WorkbookFactory.create(inp);
    Sheet sheet = wb.getSheetAt(0);

    Iterator<Row> iterator = sheet.iterator();
    
    while(iterator.hasNext()) {
    	String value = "";
    	String resultValue = "";
    	
    	Row currentRow = iterator.next();
    	
    	Iterator<Cell> cellIterator = currentRow.iterator();
    	
    	while(cellIterator.hasNext()) {
    		
    		Cell currentCell = cellIterator.next();
    		
    		switch (currentCell.getCellType()){
            case FORMULA:
                value = currentCell.getCellFormula();
                break;
            case NUMERIC:
                value = currentCell.getNumericCellValue()+"";
                break;
            case STRING:
                value = currentCell.getStringCellValue()+"";
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = currentCell.getErrorCellValue()+"";
                break;
            default:
                value = new String();
                break;
            }
    		
    		/*if(!currentCell.getCellType().equals()) {
    			
    		}*/
    		
    		resultValue += value + "\t";
    		
    		//cellLength++;
    		
    		//System.out.print(value + " ");
    	}
    	
    	values.add(resultValue);
    	//System.out.println();
    }
    
    
    
    } catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
    
    return values;
    }

}