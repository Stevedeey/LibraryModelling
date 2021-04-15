package com.library.utilities;

import enums.BookCategories;
import enums.Sorting;
import com.library.model.Book;
import com.library.model.Person;

import java.util.*;

public class BookDatabase {


    private static List<Book> bookList = JSONmapper.myListOfBooks;

    public static List<Book> getBookList() {
        return bookList;
    }

    public static int bookId = bookList.size();
    private static RecordDisplayClass<Book, Object, Object> display = new RecordDisplayClass<>();

    public static void updateBooks(Book book) {
        List<Book> allBooks = bookList;
        allBooks.add(book);
        bookList = allBooks;
    }

    public static List<Book> sortBookBy(Person person, String params) {
        if (person.getRole().equalsIgnoreCase("librarian")) {
            enums.Sorting sort = validateInputsToSort(params);
            if (sort == null) return null;

            List sortedBook = bookList;
            Comparator<Book> nameSorter = null;

            switch (sort) {
                case PAGE:
                    nameSorter = (a, b) -> {
                        if (a.getPages() > b.getPages()) return 1;
                        else return -1;
                    };
                    break;
                case YEAR:
                    nameSorter = (a, b) -> {
                        if (a.getYear() > b.getYear()) return 1;
                        else return -1;
                    };
                    break;
                case AUTHOR:
                    nameSorter = (a, b) -> a.getAuthor().compareToIgnoreCase(b.getAuthor());
                    break;
                case COUNTRY:
                    nameSorter = (a, b) -> a.getCountry().compareToIgnoreCase(b.getCountry());
                    break;
                case CATEGORY:
                    nameSorter = (a, b) -> a.getCategory().compareToIgnoreCase(b.getCategory());
                    break;
            }

            Collections.sort(sortedBook, nameSorter);
            display.displayBookInformation(sortedBook);
            return sortedBook;
        } else {
            System.out.println("You don't have access to this resource");
            return null;
        }
    }

    public static List<Book> searchBookByCategory(String category) {
        BookCategories categories;
        List searchItemLists = new ArrayList();

        try {
            categories = BookCategories.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException error) {
            System.out.println("Enter valid Category:\n e.g FICTION, HISTORY, JOURNALS\n" +
                    "\t\tLITERATURE AND PROGRAMMING");
            return null;
        }

        switch (categories) {
            case FICTION:
            case HISTORY:
            case JOURNALS:
            case LITERATURE:
            case PROGRAMMING:

                for (Book book : bookList) {
                    if (book.getCategory().equalsIgnoreCase(category)) {
                        searchItemLists.add(book);
                    }
                }

                break;
        }

        display.displayBookInformation(searchItemLists);
        return searchItemLists;

    }

    public static List<Book> searchBookByTitle(String title) {
        List searchItemLists = new ArrayList();

        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                searchItemLists.add(book);
            }
        }

        display.displayBookInformation(searchItemLists);

        return searchItemLists;
    }

    public static List<Book> searchBookByCountryName(String country) {
        List searchItemLists = new ArrayList();

        for (Book book : bookList) {
            if (book.getCountry().equalsIgnoreCase(country)) {
                searchItemLists.add(book);
            }
        }

        display.displayBookInformation(searchItemLists);

        return searchItemLists;
    }

    public static List<Book> searchBookByLanguage(String language) {
        List searchItemLists = new ArrayList();

        for (Book book : bookList) {
            if (book.getLanguage().equalsIgnoreCase(language)) {
                searchItemLists.add(book);
            }
        }

        display.displayBookInformation(searchItemLists);

        return searchItemLists;
    }

    public static Comparator<Book> comparator = new Comparator<Book>() {
        public int compare(Book book, Book another) {
            return book.getTitle().compareTo(another.getTitle());
        }
    };

    public static List<Book> getBook(String title) {
        List borrowBook = new ArrayList();

        Book book = new Book(title);
        Collections.sort(bookList, comparator);

        int index = Collections.binarySearch(bookList, book, comparator);
        if (index >= 0) {
            book = bookList.get(index);
            borrowBook.add(book);
        }

        display.displayBookInformation(borrowBook);

        return borrowBook;

    }


    private static Sorting validateInputsToSort(String data) {
        Sorting sort;
        try {
            sort = Sorting.valueOf(data.toUpperCase());
        } catch (IllegalArgumentException error) {
            System.out.println("Enter valid Search:\n e.g AUTHOR, PAGE, CATEGORY, COUNTRY ,\n" +
                    "YEAR AND STUDENT");
            return null;
        }

        return sort;
    }

    @Override
    public String toString() {
        String bookStore = "BookStore\n";

        for (Book book : bookList) {
            bookStore += book.toString();
        }

        display.displayBookInformation(bookList);

        return bookStore;
    }

}

