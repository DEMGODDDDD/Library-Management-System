package storage;

import book.Book;
import waitlist.ViewWaitlist;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;

public class FileManager {
    private static final String BOOK_FILE = "books.txt";
    private static final String WAITLIST_FILE = "waitlist.txt";

    public static boolean saveBooks(ArrayList<Book> books) {
        try (PrintWriter writer = new PrintWriter(new File(BOOK_FILE))) {
            for (Book b : books) {
                writer.println(toCsv(Arrays.asList(
                        b.getId(), b.getTitle(), b.getAuthor(), b.getStatus(), b.getBorrower())));
            }
            if (writer.checkError()) {
                System.out.println("Error saving books: write failed.");
                return false;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(BOOK_FILE);
        if (!file.exists()) {
            books.add(new Book("101", "Java Programming", "John Doe", "Available", "None"));
            books.add(new Book("102", "Data Structures", "Jane Smith", "Available", "None"));
            books.add(new Book("103", "Database Systems", "Alan Turing", "Available", "None"));
            saveBooks(books);
            return books;
        }
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                try {
                    List<String> parts = parseCsv(scanner.nextLine());
                    if (parts.size() != 5) {
                        System.out.println("Skipping invalid book record on line " + lineNumber + ".");
                        continue;
                    }
                    books.add(new Book(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid book record on line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public static boolean saveWaitlist(Queue<ViewWaitlist> waitlist) {
        try (PrintWriter writer = new PrintWriter(new File(WAITLIST_FILE))) {
            for (ViewWaitlist entry : waitlist) {
                writer.println(toCsv(Arrays.asList(
                        entry.getBookId(), entry.getBookTitle(), entry.getUserName())));
            }
            if (writer.checkError()) {
                System.out.println("Error saving waitlist: write failed.");
                return false;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving waitlist: " + e.getMessage());
            return false;
        }
    }

    public static Queue<ViewWaitlist> loadWaitlist() {
        Queue<ViewWaitlist> waitlist = new LinkedList<>();
        File file = new File(WAITLIST_FILE);
        if (!file.exists()) {
            return waitlist;
        }
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                try {
                    List<String> parts = parseCsv(scanner.nextLine());
                    if (parts.size() != 3) {
                        System.out.println("Skipping invalid waitlist record on line " + lineNumber + ".");
                        continue;
                    }
                    waitlist.add(new ViewWaitlist(parts.get(0), parts.get(1), parts.get(2)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid waitlist record on line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading waitlist: " + e.getMessage());
        }
        return waitlist;
    }

    private static String toCsv(List<String> fields) {
        StringBuilder line = new StringBuilder();
        for (String field : fields) {
            if (line.length() > 0) {
                line.append(',');
            }
            if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
                line.append('"').append(field.replace("\"", "\"\"")).append('"');
            } else {
                line.append(field);
            }
        }
        return line.toString();
    }

    private static List<String> parseCsv(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;
        boolean closedQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (quoted) {
                if (current == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        field.append('"');
                        i++;
                    } else {
                        quoted = false;
                        closedQuote = true;
                    }
                } else {
                    field.append(current);
                }
            } else if (current == ',') {
                fields.add(field.toString());
                field.setLength(0);
                closedQuote = false;
            } else if (current == '"' && field.length() == 0 && !closedQuote) {
                quoted = true;
            } else if (closedQuote) {
                throw new IllegalArgumentException("unexpected character after closing quote");
            } else if (current == '"') {
                throw new IllegalArgumentException("quote inside an unquoted field");
            } else {
                field.append(current);
            }
        }
        if (quoted) {
            throw new IllegalArgumentException("unterminated quoted field");
        }
        fields.add(field.toString());
        return fields;
    }
}