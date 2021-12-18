import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Date;

 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.gantt.Task;

import net.fortuna.ical4j.model.*;


public class AppointmentList{

	public ArrayList<Appointment> appointmentList;
	public ArrayList<Employee> knownEmployees;
	public String path;
	
	
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
	
	public void importExcelTable(String pathIn) throws IOException, ParseException {
		//open example table
		//FIXME - change to read in Path
        String excelFilePath = "exampleTable.xlsx";
        path = excelFilePath;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        
        //create internal workbook object based on input stream
        Workbook workbook = new XSSFWorkbook(inputStream);
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
            //System.out.print("Venue:");
            //System.out.print(cell.getStringCellValue());
            String v = cell.getStringCellValue();
            
            
            //get nex column
            cell = cellIterator.next();
            //System.out.print("  Job:");
            //System.out.print(cell.getStringCellValue());
            String j = cell.getStringCellValue();
            
          //get nex column
            cell = cellIterator.next();
            //System.out.print("  Night:");
            //System.out.print(cell.getStringCellValue());
            String n = cell.getStringCellValue();
            
          //get nex column
            cell = cellIterator.next();
            //System.out.print("  StartDate:");
            //System.out.print(cell.getStringCellValue());
            Date sd = getDate(cell.getStringCellValue());
            
            
          //get nex column
            cell = cellIterator.next();
           // System.out.print("  EndDate:");
           // System.out.print(cell.getStringCellValue());
            Date ed = getDate(cell.getStringCellValue());
            
          //get nex column
            cell = cellIterator.next();
            //System.out.print("  Install Lead:");
            //System.out.print(cell.getStringCellValue());
            Employee il = addEmployee(cell.getStringCellValue());
            
          //get nex column
            cell = cellIterator.next();
            //System.out.print("  Employee:");
            //System.out.print(cell.getStringCellValue());
            Employee e = addEmployee(cell.getStringCellValue());
            
            Appointment appt = new Appointment(v,j,il,e,sd,ed);
            appointmentList.add(appt);
            //System.out.println("DEBUG Number of Appointments in appointmentlist in AppointmentList class: " + appointmentList.size());
            //System.out.println();
        }
        
        this.orderKnownEmployeeList();
        
        
        
        
        workbook.close();
        inputStream.close();
    }
	
	public void addAppointment(String venue, String job, String installLead, String technician, String nightJob, String startDate, String endDate) throws ParseException, IOException {
		System.out.println("DEBUG:ADDING APPT");
		
		Date sd = getDate(startDate);
		Date ed = getDate(endDate);
		Employee il = addEmployee(installLead);
		Employee e = addEmployee(technician);
		
		Appointment appt = new Appointment(venue,job,il,e,sd,ed);
		appointmentList.add(appt);
		
		
		FileInputStream inputStream = new FileInputStream(new File(path));
		
		//create internal workbook object based on input stream
		Workbook workbook = new XSSFWorkbook(inputStream);
		inputStream.close();
		
		FileOutputStream os = new FileOutputStream(new File(path));
		
		//Get the first sheet. The intended format will have the appointment list on sheet 1 (0) and the visual calendar on sheet 2 (1) 
		Sheet sheet = workbook.getSheetAt(0);
	    //System.out.println("DEBUG: Got sheet");
	    
		/**/
		int lastRowNum = sheet.getLastRowNum();
		//System.out.println("DEBUG: Last row num is " + lastRowNum);
		
		sheet.createRow(lastRowNum+1);
		//System.out.println("DEBUG: Made row");
		
		Row lastRow =sheet.getRow(lastRowNum+1);
		//System.out.println("DEBUG: Got row");
		
		Iterator<Cell> cellIterator = lastRow.cellIterator();
		//System.out.println("DEBUG: Made iterator");
		
		//Make cell iterator and start it on column 1
		Cell cell = lastRow.createCell(0);
		//System.out.print("Venue:");
		//System.out.print(venue);
		cell.setCellValue(venue);
        
         
		//get nex column
		cell = lastRow.createCell(1);
		//System.out.print("  Job:");
		//System.out.print(job);
		cell.setCellValue(job);
		
       //get nex column
		cell = lastRow.createCell(2);
		//System.out.print("  Night:");
		//System.out.print(nightJob);
		cell.setCellValue(nightJob);
		
		//get nex column
		cell = lastRow.createCell(3);
		//System.out.print("  StartDate:");
		//System.out.print(startDate);
		cell.setCellValue(startDate);
         
		//get nex column
		cell = lastRow.createCell(4);
		//System.out.print("  EndDate:");
		//System.out.print(endDate);
		cell.setCellValue(endDate);
         
		//get nex column
		cell = lastRow.createCell(5);
		//System.out.print("  Install Lead:");
		//System.out.print(installLead);
		cell.setCellValue(installLead);
         
		//get nex column
		cell = lastRow.createCell(6);
		//System.out.print("  Employee:");
		//System.out.print(technician);
		cell.setCellValue(technician);
        
		workbook.write(os);
		workbook.close();
		
         /**/
	}

	@SuppressWarnings("deprecation")
	public void saveVisualSchedule() throws IOException {
		
		FileInputStream inputStream = new FileInputStream(new File(path));
		
		//create internal workbook object based on input stream
		Workbook workbook = new XSSFWorkbook(inputStream);
		inputStream.close();
		
		FileOutputStream os = new FileOutputStream(new File(path));
		
		Sheet secondSheet;
        if (workbook.getNumberOfSheets() <= 1) {
        	secondSheet = workbook.createSheet("visual schedule");
        	System.out.println("DEBUG: Made second sheet");
        }
        else{
        	System.out.println("DEBUG: Got second sheet");
        	//Remove old sheet
        	workbook.removeSheetAt(1);
        	secondSheet = workbook.createSheet("visual schedule");
        }
        
        //FIXME Output graph to excel sheet
        
        //Draw Calendar
        Row row = secondSheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("November");
        
        for (int x = 1; x < 31; x++) {
        	System.out.println(x);
        	cell = row.createCell(x);
        	cell.setCellValue(x);
        }
        
        CellStyle style1 = workbook.createCellStyle(); 
        style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());  
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        
        CellStyle style2 = workbook.createCellStyle(); 
        style2.setFillForegroundColor(IndexedColors.RED.getIndex());  
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  

        
        
      //Do this for each employee
    	for (int x = 0; x < knownEmployees.size(); x++) {
    		//Get their name
    		String name = knownEmployees.get(x).name;
    		
    		//Add their row in calendar
    		row = secondSheet.createRow(x+1);
    		cell = row.createCell(0);
        	cell.setCellValue(name);
        	//Make all the cells, to make my life easier
        	for (int y = 1; y < 31; y++) {
            	row.createCell(y);
            }
    		
    		//Get all appointments in the list that this employee is part of
    		//Check each appointment
    		int g = 0;
    		for(int y = 0; y < appointmentList.size(); y++) {
    			//Use function in Appointment class to verify if technician is going
    			
    			if (appointmentList.get(y).isEmployeeGoing(name)) {
    				int s = appointmentList.get(y).getStartDate().getDate();
    				int e = appointmentList.get(y).getEndDate().getDate();
    				for (int v = s; v <= e; v++) {
    					System.out.println("DEBUG: Get cell at row " + x + " and column " + v);
    					cell = row.getCell(v);
    					if (cell.getCellStyle() == style1) {
    						System.out.println("DEBUG:Style matches style 1 (ie is already set)");
    						cell.setCellStyle(style2);
    					}
    					else {
    						cell.setCellStyle(style1);
    						System.out.println("DEBUG:Style does not match style 1");
    					}
    				}
    			}
    		}
    	}
        
		workbook.write(os);
		workbook.close();
	}

}