import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Smart Campus Assistant (Utility Tools)
 * Author: Zackaria Zagade
 * Description: A GUI-based utility tool module with:
 *  - Simple Calculator
 *  - Campus Event Scheduler (Add / View)
 */
public class Utility {

    private static final String EVENTS_CSV_PATH = "events.csv";
    private static final DateTimeFormatter DATE_F = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
    private static final List<Event> EVENTS_LIST = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Utility::runApp);
    }

    private static void runApp() {
        safeLoadEvents();

        int choice;
        do {
            choice = showMenu();
            switch (choice) {
                case 1 -> calculator();
                case 2 -> addEvent();
                case 3 -> viewEvents();
                case 0 -> {
                    safeSaveEvents();
                    JOptionPane.showMessageDialog(null, "Exiting Utility Tools...");
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int showMenu() {
        String menu = """
                ===== UTILITY TOOLS (Zackaria Zagade) =====
                1. Simple Calculator
                2. Add Campus Event
                3. View Events
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

    // ------------------- Calculator -------------------
    private static void calculator() {
        JOptionPane.showMessageDialog(null,
                "You have entered the calculator.\nType 'exit' in any prompt to leave.\nFormat: <number> <operator> <number>\nExample: 4.8 * 4\nOperators: +  -  *  /");

        while (true) {
            String input = JOptionPane.showInputDialog("Enter expression (or type 'exit'):");
            if (input == null || input.equalsIgnoreCase("exit")) return;
            if (input.trim().isEmpty()) continue;

            String[] parts = input.trim().split("\\s+");
            if (parts.length != 3) {
                JOptionPane.showMessageDialog(null, "Use format: number operator number (e.g., 5 * 2)");
                continue;
            }

            try {
                double left = Double.parseDouble(parts[0]);
                String op = parts[1];
                double right = Double.parseDouble(parts[2]);
                double result;

                switch (op) {
                    case "+" -> result = left + right;
                    case "-" -> result = left - right;
                    case "*" -> result = left * right;
                    case "/" -> {
                        if (right == 0) {
                            JOptionPane.showMessageDialog(null, "Error: Cannot divide by 0");
                            continue;
                        }
                        result = left / right;
                    }
                    default -> {
                        JOptionPane.showMessageDialog(null, "Invalid operator. Use +, -, *, or /");
                        continue;
                    }
                }

                JOptionPane.showMessageDialog(null, "Result: " + result);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid numbers. Example: 3 * 5");
            }
        }
    }

    // ------------------- Event Scheduler -------------------
    private static void addEvent() {
        JOptionPane.showMessageDialog(null, "Add a new campus event.");

        LocalDate date;
        while (true) {
            String dateStr = JOptionPane.showInputDialog("Enter event date (yyyy-MM-dd):");
            if (dateStr == null) return;
            try {
                date = LocalDate.parse(dateStr.trim(), DATE_F);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Use format yyyy-MM-dd (e.g., 2025-03-18)");
            }
        }

        String time;
        do {
            time = JOptionPane.showInputDialog("Enter time (e.g., 14:00 or 2PM):");
            if (time == null) return;
            time = time.trim();
        } while (time.isEmpty());

        String title;
        do {
            title = JOptionPane.showInputDialog("Enter event title:");
            if (title == null) return;
            title = title.trim();
        } while (title.isEmpty());

        EVENTS_LIST.add(new Event(date, time, title));
        safeSaveEvents();
        JOptionPane.showMessageDialog(null, "Event added successfully!");
    }

    private static void viewEvents() {
        if (EVENTS_LIST.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No events available. Add one first.");
            return;
        }

        StringBuilder sb = new StringBuilder("Your Campus Events:\n\n");
        EVENTS_LIST.stream()
                .sorted(Comparator.comparing((Event e) -> e.date)
                        .thenComparing(e -> e.time.toLowerCase(Locale.ROOT)))
                .forEach(e -> sb.append(e).append("\n"));

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ------------------- File Handling -------------------
    private static void safeLoadEvents() {
        if (!Files.exists(Paths.get(EVENTS_CSV_PATH))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(EVENTS_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    EVENTS_LIST.add(Event.fromCsv(line));
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage());
        }
    }

    private static void safeSaveEvents() {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(EVENTS_CSV_PATH)))) {
            for (Event e : EVENTS_LIST) {
                pw.println(e.toCsv());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving events: " + e.getMessage());
        }
    }

    // ------------------- Event Class -------------------
    private static final class Event {
        final LocalDate date;
        final String time;
        final String title;

        Event(LocalDate date, String time, String title) {
            this.date = date;
            this.time = time;
            this.title = title;
        }

        String toCsv() {
            return date.format(DATE_F) + "," + time + "," + title;
        }

        static Event fromCsv(String line) {
            String[] parts = line.split(",", 3);
            LocalDate d = LocalDate.parse(parts[0], DATE_F);
            return new Event(d, parts[1], parts[2]);
        }

        @Override
        public String toString() {
            return date.format(DATE_F) + " " + time + " - " + title;
        }
    }
}
