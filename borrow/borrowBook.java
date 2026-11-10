package borrow;

import book.Book;
import waitlist.ViewWaitlist;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class borrowBook {
    public static void execute(ArrayList<Book> books, Queue<ViewWaitlist> waitlist, Scanner scanner) {
        System.out.print("Enter Book ID to borrow: ");
        String id = scanner.nextLine();
        Book target = null;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                target = b;
                break;
            }
        }
        if (target != null) {
            String name = "";
            while (true) {
                System.out.print("Enter Name: ");
                name = scanner.nextLine().trim();
                if (name.matches("^[a-zA-Z\\s]+$") && !name.isEmpty()) {
                    break;
                }
                System.out.println("Invalid name. Numbers and special characters are not allowed.");
            }

            if (target.getStatus().equalsIgnoreCase("Available")) {
                target.setStatus("Borrowed");
                target.setBorrower(name);
                if (FileManager.saveBooks(books)) {
                    System.out.println("Book borrowed successfully by " + name + ".");
                } else {
                    System.out.println("Book borrowed in memory, but could not be saved.");
                }
            } else {
                waitlist.add(new ViewWaitlist(target.getId(), target.getTitle(), name));
                if (FileManager.saveWaitlist(waitlist)) {
                    System.out.println("Book is currently unavailable. " + name + " has been added to the waitlist queue.");
                } else {
                    System.out.println("Waitlist updated in memory, but could not be saved.");
                }
            }
        } else {
            System.out.println("Book not found.");
        }
    }
}