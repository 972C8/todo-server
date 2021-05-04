import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;
    private Account account;

    @BeforeEach
    void setUp() {
        model = new Model();
        //create login for all methods to use
        model.createLogin("mail", "pass");
    }

    @AfterEach
    void tearDown() {
        //Print all accounts for the end of each method
        System.out.println(model.getAccounts());
    }

    @Test
    void createLogin() {
        assertTrue(model.createLogin("mail1", "pass1"));
    }

    @Test
    void login() {

        //Must return true as login is valid
        assertTrue(model.login("mail", "pass"));

        //Must return false as login is invalid
        assertFalse(model.login("mail", "passWRONG"));
        assertFalse(model.login("mailWRONG", "pass"));
    }

    @Test
    void logout() {
        assertTrue(model.logout());
    }

    @Test
    void changePassword() {
        model.login("mail", "pass");

        //change password and try login with new password
        assertTrue(model.changePassword("passNEW"));
        assertTrue(model.login("mail", "passNEW"));
        assertFalse(model.login("mail1", "passWRONG"));

        model.createLogin("mail1", "pass1");
        model.createLogin("mail5", "pass5");
        model.createLogin("mail10", "pass10");
        model.login("mail5", "pass5");
        System.out.println(model.getAccounts());
        model.changePassword("passNEW");
        model.login("mail1", "pass1");
        model.changePassword("pass1NEW");
        System.out.println(model.getAccounts());
    }

    @Test
    void createToDo() {
        model.login("mail", "pass");
        assertTrue(model.createToDo("title", "HIGH", "description"));
    }
}