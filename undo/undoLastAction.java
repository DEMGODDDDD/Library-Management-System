package undo;

import book.Book;
import storage.FileManager;
import java.util.ArrayList;
import java.util.Stack;

public class undoLastAction {

    public static class Action {
        private String type;
        private Book book;

        public Action(String type, Book book) {
            this.type = type;
            this.book = book;
        }

        public String getType() { return type; }
        public Book getBook() { return book; }
    }

    public static void execute(ArrayList<Book> books, Stack<Action> history) {
        if (history.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }
        Action lastAction = history.pop();
        String type = lastAction.getType();
        Book book = lastAction.getBook();

        if (type.equals("ADD")) {
            books.removeIf(b -> b.getId().equals(book.getId()));
            System.out.println("Undo: Removed added book " + book.getTitle());
        } else if (type.equals("DELETE")) {
            books.add(book);
            System.out.println("Undo: Restored deleted book " + book.getTitle());
        } else if (type.equals("UPDATE")) {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId().equals(book.getId())) {
                    books.set(i, book);
                    break;
                }
            }
            System.out.println("Undo: Reverted changes to book " + book.getTitle());
        }
        if (!FileManager.saveBooks(books)) {
            System.out.println("Undo applied in memory, but could not be saved.");
        }
    }
}