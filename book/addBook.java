package book;

import undo.undoLastAction;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class addBook {
    public static void execute(ArrayList<Book> books, Stack<undoLastAction.Action> history, Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Book ID cannot be empty.");
            return;
        }
        if (!id.matches("[0-9]+")) {
            System.out.println("Book ID must contain numbers only.");
            return;
        }
        for (Book book : books) {
            if (book.getId().equals(id)) {
                System.out.println("A book with that ID already exists.");
                return;
            }
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();
        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty.");
            return;
        }
        
        Book newBook = new Book(id, title, author, "Available", "None");
        books.add(newBook);
        history.push(new undoLastAction.Action("ADD", newBook));
        if (FileManager.saveBooks(books)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Book added in memory, but could not be saved.");
        }
    }
}