package com.library.utilities;

import com.library.enums.BookCategories;
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


// Lambda method for search by three criteria

    BookDatabaseService searchByTitleAndOthersParameters = (params) -> {
        List searchItemsList = new ArrayList();
        long dataTitle = bookList.stream().
                filter(item -> item.getTitle().equalsIgnoreCase(params)).count();
        long dataLanguage = bookList.stream().
                filter(item -> item.getLanguage().equalsIgnoreCase(params)).count();
        if (dataTitle > 0) {
            bookList.stream()
                    .filter(item -> item.getTitle().equalsIgnoreCase(params))
                    .forEach(book -> searchItemsList.add(book));
        } else if (dataLanguage > 0)
            bookList.stream()
                    .filter(book -> book.getLanguage().equalsIgnoreCase(params))
                    .forEach(book -> searchItemsList.add(book));
        else {
            bookList.stream()
                    .filter(item -> item.getCountry().equalsIgnoreCase(params))
                    .forEach(book -> searchItemsList.add(book));
        }
        display.displayBookInformation(searchItemsList);
        return searchItemsList;
    };

    //The getter for searchingByDifferentParameters lambda method
    public BookDatabaseService getSearchByTitleAndOthersParameters() {
        return searchByTitleAndOthersParameters;
    }

    //Implementation for Search by category
    BookDatabaseService ServiceImplementationForSearch1 = (parameter) -> {
        BookCategories categories = null;
        List searchItemLists = new ArrayList();

        try {
            categories = BookCategories.valueOf(parameter.toUpperCase());
        } catch (IllegalArgumentException error) {
            System.out.println("Enter valid parameter:\n e.g FICTION, HISTORY, JOURNALS\n" +
                    "\t\tLITERATURE AND PROGRAMMING");
            return null;
        }
        switch (categories) {
            case FICTION:
            case HISTORY:
            case JOURNALS:
            case LITERATURE:
            case PROGRAMMING:

                bookList.forEach(book -> {
                    if (book.getCategory().equalsIgnoreCase(parameter)) {
                        searchItemLists.add(book);
                    }
                });
                break;
        }
        display.displayBookInformation(searchItemLists);
        return searchItemLists;
    };

    /**
     * Getter for the Lamda Method 1
     *
     * @return
     */
    public BookDatabaseService ServiceImplementationForSearch1() {
        return ServiceImplementationForSearch1;
    }


    //Implementation 4 Language
//    BookDatabaseService ServiceImplementationForSearch4 = (parameter) -> {
//
//        List searchItemLists = new ArrayList();
//        bookList.forEach(book ->
//        {
//            if (book.getLanguage().equalsIgnoreCase(parameter)) searchItemLists.add(book);
//        });
//
//        display.displayBookInformation(searchItemLists);
//
//        return searchItemLists;
//    };
//
//    /**
//     * Getter for the Lamda Method 4
//     *
//     * @return
//     */
//    public BookDatabaseService ServiceImplementationForSearch4() {
//        return ServiceImplementationForSearch4;
//    }

    public static Comparator<Book> comparator = new Comparator<Book>() {
        public int compare(Book book, Book another) {
            return book.getTitle().compareTo(another.getTitle());
        }
    };
    //Lambda Method for GetBook
    BookDatabaseService getBook = parameter -> {
        List borrowBook = new ArrayList();

        Book book = new Book(parameter);
        Collections.sort(bookList, comparator);

        int index = Collections.binarySearch(bookList, book, comparator);
        if (index >= 0) {
            book = bookList.get(index);
            borrowBook.add(book);
        }

        display.displayBookInformation(borrowBook);

        return borrowBook;
    };

    // The Getter for the get book Lambda above
    public BookDatabaseService getGetBook() {
        return getBook;
    }

    /**
     * The method updates the book list when a new book is added
     *
     * @param book
     */
    public static void updateBooks(Book book) {
        List<Book> allBooks = bookList;
        allBooks.add(book);
        bookList = allBooks;
        System.out.println("You just added: " + book.getTitle() +
                " " + book.getCategory());
    }

    /**
     * This method ensures that the right data is enterd for sorting
     *
     * @param data
     * @return
     */
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

    /**
     * This method sorts the list based on the parameter passed in
     *
     * @param person
     * @param params
     * @return
     */
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

