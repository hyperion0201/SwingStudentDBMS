package StudentManagement;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
  
class DBConnectFrame
    extends JFrame
    implements ActionListener {
  
    // Components of the Form
    private Container c;
    private JLabel connectionString;
    private JLabel hint;
    private JLabel dbUsername;
    private JLabel dbPassword;
    private JTextField connectionStringField;
    private JTextField dbUsernameField;
    private JTextField dbPasswordField;
    private JButton submit;

    public static final Integer FONT_SIZE = 12;
    // constructor, to initialize the components
    // with default values.
    public DBConnectFrame()
    {
        setTitle("Connect to Database (PostgreSQL)");
        setBounds(300, 90, 500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
  
        c = getContentPane();
        c.setLayout(null);
  
        connectionString = new JLabel("Connection string : ");
        connectionString.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        connectionString.setSize(300, 30);
        connectionString.setLocation(50, 50);
        c.add(connectionString);

        hint = new JLabel("Format: jdbc:postgresql://<database_host>:<port>/<database_name>");
        hint.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
        hint.setSize(500, 30);
        hint.setLocation(50, 70);
        c.add(hint);
  
        dbUsername = new JLabel("Username");
        dbUsername.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        dbUsername.setSize(100, 20);
        dbUsername.setLocation(50, 100);
        c.add(dbUsername);
  
        dbPassword = new JLabel("Password");
        dbPassword.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        dbPassword.setSize(100, 20);
        dbPassword.setLocation(50, 150);
        c.add(dbPassword);

        connectionStringField = new JTextField();
        connectionStringField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        connectionStringField.setSize(250, 20);
        connectionStringField.setLocation(200, 50);
        c.add(connectionStringField);

  
        dbUsernameField = new JTextField();
        dbUsernameField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        dbUsernameField.setSize(250, 20);
        dbUsernameField.setLocation(200, 100);
        c.add(dbUsernameField);
  
        dbPasswordField = new JTextField();
        dbPasswordField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        dbPasswordField.setSize(250, 20);
        dbPasswordField.setLocation(200, 150);
        c.add(dbPasswordField);
  
  
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        submit.setSize(100, 20);
        submit.setLocation(200, 200);
        submit.addActionListener(this);
        c.add(submit);
  
        setVisible(true);
    }
  
    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e)
    {
        var connectionString = connectionStringField.getText();
        var userName = dbUsernameField.getText();
        var password = dbPasswordField.getText();

        var dbInstance = new DBService(connectionString, userName, password);
        try {
            dbInstance.connect();
            JOptionPane.showMessageDialog(null, "Connected to database.");
            this.setVisible(false);
            var mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error when connecting to database!", "Connect error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
  
// Driver Code
