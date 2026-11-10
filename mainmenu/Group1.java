package mainmenu;

import book.Book;
import undo.undoLastAction;
import storage.FileManager;
import book.addBook;
import book.updateBook;
import book.deleteBook;
import borrow.borrowBook;
import borrow.returnBook;
import waitlist.ViewWaitlist;
import search.searchById;
import search.searchByKeyword;
import sort.sortBooks;
import display.displayAllBooks;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.Scanner;

public class Group1 {
    public static void main(String[] args) {
        ArrayList<Book> books = FileManager.loadBooks();
        Stack<undoLastAction.Action> history = new Stack<>();
        Queue<ViewWaitlist> waitlist = FileManager.loadWaitlist();
        Scanner scanner = new Scanner(System.in);

        int choice = -1;
        do {
            System.out.println("\n==========LIBRARY MANAGEMENT SYSTEM==========");
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("4. Undo last action");
            System.out.println("5. Borrow book");
            System.out.println("6. Return book");
            System.out.println("7. View waitlist");
            System.out.println("8. Search by ID");
            System.out.println("9. Search by keyword");
            System.out.println("10. Sort books");
            System.out.println("11. Display all books");
            System.out.println("0. Exit");
            System.out.println("=============================================");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addBook.execute(books, history, scanner);
                        break;
                    case 2:
                        updateBook.execute(books, history, scanner);
                        break;
                    case 3:
                        deleteBook.execute(books, history, scanner);
                        break;
                    case 4:
                        undoLastAction.execute(books, history);
                        break;
                    case 5:
                        borrowBook.execute(books, waitlist, scanner);
                        break;
                    case 6:
                        returnBook.execute(books, waitlist, scanner);
                        break;
                    case 7:
                        ViewWaitlist.execute(waitlist);
                        break;
                    case 8:
                        searchById.execute(books, scanner);
                        break;
                    case 9:
                        searchByKeyword.execute(books, scanner);
                        break;
                    case 10:
                        sortBooks.execute(books);
                        break;
                    case 11:
                        displayAllBooks.execute(books);
                        break;
                    case 0:
                        System.out.println("Exiting system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Choose between 0 and 11.");
                }
            } else {
                if (scanner.hasNext()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                } else {
                    System.out.println("\nInput ended. Exiting system.");
                    break;
                }
            }
        } while (choice != 0);

        scanner.close();
    }
}