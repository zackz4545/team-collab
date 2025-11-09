import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Book {
    private static int NEXT_ID = 1;
    private final int id;
    private String title;
    private String author;
    private boolean issued;

    Book(String title, String author) {
        this.id = NEXT_ID++;
        this.title = title.trim();
        this.author = author.trim();
        this.issued = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return issued; }
    public void issue() { this.issued = true; }
    public void giveBack() { this.issued = false; }

    @Override
    public String toString() {
        return String.format("#%d | %s — %s %s",
                id, title, author, issued ? "[Issued]" : "[Available]");
    }
}

public class LibraryModule {
    private static final List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        seedSampleData(); // gives you two starter books for screenshots
        SwingUtilities.invokeLater(LibraryModule::runGUI);
    }

    private static void runGUI() {
        int choice;
        do {
            choice = printMenu();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> searchBooks();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> displayAvailableBooks();
                case 6 -> displayAllBooks();
                case 0 -> JOptionPane.showMessageDialog(null, "Exiting Library Module...");
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int printMenu() {
        String menu = """
                ===== LIBRARY MODULE (Amaan Zafar) =====
                1. Add Book
                2. Search Books (by title or author)
                3. Issue Book (by ID or Title)
                4. Return Book (by ID or Title)
                5. Display Available Books
                6. Display All Books
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

    private static void addBook() {
        String title = JOptionPane.showInputDialog("Enter title:");
        if (title == null) return;
        String author = JOptionPane.showInputDialog("Enter author:");
        if (author == null) return;

        title = title.trim();
        author = author.trim();

        if (title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Title/Author cannot be empty.");
            return;
        }
        books.add(new Book(title, author));
        JOptionPane.showMessageDialog(null, "Book added successfully!");
    }

    private static void searchBooks() {
        String q = JOptionPane.showInputDialog("Search text (title or author):");
        if (q == null || q.trim().isEmpty()) return;

        q = q.trim().toLowerCase();
        boolean found = false;
        StringBuilder sb = new StringBuilder();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(q) || b.getAuthor().toLowerCase().contains(q)) {
                sb.append(b).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No matching books found.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void issueBook() {
        Book b = findBookPrompt("issue");
        if (b == null) return;

        if (b.isIssued()) {
            JOptionPane.showMessageDialog(null, "That book is already issued.");
        } else {
            b.issue();
            JOptionPane.showMessageDialog(null, "Book issued successfully!");
        }
    }

    private static void returnBook() {
        Book b = findBookPrompt("return");
        if (b == null) return;

        if (!b.isIssued()) {
            JOptionPane.showMessageDialog(null, "That book was not issued.");
        } else {
            b.giveBack();
            JOptionPane.showMessageDialog(null, "Book returned successfully!");
        }
    }

    private static void displayAvailableBooks() {
        boolean any = false;
        StringBuilder sb = new StringBuilder();
        for (Book b : books) {
            if (!b.isIssued()) {
                sb.append(b).append("\n");
                any = true;
            }
        }
        if (!any) sb.append("No available books.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void displayAllBooks() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Library is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Book b : books) sb.append(b).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ----- helpers -----
    private static Book findBookPrompt(String actionWord) {
        String[] options = {"By ID", "By Title"};
        int opt = JOptionPane.showOptionDialog(null,
                "Find book to " + actionWord + " by:",
                "Find Book",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (opt == -1) return null; // cancel pressed

        if (opt == 0) { // by ID
            String idStr = JOptionPane.showInputDialog("Enter book ID:");
            if (idStr == null) return null;
            try {
                int id = Integer.parseInt(idStr.trim());
                for (Book b : books) if (b.getId() == id) return b;
                JOptionPane.showMessageDialog(null, "No book with ID " + id + ".");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID format.");
            }
        } else if (opt == 1) { // by title
            String t = JOptionPane.showInputDialog("Enter title:");
            if (t == null) return null;
            for (Book b : books) if (b.getTitle().equalsIgnoreCase(t.trim())) return b;
            JOptionPane.showMessageDialog(null, "No book with title \"" + t + "\".");
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(prompt);
            if (s == null) return 0;
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a number.");
            }
        }
    }

    private static void seedSampleData() {
        books.add(new Book("Atomic Habits", "James Clear"));
        books.add(new Book("Clean Code", "Robert C. Martin"));
    }
}
