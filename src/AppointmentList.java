import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.fortuna.ical4j.model.*;


public class AppointmentList{

    public static double avr(double[][] array){
    	
    	double sum = 0;
    	
    	for(int x = 0; x < array.length; x++) {
    		double grade = array[x][0];
    		double weight = array[x][1];
    		
    		sum += grade*weight;
    	}
    	
    	
        return sum;
    }
	
	public void importICS() {
		
	}
	
	public void exportICS() {
		
	}
	
	public static void importExcelTable(String path) throws IOException {
		//open example table
		//FIXME - change to read in Path
		System.out.println("In function");
        String excelFilePath = "exampleTable.xlsx";
        System.out.println("Made path");
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        System.out.println("Made input stream based on path");
        
        //create internal workbook object based on input stream
        Workbook workbook = new XSSFWorkbook(inputStream);
        System.out.println("made workbook from input stream");
        //Get the first sheet. The intended format will have the appointment list on sheet 1 (0) and the visual calendar on sheet 2 (1) 
        Sheet firstSheet = workbook.getSheetAt(0);
        
        //get row 1
        Iterator<Row> iterator = firstSheet.iterator();

        //while there are next rows
        while (iterator.hasNext()) {
        	//get next row
            Row nextRow = iterator.next();
            //make an iterator for the cells in that row 
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            
            //while there are cells
            while (cellIterator.hasNext()) {
            	
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                	case STRING:
                		System.out.print(cell.getStringCellValue());
                		break;
                	case BOOLEAN:
                		System.out.print(cell.getBooleanCellValue());
                		break;
                	case NUMERIC:
                		System.out.print(cell.getNumericCellValue());
                		break;
                	default:
                		break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }
         
        workbook.close();
        inputStream.close();
    }
	
}