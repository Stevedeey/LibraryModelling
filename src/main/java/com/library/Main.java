package com.library;

import com.library.model.Book;
import com.library.model.Person;
import com.library.services.serviceImplementation.LibrarianImplementation;
import com.library.utilities.BookDatabase;

import javax.naming.NoPermissionException;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws NoPermissionException {
        boolean showMenu = true;
        while(showMenu)
        {
            showMenu = Menu.main_menu();

        }
        Menu.performSearching();




    }
}
