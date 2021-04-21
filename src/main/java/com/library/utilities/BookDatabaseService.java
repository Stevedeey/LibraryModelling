package com.library.utilities;

import com.library.model.Book;

import java.util.List;

public interface BookDatabaseService {
    List<Book> searchBookByParameter(String parameter);
}
