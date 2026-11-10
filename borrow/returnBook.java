package borrow;

import book.Book;
import waitlist.ViewWaitlist;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class returnBook {
    public static void execute(ArrayList<Book> books, Queue<ViewWaitlist> waitlist, Scanner scanner) {
        System.out.print("Enter Book ID to return: ");
        String id = scanner.nextLine();
        Book target = null;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                target = b;
                break;
            }
        }
        if (target != null) {
            if (!target.getStatus().equalsIgnoreCase("Borrowed")) {
                System.out.println("This book is not currently borrowed.");
                return;
            }

            ViewWaitlist nextEntry = null;
            for (ViewWaitlist entry : waitlist) {
                if (entry.getBookId().equals(id)) {
                    nextEntry = entry;
                    break;
                }
            }

            if (nextEntry == null) {
                target.setStatus("Available");
                target.setBorrower("None");
            } else {
                waitlist.remove(nextEntry);
                target.setStatus("Borrowed");
                target.setBorrower(nextEntry.getUserName());
                System.out.println("Book returned and assigned to " + nextEntry.getUserName() + ".");
            }
            boolean booksSaved = FileManager.saveBooks(books);
            boolean waitlistSaved = FileManager.saveWaitlist(waitlist);
            if (!booksSaved || !waitlistSaved) {
                System.out.println("Return processed in memory, but persistence failed; book and waitlist data may be inconsistent.");
                return;
            }
            if (nextEntry == null) {
                System.out.println("Book returned successfully.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }
}