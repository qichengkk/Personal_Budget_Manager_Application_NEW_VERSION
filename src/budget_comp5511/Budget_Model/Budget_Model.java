package budget_comp5511.Budget_Model;

import java.sql.*;
import java.util.ArrayList;

public class Budget_Model {
	private static Connection con;
	// private MessageHashing ms;
	private String createUser = "CREATE TABLE IF NOT EXISTS User("
			+ " id INTEGER  PRIMARY KEY,"
			+ " username VARCHAR(64) NOT NULL UNIQUE,"
			+ "password VARCHAR(64) NOT NULL);";
	private String createAccount = "CREATE TABLE IF NOT EXISTS Account("
			+ " id INTEGER  PRIMARY KEY,"
			+ " bank_account_id INTEGER NOT NULL,"
			+ " balance FLOAT DEFAULT 0.0," + " type VARCHAR(10) NOT NULL,"
			+ " bank VARCHAR(64) NOT NULL,"
			+ " address VARCHAR(128) NOT NULL);";
	private String createOwns = "CREATE TABLE IF NOT EXISTS Owns("
			+ " user_id INT NOT NULL," + " account_id INT NOT NULL,"
			+ " PRIMARY KEY(user_id, account_id),"
			+ " FOREIGN KEY(user_id) REFERENCES User(id)"
			+ " ON UPDATE CASCADE " + " ON DELETE CASCADE,"
			+ " FOREIGN KEY(account_id) REFERENCES Account(id)"
			+ " ON DELETE CASCADE" + " ON UPDATE CASCADE); ";
	private String createProvider = "CREATE TABLE IF NOT EXISTS Provider("
			+ " id INTEGER  PRIMARY KEY," + " name VARCHAR(50) NOT NULL,"
			+ " type VARCHAR(10) NOT NULL," + " address VARCHAR(50) NOT NULL,"
			+ " UNIQUE(name,type,address));";
	private String createTransactions = "CREATE TABLE IF NOT EXISTS Transactions("
			+ " id INTEGER PRIMARY KEY,"
			+ " account_id INT NOT NULL,"
			+ " provider_id INT NOT NULL,"
			+ " type VARCHAR(10) NOT NULL,"
			+ " status VARCHAR(10) NOT NULL,"
			+ " amount float DEFAULT 0.0,"
			+ " time VARCHAR(30) NOT NULL,"
			+ " due_date VARCHAR(20), "
			+ " duration VARCHAR(10),"
			+ " FOREIGN KEY(account_id) REFERENCES Account(id) "
			+ " ON DELETE CASCADE "
			+ " ON UPDATE CASCADE,"
			+ " FOREIGN KEY (provider_id) REFERENCES Provider(id) "
			+ " ON DELETE CASCADE " + " ON UPDATE CASCADE);";

	/**
	 * By using bank account id and type of that bank account Check whether
	 * specific account exist
	 * 
	 * @param bankAccountId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private boolean isBankAccountExist(int bankAccountId, String type)
			throws Exception {

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT * FROM Account WHERE bank_account_id = "
				+ bankAccountId + " AND type = \'" + type.toUpperCase().trim()
				+ "\' ;";
		int result = select.executeUpdate(query);
		con.close();
		if (result > 0)
			return true;
		return false;
	}

	/**
	 * For displaying provider name purpose
	 * 
	 * @param providerId
	 * @return
	 * @throws Exception
	 */
	private String getProviderNameByProviderId(int providerId) throws Exception {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT name FROM Provider WHERE id=" + providerId + ";";
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			String name = result.getString("name");
			result.close();
			return name;
		}
		throw new Exception("Reading error");
	}

	/**
	 * For displaying provider address purpose
	 * 
	 * @param providerId
	 * @return
	 * @throws Exception
	 */
	private String getProviderAddressByProviderId(int providerId)
			throws Exception {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT address FROM Provider WHERE id=" + providerId
				+ ";";
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			String address = result.getString("address");
			result.close();
			return address;

		}
		throw new Exception("Reading error");
	}

	/**
	 * ONLY FOR INCREMENT 1 TEMPORARY Get account id (NOT bank_acount_id) from
	 * the account table By username and type Since in first temp increment,
	 * user only has 3 default account "CASH, CREDIT DEBIT" and CANNOT create
	 * any account THIS FUNCTION WORKS
	 * 
	 * @param username
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private int getAccountIdByUsernameAndType(String username, String type)
			throws Exception {
		int userId = this.getUserIdByUsername(username);
		String query = "SELECT id FROM Account WHERE type = \'"
				+ type.toUpperCase().trim() + "\' AND id IN("
				+ " SELECT account_id FROM Owns WHERE user_id=" + userId
				+ " );";
		System.out.println(query);
		// Get connection
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			int accountId = result.getInt("id");
			// Close connection
			con.close();
			System.out.println("This is account id" + accountId);
			return accountId;
		}
		throw new Exception("Error account_id READING");
	}

	/**
	 * This for future Get user account id by bank account id and type, here
	 * assume bank account id is REAL, The conclusion is bank_account_id is
	 * UNIQUE EXCEPT CASH
	 * 
	 * @param bankAccountId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private int getAccountIdByBankAccountIdAndType(int bankAccountId,
			String type) throws Exception {
		// Get connection to DB
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT * FROM Account WHERE bank_account_id = "
				+ bankAccountId + " AND type = \'" + type.toUpperCase().trim()
				+ "\';";
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			int accountId = result.getInt("id");
			// Close connection to DB
			con.close();
			return accountId;
		}
		throw new Exception("Error account_id READING");
	}

	/**
	 * Return the user ID by username, HERE username is UNIQUE too
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private int getUserIdByUsername(String username) throws Exception {
		// Get connection to DB
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT id FROM User WHERE username = \'" + username
				+ "\' ;";
		System.out.println(query);
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			int userId = result.getInt("id");
			// Connection close
			con.close();
			return userId;
		}
		throw new Exception("Error user_id READING");
	}

	/**
	 * Check whether specific provider is exist
	 * 
	 * @param providerName
	 * @param type
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private boolean isProviderExist(String providerName, String type,
			String address) throws Exception {
		// Get connection to DB
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT COUNT(*) AS num FROM Provider WHERE name= \'"
				+ providerName.toUpperCase().trim() + "\' AND type = \'"
				+ type.toUpperCase().trim() + "\' AND address= \'"
				+ address.toUpperCase().trim() + "\' ;";
		ResultSet result = select.executeQuery(query);
		result.next();
		int num = result.getInt("num");
		System.out.println("The num of provider query is " + num);
		// Connection close
		con.close();
		if (num > 0)
			return true;
		return false;
	}

	/**
	 * For displaying payment type purpose
	 * 
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	private String getPaymentTypeByAccountId(int accountId) throws Exception {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT type FROM Account WHERE id=" + accountId + ";";
		ResultSet result = select.executeQuery(query);
		if (result.next()) {
			String type = result.getString("type");
			con.close();
			return type;
		}
		throw new Exception("Read ACCOUNT TYPE ERROR");
	}

	/**
	 * Get provider id by using the KEY combination of name type and address
	 * 
	 * @param providerName
	 * @param type
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private int getProviderIdByNameTypeAndAddress(String providerName,
			String type, String address) throws Exception {
		String query = "SELECT id FROM Provider WHERE name= \'"
				+ providerName.toUpperCase().trim() + "\' AND type = \'"
				+ type.toUpperCase().trim() + "\' AND address= \'"
				+ address.toUpperCase().trim() + "\' ;";
		// Get connection to DB
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		ResultSet result = select.executeQuery(query);
		result.next();
		int providerId = new Integer(result.getInt("id"));
		System.out.println("This is provider id " + providerId);
		con.close();
		return providerId;
	}

	/**
	 * Add a provider by using name, type and address and return the added
	 * provider's ID IF Provider already exist, it will directly return the
	 * existing provider ID
	 * 
	 * @param providerName
	 * @param providerType
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private int addProvider(String providerName, String providerType,
			String address) throws Exception {
		if (isProviderExist(providerName, providerType, address)) {
			// if this provider already exist, directly return current provider
			// id to the caller
			return this.getProviderIdByNameTypeAndAddress(providerName,
					providerType, address);
		}
		// Get connection to DB
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement insert = con.createStatement();
		String query = "INSERT INTO Provider(name,type,address) VALUES(\'"
				+ providerName.toUpperCase().trim() + "\' , \'"
				+ providerType.toUpperCase().trim() + "\' , \'"
				+ address.toUpperCase().trim() + "\');";
		int result = insert.executeUpdate(query);
		if (result == 0)
			throw new Exception("Create provider error");
		int providerId = getProviderIdByNameTypeAndAddress(providerName
				.toUpperCase().trim(), providerType.toUpperCase().trim(),
				address.toUpperCase().trim());
		// Close connection
		con.close();
		return providerId;
	}

	/**
	 * Constructor, it will create the table only IF the tables are NOT EXIST
	 */
	public Budget_Model() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement create = con.createStatement();
			create.executeUpdate(createUser);
			create.executeUpdate(createAccount);
			create.executeUpdate(createProvider);
			create.executeUpdate(createTransactions);
			create.executeUpdate(createOwns);
			// ms=new MessageHashing();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * For new user create an account through username and password
	 * 
	 * When a user is created, automatically create a CASH account for user And
	 * never allow user to create CASH account again.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean createUser(String username, String password)
			throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");

			if (isUserExist(username))
				return false;
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			/*
			 * Create CASH Account for user
			 */
			String query = "INSERT INTO User (username,password) VALUES(\'"
					+ username + "\',\'" + Encryption.HashToSHA256(password)
					+ "\');";
			Statement insert = con.createStatement();
			if (insert.executeUpdate(query) != 0) {
				int userId = getUserIdByUsername(username);
				if (userId == 0)
					throw new Exception("create user problem");
				// Add CASH Account
				addBankAccount(0, userId, (float) 0.0, "CASH", "NONE", "NONE");
				// Add Debit Account
				addBankAccount(1, userId, (float) 0.0, "DEBIT", "NONE", "NONE");
				// Add Credit Account
				addBankAccount(2, userId, (float) 0.0, "CREDIT CARD", "NONE",
						"NONE");
				con.close();
				return true;
			} else {
				con.close();
				return false;
			}
			/*
			 * Create Accounts finished
			 */
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
			return false;
		}
	}

	/**
	 * Check whether given user exist in system
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public boolean isUserExist(String username) throws SQLException {
		try {
			// Get connection to DB
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement select = con.createStatement();
			String query = "SELECT COUNT(*) AS num FROM User WHERE username= \'"
					+ username + "\';";
			select = con.createStatement();
			ResultSet result = select.executeQuery(query);
			result.next();
			int count = result.getInt("num");
			con.close();
			if (count != 0)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Add a new bank account into account table with specific information
	 * Specify the owner of this account by specific user_id
	 * 
	 * @param bankAccountId
	 * @param userId
	 * @param accountType
	 * @param bank
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean addBankAccount(int bankAccountId, int userId, float balance,
			String accountType, String bank, String address) throws Exception {
		boolean accountVarification = isBankAccountExist(bankAccountId,
				accountType);
		// If account with the same id and type already exist, no need to create
		if (accountVarification)
			return false;
		try {
			// Get connection to DB
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement insert = con.createStatement();
			String query = "INSERT INTO Account (bank_account_id,balance,type,bank,address) VALUES("
					+ bankAccountId
					+ ","
					+ balance
					+ ",\'"
					+ accountType
					+ "\',\'" + bank + "\',\'" + address + "\')";
			insert = con.createStatement();
			int result = insert.executeUpdate(query);
			// System.out.println("The result for create account is " + result);
			if (result != 0) {
				int accountId = this.getAccountIdByBankAccountIdAndType(
						bankAccountId, accountType.toUpperCase().trim());
				query = "INSERT INTO Owns (user_id,account_id) VALUES("
						+ userId + "," + accountId + ")";
				result = insert.executeUpdate(query);
				// System.out.println("The result for create owns is " +
				// result);
			}
			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
			return false;
		}
	}

	/**
	 * For login purpose, check whether username and password match
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean validatePin(String username, String password)
			throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement select = con.createStatement();
			String query = "SELECT COUNT(*) AS num FROM User WHERE username= \'"
					+ username
					+ "\' AND password =\'"
					+ Encryption.HashToSHA256(password) + "\' ;";
			select = con.createStatement();
			ResultSet result = select.executeQuery(query);
			int count = result.getInt("num");
			con.close();
			if (count != 0)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete expense with specific expenseId
	 * 
	 * @param expenseId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteTransaction(int transactionId) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement delete = con.createStatement();
			String query = "DELETE FROM Transactions WHERE id = "
					+ transactionId + ";";
			delete = con.createStatement();
			int result = delete.executeUpdate(query);
			con.close();
			if (result > 0)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get user specific transaction (purchase bill etc)
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ArrayList<String>> getTransactionByUsernameAndType(
			String username, String type) throws Exception {
		int userId = this.getUserIdByUsername(username);
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT * FROM Transactions WHERE account_id IN (SELECT account_id FROM Owns WHERE user_id ="
				+ userId
				+ " AND account_id IN (SELECT account_id FROM Account WHERE type = \'"
				+ type.toUpperCase().trim() + "\'));";
		select = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet result = select.executeQuery(query);
		result = select.executeQuery(query);
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
		while (result.next()) {
			int providerId = result.getInt("provider_id");
			ArrayList<String> sub = new ArrayList<String>();
			sub.add(result.getString("id"));
			sub.add(this.getProviderNameByProviderId(providerId));
			sub.add(this.getProviderAddressByProviderId(providerId));
			sub.add(result.getString("amount"));
			sub.add(result.getString("time"));
			sub.add(result.getString("duration"));
			sub.add(result.getString("type"));
			sub.add(result.getString("status"));
			sub.add(result.getString("due_date"));

			arrayList.add(sub);
		}
		con.close();
		return arrayList;
	}

	/**
	 * Display all this user's Transactions
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArrayList<String>> getTransactionByUsername(String username)
			throws Exception {
		int userId = this.getUserIdByUsername(username);
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement select = con.createStatement();
		String query = "SELECT * FROM Transactions WHERE account_id IN (SELECT account_id FROM Owns WHERE user_id ="
				+ userId + ");";
		select = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet result = select.executeQuery(query);
		result = select.executeQuery(query);
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
		while (result.next()) {
			int providerId = result.getInt("provider_id");
			ArrayList<String> sub = new ArrayList<String>();
			sub.add(result.getString("id"));
			sub.add(result.getString("type"));
			sub.add(this.getProviderNameByProviderId(providerId));
			sub.add(this.getProviderAddressByProviderId(providerId));
			sub.add(result.getString("amount"));
			sub.add(result.getString("time"));
			sub.add(result.getString("duration"));
			sub.add(this.getPaymentTypeByAccountId(result.getInt("account_id")));
			sub.add(result.getString("status"));
			sub.add(result.getString("due_date"));

			arrayList.add(sub);
		}
		con.close();
		return arrayList;
	}

	/**
	 * Payment paid, unpaid...whatever
	 * 
	 * @param expenseId
	 * @param status
	 * @throws SQLException
	 */
	public void updateTransaction(int transactionId, String attribute,
			String value) throws Exception {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement update = con.createStatement();
		if (!(attribute.equals("provider_id") || attribute.equals("account_id") || attribute
				.equals("amount"))) {

			String query = "UPDATE Transactions SET "
					+ attribute.trim().toLowerCase() + " = \'"
					+ value.toUpperCase().trim() + "\' WHERE id = "
					+ transactionId + ";";
			update = con.createStatement();
			update.executeUpdate(query);

		} else if (attribute.equals("amount")) {
			String query = "UPDATE Transactions SET amount = "
					+ Float.parseFloat(value) + " WHERE id = " + transactionId
					+ ";";
			update = con.createStatement();
			update.executeUpdate(query);
		} else {
			String query = "UPDATE Transactions SET "
					+ attribute.trim().toLowerCase() + " = "
					+ Integer.parseInt(value) + " WHERE id = " + transactionId
					+ ";";
			update = con.createStatement();
			update.executeUpdate(query);
		}
		con.close();
	}

	/**
	 * make a transaction
	 * 
	 * @param accountId
	 * @param providerName
	 * @param providerType
	 * @param address
	 * @param amount
	 * @param type
	 * @param status
	 * @param interval
	 * @return
	 * @throws Exception
	 */
	public boolean makeTransaction(String username, String accountType,
			String providerName, String providerType, String address,
			float amount, String type, String status, String duration,
			String dateTime, String dueDate) throws Exception {
		int providerId = 0;
		int accountId = getAccountIdByUsernameAndType(username, accountType);
		if (isProviderExist(providerName.toUpperCase().trim(), providerType
				.toUpperCase().trim(), address.toUpperCase().trim()) == false) {
			providerId = addProvider(providerName, providerType, address);
		} else {
			providerId = getProviderIdByNameTypeAndAddress(providerName,
					providerType, address);
		}

		/*
		 * Check whether this is a bill
		 */
		if (duration.equals("weekly") || duration.equals("monthly")
				|| duration.equals("yearly")) {
			/*
			 * add a bill payment
			 */
			String query = "INSERT INTO Transactions(account_id,provider_id,amount,type,status,time,dueDate,duration) VALUES("
					+ accountId
					+ ","
					+ providerId
					+ ","
					+ amount
					+ ",\'"
					+ type
					+ "\',\'"
					+ status
					+ "\',\'"
					+ dueDate
					+ "\' , \'"
					+ duration + "\')";
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement insert = con.createStatement();
			int result = insert.executeUpdate(query);
			if (result > 0) {
				con.close();
				return true;
			}
			con.close();
			return false;
		} else {
			/*
			 * add a purchase
			 */
			String query = "INSERT INTO Transactions(account_id,provider_id,amount,type,status,time,due_Date) VALUES("
					+ accountId
					+ ","
					+ providerId
					+ ","
					+ amount
					+ ",\'"
					+ type.toUpperCase().trim()
					+ "\',\'"
					+ status.toUpperCase().trim()
					+ "\' , \'"
					+ dateTime
					+ "\' , \'" + dueDate + "\' )";
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement insert = con.createStatement();
			int result = insert.executeUpdate(query);
			con.close();
			if (result > 0)
				return true;
			return false;
		}
	}

	/**
	 * For delete expense purpose
	 * 
	 * @param transactionId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTransactionById(int transactionId) throws Exception {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement delete = con.createStatement();
		String query = "DELETE FROM Transactions WHERE id = " + transactionId
				+ ";";
		int affectedRow = delete.executeUpdate(query);
		if (affectedRow == 1)
			return true;
		return false;
	}

	public boolean updateTransaction(int transactionId, String username,
			String accountType, String providerName, String providerType,
			String address, String amount, String type, String status,
			String duration, String dateTime, String dueDate) throws Exception {
		int providerId = 0;
		int accountId = this.getAccountIdByUsernameAndType(username,
				accountType);
		if (this.isProviderExist(providerName, providerType, address) == false) {
			providerId = this.addProvider(providerName, providerType, address);
		} else {
			providerId = this.getProviderIdByNameTypeAndAddress(providerName,
					providerType, address);
		}

		if (accountId == -1) {
			throw new Exception("Username and account type parameters error");
		}
		String query = "UPDATE Transactions " + "SET account_id = " + accountId
				+ ", " + "provider_id=" + providerId + ", " + "type= \'"
				+ type.toUpperCase().trim() + "\' , " + "status= \'"
				+ status.toUpperCase().trim() + " \' ," + "amount="
				+ Float.valueOf(amount) + " ," + "time= \'" + dateTime.toUpperCase().trim() + "\' ,"
				+ "duration=\'" + duration.toUpperCase().trim() + "\' , " + "due_date= \'" + dueDate.toUpperCase().trim()
				+ "\' WHERE id=" + transactionId + ";";
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement update = con.createStatement();
		int result = update.executeUpdate(query);
		if (result == 1)
			return true;
		return false;

	}
}
