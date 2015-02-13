import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.HeadlessException;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.SystemColor;


/**
 * Main View of this application
 * @author Qicheng Lao and Kaminderpal
 *
 */

public class MainFrame {

	// main frame components
	private JFrame frameMain;
	
	private JPanel panelTop;
	private JPanel panelSide;
	private JPanel panelMain;
	private JPanel panelPurchase;
	private JPanel panelBill;
	private JLabel lblWelcome;
	
	//private GridBagLayout layoutMain;
	private GridBagLayout layoutPurchase;
	private GridBagLayout layoutBill;
	
	private GridBagConstraints gbs;		//define a GridBagConstraints 
	
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	
	private JButton btnUpdateexp;
	
	private JTextField textFieldPName;
	private JTextField textFieldPLocation;
	private JDateChooser pickerPDate;
	private JTextField textFieldPAmount;
	private JComboBox comboBoxPMode;
	private JComboBox comboBoxPStatus;
	private JDateChooser pickerPDue;
	private JSpinner spinner;
	private JButton btnCancelPurchase;
	
	private JTextField textFieldBName;
	private JTextField textFieldBAmount;
	private JComboBox comboBoxBMode;
	private JComboBox comboBoxBStatus;
	private JDateChooser pickerBDue;
	private JComboBox comboBoxBInterval;
	private JButton btnCancelBill;
	
	
	// buttons that have access to database in main frame 
	private JButton btnAddPurchase;
	private JButton btnAddBill;
	private JButton btnUpdatePurchase;
	private JButton btnUpdateBill;
	private JButton btnDeleteexp;
	// end of main frame components
	
	private User theuser;

	/**
	 * View constructor
	 */
	public MainFrame(User theuser) throws HeadlessException {
		
		this.theuser = theuser;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frameMain.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.setBounds(200, 100, 1000, 600);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setTitle("Personal Budget Manager Application");
		frameMain.setVisible(true);
        frameMain.getContentPane().setLayout(new BorderLayout(0, 0));
        //frameMain.setBackground(Color.LIGHT_GRAY);
        //frameMain.setLocationRelativeTo(null);
        
        setupMenuBar();		//add menuBar to the main frame
        
        //Top panel view
        panelTop = new JPanel();
        frameMain.getContentPane().add(panelTop, BorderLayout.NORTH);
        panelTop.setPreferredSize(new Dimension(0, 50));
        
        //unused panel reserved for future use
		JPanel panelnothing1 = new JPanel();	
		panelTop.add(panelnothing1);
		
		lblWelcome = new JLabel("Welcome, XXXXXX!   ");
		lblWelcome.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblWelcome.setHorizontalAlignment(SwingConstants.RIGHT);
		//lblWelcome.setBounds(421, 9, 328, 27);
		//lblWelcome.setPreferredSize(new Dimension(50, 0));
		panelTop.add(lblWelcome);
		
		GridBagLayout layoutTop = new GridBagLayout();  
        panelTop.setLayout(layoutTop);
        GridBagConstraints gbs= new GridBagConstraints();	//define a GridBagConstraints  
        gbs.fill = GridBagConstraints.BOTH;
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 1;	//set 0 if fixed size
        gbs.weighty = 1;	//set nonzero if size adjustable
        layoutTop.setConstraints(panelnothing1, gbs);
        
        gbs.gridwidth = 0;
        gbs.weightx = 0;
        gbs.weighty = 0;
        layoutTop.setConstraints(lblWelcome, gbs);
        
        //Side panel view
        panelSide = new JPanel();
        panelSide.setBackground(SystemColor.controlShadow);
        panelSide.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelSide.setPreferredSize(new Dimension(200, 0));
        //panelSide.setLayout(new BoxLayout(panelSide, BoxLayout.Y_AXIS));
        frameMain.getContentPane().add(panelSide, BorderLayout.WEST);
        
        panelMain = new JPanel();
        panelMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        //panelMain.setBackground(Color.WHITE);
        frameMain.getContentPane().add(panelMain, BorderLayout.CENTER);
        
		
        viewExpense();
        
        
        //unused panel for the purpose of layout
        JPanel panelnothing2 = new JPanel();	
        panelnothing2.setBackground(SystemColor.controlShadow);
        panelSide.add(panelnothing2);
        panelnothing2.setPreferredSize(new Dimension(180,50));
        
        JButton btnViewexp = new JButton("View expenses");
        btnViewexp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		restoreDefault();
        	}
        });
        panelSide.add(btnViewexp);
        btnViewexp.setPreferredSize(new Dimension(150,50));
        
        JButton btnAddexp = new JButton("Add a new expense");
        btnAddexp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {

        		JRadioButton radioPurchase = new JRadioButton("Purchase");
        		JRadioButton radioBill = new JRadioButton("Bill");
        		radioPurchase.setSelected(true);
        		
        		final ButtonGroup group = new ButtonGroup();
                group.add(radioPurchase);
                group.add(radioBill);
                
                JPanel panel = new JPanel();
                panel.add(radioPurchase);
                panel.add(radioBill);
        		
        		int selection = JOptionPane.showOptionDialog(null, panel, 
        				"Select Expense Type", JOptionPane.OK_CANCEL_OPTION, 
        				JOptionPane.QUESTION_MESSAGE, null, null, null);
        		
        		if(selection == JOptionPane.OK_OPTION)
        		{
        			boolean isPurchase = radioPurchase.isSelected();
            		boolean isBill = radioBill.isSelected();
            		if(isPurchase || isBill)
            		{
            			clearMainPanel();			//clear the main panel
            			btnUpdateexp.setVisible(false);
                		btnDeleteexp.setVisible(false);
                		if(isPurchase)
                		{
                			purchaseView();		//open add purchase view
                			addPurchaseButtons();
                		}
                		else
                		{
                			billView();			//open add bill view
                			addBillButtons();
                		}
            		}
        		}

        	}
        });
        panelSide.add(btnAddexp);
        btnAddexp.setPreferredSize(new Dimension(150,50));
        
        btnUpdateexp = new JButton("Update the status");
        btnUpdateexp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int row = table.getSelectedRow();
        		if(row == -1)
        			JOptionPane.showMessageDialog(null, 
        					"Please select the expense you want to update!", "ERROR", JOptionPane.ERROR_MESSAGE);
        		else
        		{
        			int expenseID = Integer.parseInt((String) table.getValueAt(row, 0));
        			String mode = (String) table.getValueAt(row, 7);
        			String oldstatus = (String) table.getValueAt(row, 8);
        			String newstatus;
        			if(oldstatus.equalsIgnoreCase("Paid"))
        				newstatus = "UNPAID";
        			else
        				newstatus = "PAID";
        			try 
        			{
						if(theuser.updateStatus(expenseID, mode, oldstatus, newstatus))
						{
							restoreDefault();
							JOptionPane.showMessageDialog(null, "Expense has been changed!");
						}
					} catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        
        });
        panelSide.add(btnUpdateexp);
        btnUpdateexp.setPreferredSize(new Dimension(150,50));
        
        btnDeleteexp = new JButton("Delete the expense");
        btnDeleteexp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int row = table.getSelectedRow();
        		if(row == -1)
        			JOptionPane.showMessageDialog(null, 
        					"Please select the expense you want to delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        		else
        		{
        			int expenseID = Integer.parseInt((String) table.getValueAt(row, 0));
        			try 
        			{
						if(theuser.deleteExpense(expenseID))
						{
							restoreDefault();
							JOptionPane.showMessageDialog(null, "Expense has been deleted!");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
        panelSide.add(btnDeleteexp);
        btnDeleteexp.setPreferredSize(new Dimension(150,50));
        
        panelPurchase = new JPanel();
	}
	
	private void setupMenuBar()
	{
		JMenuBar myMenuBar = new JMenuBar();
        JMenu myMenu = new JMenu("File");
        
        JMenuItem itemExp = new JMenuItem("My Expenses");
        itemExp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		restoreDefault();
        	}
        });
        myMenu.add(itemExp);
        
        JMenuItem itemAcc = new JMenuItem("My Accounts");
        itemAcc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "To be implemented!", 
    					"INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        myMenu.add(itemAcc);
        
        myMenu.addSeparator();
        
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frameMain.dispose();
        	}
        });
        myMenu.add(itemExit);
        
        myMenuBar.add(myMenu);
        frameMain.setJMenuBar(myMenuBar);
        
        myMenu = new JMenu("Help");
        myMenuBar.add(myMenu);
	}
	
	/**
	 * Get back to view all expenses
	 * @throws SQLException 
	 */
	private void viewExpense() 
	{
		panelMain.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(0,0));
		//scrollPane.setBounds(8, 117, 800, 411);
		panelMain.add(scrollPane);
		
		
		String[] colNames = {"Expense ID", "Expense Type",
                "Provider Name",
                "Location",
                "Amount",
                "DateTime","Interval","Payment Mode","Status","Due Date",};
		try
		{
			ArrayList<ArrayList<String>> expenses = theuser.viewExpenses();
			String[][] data = new String[expenses.size()][10];
			for(int i = 0; i < expenses.size(); ++i)
			{
				for(int j = 0; j < 10; ++j)
				{
					//Character.toString(str.charAt(0)).toUpperCase()+str.substring(1)
					data[i][j] = expenses.get(i).get(j);
				}
			}
			model = new DefaultTableModel(data, colNames);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		
		table = new JTable(model);
		table.setColumnSelectionAllowed(false);
	    table.setRowSelectionAllowed(true);
	    
	    table.getColumn("Expense ID").setPreferredWidth(0);
	    table.getColumn("Expense ID").setMinWidth(0);
	    table.getColumn("Expense ID").setMaxWidth(0);
		scrollPane.setViewportView(table);
		
		panelMain.validate();
		
	}

	private void clearMainPanel()
	{
		panelMain.removeAll();
		panelMain.validate();
		//panelMain.repaint();
	}

	private void restoreDefault()
	{
		clearMainPanel();
		viewExpense();
		btnUpdateexp.setVisible(true);
		btnDeleteexp.setVisible(true);
	}
	
	public void purchaseView()
	{
        panelPurchase = new JPanel();
		panelPurchase.setPreferredSize(new Dimension(350, 0));
		panelPurchase.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMain.add(panelPurchase);
        
        gbs= new GridBagConstraints();		//define a GridBagConstraints  
        gbs.fill = GridBagConstraints.BOTH;
		
		 //########### components for Add/Update purchase view ####################################
        JLabel lblPType = new JLabel("Purchase Type");
        lblPType.setHorizontalAlignment(SwingConstants.CENTER);
        lblPType.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        lblPType.setPreferredSize(new Dimension(400,50));
        panelPurchase.add(lblPType);
        
        JLabel lblPName = new JLabel("Provider Name:");
        lblPName.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPName);
        
        textFieldPName = new JTextField();
		textFieldPName.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(textFieldPName);
		
		JLabel lblPLocation = new JLabel("Provider Location:");
		lblPLocation.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPLocation);
        
        textFieldPLocation = new JTextField();
        textFieldPLocation.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(textFieldPLocation);
		
		JLabel lblPDate = new JLabel("Date:");
		lblPDate.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPDate);
        
		//###############################calendar###############################
		pickerPDate = new JDateChooser();
		pickerPDate.setPreferredSize(new Dimension(200,35));
		//pickerPDate.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		panelPurchase.add(pickerPDate);
		
		JLabel lblPTime = new JLabel("Time:");
		lblPTime.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPTime);
		
		//##############################time####################################
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24); 	// 24 == 12 PM == 00:00:00
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		SpinnerDateModel model = new SpinnerDateModel();
		model.setValue(calendar.getTime());

		spinner = new JSpinner(model);

		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); // this makes what you want
		formatter.setOverwriteMode(true);

		spinner.setEditor(editor);
		panelPurchase.add(spinner);
		
		JLabel lblPAmount = new JLabel("Amount:");
		lblPAmount.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPAmount);
        
        textFieldPAmount = new JTextField();
        textFieldPAmount.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(textFieldPAmount);
		
		JLabel lblPMode = new JLabel("Payment Mode:");
		lblPMode.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPMode);
        
		comboBoxPMode = new JComboBox();
		comboBoxPMode.setModel(new DefaultComboBoxModel(new String[] {"Credit Card", "Cash", "Debit"}));
		comboBoxPMode.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(comboBoxPMode);
		
		final JLabel lblPStatus = new JLabel("Status:");
		lblPStatus.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPStatus);
        
		comboBoxPStatus = new JComboBox();
		comboBoxPStatus.setModel(new DefaultComboBoxModel(new String[] {"Unpaid", "Paid"}));
		comboBoxPStatus.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(comboBoxPStatus);
		
		final JLabel lblPDue = new JLabel("Due Date:");
		lblPDue.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(lblPDue);
		
		///////////////###############################calendar###############################
		pickerPDue = new JDateChooser();
		pickerPDue.setPreferredSize(new Dimension(200,35));
		panelPurchase.add(pickerPDue);
        
		
		final JPanel panelnothing3 = new JPanel();
		panelnothing3.setPreferredSize(new Dimension(150,35));
		panelPurchase.add(panelnothing3);
		
		/*
		JButton btnAddPurchase = new JButton("Add a Purchase Expense");
		btnAddPurchase.setPreferredSize(new Dimension(50,35));
		panelPurchase.add(btnAddPurchase);
		*/
		layoutPurchase = new GridBagLayout();
        panelPurchase.setLayout(layoutPurchase);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPType, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPName, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(textFieldPName, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPLocation, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(textFieldPLocation, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPDate, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(pickerPDate, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPTime, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(spinner, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPAmount, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(textFieldPAmount, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPMode, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(comboBoxPMode, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPStatus, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(comboBoxPStatus, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(lblPDue, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(pickerPDue, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutPurchase.setConstraints(panelnothing3, gbs);
        //########### end of components for Add/Update purchase view #########################
        comboBoxPMode.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if(comboBoxPMode.getSelectedItem().toString() == "Cash" || comboBoxPMode.getSelectedItem().toString() =="Debit")
                {
            		lblPStatus.setVisible(false);
            		lblPDue.setVisible(false);
            		comboBoxPStatus.setVisible(false);
            		pickerPDue.setVisible(false);
            		panelnothing3.setPreferredSize(new Dimension(150,105));
                }
            	else
            	{
            		lblPStatus.setVisible(true);
            		lblPDue.setVisible(true);
            		comboBoxPStatus.setVisible(true);
            		pickerPDue.setVisible(true);
            		panelnothing3.setPreferredSize(new Dimension(150,35));
            	}
            }
        });
        
        comboBoxPStatus.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if(comboBoxPStatus.getSelectedItem().toString() == "Paid")
                {
            		lblPDue.setVisible(false);
            		pickerPDue.setVisible(false);
            		panelnothing3.setPreferredSize(new Dimension(150,70));
                }
            	else
            	{
            		lblPDue.setVisible(true);
            		pickerPDue.setVisible(true);
            		panelnothing3.setPreferredSize(new Dimension(150,35));
            	}
            }
        });
        
        
        panelMain.validate();
	}
	
	private void billView()
	{
		panelBill = new JPanel();
		panelBill.setPreferredSize(new Dimension(350, 0));
		panelBill.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMain.add(panelBill);
        
        gbs= new GridBagConstraints();		//define a GridBagConstraints  
        gbs.fill = GridBagConstraints.BOTH;
		
		//########### components for Add/Update bill view ####################################
        JLabel lblBType = new JLabel("Bill Type");
        lblBType.setHorizontalAlignment(SwingConstants.CENTER);
        lblBType.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        lblBType.setPreferredSize(new Dimension(400,50));
        panelBill.add(lblBType);
        
        JLabel lblBName = new JLabel("Provider Name:");
        lblBName.setPreferredSize(new Dimension(150,35));
        panelBill.add(lblBName);
        
        textFieldBName = new JTextField();
		textFieldBName.setPreferredSize(new Dimension(200,35));
		panelBill.add(textFieldBName);

		JLabel lblBAmount = new JLabel("Amount:");
		lblBAmount.setPreferredSize(new Dimension(150,35));
		panelBill.add(lblBAmount);
        
        textFieldBAmount = new JTextField();
        textFieldBAmount.setPreferredSize(new Dimension(200,35));
        panelBill.add(textFieldBAmount);
        
        JLabel lblBInterval = new JLabel("Repetition Interval:");
		lblBInterval.setPreferredSize(new Dimension(150,35));
		panelBill.add(lblBInterval);
        
		comboBoxBInterval = new JComboBox();
		comboBoxBInterval.setModel(new DefaultComboBoxModel(new String[] {"Weekly", "Monthly", "Yearly"}));
		comboBoxBInterval.setPreferredSize(new Dimension(200,35));
		panelBill.add(comboBoxBInterval);
        
        JLabel lblBMode = new JLabel("Payment Mode:");
		lblBMode.setPreferredSize(new Dimension(150,35));
		panelBill.add(lblBMode);
        
		comboBoxBMode = new JComboBox();
		comboBoxBMode.setModel(new DefaultComboBoxModel(new String[] {"Credit Card", "Cash", "Debit"}));
		comboBoxBMode.setPreferredSize(new Dimension(200,35));
		panelBill.add(comboBoxBMode);
		
		final JLabel lblBStatus = new JLabel("Status:");
		lblBStatus.setPreferredSize(new Dimension(150,35));
		panelBill.add(lblBStatus);
        
		comboBoxBStatus = new JComboBox();
		comboBoxBStatus.setModel(new DefaultComboBoxModel(new String[] {"Unpaid", "Paid"}));
		comboBoxBStatus.setPreferredSize(new Dimension(200,35));
		panelBill.add(comboBoxBStatus);
		
		final JLabel lblBDue = new JLabel("Due Date:");
		lblBDue.setPreferredSize(new Dimension(150,35));
		panelBill.add(lblBDue);
        
		pickerBDue = new JDateChooser();
		pickerBDue.setPreferredSize(new Dimension(200,35));
		panelBill.add(pickerBDue);
		
		final JPanel panelnothing4 = new JPanel();
		panelnothing4.setPreferredSize(new Dimension(150,105));
		panelBill.add(panelnothing4);

		layoutBill = new GridBagLayout();
        panelBill.setLayout(layoutBill);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBType, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBName, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(textFieldBName, gbs);

        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBAmount, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(textFieldBAmount, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBInterval, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(comboBoxBInterval, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBMode, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(comboBoxBMode, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBStatus, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(comboBoxBStatus, gbs);
        
        gbs.gridwidth = 1;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(lblBDue, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(pickerBDue, gbs);
        
        gbs.gridwidth = 0;	//number of grids, if the last one, then set to 0
        gbs.weightx = 0;	//set 0 if fixed size
        gbs.weighty = 0;	//set nonzero if size adjustable
        layoutBill.setConstraints(panelnothing4, gbs);
        //########### end of components for Add/Update purchase view #########################
        comboBoxBMode.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if(comboBoxBMode.getSelectedItem().toString() == "Cash" || comboBoxBMode.getSelectedItem().toString() =="Debit")
                {
            		lblBStatus.setVisible(false);
            		lblBDue.setVisible(false);
            		comboBoxBStatus.setVisible(false);
            		pickerBDue.setVisible(false);
            		panelnothing4.setPreferredSize(new Dimension(150,175));
                }
            	else
            	{
            		lblBStatus.setVisible(true);
            		lblBDue.setVisible(true);
            		comboBoxBStatus.setVisible(true);
            		pickerBDue.setVisible(true);
            		panelnothing4.setPreferredSize(new Dimension(150,105));
            	}
            }
        });
        
        comboBoxBStatus.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if(comboBoxBStatus.getSelectedItem().toString() == "Paid")
                {
            		lblBDue.setVisible(false);
            		pickerBDue.setVisible(false);
            		panelnothing4.setPreferredSize(new Dimension(150,140));
                }
            	else
            	{
            		lblBDue.setVisible(true);
            		pickerBDue.setVisible(true);
            		panelnothing4.setPreferredSize(new Dimension(150,105));
            	}
            }
        });
        
        panelMain.validate();
	}

	/**
	 * Add purchase buttons
	 */
	public void addPurchaseButtons()
	{
		btnCancelPurchase = new JButton("Cancel");
		btnCancelPurchase.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		restoreDefault();
        	}
        });
		btnCancelPurchase.setPreferredSize(new Dimension(150,50));
		panelPurchase.add(btnCancelPurchase);
		
		btnAddPurchase = new JButton("Add a Purchase Expense");
		btnAddPurchase.setPreferredSize(new Dimension(300,50));
		panelPurchase.add(btnAddPurchase);
		btnAddPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("###########add purchase action###########");
					String type = "Purchase";
					String pname = getProviderPName();
					String paddress = getProviderPLocation();
					String pdate = "";
					if(getPDate() != null)
						pdate = getPDate().toString();
					String pamount = getPAmount();
					String pmode = getPMode();
					String ptime = getPTime();
					String pstatus = getPStatus();
					String pdue_date = getPDue();
					
					if(theuser.addPurchaseAction(type, pname, paddress, pdate, ptime, pamount, pmode, pstatus, pdue_date))
					{
						restoreDefault();
						JOptionPane.showMessageDialog(null, "You have successfully added purchase transaction!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panelPurchase.validate();
		
	}
	/**
	 * Add Bill Buttons 
	 */
	private void addBillButtons()
	{
		btnCancelBill = new JButton("Cancel");
		btnCancelBill.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		restoreDefault();
        	}
        });
		btnCancelBill.setPreferredSize(new Dimension(150,50));
		panelBill.add(btnCancelBill);
		
		btnAddBill = new JButton("Add a Bill Expense");
		btnAddBill.setPreferredSize(new Dimension(300,50));
		panelBill.add(btnAddBill);
		btnAddBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("###########add bill action###########");
					String type = "Bill";
					String bname = getProviderBName();
					String bamount = getBAmount();
					String bmode = getBMode();
					String bstatus = getBStatus();
					String bdue_date = getBDue();
					String binterval = getBInterval();
					String blocation = "Headquaters";
					
					if(theuser.addBillAction(type, bname, bamount, bmode, binterval, blocation, bstatus, bdue_date))
					{
						restoreDefault();
						JOptionPane.showMessageDialog(null, "You have successfully added bill transaction!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panelBill.validate();
	}

	
	
	//*************************************************************************************************
	//**************Getter and Setter methods for expense add and update ******************************
	//*************************************************************************************************
	public void setlblWelcomeText(String message)
	{
		lblWelcome.setText(message);
	}
	
	/**
	* Get the Provider Name for purchase expense
	* @return String
	*/
	public String getProviderPName(){
			return textFieldPName.getText();
		}
	/**
	 * Get the Provider Location for purchase expense
	 * @return String
	 */
	public String getProviderPLocation(){
		return textFieldPLocation.getText();
	}
	/**
	 * Get the Date for purchase expense
	 * @return String
	 */
	public String getPDate(){
		if(pickerPDate.getDate() != null)
			return new SimpleDateFormat("yyyy-MM-dd").format(pickerPDate.getDate());
		else
			return "";
	}
	/**
	 * Get the Time for purchase expense
	 * @return String
	 */
	public String getPTime(){
		if(spinner.getValue() != null)
			return new SimpleDateFormat("h:mm a").format(spinner.getValue());
		else
			return "";
	}
	/**
	 * Get the Amount for purchase expense
	 * @return String
	 */
	public String getPAmount(){
		return textFieldPAmount.getText();
	}
	/**
	 * Get the Payment Mode for purchase expense
	 * @return String
	 */
	public String getPMode(){
		return comboBoxPMode.getSelectedItem().toString();
	}
	/**
	 * Get the Status for purchase expense
	 * @return String
	 */
	public String getPStatus(){
		return comboBoxPStatus.getSelectedItem().toString();
	}
	/**
	 * Get the Due date for purchase expense
	 * @return String
	 */
	public String getPDue(){
		if(pickerPDue.getDate() != null)
			return new SimpleDateFormat("yyyy-MM-dd").format(pickerPDue.getDate());
		else
			return "";
	}
	
	
	/**
	 * Get the Provider Name for bill expense
	 * @return String
	 */
	public String getProviderBName(){
		return textFieldBName.getText();
	}
	/**
	 * Get the Amount for bill expense
	 * @return String
	 */
	public String getBAmount(){
		return textFieldBAmount.getText();
	}
	/**
	 * Get the Payment Mode for bill expense
	 * @return String
	 */
	public String getBMode(){
		return comboBoxBMode.getSelectedItem().toString();
	}
	/**
	 * Get the Status for bill expense
	 * @return String
	 */
	public String getBStatus(){
		return comboBoxBStatus.getSelectedItem().toString();
	}
	/**
	 * Get the Due date for bill expense
	 * @return String
	 */
	public String getBDue(){
		if(pickerBDue.getDate() != null)
			return new SimpleDateFormat("yyyy-MM-dd").format(pickerBDue.getDate());
		else
			return "";
	}
	/**
	 * Get the Repetition interval for bill expense
	 * @return String
	 */
	public String getBInterval(){
		return comboBoxBInterval.getSelectedItem().toString();
	}
}
