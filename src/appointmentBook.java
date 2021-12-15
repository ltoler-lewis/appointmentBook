import javax.swing.JFrame;
import javax.swing.JLabel;

//Laura Toler

public class appointmentBook{

    public static double avr(double[][] array){
    	
    	double sum = 0;
    	
    	for(int x = 0; x < array.length; x++) {
    		double grade = array[x][0];
    		double weight = array[x][1];
    		
    		sum += grade*weight;
    	}
    	
    	
        return sum;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}