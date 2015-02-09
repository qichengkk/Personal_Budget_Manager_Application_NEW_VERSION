package budget_comp5511.Application;

import java.awt.EventQueue;

import budget_comp5511.Budget_Controller.Budget_Controller;
import budget_comp5511.Budget_Model.Budget_Model;
import budget_comp5511.Budget_View.Budget_View;


public class Application {

	private static Budget_Model themodel;
	private static Budget_Controller thecontroller;
	private static Budget_View theview;
	/**
	 * @param args
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				runApp();
				/*try {
					Login_View frame = new Login_View(null);
					frame.Login_View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		});
	}// end of main method
	
	public static void runApp(){
			themodel = new Budget_Model();
		//System.out.println("Hello kamy!!after model");
		 thecontroller = new Budget_Controller(theview, themodel);
		 theview = new Budget_View(thecontroller);
		
		
	}
	
}
