package book;

import undo.undoLastAction;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class updateBook {
    public static void execute(ArrayList<Book> books, Stack<undoLastAction.Action> history, Scanner scanner) {
        System.out.print("Enter Book ID to update: ");
        String id = scanner.nextLine();
        Book target = null;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                target = b;
                break;
            }
        }
        if (target != null) {
            Book oldState = new Book(target.getId(), target.getTitle(), target.getAuthor(), target.getStatus(), target.getBorrower());
            System.out.print("Enter new Title: ");
            target.setTitle(scanner.nextLine());
            System.out.print("Enter new Author: ");
            target.setAuthor(scanner.nextLine());
            history.push(new undoLastAction.Action("UPDATE", oldState));
            if (FileManager.saveBooks(books)) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book updated in memory, but could not be saved.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }
}