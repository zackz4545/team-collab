import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple GPUCalculator (Student Module)
 * Author: Christopher Wells
 * Description: basic Java Swing program for managing students
 * - add/search/display student info
 * - calculate GPA
 */

public class GPACalculator {

    // File where students are saved
    private final File studentFile = new File("students.txt");

    // GUI components
    private JFrame mainFrame;
    private JTextField txtId, txtName, txtMajor;
    private JTextField txtSearchId;
    private JTextArea outputArea;

    private JTable studentTable;
    private DefaultTableModel studentModel;

    private JTable gpaTable;
    private DefaultTableModel gpaModel;
    private JLabel lblResult;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GPACalculator app = new GPACalculator();
            app.setupFile();
            app.createAndShowGUI();
        });
    }

    // makes sure students.txt exists
    private void setupFile() {
        try {
            if (!studentFile.exists()) {
                studentFile.createNewFile();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not create students.txt");
        }
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("GPACalculator - Student Module");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocationRelativeTo(null);

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("Add Student", buildAddPanel());
        tabPane.addTab("Search Student", buildSearchPanel());
        tabPane.addTab("Display Students", buildDisplayPanel());
        tabPane.addTab("GPA Calculator", buildGpaPanel());

        mainFrame.setContentPane(tabPane);
        mainFrame.setVisible(true);

        loadStudentsToTable();
    }

    // panel for adding a student
    private JPanel buildAddPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Major:"));
        txtMajor = new JTextField();
        panel.add(txtMajor);

        JButton btnAdd = new JButton("Save Student");
        btnAdd.addActionListener(e -> addStudent());
        panel.add(btnAdd);

        return panel;
    }

    // panel for searching a student
    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();

        top.add(new JLabel("Enter Student ID:"));
        txtSearchId = new JTextField(12);
        top.add(txtSearchId);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> findStudent());
        top.add(btnSearch);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        return panel;
    }

    // panel for displaying all students
    private JPanel buildDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        studentModel = new DefaultTableModel(new Object[]{"ID", "Name", "Major"}, 0);
        studentTable = new JTable(studentModel);

        JButton btnRefresh = new JButton("Refresh List");
        btnRefresh.addActionListener(e -> loadStudentsToTable());

        panel.add(btnRefresh, BorderLayout.NORTH);
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        return panel;
    }

    // GPA calculator panel
    private JPanel buildGpaPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        gpaModel = new DefaultTableModel(new Object[]{"Grade", "Credits"}, 0);
        gpaTable = new JTable(gpaModel);

        JButton btnAddCourse = new JButton("Add Course");
        btnAddCourse.addActionListener(e -> gpaModel.addRow(new Object[]{"", ""}));

        JButton btnCalc = new JButton("Calculate GPA");
        btnCalc.addActionListener(e -> calculateGPA());

        JPanel topPanel = new JPanel();
        topPanel.add(btnAddCourse);
        topPanel.add(btnCalc);

        lblResult = new JLabel("GPA: ---");
        lblResult.setHorizontalAlignment(SwingConstants.CENTER);
        lblResult.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gpaTable), BorderLayout.CENTER);
        panel.add(lblResult, BorderLayout.SOUTH);

        return panel;
    }

    // Adds a student to the file
    private void addStudent() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String major = txtMajor.getText().trim();

        if (id.isEmpty() || name.isEmpty() || major.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please fill out all fields.");
            return;
        }

        if (!idIsUnique(id)) {
            JOptionPane.showMessageDialog(mainFrame, "Student ID already exists.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(studentFile, true))) {
            bw.write(id + "|" + name + "|" + major);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving student.");
            return;
        }

        JOptionPane.showMessageDialog(mainFrame, "Student added successfully!");

        txtId.setText("");
        txtName.setText("");
        txtMajor.setText("");

        loadStudentsToTable();
    }

    // checks if the student ID is already in file
    private boolean idIsUnique(String id) {
        for (String[] s : getAllStudents()) {
            if (s.length > 0 && s[0].equals(id)) {
                return false;
            }
        }
        return true;
    }

    // searches student by ID
    private void findStudent() {
        String id = txtSearchId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Enter an ID first!");
            return;
        }

        boolean found = false;
        for (String[] s : getAllStudents()) {
            if (s.length >= 3 && s[0].equals(id)) {
                outputArea.setText("ID: " + s[0] + "\nName: " + s[1] + "\nMajor: " + s[2]);
                found = true;
                break;
            }
        }

        if (!found) {
            outputArea.setText("Student not found.");
        }
    }

    // loads all students to JTable
    private void loadStudentsToTable() {
        studentModel.setRowCount(0);
        for (String[] s : getAllStudents()) {
            if (s.length >= 3) {
                studentModel.addRow(s);
            }
        }
    }

    // reads from file and returns student list
    private List<String[]> getAllStudents() {
        List<String[]> list = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(studentFile.toPath())) {
                if (line.trim().length() > 0) {
                    list.add(line.split("\\|"));
                }
            }
        } catch (IOException e) {
            // ignore if empty or unreadable
        }
        return list;
    }

    // GPA calculation logic
    private void calculateGPA() {
        double totalPoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < gpaModel.getRowCount(); i++) {
            String grade = String.valueOf(gpaModel.getValueAt(i, 0)).trim();
            String creditStr = String.valueOf(gpaModel.getValueAt(i, 1)).trim();

            double credits;
            try {
                credits = Double.parseDouble(creditStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid credit value at row " + (i + 1));
                return;
            }

            double points = gradeToPoints(grade);
            if (points < 0) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid grade at row " + (i + 1));
                return;
            }

            totalCredits += credits;
            totalPoints += credits * points;
        }

        if (totalCredits == 0) {
            lblResult.setText("GPA: --- (no valid courses)");
            return;
        }

        double gpa = totalPoints / totalCredits;
        lblResult.setText("GPA: " + String.format("%.2f", gpa));
    }

    // Converts letter grade to numeric GPA value
    private double gradeToPoints(String g) {
        if (g == null) return -1;
        g = g.trim().toUpperCase();

        switch (g) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default:
                try {
                    double val = Double.parseDouble(g);
                    if (val >= 0 && val <= 4) return val;
                } catch (Exception e) {
                    return -1;
                }
                return -1;
        }
    }
}