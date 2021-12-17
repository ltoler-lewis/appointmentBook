import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import net.fortuna.ical4j.model.*;


public class Appointment {
	
	private int UUID;
	private String venue;
	private String job;
	private String jobDescription;
	
	private Employee installLead;
	private ArrayList<Employee> employees;
	
	private Date startDate;
	private Date endDate;
	
	public Appointment(String v, String j, String jd, Employee i, ArrayList<Employee> eList, Date start, Date end) {
		employees = new ArrayList<Employee>();
		venue = v;
		job = j;
		jobDescription = jd;
		installLead = i;
		employees = eList;
		startDate = start;
		endDate = end;
		
		int ra = ThreadLocalRandom.current().nextInt(100000, 1000000);
		UUID = ra;
		
		
	}
	
	public Appointment(String v, String j, Employee i, ArrayList<Employee> eList, Date start, Date end) {
		employees = new ArrayList<Employee>();
		venue = v;
		job = j;
		jobDescription = "None available";
		installLead = i;
		employees = eList;
		startDate = start;
		endDate = end;
	
		int ra = ThreadLocalRandom.current().nextInt(100000, 1000000);
		UUID = ra;
	}
	
	public Appointment(String v, String j, Employee i, Employee e, Date start, Date end) {
		employees = new ArrayList<Employee>();
		venue = v;
		job = j;
		jobDescription = "None available";
		installLead = i;
		//employees = new ArrayList<Employee>;
		employees.add(e);
		startDate = start;
		endDate = end;
		
		System.out.println("DEBUG: Make appointment");
		int ra = ThreadLocalRandom.current().nextInt(100000, 1000000);
		UUID = ra;
	}
	
	//getters
	public String getVenue() {
		return venue;
	}
	
	public String getJob() {
		return job;
	}
	
	public String getJobDescription() {
		return jobDescription;
	}
	
	public Employee getInstallLead() {
		return installLead;
	}
	
	public ArrayList<Employee> getEmployees(){
		ArrayList<Employee> list = employees;
		list.add(installLead);
		return list;
	}
	
	public boolean isEmployeeGoing(Employee e) {
		if (e.name.equals(installLead.name)) {
			return true;
		}
		else {
			for (int x = 0; x < employees.size(); x++) {
				if (e.name.equals(employees.get(x).name)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean isEmployeeGoing(String n) {
		Employee e = new Employee(n);
		if ( e.name.equals(installLead.name)) {
			return true;
		}
		else {
			for (int x = 0; x < employees.size(); x++) {
				if (e.name.equals(employees.get(x).name)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	//setters
	public void setVenue(String v) {
		venue = v;
	}
	
	public void setJob(String j) {
		job = j;
	}
	
	public void setJobDescription(String jd) {
		jobDescription = jd;
	}
	
	public void setInstallLead(Employee i) {
		installLead = i;
	}
	
	public void addEmployee(Employee e) {
		//If employee is not already going
		if (!isEmployeeGoing(e)) {
			employees.add(e);
		}
	}
	
	public void removeEmployee (Employee e) { //Only works if employee is not the install lead
		if (employees.contains(e)) {
			employees.remove(e);
		}
	}
	
	public void exportICS() {
		
	}
	
	public static void exportICS(Appointment a) {
		
	}
	
}
