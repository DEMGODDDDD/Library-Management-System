package sort;

import book.Book;
import storage.FileManager;
import java.util.ArrayList;

public class sortBooks {
    public static void execute(ArrayList<Book> books) {
        if (books.size() <= 1) {
            System.out.println("Books are already sorted.");
            return;
        }
        mergeSort(books, 0, books.size() - 1);
        if (FileManager.saveBooks(books)) {
            System.out.println("Books sorted successfully by ID using Merge Sort.");
        } else {
            System.out.println("Books sorted in memory, but could not be saved.");
        }
    }

    private static void mergeSort(ArrayList<Book> books, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(books, left, mid);
            mergeSort(books, mid + 1, right);
            merge(books, left, mid, right);
        }
    }

    private static void merge(ArrayList<Book> books, int left, int mid, int right) {
        ArrayList<Book> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            if (books.get(i).getId().compareTo(books.get(j).getId()) <= 0) {
                temp.add(books.get(i));
                i++;
            } else {
                temp.add(books.get(j));
                j++;
            }
        }

        while (i <= mid) {
            temp.add(books.get(i));
            i++;
        }

        while (j <= right) {
            temp.add(books.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            books.set(left + k, temp.get(k));
        }
    }
}