package StudentManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class FileService {
    private static final String FILE_PATH = "./data.csv";
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "id,name,mark,avatar,address, extraInfo";

    public FileService() {
    }
    
    public static void writeCsvFile(List<Student> studentList) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(FILE_PATH);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Student student : studentList) {
                fileWriter.append(String.valueOf(student.getId()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(student.getMark()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getAvatar());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getAddress());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getExtraInfo());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            JOptionPane.showMessageDialog(null, "Exported to CSV file.", "Export successfully",
                    JOptionPane.DEFAULT_OPTION);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error when exporting to CSV file!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
    
    public static List<Student> readCsvFile() {
        BufferedReader br = null;
        List<Student> studentList = new ArrayList<>();
        try {
            String line;
            br = new BufferedReader(new FileReader(FILE_PATH));
            line = br.readLine();
            while ((line = br.readLine()) != null || line == "") {
                int id = Integer.parseInt(parseCsvLine(line).get(0));
                String name = parseCsvLine(line).get(1);
                Float mark = Float.parseFloat(parseCsvLine(line).get(2));
                String avatar = parseCsvLine(line).get(3);
                String address = parseCsvLine(line).get(4);
                String extraInfo = parseCsvLine(line).get(5);
                Student s = new Student(id, name, mark, avatar, address, extraInfo);
                studentList.add(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
        return studentList;
    }

    public static List<String> parseCsvLine(String csvLine) {
        List<String> result = new ArrayList<String>();
        if (csvLine != null) {
            String[] splitData = csvLine.split(COMMA_DELIMITER);
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }
}
