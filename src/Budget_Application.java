import java.awt.EventQueue;


public class Budget_Application {
	
	
	/**
	 * @param args
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				runApp();
			}
		});
	}// end of main method
	
	public static void runApp(){
		 LoginFrame loginFrame = new LoginFrame();
	}
	
}
