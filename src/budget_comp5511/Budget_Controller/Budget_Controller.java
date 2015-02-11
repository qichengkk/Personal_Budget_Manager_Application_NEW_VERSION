package budget_comp5511.Budget_Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import budget_comp5511.Budget_Model.Budget_Model;
import budget_comp5511.Budget_View.Budget_View;

/**
 * Main Controller of this application
 * @author Kaminderpal
 *
 */
public class Budget_Controller {
	
	private Budget_View theview;
	private Budget_Model themodel;
	private ActionListener login_Listener;
	//constructor
	public Budget_Controller(Budget_View theview, Budget_Model themodel){
		
		this.theview = theview;
		this.themodel = themodel;
		//this.theview.addloginListener(new Listener());
	}
	
	/**
	 * validate Username and Password
	 * @throws SQLException 
	 */
	public boolean login_Validate(String uname, String pwd) throws SQLException{
		if(themodel.validatePin(uname, pwd) == true){
			return true;
		}
		else
			return false;
		
	}
	/**
	 * create new user and check there is existing one.
	 * @return
	 * @throws SQLException
	 */
	
	public boolean signUp_User(String uname, String pwd, String con_pwd) throws SQLException{
		if(themodel.isUserExist(uname)== true)
		{
			JOptionPane.showMessageDialog(null, "ERROR: User name already exists!", 
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!pwd.equals(con_pwd))
		{
			JOptionPane.showMessageDialog(null, "ERROR: Passaword does not match!", 
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(themodel.createUser(uname, pwd) == true)
			return true;
		else
			return false;
	}//end of main else	
	/**
	 * Add purchase transaction in table
	 * @param uname
	 * @param mode
	 * @param providerName
	 * @param providerType
	 * @param address
	 * @param amount
	 * @param type
	 * @param status
	 * @param duration
	 * @param dateTime
	 * @param dueDate
	 * @return
	 * @throws Exception 
	 */
	public boolean add_transaction(String uname,String mode, String providerName,String providerType, String address, float amount, String type,
			String status, String duration, String dateTime, String dueDate ) throws SQLException
	{
		try {
			if(themodel.makeTransaction(uname,mode, providerName, providerType, address, amount, type, status, duration, dateTime, dueDate)== true)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}//end of method add account
	/**
	 * Add bill transaction in table
	 * @throws Exception 
	 */
	/*public boolean add_bill_transaction(String uname,String bmode, String providerName,
			String providerType, String address, float amount,String type,
			String status, String duration, String dateTime, String dueDate) throws Exception{
		if(themodel.makeTransaction(uname, bmode, providerName, providerType, address, amount, type, status, duration, dateTime, dueDate) == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/
	public ArrayList all_expense(String username) throws SQLException
	{
		ArrayList<ArrayList<String>> arraylist = new ArrayList();
		try {
			arraylist = themodel.getTransactionByUsername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arraylist;
	}
	
	public boolean delete_expense(int row, int expenseID) throws SQLException{
		
		if(row != -1)
		{
			int selection = JOptionPane.showOptionDialog(null, "Are you sure?", 
    				"Confirmation", JOptionPane.OK_CANCEL_OPTION, 
    				JOptionPane.QUESTION_MESSAGE, null, null, null);
    		
    		if(selection == JOptionPane.OK_OPTION)
    		{
    			themodel.deleteTransaction(expenseID);
    			return true;
    		}
    		else
    			return false;
		}
		else{
			JOptionPane.showMessageDialog(null, "Please select expense you want to delete","Warning",JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
  }
