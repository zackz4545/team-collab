import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Smart Campus Assistant (Utility Tools)
 * Author: Zackaria Zagade
 * Description: This code is apart of a calculator and a event scheduler.
 * This makes it optimal for the user to run in an easy to read menu.
 * The calculator accepts basic symbols and understands inputs.
 * For the event scheduler you can add view to help your organization more.
 */

public class UtilityTools {

    private static final String EVENTS_CSV_PATH = "events.csv";
    private static final DateTimeFormatter DATE_F = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
    // This makes it so the events will be formatted in year month day format.

    private static final List<Event> EVENTS_LIST = new ArrayList<>();
    // This in turn will be the sub menu because of how the arrays will work.

    public static void run(Scanner sc) {

        if (EVENTS_LIST.isEmpty()) {
            safeLoadEvents();
        }
        while (true) {
            System.out.println();
            System.out.println("---Utility Tools---");
            System.out.println("1) Simple Calculator");
            System.out.println("2) Add Campus Event");
            System.out.println("3) View Events");
            System.out.println("4) Back to Main Menu");
            System.out.println("Pick 1-4");
            // This will prompt the user to pick one of the 4 utility tools options
            String MENU_CHOICE = sc.nextLine().trim();

            switch (MENU_CHOICE) {
                case "1":
                    calculator(sc);
                    break;
                case "2":
                    addEventFlow(sc);
                    break;
                case "3":
                    viewEvents();
                    break;
                case "4":
                    safeSaveEvents();
                    return;
                default:
                    System.out.println("Enter 1, 2, 3, or 4");
                    // This is a conditional output based on the users input of 1, 2, 3, or 4
            }
        }
    }

    /**
     * This calculator will accept simple input and give outputs.
     *It handles errors and makes sure the calculcator input is valid.
     */
    private static void calculator(Scanner sc) {
        System.out.println();
        System.out.println("You have entered the calculator, if you want to go back type exit");
        System.out.println("Format: <number> <operator> <number> example, 4.8 * 4");
        System.out.println("The operators are: - + * /");
        // This will give you a user basic tutorial on how to use the calculator and have them gain a sense of comfort.
        while (true) {
            System.out.print(">");
            String questionLine = sc.nextLine().trim();
            if (questionLine.equalsIgnoreCase("exit")) return;
            if (questionLine.isEmpty()) continue;
            String[] parts = questionLine.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Try another like 9 - 3 or 4 * 2");
                continue;
            }
            try {
                double leftOperand = Double.parseDouble(parts[0]);
                String operatorSymbol = parts[1];
                double rightOperand = Double.parseDouble(parts[2]);

                double finalResult;
                switch (operatorSymbol) {
                    case "+":
                        finalResult = leftOperand + rightOperand;
                        break;
                    case "-":
                        finalResult = leftOperand - rightOperand;
                        break;
                    case "*":
                        finalResult = leftOperand * rightOperand;
                        break;
                    case "/":
                        if (rightOperand == 0.0) {
                            System.out.println("Error: cannot divide by 0");
                            continue;
                        }
                        finalResult = leftOperand / rightOperand;
                        break;
                    default:
                        System.out.println("Unknown Symbol Detected. Please use + - * /");
                        continue;
                }
                System.out.println("= " + finalResult);
            } catch (NumberFormatException numberFormatEx) {
                System.out.println("Invalid number detected. Use something like 3 * 5");
            }
        }
    }

    /**
     * This is to create a new event in the utility tools.
     * This will allow the user to make events to be easily kept.
     * This accounts for invalid input and gives the user
     */
    private static void addEventFlow(Scanner sc) {
        System.out.println();
        System.out.println("Add Campus Event");
        LocalDate date = promptDate(sc, "Event date (yyyy-MM-dd): ");
        String time = promptNonEmpty(sc, "Time (e.g., 14:00 or 2PM): ");
        String title = promptNonEmpty(sc, "Title: ");

        Event e = new Event(date, time, title);
        EVENTS_LIST.add(e);
        System.out.println("Event added.");
        safeSaveEvents();
    }
    // This is to display events if the user has any. This precise organization will keep the users experience good.
    private static void viewEvents() {
        System.out.println();
        System.out.println("Your Events");
        if (EVENTS_LIST.isEmpty()) {
            System.out.println("You have no events to show. Add one first.");
            return;
        }
        EVENTS_LIST.stream()
                .sorted(Comparator.comparing((Event e) -> e.date)
                        .thenComparing(e -> e.time.toLowerCase(Locale.ROOT)))
                .forEach(System.out::println);
    }

    /**
     * This loads events from the CSV file if it has anything.
     * This will also use java.io and other methods like loops and parsing to make this work.
     * The code will also stay in tact if there is an error in the users message.
     */
    private static void safeLoadEvents() {
        if (!Files.exists(Paths.get(EVENTS_CSV_PATH)))
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(EVENTS_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                try {
                    Event e = Event.fromCsv(trimmed);
                    EVENTS_LIST.add(e);
                } catch (IllegalArgumentException parseErr) {
                    System.out.println("Line skipped in " + EVENTS_CSV_PATH + ": " + trimmed);
                }
            }
        } catch (IOException io) {
            System.out.println("Events could not be loaded: " + io.getMessage());
        }
    }
    // This will make sure to save all the events. This will make sure the file is closed and saved.
    private static void safeSaveEvents() {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(EVENTS_CSV_PATH)))) {
            for (Event e : EVENTS_LIST) {
                pw.println(e.toCsv());
            }
        } catch (IOException io) {
            System.out.println("Warning: Events could not be saved: " + io.getMessage());
        }
    }
    // This will make sure the user enters a valid date format for the event. It will demonstrate a loop until the right format is entered.
    private static LocalDate promptDate(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            try {
                return LocalDate.parse(s, DATE_F);
            } catch (Exception e) {
                System.out.println("Use (yyyy-MM-dd) Format");
            }
        }
    }
    // This will prompt the user if the field is left empty.
    private static String promptNonEmpty(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty");
        }
    }
    // This will keep all of the information nested and easier to read. It uses a simple model for the events with CSV.
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
            String[] p = splitCsv(line, 3);
            LocalDate d = LocalDate.parse(p[0], DATE_F);
            return new Event(d, p[1], p[2]);
        }

        @Override
        public String toString() {
            return date.format(DATE_F) + " " + time + " - " + title;
        }
    }

    /**
     * This uses a CSV splitter that when a file will return the expected fields.
     * It will change its output based on if fewer or more fields are expected and give different answers.
     * This is a demonstration of arrays and indexes.
     */
    private static String[] splitCsv(String line, int expected) {
        String[] raw = line.split(",", -1);
        String[] out = new String[expected];
        for (int i = 0; i < expected; i++) {
            out[i] = (i < raw.length) ? raw[i] : "";
        }
        return out;
    }
}