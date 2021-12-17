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
    public AppointmentList appointmentList;
    
    
private IntervalCategoryDataset getCategoryDataset() {  
   
	TaskSeries series1 = new TaskSeries("Schedule"); 
	
	ArrayList<Task> tasklist = new ArrayList<Task>();
	for (int x = 0; x < appointmentList.knownEmployees.size(); x++) {
		String name = appointmentList.knownEmployees.get(x).name;
		
		//Get all appointments in the list that this employee is part of
		for(int y = 0; y < appointmentList.appointmentList.size(); y++) {
			if (appointmentList.appointmentList.get(y).isEmployeeGoing(name)) {
				String name1 = name;
				if (y>0) {
					name1 = name1 + " " + String.valueOf(y);
				}
				Task t =  new Task(name1, Date.from(LocalDate.of(2017, 01,31).atStartOfDay().toInstant(ZoneOffset.UTC)),Date.from(LocalDate.of(2017, 2, 4).atStartOfDay().toInstant(ZoneOffset.UTC)));
				tasklist.add(t);
			}
		}
		
		for(int z = 1; z < tasklist.size(); z++) {
			
		}
		
	}
	
 	final Task t4 = new Task(
          "Design Phase", 
          Date.from(LocalDate.of(2017, 01,31).atStartOfDay().toInstant(ZoneOffset.UTC)),Date.from(LocalDate.of(2017, 2, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
      );
      final Task st41 = new Task(
           "Design 1", 
           Date.from(LocalDate.of(2017, 4,28).atStartOfDay().toInstant(ZoneOffset.UTC)),Date.from(LocalDate.of(2017, 5, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
      );
      st41.setPercentComplete(1.0);
      final Task st42 = new Task(
          "Design 2", 
          Date.from(LocalDate.of(2017, 05,20).atStartOfDay().toInstant(ZoneOffset.UTC)),Date.from(LocalDate.of(2017, 6, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
      );
      st42.setPercentComplete(1.0);
      final Task st43 = new Task(
          "Design 3", 
          Date.from(LocalDate.of(2017, 07,20).atStartOfDay().toInstant(ZoneOffset.UTC)),Date.from(LocalDate.of(2017, 8, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
      );
      st43.setPercentComplete(1.0);
      t4.addSubtask(st41);
      t4.addSubtask(st42);
      t4.addSubtask(st43);
      series1.add(t4);
  
      TaskSeriesCollection dataset = new TaskSeriesCollection();  
      dataset.add(series1);
      return dataset;  
    }  
   
    
    public static void addComponentsToPane(Container pane) {
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
 
    button = new JButton("Long-Named Button 4");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 40;      //make this component tall
    c.weightx = 0.0;
    c.gridwidth = 3;
    c.gridx = 0;
    c.gridy = 1;
    pane.add(button, c);
 
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
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) throws IOException, ParseException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        //AppointmentList a = new AppointmentList("temp");
        
        //a.importExcelTable("tempPath");
    }
}