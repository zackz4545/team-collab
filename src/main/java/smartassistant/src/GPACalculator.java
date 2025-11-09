import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GPACalculator {

    private static final List<String[]> students = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GPACalculator::runApp);
    }

    private static void runApp() {
        int choice;
        do {
            choice = showMenu();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> searchStudent();
                case 3 -> displayStudents();
                case 4 -> calculateGPA();
                case 0 -> JOptionPane.showMessageDialog(null, "Exiting GPA Calculator...");
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int showMenu() {
        String menu = """
                ===== GPA CALCULATOR (Christopher Wells) =====
                1. Add Student
                2. Search Student (by ID)
                3. Display Students
                4. GPA Calculator
                0. Exit
                """;
        String input = JOptionPane.showInputDialog(menu + "\nEnter choice:");
        if (input == null) return 0; // cancel acts as exit
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a number.");
            return -1;
        }
    }

    private static void addStudent() {
        String id = JOptionPane.showInputDialog("Enter Student ID:");
        if (id == null) return;
        String name = JOptionPane.showInputDialog("Enter Full Name:");
        if (name == null) return;
        String major = JOptionPane.showInputDialog("Enter Major:");
        if (major == null) return;

        id = id.trim();
        name = name.trim();
        major = major.trim();

        if (id.isEmpty() || name.isEmpty() || major.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        if (!idIsUnique(id)) {
            JOptionPane.showMessageDialog(null, "Student ID already exists.");
            return;
        }

        students.add(new String[]{id, name, major});
        JOptionPane.showMessageDialog(null, "Student added successfully!");
    }

    private static void searchStudent() {
        String id = JOptionPane.showInputDialog("Enter Student ID to search:");
        if (id == null || id.trim().isEmpty()) return;

        for (String[] s : students) {
            if (s[0].equals(id.trim())) {
                JOptionPane.showMessageDialog(null,
                        "ID: " + s[0] + "\nName: " + s[1] + "\nMajor: " + s[2]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Student not found.");
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String[] s : students) {
            sb.append("ID: ").append(s[0])
                    .append(" | Name: ").append(s[1])
                    .append(" | Major: ").append(s[2])
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static boolean idIsUnique(String id) {
        for (String[] s : students) {
            if (s[0].equals(id)) return false;
        }
        return true;
    }

    private static void calculateGPA() {
        String numStr = JOptionPane.showInputDialog("How many courses do you want to enter?");
        if (numStr == null) return;

        int courses;
        try {
            courses = Integer.parseInt(numStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number of courses.");
            return;
        }

        double totalPoints = 0;
        double totalCredits = 0;

        for (int i = 1; i <= courses; i++) {
            String grade = JOptionPane.showInputDialog("Enter grade for course " + i + " (A, B+, etc.):");
            if (grade == null) return;
            String creditStr = JOptionPane.showInputDialog("Enter credits for course " + i + ":");
            if (creditStr == null) return;

            double credits;
            try {
                credits = Double.parseDouble(creditStr.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid credit value for course " + i);
                return;
            }

            double points = gradeToPoints(grade);
            if (points < 0) {
                JOptionPane.showMessageDialog(null, "Invalid grade for course " + i);
                return;
            }

            totalCredits += credits;
            totalPoints += credits * points;
        }

        if (totalCredits == 0) {
            JOptionPane.showMessageDialog(null, "No valid courses entered.");
            return;
        }

        double gpa = totalPoints / totalCredits;
        JOptionPane.showMessageDialog(null, "Calculated GPA: " + String.format("%.2f", gpa));
    }

    private static double gradeToPoints(String g) {
        if (g == null) return -1;
        g = g.trim().toUpperCase();

        return switch (g) {
            case "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> {
                try {
                    double val = Double.parseDouble(g);
                    yield (val >= 0 && val <= 4) ? val : -1;
                } catch (Exception e) {
                    yield -1;
                }
            }
        };
    }
}
