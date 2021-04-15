package com.library;

import com.library.model.Book;
import com.library.model.Person;
import com.library.services.serviceImplementation.LibrarianImplementation;
import com.library.utilities.BookDatabase;
import com.library.utilities.RecordDisplayClass;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Menu {
    private static RecordDisplayClass<Book, Object, Object> display = new RecordDisplayClass<>();
    static List<Person> listOfPersons = new ArrayList<>();
    static List<Book> listOfAllBooks = BookDatabase.getBookList();

    public static boolean main_menu() throws NoPermissionException {
        Scanner scanner = new Scanner(System.in);
        String studlevel = "";
        Person teacher4 = null;
        Person student1 = null;
        Person student2 = null;
        String studentL = "";

        /**
         * GETTING STARTED WITH THREE TEACHERS AND
         */
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        Person teacher1 = new Person(33, "Osereme Okoduwa", "Teacher");
        Person teacher2 = new Person(35, "Ifeanyichukwu Isaiah", "Teacher");
        Person teacher3 = new Person(43, "Gadibia Daro", "Teacher");
        listOfPersons.add(teacher1);
        listOfPersons.add(teacher2);
        listOfPersons.add(teacher3);


        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n**********************************************************************************************");
        System.out.println("WELCOME TO THE LIBRARY DASHBOARD");
        System.out.println("\n ******************************************************************************************");
        System.out.println("TO SEE VARIOUS IMPLEMENTATION OF THIS LIBRARY SYSTEM, PERFORM OPERATIONS IN THE ORDER BELOW AND OBSERVE CLOSELY...");
        System.out.println("==============================================================================================");
        System.out.println("1) TO ADD A NEW BOOK\n");
        System.out.println("2) TO CHECK THE LIBRARY'S BOOK\n");


        System.out.println("3) To MAKE THREE TEACHERS PLACE A REQUEST FOR A BOOK" +
                              " AND ISSUE THE BOOK TO A TEACHER FIRST COME, FIRST SERVED rule \n ");

        System.out.println("4)TO ADD ONE MORE TEACHER (SAY TEACHER 4) AND ADD ANOTHER STUDENT TOO\n" +
                             " \t AND MAKE THE TEACHER 4 AND STUDENT 1 PLACE A REQUEST FOR A BOOK\n" +
                                "\t ISSUE THE BOOK TO THE STUDENT BASED ON PRIORITY\n");

        System.out.println("5) TO ADD A(JUNIOR) STUDENT SAY STUDENT 2 AND MAKE A REQUEST\n" +
                             "\t TO ISSUE THE BOOK TO THE SENIOR STUDENT BASED ON PRIORITY\n");

        System.out.println("6) TO PRINT ALL THE RECORD OF THE ISSUED BOOK\n");
        System.out.println("7) TO RETURN BOOKS\n");
        System.out.println("8) TO QUIT\n");

        System.out.println();
        switch (scanner.next()) {
            case "1":
                LibrarianImplementation.addNewBook();
            case "2":
                display.displayBookInformation(listOfAllBooks);
                return true;


            case "3":
                //Processing three teachers
                System.out.println(".....PROCESSING BOOK ISSUANCE FOR THREE TEACHERS.............................................");
                LibrarianImplementation.makeBookRequest(librarian, teacher1, "Things Fall Apart");
                LibrarianImplementation.makeBookRequest(librarian, teacher2, "Things Fall Apart");
                LibrarianImplementation.makeBookRequest(librarian, teacher3, "Things Fall Apart");
                if (teacher1.getRequest().equalsIgnoreCase(teacher2.getRequest()) && teacher2.getRequest().equalsIgnoreCase(teacher3.getRequest())) {
                    PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
                    priorityQueue.add(teacher1);
                    priorityQueue.add(teacher2);
                    priorityQueue.add(teacher3);
                    LibrarianImplementation.issueBook(librarian, priorityQueue);
                }
                //display.displayPersons(listOfPersons);
                System.out.println("\nBook requested for by " + teacher1.getName() + "is: " + teacher1.getRequest());
                System.out.println("\nBook requested for by " + teacher2.getName() + "is: " + teacher2.getRequest());
                System.out.println("\nBook requested for by " + teacher3.getName() + "is: " + teacher3.getRequest());
                System.out.println(".....DONE!!.............................................");
                return true;

            case "4":
                //PROCESSING FOR A TEACHER AND A STUDENT
                teacher4 = new Person(30, "Commando Wizjid", "Teacher");
                student1 = new Person(21, "Idowu Akinwale", "Student");
                studlevel = student1.setLevel("SS3");
                LibrarianImplementation.makeBookRequest(librarian, teacher4, "A Doll's House");
                if (studlevel.equals("successful"))
                    LibrarianImplementation.makeBookRequest(librarian, student1, "A Doll's House");
                System.out.println(".....PROCESSING REQUEST.............................................");
                System.out.println("\nBook requested for by " + teacher4.getName() + "is: " + teacher4.getRequest());
                System.out.println("Book requested for by " + student1.getName() + "is: " + student1.getRequest());
                System.out.println(".....BOOK ISSUANCE STAGE.............................................");
                if (student1.getRequest().equalsIgnoreCase(teacher4.getRequest())) {
                    PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
                    if (studlevel.equals("successful"))
                        priorityQueue.add(student1);
                    priorityQueue.add(teacher4);
                    LibrarianImplementation.issueBook(librarian, priorityQueue);
                } else if (teacher4.getRequest() != null) LibrarianImplementation.issueBook(librarian, teacher4);
                else if (student1.getRequest() != null) LibrarianImplementation.issueBook(librarian, student1);
                System.out.println(".....DONE!!!.............................................");
                return true;

//
            case "5":
                //PROCESSING FOR STUDENTS (SENIOR VS JUNIOR)
                student2 = new Person(19, "Solanke Shodipe", "Student");
                student1 = new Person(21, "Idowu Akinwale", "Student");
               String studlevel1 = student1.setLevel("SS3");
                String mess = student2.setLevel("JSS3");

                if (studlevel1.equals("successful"))
                LibrarianImplementation.makeBookRequest(librarian, student1, "Things Fall Apart");
                System.out.println(".....PROCESSING REQUEST.............................................");
                System.out.println("\nBook requested for by " + student1.getName() + "is: " + student1.getRequest()+" of class "+student1.getLevel());
                System.out.println("Book requested for by " + student2.getName() + "is: " + student2.getRequest()+" of class "+student2.getLevel());
                System.out.println(".....BOOK ISSUANCE STAGE.............................................");
                if (mess.equals("successful"))
                    LibrarianImplementation.makeBookRequest(librarian, student2, "Things Fall Apart");
                if (student1.getRequest().equalsIgnoreCase(student2.getRequest())) {
                    PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
                    if (mess.equals("successful")) priorityQueue.add(student2);
                    if (studlevel1.equals("successful")) priorityQueue.add(student1);
                    LibrarianImplementation.issueBook(librarian, priorityQueue);
                } else if (student1.getRequest() != null) LibrarianImplementation.issueBook(librarian, student1);
                else if (teacher2.getRequest() != null) LibrarianImplementation.issueBook(librarian, student2);
                System.out.println(".....DONE!!!.............................................");
                return true;

            case "6":
                LibrarianImplementation.getBookIssuedRecord(librarian);
                return true;
            case "7":
            try {
                  System.out.println("\n" + LibrarianImplementation.returnBook(librarian, teacher4));
                System.out.println("\n" + LibrarianImplementation.returnBook(librarian, teacher2));
                System.out.println("\n" + LibrarianImplementation.returnBook(librarian, student2));
                System.out.println("\n" + LibrarianImplementation.returnBook(librarian, student1));
                System.out.println("\n" + LibrarianImplementation.returnBook(librarian, teacher1));
            }
            catch (Exception ex)
            {
                System.out.println("You need to borrow book");
            }

                return true;
            case "8":
                return false;

            default:
                return true;
        }
    }

    public static boolean performSearching() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n**********************************************************************************************");
        System.out.println("WELCOME TO THE LIBRARY  SEARCHING DASHBOARD");
        System.out.println("\n ******************************************************************************************");
        System.out.println("==============================================================================================");

        System.out.println("1) To search by category");
        System.out.println("2) To search by title");
        System.out.println("3) Exit");
        switch (sc.next()) {
            case "1":
                System.out.println("Enter the category you want to search with..");
                String category = sc.next();
                BookDatabase.searchBookByCategory(category);
                return true;
            case "2":
                System.out.println("Enter the title you want to search with..");
                String category2 = sc.next();
                BookDatabase.searchBookByCategory(category2);
                return true;
            case "3":
                return false;
            default:
                return true;
        }



    }
    public  static  boolean  handleSort() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n**********************************************************************************************");
        System.out.println("WELCOME TO THE LIBRARY  SEARCHING DASHBOARD");
        System.out.println("\n ******************************************************************************************");
        System.out.println("==============================================================================================");

        System.out.println("1) To search by category");
        System.out.println("2) To search by title");
        System.out.println("3) To search by country");
        System.out.println("4) Exit");
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        switch (sc.next()) {
            case "1":
                System.out.println("Enter the category you want to sort with..");
                String category = sc.next();
                BookDatabase.sortBookBy(librarian,category);
                return true;
            case "2":
                System.out.println("Enter the title you want to sort with..");
                String title = sc.next();
                BookDatabase.sortBookBy(librarian,title);
                return true;
            case "3":
                System.out.println("Enter the country you want to sort with..");
                String country = sc.next();
                BookDatabase.sortBookBy(librarian,country);
                return true;
            case "4": return  false;
            default:
                return true;
        }
    }
}



