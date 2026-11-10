package display;

import book.Book;
import java.util.ArrayList;

public class displayAllBooks {
    public static void execute(ArrayList<Book> books) {
        System.out.println("\n--- BOOK CATALOG ---");
        if (books.isEmpty()) {
            System.out.println("Catalog is empty.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }
}