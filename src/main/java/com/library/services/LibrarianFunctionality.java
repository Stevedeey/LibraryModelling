package com.library.services;

import com.library.model.Book;
import com.library.model.Person;

import java.util.Map;

public interface LibrarianFunctionality {

    public static String addNewBook() {
        return null;
    }

    public static Map<Integer, String> getBookIssuedRecord() {
        return null;
    }

    public static String returnBook(Person librarian, Person student) {
        return null;
    }

    public static <T> String issueBook(Person librarian, T object) {
        return null;
    }

    public static void setUpBookRequest(Person librarian, Person person, String title, String author) { }

}
