import java.sql.SQLException;
import java.util.ArrayList;

import Database.Expense_BLL;


public class ExpenseHandler
{
	private Expense_BLL expenseDB;
	
	/**
	 * ExpenseHandler constructor
	 */
	public ExpenseHandler()
	{
		try {
			expenseDB = new Expense_BLL();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get back all expenses from database
	 * @throws SQLException 
	 */
	public ArrayList viewExpenses(String userName) throws SQLException
	{
		ArrayList<ArrayList<String>> arraylist = new ArrayList();
		try {
			arraylist = expenseDB.viewExpenseByUsername(userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arraylist;
	}
	
	protected boolean addExpense(String username, String accountType, String providerName, 
			String providerType, String address, float amount, String type, 
			String status, String duration, String dateTime, String dueDate) throws Exception
	{
		return expenseDB.addExpense(username, accountType, providerName, providerType, 
				address, amount, type, status, duration, dateTime, dueDate);
	}
	
	protected void updateStatus(int expenseID, String newstatus) throws Exception
	{
		expenseDB.updateExpense(expenseID, "status", newstatus);
	}
	
	protected void deleteExpense(int expenseID) throws Exception
	{
		expenseDB.deleteExpense(expenseID);
	}
	
}