package search;

import book.Book;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class searchByKeyword {
    public static void execute(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Enter keyword (Title/Author): ");
        String keyword = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }
        boolean found = false;
        System.out.println("\n--- SEARCH RESULTS ---");
        for (Book b : books) {
            if (b.getTitle().toLowerCase(Locale.ROOT).contains(keyword)
                    || b.getAuthor().toLowerCase(Locale.ROOT).contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching books found.");
        }
    }
}