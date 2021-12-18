import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Date;

 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.fortuna.ical4j.model.*;


public class AppointmentList{

	public ArrayList<Appointment> appointmentList;
	public ArrayList<Employee> knownEmployees;
	
	public AppointmentList() {
		appointmentList = new ArrayList<Appointment>();
		
		knownEmployees = new ArrayList<Employee>();
	}
	
	public Employee addEmployee(String name) {
		for(int x = 0; x < knownEmployees.size(); x++) {
			if(knownEmployees.get(x).name.equals(name)) {
				return knownEmployees.get(x);
			}
		}
		Employee e = new Employee(name);
		knownEmployees.add(e);
		return e;
	}
	
	public static Date getDate(String inDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date date = formatter.parse(inDate);
		return date;
	}
	
	public void orderKnownEmployeeList() {
	 	int n = knownEmployees.size();  
	 	for(int i=0; i < n; i++){  
	 		for(int j=1; j < (n-i); j++){  
	 			int x = knownEmployees.get(j-1).name.compareTo(knownEmployees.get(j).name);
	 			
	 			if(x > 0){  
	 				//swap elements  
	 				Collections.swap(knownEmployees, j-1, j);
                }  
	                          
             }  
	 	}  
	}
	
	public void importICS() {
		
	}
	
	public void exportICS() {
		
	}
	
	public void importExcelTable(String path) throws IOException, ParseException {
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
        //get first row, which should be table headers
        Row nextRow = iterator.next();
        //while there are next rows
        while (iterator.hasNext()) {
        	//get next row
            nextRow = iterator.next();
            //make an iterator for the cells in that row 
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            
            //Make cell iterator and start it on column 1
            Cell cell = cellIterator.next();
            System.out.print("Venue:");
            System.out.print(cell.getStringCellValue());
            String v = cell.getStringCellValue();
            
            
            //get nex column
            cell = cellIterator.next();
            System.out.print("  Job:");
            System.out.print(cell.getStringCellValue());
            String j = cell.getStringCellValue();
            
          //get nex column
            cell = cellIterator.next();
            System.out.print("  Night:");
            System.out.print(cell.getStringCellValue());
            String n = cell.getStringCellValue();
            
          //get nex column
            cell = cellIterator.next();
            System.out.print("  StartDate:");
            System.out.print(cell.getStringCellValue());
            Date sd = getDate(cell.getStringCellValue());
            
            
          //get nex column
            cell = cellIterator.next();
            System.out.print("  EndDate:");
            System.out.print(cell.getStringCellValue());
            Date ed = getDate(cell.getStringCellValue());
            
          //get nex column
            cell = cellIterator.next();
            System.out.print("  Install Lead:");
            System.out.print(cell.getStringCellValue());
            Employee il = addEmployee(cell.getStringCellValue());
            
          //get nex column
            cell = cellIterator.next();
            System.out.print("  Employee:");
            System.out.print(cell.getStringCellValue());
            Employee e = addEmployee(cell.getStringCellValue());
            
            Appointment appt = new Appointment(v,j,il,e,sd,ed);
            appointmentList.add(appt);
            //System.out.println("DEBUG Number of Appointments in appointmentlist in AppointmentList class: " + appointmentList.size());
            //System.out.println();
        }
        
        this.orderKnownEmployeeList();
        
        if (workbook.getNumberOfSheets() <= 1) {
        	Sheet secondSheet = workbook.createSheet("visual schedule");
        	System.out.println("DEBUG: Get second sheet");
        }
        else{
        	Sheet secondSheet = workbook.getSheetAt(1);
        	//Clear anything in the sheet
        	Iterator<Row> rowIte =  secondSheet.iterator();
        	while(rowIte.hasNext()){
        		rowIte.next();              
        	    rowIte.remove();
        	}
        }
        
        //FIXME Output graph to excel sheet
        
        
        
        
        workbook.close();
        inputStream.close();
    }
	
	
}