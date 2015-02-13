import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * Login View of this application
 * @author Qicheng Lao
 *
 */
public class LoginFrame {
	
	// login frame components
	private JFrame frameLogin;
	private JTextField textFieldName;
	private JPasswordField passwordFieldPW;
	private JPasswordField passwordFieldPWConfirm;

	private JButton btnLogin;
	private JButton btnSignup;
	// end of login frame components
	
	
	public User theuser;
	public MainFrame mainFrame;
	
	/**
	 * LoginFrame constructor
	 */
	public LoginFrame()	{
		theuser = new User();
		initialize();
	}

	/**
	 * Initialize the contents of the frameLogin.
	 */
	public void initialize() {
		frameLogin = new JFrame();		//frameLogin
		frameLogin.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameLogin.setBounds(400, 150, 500, 400);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.setVisible(true);
		frameLogin.getContentPane().setLayout(null);
		
		JLabel labelWelcome = new JLabel("Welcome to Personal Budget Manager Application!");
		labelWelcome.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		labelWelcome.setBounds(10, 10, 480, 100);
		frameLogin.getContentPane().add(labelWelcome);
		
		JLabel labelName = new JLabel("User name:");
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setBounds(116, 131, 83, 16);
		frameLogin.getContentPane().add(labelName);
		
		textFieldName = new JTextField();				//input user name here
		textFieldName.setBounds(218, 125, 134, 28);
		frameLogin.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		//textFieldName.setDocument(new JTextFieldLimit(10));
		
		JLabel labelPW = new JLabel("Password:");
		labelPW.setHorizontalAlignment(SwingConstants.CENTER);
		labelPW.setBounds(107, 165, 99, 16);
		frameLogin.getContentPane().add(labelPW);
		
		passwordFieldPW = new JPasswordField();			//input password here
		passwordFieldPW.setHorizontalAlignment(SwingConstants.LEFT);
		passwordFieldPW.setBounds(218, 159, 134, 28);
		frameLogin.getContentPane().add(passwordFieldPW);
		
		btnLogin = new JButton("Log in");
		btnLogin.setBounds(238, 199, 83, 29);
		frameLogin.getContentPane().add(btnLogin);
		
		final JSeparator separator = new JSeparator();
		separator.setBounds(106, 240, 277, 12);
		frameLogin.getContentPane().add(separator);
		
		final JLabel lblInfo = new JLabel("New User?");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(116, 269, 83, 16);
		frameLogin.getContentPane().add(lblInfo);
		
		//########### components for sign up view ####################################
		final JLabel lblPWConfirm = new JLabel("Confirmation:");
		lblPWConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		lblPWConfirm.setBounds(107, 204, 107, 16);
				
		passwordFieldPWConfirm = new JPasswordField();
		passwordFieldPWConfirm.setHorizontalAlignment(SwingConstants.LEFT);
		passwordFieldPWConfirm.setBounds(218, 198, 134, 28);
				
		btnSignup = new JButton("Sign up");
		btnSignup.setBounds(218, 264, 83, 29);
		
		final JButton btnCancelSignup = new JButton("Cancel");
		btnCancelSignup.setBounds(325, 264, 83, 29);
		//########### end of components for sign up view ############################
		
		final JButton btnShowSignup = new JButton("Sign up now");
		btnShowSignup.setBounds(218, 264, 117, 29);
		frameLogin.getContentPane().add(btnShowSignup);
		/**
		 * ShowSignup Button Action Listener
		 * Display sign up view
		 */
		btnShowSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("here");
				frameLogin.remove(btnLogin);
				frameLogin.remove(separator);
				frameLogin.remove(lblInfo);
				frameLogin.remove(btnShowSignup);
				frameLogin.getContentPane().add(lblPWConfirm);
				frameLogin.getContentPane().add(passwordFieldPWConfirm);
				frameLogin.getContentPane().add(btnSignup);
				frameLogin.getContentPane().add(btnCancelSignup);
				frameLogin.repaint();
			}
		});
		
		/**
		 * CancelSignup Button Action Listener
		 * Cancel the display of sign up view
		 */
		btnCancelSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameLogin.getContentPane().add(btnLogin);
				frameLogin.getContentPane().add(separator);
				frameLogin.getContentPane().add(lblInfo);
				frameLogin.getContentPane().add(btnShowSignup);
				frameLogin.remove(lblPWConfirm);
				frameLogin.remove(passwordFieldPWConfirm);
				frameLogin.remove(btnSignup);
				frameLogin.remove(btnCancelSignup);
				frameLogin.repaint();
			}
		});
		
		loginButtonListener();
	}
	
	private void loginButtonListener()
	{
		/**
		 * Login Button Action Listener
		 */
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("###########login action###########");
					String uname = getUsername();
					String pwd = String.valueOf((getPassword()));
					if(theuser.loginAction(uname, pwd))
					{
						removeFrame();
						mainFrame = new MainFrame(theuser);
						mainFrame.setlblWelcomeText("Welcome, " + uname + "!      ");
						JOptionPane.showMessageDialog(null, "You are logged in!", 
								"INFORMATION", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		/**
		 * Signup Button Action Listener
		 */
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("###########signup action###########");
					String uname = getUsername();
					String pwd = String.valueOf((getPassword()));
					String con_pwd = String.valueOf(getConfirm_Password_Signup());
					if(theuser.signupAction(uname, pwd, con_pwd))
					{
						removeFrame();
						initialize();
						JOptionPane.showMessageDialog(null, "You have signed up! Please log in!", 
								"INFORMATION", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
	}
	
	/**
	 * Remove frameLogin.
	 */
	public void removeFrame()
	{
		frameLogin.dispose();
	}
	
	//*************************************************************************************************
	//**************Getter and Setter methods for login and signup*************************************
	//*************************************************************************************************
	/**
	 * Get User Name
	 * @return String
	 */
	public String getUsername(){
		return textFieldName.getText();
	}
	/**
	 * Get the Password
	 * @return char[]
	 */
	public char[] getPassword(){
		return passwordFieldPW.getPassword();
	}
	/**
	 * Get the Password confirmation
	 * @return char[]
	 */
	public char[] getConfirm_Password_Signup(){
		return passwordFieldPWConfirm.getPassword();
	}
	
}
