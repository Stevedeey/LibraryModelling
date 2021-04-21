package com.library;

import com.library.model.Book;
import com.library.model.Person;
import com.library.services.serviceImplementation.LibrarianImplementation;
import com.library.utilities.BookDatabase;
import com.library.utilities.BookDatabaseService;
import com.library.utilities.Extras;
import com.library.utilities.RecordDisplayClass;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {
    private static RecordDisplayClass<Book, Object, Object> display = new RecordDisplayClass<>();

    static List<Book> listOfAllBooks = BookDatabase.getBookList();


    public static boolean handleMainMenu() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        RecordDisplayClass display = new RecordDisplayClass();
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        System.out.println("*".repeat(90));
        System.out.println("*".repeat(10)+"WELCOME TO OUR LIBRARY MANAGEMENT SYSTEM"+"*".repeat(10));
        System.out.println("*".repeat(90));
        System.out.println("1) TO VIEW ALL BOOKS\n");
        System.out.println("a) TO ADD A NEW BOOK\n");
        System.out.println("2) TO SEARCH FOR A BOOK\n");
        System.out.println("3) TO PLACE A REQUEST FOR BOOKS \n");
        System.out.println("4) TO PLACE A REQUEST WITH(PRELOADED BORROWERS INFO) FOR QUICK DEMO\n");
        System.out.println("5) TO SEE THE LIST OF THE ISSUED BOOKS\n");
        System.out.println("d) TO RETURN BOOK(S)\n");
        System.out.println("6) TO EXIT\n");

        switch (sc.nextLine()) {
            case "1":
                display.displayBookInformation(listOfAllBooks);
                return true;
            case "a": LibrarianImplementation.addNewBook();
                return true;
            case "2": handleSearching();
                return true;
            case "3":
                handleRequestMaking();
                return true;
            case "4":
                handleSearchingVersion2();
                System.out.println("Press 5 to see the list of the issued book(s)");
                return true;
            case "5":
                processingRequests();
                return true;
            case "d":
                for (Person person: Person.myQue)
                {
                    System.out.println( LibrarianImplementation.returnBook(librarian,person));
                    System.out.println("\n\n");
                }
                return true;
            case "6":return false;
            default: return true;

        }
    }
    public  static boolean handleRequestMaking() throws NoPermissionException {
        Scanner scanner = new Scanner(System.in);
        Person person = null;
        System.out.println("\n**********************************************************************************************");
        System.out.println("WELCOME TO OUR BOOK REQUEST DASHBOARD");
        System.out.println("\n *****************************************************");
        System.out.println("Select your Role...\n 1.) TEACHER\n\n 2.) STUDENT\n\n q.) STOP MAKING REQUEST");
        switch (scanner.nextLine()) {

            case "1":
                String teacherStringId = "ENTER YOUR ID: ";
                int id = Extras.handlingNumberFormatException(teacherStringId, scanner);
                System.out.println("ENTER NAME: ");
                String name = scanner.nextLine();
                String role = "Teacher";
                person = new Person(id, name, role);
                System.out.println("Enter the title of the book you want to borrow");
                String booktitle = scanner.nextLine();
               person.makeRequest(booktitle);
                return true;

            case "2":
                String studentStringId = "ENTER YOUR ID: ";
                int studentId = Extras.handlingNumberFormatException(studentStringId, scanner);
                System.out.println("ENTER  NAME: ");
                String studentName = scanner.nextLine();
                System.out.println("Enter your class");
                String classs = scanner.nextLine();
                String studentRole = "Student";
                person = new Person(studentId, studentName, studentRole);
                person.setLevel(classs);
                System.out.println("Enter the title of the book you want to borrow");
                String booktitle2 = scanner.nextLine();
                person.makeRequest(booktitle2);
                return true;
            case "q":
                return false;
            default:
                System.out.println("Invalid Option");
                return true;
        }
    }
    public static void testing()
    {

    }

    public static void main(String[] args) {
        testing();
    }
    private static void processingRequests() throws NoPermissionException {
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>();

        Person.allRequests.forEach(datarequest-> {
            Person.myQue.forEach(person-> {
                if (datarequest.equals(person.getRequest()))
                    priorityQueue.add(person);

            });
            if(priorityQueue.size() == 1) LibrarianImplementation.issueBook(librarian, priorityQueue.peek());
            else LibrarianImplementation.issueBook(librarian, priorityQueue);
            priorityQueue.clear();
        });



        LibrarianImplementation.getBookIssuedRecord(librarian);
        Person.myQue.
                forEach(person -> LibrarianImplementation.returnBook(librarian,person));
//        for (Person person2 : Person.myQue) {
//            LibrarianImplementation.returnBook(librarian, person2);
//        }
  }
    private static  void handleSearchingVersion2() throws NoPermissionException {
        Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
        Person teacher1 = new Person(33, "Osereme Okoduwa", "Teacher");
        Person teacher2 = new Person(35, "Ifeanyichukwu Isaiah", "Teacher");
        Person teacher3 = new Person(43, "Gadibia Daro", "Teacher");
        Person student1 = new Person(21, "Ngozi Salami", "Student");
        Person student2 = new Person(19, "Bill Arizon", "Student");
        Person student3 = new Person(20, "Seth Mohammed", "Student");
        student1.setLevel("JS2");
        student2.setLevel("SS3");
        student3.setLevel("JS1");


        teacher1.makeRequest("The Epic Of Gilgamesh");
        teacher3.makeRequest("Oedipus the King");
        teacher2.makeRequest("The Epic Of Gilgamesh");
        student1.makeRequest("Oedipus the King");
        student2.makeRequest("The Epic Of Gilgamesh");
        student3.makeRequest("The Decameron");
        //        System.out.println(Person.allRequests);
//        System.out.println(Person.myQue);

        PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
        Person.allRequests.forEach(request -> {
            Person.myQue.forEach(person -> {
                if (request.equals(person.getRequest())) {
                    priorityQueue.add(person);
                }
            });

            if(priorityQueue.size() == 1) LibrarianImplementation.issueBook(librarian, priorityQueue.peek());
            else LibrarianImplementation.issueBook(librarian, priorityQueue);
            priorityQueue.clear();
        });
//        for (String request : Person.allRequests){
//            for (Person person : Person.myQue) {
//                if (request.equals(person.getRequest())) {
//                    priorityQueue.add(person);
//                }
//            }
//            if(priorityQueue.size() == 1) LibrarianImplementation.issueBook(librarian, priorityQueue.peek());
//            else LibrarianImplementation.issueBook(librarian, priorityQueue);
//            priorityQueue.clear();
//        }

      //  LibrarianImplementation.getBookIssuedRecord(librarian);

//        for (Person person : Person.myQue) {
//            LibrarianImplementation.returnBook(librarian, person);
//        }
//        Person.myQue
//                .forEach(person -> LibrarianImplementation.returnBook(librarian,person));

    }
    private static boolean handleSearching() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        BookDatabase bookDatabase  = new BookDatabase();
        System.out.println("**********************************************************************************************");
        System.out.println("*".repeat(10)+"WELCOME TO THE LIBRARY  SEARCHING DASHBOARD");
        System.out.println("******************************************************************************************");
        System.out.println("==============================================================================================");

        System.out.println("1) TO SEARCH BY CATEGORY");
        System.out.println("2) TO SEARCH BY TITLE");
        System.out.println("3) TO SEARCH BY COUNTRY");
        System.out.println("4) TO SEARCH BY LANGUAGE");
        System.out.println("5) EXIT");
        switch (sc.nextLine()) {
            case "1":
                System.out.println("Enter the category you want to search with..");
                String category = sc.nextLine();
               // BookDatabase.searchBookByCategory(category);
                bookDatabase.ServiceImplementationForSearch1().searchBookByParameter(category);
                return true;
            case "2":
                System.out.println("Enter the title you want to search with..");
                String title = sc.nextLine();
                //bookDatabase.ServiceImplementationForSearch2().searchBookByParameter(title);
                bookDatabase.getSearchByTitleAndOthersParameters().searchBookByParameter(title);
                return true;
            case "3":
                System.out.println("Enter the country you want to search with..");
                String country = sc.nextLine();
                //bookDatabase.ServiceImplementationForSearch3().searchBookByParameter(country);
                bookDatabase.getSearchByTitleAndOthersParameters().searchBookByParameter(country);
                return true;
            case "4":
                System.out.println("Enter the language you want to search with..");
                String language = sc.nextLine();
               // bookDatabase.ServiceImplementationForSearch4().searchBookByParameter(language);
                bookDatabase.getSearchByTitleAndOthersParameters().searchBookByParameter(language);
                return true;
            case "5":
                return false;
            default:
                return true;
        }



    }
    private  static  boolean  handleSort() throws NoPermissionException {
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
        switch (sc.nextLine()) {
            case "1":
                System.out.println("Enter the category you want to sort with..");
                String category = sc.nextLine();
                BookDatabase.sortBookBy(librarian,category);
                return true;
            case "2":
                System.out.println("Enter the title you want to sort with..");
                String title = sc.nextLine();
                BookDatabase.sortBookBy(librarian,title);
                return true;
            case "3":
                System.out.println("Enter the country you want to sort with..");
                String country = sc.nextLine();
                BookDatabase.sortBookBy(librarian,country);
                return true;
            case "4": return  false;
            default:
                return true;
        }
    }






}



