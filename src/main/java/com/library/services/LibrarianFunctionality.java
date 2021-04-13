package com.library.services;

import com.library.model.Book;

public interface LibrarianFunctionality {

    Book createBook();
    void createRecord();
    String updateRecord(int x);
    String issueBook();
    boolean deleteRecord(int x);

}
