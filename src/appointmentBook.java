
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
    public static IntervalCategoryDataset dataset;
    public static JFreeChart chart;
    public static ChartPanel panel;

    private static IntervalCategoryDataset getCategoryDataset(AppointmentList appointmentList) {  
    	    	
    	TaskSeries series1 = new TaskSeries("Schedule"); 
    	
    	//System.out.println("DEBUG: Number of appointments in apptlist: " + appointmentList.appointmentList.size());
    	//System.out.println("DEBUG: Number of known employees in apptlist: " + appointmentList.knownEmployees.size());

    	//Create a list to hold Task Objects from JFreeChart library
    	ArrayList<Task> tasklist = new ArrayList<Task>();
    	
    	//Do this for each employee
    	for (int x = 0; x < appointmentList.knownEmployees.size(); x++) {
    		//Get their name
    		String name = appointmentList.knownEmployees.get(x).name;
    		//System.out.println("DEBUG: Looking for appointments for: " + name);
		
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
    				//System.out.println("Task for " + name1 + " from " + appointmentList.appointmentList.get(y).getStartDate() + " to " + appointmentList.appointmentList.get(y).getEndDate());

					//Create a taskusing the name and the start/end Dates from the Appointment object
					Task t =  new Task(name1, appointmentList.appointmentList.get(y).getStartDate(),appointmentList.appointmentList.get(y).getEndDate());
				
					//Add task to array
					tasklist.add(t);
    			}
    		}
		
    		if(tasklist.size()==0) {//If no appointments found, don't add him to the chart (Not ideal, but whatever)
    			//Do nothing
    			//System.out.println("DEBUG: No tasks for employee");
    		}
    		else if (tasklist.size() ==1) {//If one appointment found, make a single task 
    			//System.out.println("DEBUG: One task for employee");
    			series1.add(tasklist.get(0));
    			tasklist.clear();
    		}
    		else if (tasklist.size() > 1 ) {//If multiple appointments, make initial task the primary task and add the others as subtasks
    			//System.out.println("DEBUG: Several tasks for employee");
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

    
    public static void main(String[] args) throws IOException, ParseException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	//Create the AppointmentList object
        AppointmentList apptList = new AppointmentList();
        
        
        //Import the appointments from the excel file specified into the AppointmentList
        //Doesn't actually use specified file yet
        apptList.importExcelTable("tempPath");
        
        //Take that list of appointments and create an Interval Category Dataset from the JFreeCharts Library 
        dataset  = getCategoryDataset(apptList);
        //dataset.addChangeListener(new DatasetChangeListener);
        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//Create and set up the window.
                JFrame frame = new JFrame("Scheduler");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
                //Set up the content pane.
                //addComponentsToPane(frame.getContentPane(), dataset);
                
                Container pane = frame.getContentPane();
                if (RIGHT_TO_LEFT) {
                    pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                }
         
                pane.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                if (shouldFill) {
                	//natural height, maximum width
                	c.fill = GridBagConstraints.HORIZONTAL;
                }
         
                JButton buttonAdd = new JButton("Add Appointment");
                if (shouldWeightX) {
                	c.weightx = 0.5;
                }
                c.fill = GridBagConstraints.HORIZONTAL;
            	c.gridx = 0;
            	c.gridy = 0;
            	pane.add(buttonAdd, c);
            	
            	//Create input dialog
            	JTextField installLead = new JTextField(10);
            	JTextField venue = new JTextField(10);
            	JTextField empolyee = new JTextField(10);
            	JTextField nightJob = new JTextField(1);
            	JTextField startDate = new JTextField(10);
            	JTextField endDate = new JTextField(10);
            	JTextField job = new JTextField(10);
            	Object[] message = {
            		    "Venue:", venue,
            		    "Job:", job,
            		    "Installation Lead:", installLead,
            		    "Technician:", empolyee,
            		    "Night Job(Y/N):", nightJob,
            		    "Start Date (MM/dd/yyyy):", startDate,
            		    "End Date (MM/dd/yyyy):", endDate,
            		};
            	
            	

         
         
            	JButton buttonSave = new JButton("Save Changes");
            	c.fill = GridBagConstraints.HORIZONTAL;
            	c.weightx = 0.5;
            	c.gridx = 2;
            	c.gridy = 0;
            	pane.add(buttonSave, c);
            	
            	//Take dataset generated and make a Gantt Chart
        	    chart = ChartFactory.createGanttChart(  
        	            "Gantt Chart Example", // Chart title  
        	            "Employees", // X-Axis Label  
        	            "", // Y-Axis Label  
        	            dataset);  
        	  
        	    panel = new ChartPanel(chart);  
         
            	JButton button = new JButton("Long-Named Button 4");
            	c.fill = GridBagConstraints.HORIZONTAL;
            	c.ipady = 40;      //make this component tall
        	    c.weightx = 0.0;
        	    c.gridwidth = 3;
        	    c.gridx = 0;
        	    c.gridy = 1;
        	    
            	buttonAdd.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    	int option = JOptionPane.showConfirmDialog(pane, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
        		    	if (option == JOptionPane.OK_OPTION)
        		    	{
        		    	    String value1 = venue.getText();
        		    	    String value2 = job.getText();
        		    	    String value3 = installLead.getText();
        		    	    String value4 = empolyee.getText();
        		    	    String value5 = startDate.getText();
        		    	    String value6 = endDate.getText();
        		    	    String value7 = nightJob.getText();
        		    	    
        		    	    try {
								apptList.addAppointment(value1, value2, value3, value4, value7, value5, value6);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException i) {
								// TODO Auto-generated catch block
								i.printStackTrace();
							}
        		    	}
        		    	dataset = getCategoryDataset(apptList);
        		    	chart = ChartFactory.createGanttChart(  
                	            "Gantt Chart Example", // Chart title  
                	            "Employees", // X-Axis Label  
                	            "", // Y-Axis Label  
                	            dataset);  
        		    	ChartPanel panel2 = new ChartPanel(chart);  
        		    	SwingUtilities.invokeLater(() -> {
        	                frame.remove(panel);
        	                frame.add(panel2);
        	                frame.pack();
        	                frame.invalidate();
        	                frame.revalidate();
        	                frame.repaint();
        	            });

                    }
                });
        	                	
            	buttonSave.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    	try {
							apptList.saveVisualSchedule();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                });
        	    pane.add(panel, c);
                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
        
        
        
    }
}