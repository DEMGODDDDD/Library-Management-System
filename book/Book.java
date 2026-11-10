package book;

public class Book {
    private String id;
    private String title;
    private String author;
    private String status;
    private String borrower;

    public Book(String id, String title, String author, String status, String borrower) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.borrower = borrower;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getBorrower() { return borrower; }
    public void setBorrower(String borrower) { this.borrower = borrower; }

    public String toString() {
        String base = "ID: " + id + " | Title: " + title + " | Author: " + author + " | Status: " + status;
        if (status.equalsIgnoreCase("Borrowed")) {
            base += " | Borrowed By: " + borrower;
        }
        return base;
    }
}