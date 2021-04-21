package com.library;

import com.library.model.Book;
import com.library.utilities.BookDatabase;
import com.library.utilities.BookDatabaseService;
import com.library.utilities.RecordDisplayClass;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Book> listOfAllBooks = BookDatabase.getBookList();

    public static void main(String[] args) throws NoPermissionException {
        boolean showMenu = true;
        while (showMenu) showMenu = Menu.handleMainMenu();


    }

}
