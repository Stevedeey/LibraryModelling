import com.library.model.Person;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.NoPermissionException;

public class PersonTest {

    Person librarian = new Person(29, "Olaleye Oluwatosin", "Librarian");
    Person teacher1 = new Person(33, "Osereme Okoduwa", "Teacher");
    Person teacher2 = new Person(35, "Ifeanyichukwu Isaiah", "Teacher");
    Person teacher3 = new Person(43, "Gadibia Daro", "Teacher");

    Person teacher4 = new Person(30, "Commando Wizjid", "Teacher");
    Person seniorStudent = new Person(21, "Idowu Akinwale", "Student");
    Person juniorStudent = new Person(21, "Idowu Akinwale", "Student");




    public PersonTest() throws NoPermissionException {
    }

    @Test
    public void testSetLevelValidClass()
    {

        String studlevelSenior= "SS3";
        String studlevelJunior = "JS3";

        Assert.assertEquals("successful",seniorStudent.setLevel(studlevelSenior));
        Assert.assertEquals("successful",juniorStudent.setLevel(studlevelJunior));


    }
    @Test
    public void testSetLevelInvalidClass()
    {

        String studlevelSenior= "SS9";
        String studlevelJunior = "JS9";

        Assert.assertEquals("failed",seniorStudent.setLevel(studlevelSenior));
        Assert.assertEquals("failed",juniorStudent.setLevel(studlevelJunior));
    }



}
