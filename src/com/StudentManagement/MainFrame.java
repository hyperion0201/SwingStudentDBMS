package StudentManagement;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.*;
import java.awt.event.*;


class IntComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        Integer int1 = (Integer) o1;
        Integer int2 = (Integer) o2;
        return int1.compareTo(int2);
    }

    public boolean equals(Object o2) {
        return this.equals(o2);
    }
}

class FloatComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        Float f1 = (Float) o1;
        Float f2 = (Float) o2;
        return f1.compareTo(f2);
    }

    public boolean equals(Object o2) {
        return this.equals(o2);
    }
}

class MainFrame extends JFrame implements ActionListener{
    
    private Container container;
    private JLabel windowHeader;
    private JLabel id;
    private JLabel name;
    private JLabel mark;
    private JLabel avatar;
    private JLabel address;
    private JLabel extraInfo;

    private JTextField idField;
    private JTextField nameField;
    private JTextField markField;
    private JTextField avatarField;
    private JTextField addressField;
    private JTextField extraField;

    private JTable studentTable;

    private JButton addStudentBtn;
    private JButton removeStudentBtn;
    private JButton editStudentBtn;
    private JButton writeFileBtn;
    private JButton readFileBtn;

    private DBService dbService;

    private int selectedId;

    public MainFrame() {
        setTitle("Student Management");
        setBounds(300, 90, 1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        container = getContentPane();
        container.setLayout(null);

        windowHeader = new JLabel("MAIN MENU");
        windowHeader.setFont(new Font("Arial", Font.PLAIN, 30));
        windowHeader.setSize(300, 30);
        windowHeader.setLocation(400, 50);
        container.add(windowHeader);

        id = new JLabel("ID : ");
        id.setFont(new Font("Arial", Font.PLAIN, 12));
        id.setSize(300, 30);
        id.setLocation(50, 100);
        container.add(id);
        name = new JLabel("Name : ");
        name.setFont(new Font("Arial", Font.PLAIN, 12));
        name.setSize(300, 30);
        name.setLocation(50, 150);
        container.add(name);
        mark = new JLabel("Mark : ");
        mark.setFont(new Font("Arial", Font.PLAIN, 12));
        mark.setSize(300, 30);
        mark.setLocation(50, 200);
        container.add(mark);
        avatar = new JLabel("Avatar : ");
        avatar.setFont(new Font("Arial", Font.PLAIN, 12));
        avatar.setSize(300, 30);
        avatar.setLocation(500, 100);
        container.add(avatar);
        address = new JLabel("Address : ");
        address.setFont(new Font("Arial", Font.PLAIN, 12));
        address.setSize(300, 30);
        address.setLocation(500, 150);
        container.add(address);
        extraInfo = new JLabel("Extra info : ");
        extraInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        extraInfo.setSize(300, 30);
        extraInfo.setLocation(500, 200);
        container.add(extraInfo);

        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 12));
        idField.setSize(250, 30);
        idField.setLocation(100, 100);
        container.add(idField);
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 12));
        nameField.setSize(250, 30);
        nameField.setLocation(100, 150);
        container.add(nameField);
        markField = new JTextField();
        markField.setFont(new Font("Arial", Font.PLAIN, 12));
        markField.setSize(250, 30);
        markField.setLocation(100, 200);
        container.add(markField);
        avatarField = new JTextField();
        avatarField.setFont(new Font("Arial", Font.PLAIN, 12));
        avatarField.setSize(250, 30);
        avatarField.setLocation(600, 100);
        container.add(avatarField);
        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 12));
        addressField.setSize(250, 30);
        addressField.setLocation(600, 150);
        container.add(addressField);
        extraField = new JTextField();
        extraField.setFont(new Font("Arial", Font.PLAIN, 12));
        extraField.setSize(250, 30);
        extraField.setLocation(600, 200);
        container.add(extraField);

        addStudentBtn = new JButton("Add");
        addStudentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        addStudentBtn.setSize(100, 20);
        addStudentBtn.setLocation(200, 250);
        addStudentBtn.addActionListener(this);
        container.add(addStudentBtn);

        editStudentBtn = new JButton("Edit");
        editStudentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        editStudentBtn.setSize(100, 20);
        editStudentBtn.setLocation(400, 250);
        editStudentBtn.addActionListener(this);
        container.add(editStudentBtn);

        removeStudentBtn = new JButton("Remove");
        removeStudentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        removeStudentBtn.setSize(100, 20);
        removeStudentBtn.setLocation(600, 250);
        removeStudentBtn.addActionListener(this);
        container.add(removeStudentBtn);

        writeFileBtn = new JButton("Export to CSV");
        writeFileBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        writeFileBtn.setSize(100, 20);
        writeFileBtn.setLocation(300, 650);
        writeFileBtn.addActionListener(this);
        container.add(writeFileBtn);

        readFileBtn = new JButton("Import from CSV");
        readFileBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        readFileBtn.setSize(100, 20);
        readFileBtn.setLocation(500, 650);
        readFileBtn.addActionListener(this);
        container.add(readFileBtn);


        dbService = new DBService();
        try {
            dbService.connect();
            studentTable = new JTable();

            studentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        getSelectedRow(e);
                    }
                }
            });
            var students = dbService.getStudents();
            var mapped = _generateTableData(students);
            String[] columnNames = { "ID", "Name", "Mark", "Avatar", "Address", "Extra Info" };
            studentTable.setModel(new DefaultTableModel(mapped.toArray(new Object[][] {
            }), columnNames));

                  
            TableRowSorter trs = new TableRowSorter(studentTable.getModel());
            trs.setComparator(0, new IntComparator());
            trs.setComparator(2, new FloatComparator());
            studentTable.setRowSorter(trs);
            
            JScrollPane scrollPane = new JScrollPane(studentTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setBounds(100, 300, 800, 300);
            container.add(scrollPane);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            setVisible(true);
        }

    }


    private List<Object[]> _generateTableData(List<Student> list) {
        var rs = new ArrayList<Object[]>();
        for (int i = 0; i < list.size(); i++) {
            rs.add(new Object[] { list.get(i).getId(), list.get(i).getName(), list.get(i).getMark(),
                    list.get(i).getAvatar(), list.get(i).getAddress(), list.get(i).getExtraInfo() });
        }
        return rs;
    }

    private void _refreshTable() {
        try {
            var students = dbService.getStudents();
            var mappedData = _generateTableData(students);
            String[] columnNames = { "ID", "Name", "Mark", "Avatar", "Address", "Extra Info" };
            studentTable.setModel(new DefaultTableModel(mappedData.toArray(new Object[][] {}), columnNames));
            studentTable.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void getSelectedRow(MouseEvent e) {
        int row = studentTable.getSelectedRow();
        var id = studentTable.getValueAt(row, 0).toString();
        var name = studentTable.getValueAt(row, 1).toString();
        var mark = studentTable.getValueAt(row, 2).toString();
        var avatar = studentTable.getValueAt(row, 3).toString();
        var address = studentTable.getValueAt(row, 4).toString();
        var extraInfo = studentTable.getValueAt(row, 5).toString();
        idField.setText(id);
        nameField.setText(name);
        markField.setText(mark);
        avatarField.setText(avatar);
        addressField.setText(address);
        extraField.setText(extraInfo);

        selectedId = Integer.parseInt(id);
    }

    private void _addStudent() {
        var id = Integer.parseInt(idField.getText());
        var name = nameField.getText();
        var mark = Float.parseFloat(markField.getText());
        var avatar = avatarField.getText();
        var address = addressField.getText();
        var extraInfo = extraField.getText();
        var s = new Student(id, name, mark, avatar, address, extraInfo);
        dbService.addStudent(s);
    }

    private void _editStudent() {
        var id = Integer.parseInt(idField.getText());
        var name = nameField.getText();
        var mark = Float.parseFloat(markField.getText());
        var avatar = avatarField.getText();
        var address = addressField.getText();
        var extraInfo = extraField.getText();
        var s = new Student(id, name, mark, avatar, address, extraInfo);
        dbService.editStudent(selectedId, s);
    }

    private void _deleteStudent() {
        dbService.deleteStudent(selectedId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentBtn) {
            _addStudent();
            _refreshTable();
        } else if (e.getSource() == editStudentBtn) {
            _editStudent();
            _refreshTable();
        } else if (e.getSource() == removeStudentBtn) {
            _deleteStudent();
            _refreshTable();
        }
        else if (e.getSource() == writeFileBtn) {
            _writeCSV();
        }
        else if (e.getSource() == readFileBtn) {
            _readCSV();
        }
    }
    
    private void _writeCSV() {
        var students = dbService.getStudents();
        FileService.writeCsvFile(students);
    }
    
    private void _readCSV() {
        dbService.importFromCSV();
    }
}
