import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Smart Campus Assistant (Course & Faculty Module)
 * Author: Elizabeth Nsiah
 * Description:
 *  A GUI-based course management system that allows users to:
 *   - Add new courses
 *   - Search for courses by code or instructor
 *   - Display all available courses
 */
public class FacultySystem {

    private static final List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FacultySystem::runApp);
    }

    private static void runApp() {
        int choice;
        do {
            choice = showMenu();
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> searchCourse();
                case 3 -> displayCourses();
                case 0 -> JOptionPane.showMessageDialog(null, "Exiting Course & Faculty Module...");
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int showMenu() {
        String menu = """
                ===== COURSE & FACULTY MANAGEMENT SYSTEM (Elizabeth Nsiah) =====
                1. Add New Course
                2. Search Course (by Code or Instructor)
                3. Display All Courses
                0. Exit
                """;
        String input = JOptionPane.showInputDialog(menu + "\nEnter choice:");
        if (input == null) return 0;
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return -1;
        }
    }

    // ------------------- Add Course -------------------
    private static void addCourse() {
        String code = JOptionPane.showInputDialog("Enter course code:");
        if (code == null) return;
        String name = JOptionPane.showInputDialog("Enter course name:");
        if (name == null) return;
        String instructor = JOptionPane.showInputDialog("Enter instructor name:");
        if (instructor == null) return;

        code = code.trim();
        name = name.trim();
        instructor = instructor.trim();

        if (code.isEmpty() || name.isEmpty() || instructor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        if (!isUniqueCode(code)) {
            JOptionPane.showMessageDialog(null, "A course with this code already exists.");
            return;
        }

        courses.add(new Course(code, name, instructor));
        JOptionPane.showMessageDialog(null, "Course '" + name + "' added successfully!");
    }

    // ------------------- Search Course -------------------
    private static void searchCourse() {
        String keyword = JOptionPane.showInputDialog("Enter course code or instructor name:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        keyword = keyword.trim().toLowerCase();
        List<Course> results = new ArrayList<>();

        for (Course c : courses) {
            if (c.getCode().toLowerCase().contains(keyword) ||
                    c.getInstructor().toLowerCase().contains(keyword)) {
                results.add(c);
            }
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No matching courses found.");
        } else {
            StringBuilder sb = new StringBuilder("🔍 Search Results:\n\n");
            for (Course c : results) {
                sb.append("Code: ").append(c.getCode())
                        .append(" | Name: ").append(c.getName())
                        .append(" | Instructor: ").append(c.getInstructor())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // ------------------- Display All Courses -------------------
    private static void displayCourses() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No courses available.");
            return;
        }

        StringBuilder sb = new StringBuilder("📚 All Courses:\n\n");
        for (Course c : courses) {
            sb.append("Code: ").append(c.getCode())
                    .append(" | Name: ").append(c.getName())
                    .append(" | Instructor: ").append(c.getInstructor())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ------------------- Helper Methods -------------------
    private static boolean isUniqueCode(String code) {
        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return false;
            }
        }
        return true;
    }

    // ------------------- Inner Course Class -------------------
    private static class Course {
        private final String code;
        private final String name;
        private final String instructor;

        public Course(String code, String name, String instructor) {
            this.code = code;
            this.name = name;
            this.instructor = instructor;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getInstructor() {
            return instructor;
        }
    }
}
