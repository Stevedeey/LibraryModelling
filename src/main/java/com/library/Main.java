package com.library;

import com.library.model.Person;
import com.library.services.serviceImplementation.LibrarianImplementation;

import javax.naming.NoPermissionException;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws NoPermissionException {


        /**
         * Creating the persons of different Cadre
         */
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        Person teacher1 = new Person(33, "Osereme Okoduwa", "Teacher");
        Person teacher2 = new Person(35, "Ifeanyichukwu Isaiah", "Teacher");
        Person teacher3 = new Person(43, "Gadibia Daro", "Teacher");


        //Three teachers making book requests
        LibrarianImplementation.acceptBookRequest(librarian, teacher1, "A Doll's House");
        LibrarianImplementation.acceptBookRequest(librarian, teacher2, "A Doll's House");
        LibrarianImplementation.acceptBookRequest(librarian, teacher3, "A Doll's House");

        // Teacher getting the title of the book requested for
        System.out.println(teacher1.getRequest());
        System.out.println(teacher2.getRequest());


        /**
         * First Come, First Served Implementation for teachers
         */
//        if(teacher1.getRequest().equalsIgnoreCase(teacher2.getRequest())
//                && teacher2.getRequest().equalsIgnoreCase(teacher3.getRequest())){
//            PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
//            priorityQueue.add(teacher1);
//            priorityQueue.add(teacher2);
//            priorityQueue.add(teacher3);
//
//            LibrarianImplementation.issueBook(librarian, priorityQueue);
//        }


        /**
         * New Student1 and and teacher4 make request for same book
         */
        Person teacher4 = new Person(30, "Commando Wizjid", "Teacher");
        Person student1 = new Person(21, "Idowu Akinwale", "Student");
        String message = student1.setLevel("SS3");

        LibrarianImplementation.acceptBookRequest(librarian, teacher4, "Oedipus the King");
        if (message.equals("successful"))
            LibrarianImplementation.acceptBookRequest(librarian, student1, "Oedipus the King");


        /**
         * Student1 and Teacher 4 request processed.
         */
        if (student1.getRequest().equalsIgnoreCase(teacher4.getRequest())) {
            PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
            if (message.equals("successful"))
                priorityQueue.add(student1);
            priorityQueue.add(teacher4);
            LibrarianImplementation.issueBook(librarian, priorityQueue);
        } else if (teacher4.getRequest() != null) LibrarianImplementation.issueBook(librarian, teacher4);
        else if (student1.getRequest() != null) LibrarianImplementation.issueBook(librarian, student1);

        /**
         * New Student2 and and student  make request for same book
         */
        Person student2 = new Person(19, "Henry Clinton", "Student");
        String mess = student2.setLevel("JSS3");
        if(mess.equals("successful")) LibrarianImplementation.acceptBookRequest(librarian, student2, "Oedipus the King");



        /**
         * Student2 and Student1 request processed.
         */
        if(student1.getRequest().equalsIgnoreCase(student2.getRequest())){
            PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
            if(mess.equals("successful")) priorityQueue.add(student2);
            if(message.equals("successful")) priorityQueue.add(student1);
          LibrarianImplementation.issueBook(librarian, priorityQueue);
        }else if(student1.getRequest() != null) LibrarianImplementation.issueBook(librarian, student1);
        else if(teacher2.getRequest() != null) LibrarianImplementation.issueBook(librarian, student2);


        /**
         * Display Book All Issued book Record
         */
        LibrarianImplementation.getBookIssuedRecord(librarian);

        /**
         * Return books
         */
        System.out.println("\n"+LibrarianImplementation.returnBook(librarian,teacher4));
        System.out.println("\n"+LibrarianImplementation.returnBook(librarian, teacher2));
        System.out.println("\n"+LibrarianImplementation.returnBook(librarian, student2));
        System.out.println("\n"+LibrarianImplementation.returnBook(librarian, student1));
        System.out.println("\n"+LibrarianImplementation.returnBook(librarian, teacher1));

        //Cre
//        Book newBook = LibrarianImplementation.createBook();
//        System.out.println(BookCollection.getBook(newBook.getTitle()));




    }
}
