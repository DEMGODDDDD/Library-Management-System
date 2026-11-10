package book;

import undo.undoLastAction;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class deleteBook {
    public static void execute(ArrayList<Book> books, Stack<undoLastAction.Action> history, Scanner scanner) {
        System.out.print("Enter Book ID to delete: ");
        String id = scanner.nextLine();
        Book target = null;
        int index = -1;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                target = books.get(i);
                index = i;
                break;
            }
        }
        if (target != null) {
            books.remove(index);
            history.push(new undoLastAction.Action("DELETE", target));
            if (FileManager.saveBooks(books)) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book deleted in memory, but could not be saved.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }
}