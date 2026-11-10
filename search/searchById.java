package search;

import book.Book;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

public class searchById {
    public static void execute(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        books.sort(Comparator.comparing(Book::getId));

        System.out.print("Enter Book ID to search: ");
        String searchId = scanner.nextLine();

        int left = 0;
        int right = books.size() - 1;
        boolean found = false;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int res = books.get(mid).getId().compareTo(searchId);

            if (res == 0) {
                System.out.println("Found: " + books.get(mid));
                found = true;
                break;
            }
            if (res < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (!found) {
            System.out.println("Book ID not found.");
        }
    }
}