import Database.User_BLL;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class User
{
	private String userName;
	//private String userPW;	//not good to have
	
	
	private User_BLL userDB;			//user database
	private ExpenseHandler expenseHandler;

	
	/**
	 * User constructor
	 */
	public User()
	{
		try {
			userDB = new User_BLL();
			expenseHandler = new ExpenseHandler();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Check user input valid credentials.
	 * @param 
	 * @throws Exception
	 */
	protected boolean loginAction(String uname, String pwd) throws Exception{
		
		if(uname.equals("") || pwd.equals(""))
		{
			return errormessages("ERROR: Login fields are empty");
		}
		else if(userDB.validatePin(uname, pwd) == true)
		{
			userName = uname;
			return true;
		}
		else
		{
			return errormessages("ERROR: Invalid Username and Password");
		}
	}
	/**
	 * Create User 
	 * @param 
	 * @throws Exception 
	 */
	protected boolean signupAction(String uname, String pwd, String con_pwd) throws Exception{
		
		if(uname.equals("") || pwd.equals("") || con_pwd.equals(""))
			return errormessages("ERROR: Signup fields are empty");
		if(userDB.isUserExist(uname)== true)
		{
			return errormessages("ERROR: User name already exists!");
		}
		else if(!pwd.equals(con_pwd))
		{
			return errormessages("ERROR: Passaword does not match!");
		}
		else if(userDB.createUser(uname, pwd) != true)
			return errormessages("ERROR: Can't create User");
		else 
		{
			return true;
		}
	}
	
	/**
	 * Get back all expenses from database
	 * @throws SQLException 
	 */
	public ArrayList viewExpenses() throws SQLException
	{
		return expenseHandler.viewExpenses(userName);
	}
	
	
	protected boolean addPurchaseAction(String type, String pname, String paddress, String pdate, String ptime, 
			String pamount, String pmode, String pstatus, String pdue_date) throws HeadlessException, SQLException
	{
		//System.out.println("pdate" + pdate);
		//System.out.println("ptime" + ptime);
		if(pmode == "Cash" || pmode == "Debit")
		{
			pstatus = "Paid";
			pdue_date = "NA";
		}
		if(pstatus == "Paid")
			pdue_date = "NA";
		if(pname.equals("") ||paddress.equals("") || pdate.equals("") ||pamount.equals("") ||pmode.equals("") 
				||ptime.equals("")|| pstatus.equals("") || pdue_date.equals(""))
		{
			return errormessages("ERROR: Purchase Fields are empty!");
		}
		else if(!isNumeric(pamount))
			return errormessages("ERROR: Amount should be numeric!");
		else
		{
			try 
			{
				//System.out.println(pdate);
				float pamountFloat = Float.valueOf(pamount);
				return expenseHandler.addExpense(userName, pmode, pname, type, paddress, pamountFloat, 
						type, pstatus, "NA", pdate + " " + ptime, pdue_date);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	protected boolean addBillAction(String type, String bname, String bamount, String bmode, String binterval, 
			String blocation, String bstatus, String bdue_date) throws HeadlessException, SQLException
	{
		if(bmode == "Cash" || bmode == "Debit")
		{
			bstatus = "Paid";
			bdue_date = "NA";
		}
		if(bstatus == "Paid")
			bdue_date = "NA";
		if(bname.equals("") || bamount.equals("") || bmode.equals("") || bstatus.equals("")|| bdue_date.equals("") ||
				binterval.equals("") ||blocation.equals(""))
			return errormessages("ERROR: Bill Fields are empty!");
		else if(!isNumeric(bamount)){
			errormessages("ERROR: Amount should be numeric!");
		}
		else 
		{
			
			try
			{
				Float bamountFloat = Float.valueOf(bamount);
				return expenseHandler.addExpense(userName, bmode, bname, type, blocation, bamountFloat, 
						type, bstatus, binterval, "NA", bdue_date);
	
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return false;
			
	}
	
	protected boolean updateStatus(int expenseID, String mode, String oldstatus, String newstatus) throws HeadlessException, SQLException 
	{
		int selection = JOptionPane.showOptionDialog(null, "Update \"" + oldstatus 
				+ "\" to \"" + newstatus + "\" ? Are you sure?", 
				"Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if(selection == JOptionPane.OK_OPTION)
		{
			if((mode.equalsIgnoreCase("Cash") || mode.equalsIgnoreCase("Debit")) && newstatus.equalsIgnoreCase("Unpaid"))
				return errormessages("ERROR: \"UNPAID\" is only available for expenses paid by Credit Card");
			else try {
				expenseHandler.updateStatus(expenseID, newstatus);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return true;
		}
		return false;
	}
	
	protected boolean deleteExpense(int expenseID) throws Exception 
	{	
		try 
		{
			int selection = JOptionPane.showOptionDialog(null, "Are you sure?", 
    				"Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    		
    		if(selection == JOptionPane.OK_OPTION)
    		{
    			expenseHandler.deleteExpense(expenseID);
    			return true;
    		}
		}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
		return false;
		
	}
	
	
	
	/**
	 * Error Message Dialog
	 * @param errormessage
	 */
	private boolean errormessages(String errormessage)
	{
		JOptionPane.showMessageDialog(null, 
				errormessage, "ERROR", JOptionPane.ERROR_MESSAGE);
		return false;
	}
	/**
	 * Check if a string is numeric
	 * @param str
	 */
	public boolean isNumeric(String str)
	{
		try
		{
			float d = Float.valueOf(str);
		}
		catch(NumberFormatException e1)
		{
			return false;
		}
		return true;
	}//end of numeric method
	
}