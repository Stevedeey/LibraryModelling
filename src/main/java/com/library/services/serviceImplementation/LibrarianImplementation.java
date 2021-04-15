package com.library.services.serviceImplementation;

import com.library.model.Book;
import com.library.model.Person;
import com.library.utilities.BookDatabase;
import com.library.utilities.RecordDisplayClass;
import com.library.utilities.Records;

import java.text.DateFormat;
import java.util.*;

public class LibrarianImplementation {
    private static RecordDisplayClass<Person, Book, Records> display = new RecordDisplayClass<>();
    private static Integer defaultDay = 7;

    public static Book addNewBook() {
        Scanner scanner = new Scanner(System.in);
        char response = ' ';

        System.out.println("ENTER BOOK CATEGORY: \n");
        String category = scanner.next();

        System.out.println("ENTER BOOK TITLE: \n");
        String title = scanner.next();

        System.out.println("BOOK AUTHOR: \n");
        String author = scanner.next();

        String promptYearCreated = "ENTER YEAR PUBLISHED \n";
        int year = handlingNumberFormatException(promptYearCreated, scanner);

        String promptNoOfPages = "ENTER NUMBER OF PAGES \n";
        int pages = handlingNumberFormatException(promptNoOfPages, scanner);

        System.out.println("BOOK LANGUAGE: \n");
        String language = scanner.next();

        System.out.println("BOOK COUNTRY: \n");
        String country = scanner.next();

        System.out.println("BOOK IMAGE LINK: \n");
        String imageLink = scanner.next();

        System.out.println("BOOK LINK: \n");
        String link = scanner.next();

        String promptNoOfCopies = "ENTER NUMBER OF COPIES \n";
        int numOfCopies = handlingNumberFormatException(promptNoOfCopies, scanner);

        Book newBook = new Book(BookDatabase.getBookList().size() + 1, numOfCopies, author, country, category, imageLink,
                language, link, pages, title, year);

        BookDatabase.updateBooks(newBook);
        return newBook;
    }

    /**
     * This method compels the user to enter correct values where integers are needed.
     * @param prompt shows warning message
     * @param sc1 collects values from the user
     * @return
     */
    private static int handlingNumberFormatException(String prompt, Scanner sc1) {
        int intInput = 0;
        while (true) {
            System.out.println(prompt);
            String stringInput = sc1.next();
            try {
                intInput = Integer.parseInt(stringInput);
                break;
            } catch (NumberFormatException exe) {
                System.out.println("You must enter a number");
            }
        }
        return intInput;
    }

    /**
     * This method displays the info of the person(s) the book is issued to
     * @param person
     * @param book
     */
    public static void setBookIssuedRecord(Person person, List<Book> book) {
        String records = "\n";
        if (person.getRole().equalsIgnoreCase("Teacher"))
            records += "Staff ID: " + person.getId() + "\n";
        else records += "Student ID: " + person.getId() + "\n";


        records += "Name: " + person.getName() + "\n" +
                "Book Title: " + book.get(0).getTitle() + "\n" +
                "Date Issued: " + generateCurrentDate() + " " + getCurrentTime() + "\n" +
                "Return Date: " + getDateToReturnBook() + "\n";

        Records.updateDateRecord(person.getId(), records);
    }

    /**
     * This method shows the record of all issued books
     * @param librarian
     * @return
     */
    public static Map<Integer, String> getBookIssuedRecord(Person librarian) {
        if (librarian.getRole().equalsIgnoreCase("Librarian")) {
            display.displayRecordInformation(librarian, Records.getRecords());
            return Records.getRecords();
        } else {
            System.out.println("You don't have access to this action");
        }

        return null;
    }

    public static String returnBook(Person librarian, Person student) {
        String message = "";
        if (librarian.getRole().equalsIgnoreCase("Librarian")) {
            message = deleteRecord(student);
        }
        return message;
    }

    /**
     * This handles returning book action
     * @param person
     * @return
     */
    private static String deleteRecord(Person person) {
        Map<Integer, String> records;
        String message = "";
        int counter = 0;
        records = Records.getRecords();

        for (Map.Entry<Integer, String> each : records.entrySet()) {
            if (each.getKey().equals(person.getId())) counter++;
        }

        if (counter == 0) {
            message = person.getName() + ": You did not borrow any book from us";
        } else {
            records.remove(person.getId());
            message = person.getName() + ": Book returned, Borrower's record successfully deleted";
        }

        return message;
    }

    /**
     * This method handles book issuance activity
     * @param librarian
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String issueBook(Person librarian, T object){
        String message = "";
        if(librarian.getRole().equalsIgnoreCase("librarian")){
            if(object instanceof Person){
                Person person = (Person) object;
                String request = person.getRequest().trim();
                List<Book> bookIssued = BookDatabase.getBook(request);

                if(bookIssued.size() > 0){
                    message = "successful";
                    // display book to user
                    display = new RecordDisplayClass<>();
                    display.displayRecordInformation(person,bookIssued);
                    setBookIssuedRecord(person, bookIssued);
                }

            }else {
                PriorityQueue que = (PriorityQueue) object;
                Person person = (Person) que.peek();
                String request = person.getRequest().trim();
                List<Book> bookIssued = BookDatabase.getBook(request);
                int copiesOfBook = bookIssued.get(0).getNumOfCopies();

                if(copiesOfBook == 1){
                    setBookIssuedRecord(person, bookIssued);
                    //display for the person
                    System.out.println("Book(s) Issued to....");
                    display.displayRecordInformation(person, bookIssued);
                    setBookIssuedRecord(person, bookIssued);

                    //display others with no book
                    que.remove(que.peek());
                    Iterator<Person> iterator = que.iterator();

                    String result = "Book not issued to \n";

                    while (iterator.hasNext()){
                        result += "\t\t"+((Person) que.poll()).getName()+"\n";
                    }

                    System.out.println(result);
                }else {
                    Iterator<Person> iterator = que.iterator();

                    String result = "Book not issued to \n";
                    System.out.println(">>>>>>>>>>>>>>>>>>"+que);

                    while (iterator.hasNext()){
                        copiesOfBook--;
                        display.displayRecordInformation(que.peek(), bookIssued);
                        setBookIssuedRecord(person, bookIssued);
                        ((Person) que.poll()).getName();
                        if(copiesOfBook == 0) break;
                    }

                }

                que.clear();
                message = "successful";
            }
        }else{
            System.out.println("Can't use this method");
        }

        return message;
    }

    /**
     * This method handles request making
     * @param librarian
     * @param person
     * @param bookTitle
     */
    public static void makeBookRequest(Person librarian, Person person, String bookTitle) {
        person.setRequest(bookTitle);
        System.out.println(person.getName() + ", You've successfully made a request for a book");
    }

    /**
     * Getting current date
     * @return
     */
    private static String generateCurrentDate() {
        String date = "";

        DateFormat Date = DateFormat.getDateInstance();
        Calendar calendar = Calendar.getInstance();

        String currentDate = Date.format(calendar.getTime());

        return currentDate;
    }
    /**
     * Getting current time
     * @return
     */
    private static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        return " " + hours + ":" + minutes + ":" + seconds;
    }

    /**
     * Getting return date
     * @return
     */
    private static String getDateToReturnBook() {
        String currentDate = generateCurrentDate();
        String[] str = currentDate.split(" ");
        str[1] = Integer.parseInt(str[1].substring(0, str[1].indexOf(","))) + defaultDay + "";

        return str[0] + " " + str[1] + ", " + str[2];
    }
}
