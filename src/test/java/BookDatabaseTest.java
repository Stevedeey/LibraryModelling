import com.library.model.Book;
import com.library.model.Person;
import com.library.utilities.BookDatabase;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

public class BookDatabaseTest {
    List<Book> bookList = BookDatabase.getBookList();
    Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
    Person teacher1 = new Person(33, "Osereme Okoduwa", "Teacher");
    Person teacher2 = new Person(35, "Ifeanyichukwu Isaiah", "Teacher");
    Person teacher3 = new Person(43, "Gadibia Daro", "Teacher");

    Person teacher4 = new Person(30, "Commando Wizjid", "Teacher");
    Person seniorStudent = new Person(21, "Idowu Akinwale", "Student");
    Person juniorStudent = new Person(21, "Idowu Akinwale", "Student");

    public BookDatabaseTest() throws NoPermissionException {
    }
    @Test
    public void sortBookByTest()
    {
        Assert.assertNotNull(bookList);
    }
    @Test
    public void searchByCategoryTest()
    {
        String category = "Programming";
         List<Book> books = new ArrayList<>();
        for (Book book:bookList
             ) {
            if(book.getCategory()==category)
            {
                books.add(book);
            }

        }
        Assert.assertNotNull(books);
    }
    @Test
    public void searchByTitle()
    {
        String title = "Things Fall Apart";
        List<Book> books = new ArrayList<>();
        for (Book book:bookList
        ) {
            if(book.getCategory()==title)
            {
                books.add(book);
            }

        }
        Assert.assertNotNull(books);
    }
   

}
