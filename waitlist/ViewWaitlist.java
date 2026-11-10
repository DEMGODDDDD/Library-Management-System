package waitlist;

import java.util.Queue;

public class ViewWaitlist {
    private final String bookId;
    private final String bookTitle;
    private final String userName;

    public ViewWaitlist(String bookId, String bookTitle, String userName) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.userName = userName;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + " | Title: " + bookTitle + " | Name: " + userName;
    }

    public static void execute(Queue<ViewWaitlist> waitlist) {
        System.out.println("\n--- CURRENT WAITLIST ---");
        if (waitlist.isEmpty()) {
            System.out.println("Waitlist is empty.");
        } else {
            int pos = 1;
            for (ViewWaitlist entry : waitlist) {
                System.out.println(pos + ". " + entry);
                pos++;
            }
        }
    }
}