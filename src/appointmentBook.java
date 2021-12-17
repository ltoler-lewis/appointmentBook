import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import java.awt.*;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.data.category.IntervalCategoryDataset;  
import org.jfree.data.gantt.Task;  
import org.jfree.data.gantt.TaskSeries;  
import org.jfree.data.gantt.TaskSeriesCollection;  


//Laura Toler

public class appointmentBook{

	 
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    public AppointmentList apptList;
    
    
    private static IntervalCategoryDataset getCategoryDataset(AppointmentList appointmentList) {  
    	    	
    	TaskSeries series1 = new TaskSeries("Schedule"); 
    	
    	//System.out.println("DEBUG: Number of appointments in apptlist: " + appointmentList.appointmentList.size());
    	//System.out.println("DEBUG: Number of known employees in apptlist: " + appointmentList.knownEmployees.size());

    	
    	ArrayList<Task> tasklist = new ArrayList<Task>();
    	for (int x = 0; x < appointmentList.knownEmployees.size(); x++) {
    		String name = appointmentList.knownEmployees.get(x).name;
    		System.out.println("Looking for appointments for: " + name);
		
    		//Get all appointments in the list that this employee is part of
    		//Check each appointment
    		int g = 0;
    		for(int y = 0; y < appointmentList.appointmentList.size(); y++) {
    			//Use function in Appointment class to verify if technician is going
    			
    			if (appointmentList.appointmentList.get(y).isEmployeeGoing(name)) {
    				//We're going to increment the name jut in case the Gantt chart class requires it, I don't feel like testing and debugging that
    				//If we are on the first round, name is just the name, after that it is name + g
    				String name1 = name;
    				//Using this integer to count, not y
    				if (g > 0) {
    					name1 = name1 + " " + g;
    				}
    				g++;
    				//Debug printers
    				System.out.println("Task for " + name1 + " from " + appointmentList.appointmentList.get(y).getStartDate() + " to " + appointmentList.appointmentList.get(y).getEndDate());

					//Create a taskusing the name and the start/end Dates from the Appointment object
					Task t =  new Task(name1, appointmentList.appointmentList.get(y).getStartDate(),appointmentList.appointmentList.get(y).getEndDate());
				
					//Add task to array
					tasklist.add(t);
    			}
    		}
		
    		if(tasklist.size()==0) {//If no appointments found, don't add him to the chart (Not ideal, but whatever)
    			//Do nothing
    			System.out.println("DEBUG: No tasks for employee");
    		}
    		else if (tasklist.size() ==1) {//If one appointment found, make a single task 
    			System.out.println("DEBUG: One task for employee");
    			series1.add(tasklist.get(0));
    			tasklist.clear();
    		}
    		else if (tasklist.size() > 1 ) {//If multiple appointments, make initial task the primary task and add the others as subtasks
    			System.out.println("DEBUG: Several tasks for employee");
    			//For loop - start at position 1 (skipping position 0, which will be the primary task) and add each task to the primary task as a subtask
    			for(int v = 1; v < tasklist.size(); v++) {
    				//Set completion percentage to 100. Don't know what'll happen if I don't, but lets nt find out. I'm tired
    				//tasklist.get(v).setPercentComplete(1.0);
    				//Add subtask to task at index 0
    				tasklist.get(0).addSubtask(tasklist.get(v));
    			}
    			//Once all tasks have been added as subtasks
    			series1.add(tasklist.get(0));
    			tasklist.clear();
    		}
    	}
	
	//Create a TaskSeriesCollection that will be returned
	TaskSeriesCollection dataset = new TaskSeriesCollection();  
    dataset.add(series1);
    return dataset;  
    }  
   
    
    public static void addComponentsToPane(Container pane, IntervalCategoryDataset dataset) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
 
        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
        	//natural height, maximum width
        	c.fill = GridBagConstraints.HORIZONTAL;
        }
 
        button = new JButton("Button 1");
        if (shouldWeightX) {
        	c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 0;
    	c.gridy = 0;
    	pane.add(button, c);
 
    	button = new JButton("Button 2");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridx = 1;
    	c.gridy = 0;
    	pane.add(button, c);
 
    	button = new JButton("Button 3");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridx = 2;
    	c.gridy = 0;
    	pane.add(button, c);
    	
	    JFreeChart chart = ChartFactory.createGanttChart(  
	            "Gantt Chart Example", // Chart title  
	            "Software Development Phases", // X-Axis Label  
	            "Timeline", // Y-Axis Label  
	            dataset);  
	  
	    ChartPanel panel = new ChartPanel(chart);  
 
    	button = new JButton("Long-Named Button 4");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.ipady = 40;      //make this component tall
	    c.weightx = 0.0;
	    c.gridwidth = 3;
	    c.gridx = 0;
	    c.gridy = 1;
	    pane.add(panel, c);
 
	    button = new JButton("5");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 1;       //aligned with button 2
	    c.gridwidth = 2;   //2 columns wide
	    c.gridy = 2;       //third row
	    pane.add(button, c);
	    

      	
	    }
    
    	
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(IntervalCategoryDataset dataset) {
    	
    	//Create dataset
    	//IntervalCategoryDataset dataset = getCategoryDataset(apptList);  
    	
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane(), dataset);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
   
    
    
    public static void main(String[] args) throws IOException, ParseException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        AppointmentList apptList = new AppointmentList("temp");
        apptList.importExcelTable("tempPath");
        IntervalCategoryDataset icd = getCategoryDataset(apptList);
        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(icd); 
            }
        });
        
        
        
    }
}