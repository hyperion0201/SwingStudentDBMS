package StudentManagement;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBService{

    private String connectionString;
    private String userName;
    private String password;

    public DBService() {
        this.connectionString = "jdbc:postgresql://localhost/studentmanagement";
        this.userName = "postgres";
        this.password = "postgres";
    }

    public DBService(String connectionString, String userName, String password) {
        this.connectionString = connectionString;
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() throws Exception {
        Connection conn = null;
            conn = DriverManager.getConnection(connectionString, userName, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        return conn;
    }

    /**
     * @param args the command line arguments
     */
    
    public List<Student> getStudents() {
        var list = new ArrayList<Student>();
        String query = "SELECT * from \"Students\"";
        try (Connection connect = DriverManager.getConnection(connectionString, userName, password);
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                var id = result.getInt("id");
                var name = result.getString("name");
                var mark = result.getFloat("mark");
                var avatar = result.getString("avatar");
                var address = result.getString("address");
                var extraInfo = result.getString("extraInfo");
                list.add(new Student(id, name, mark, avatar, address, extraInfo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void addStudent(Student s) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, userName, password);
            String insertSql = "INSERT into \"Students\" (id, name, mark, avatar, address, \"extraInfo\") VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(insertSql);
            pst.setInt(1, s.getId());
            pst.setString(2, s.getName());
            pst.setFloat(3, s.getMark());
            pst.setString(4, s.getAvatar());
            pst.setString(5, s.getAddress());
            pst.setString(6, s.getExtraInfo());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editStudent(int id, Student s) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, userName, password);
            String insertSql = "UPDATE \"Students\" SET id = ?, name = ?, mark = ?, avatar = ?, address = ?, \"extraInfo\" = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(insertSql);
            pst.setInt(1, s.getId());
            pst.setString(2, s.getName());
            pst.setFloat(3, s.getMark());
            pst.setString(4, s.getAvatar());
            pst.setString(5, s.getAddress());
            pst.setString(6, s.getExtraInfo());
            pst.setInt(7, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, userName, password);
            String insertSql = "DELETE from \"Students\" WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(insertSql);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Delete successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importFromCSV() {
        try {
            var students = FileService.readCsvFile();
            for (Student s : students) {
                addStudent(s);
            }
            JOptionPane.showMessageDialog(null, "Import successfully!");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when import data from CSV!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}