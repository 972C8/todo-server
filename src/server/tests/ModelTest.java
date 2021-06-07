import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Account;
import server.Model;
import server.TodoItem;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();

        //create login for all methods to use
        String[] loginData = {"mail", "pass"};
        model.createLogin(loginData);
        model.login("mail", "pass");
    }

    @AfterEach
    void tearDown() {
        //Print all accounts for the end of each method
        System.out.println(model.getAccounts());

        //Reset the data after each method test
        resetData();
    }

    /**
     * Reset data after each method test.
     *
     * This is important as static ids are tracked to ensure unique identifiers.
     * They must be reset for each new test to avoid data errors.
     */
    void resetData() {
        model.resetAccounts();
        Account.resetNextId();
        TodoItem.resetNextId();
    }

    @Test
    void createLogin() {
        String[] login = {"mail1", "pass1"};
        assertTrue(model.createLogin(login).isSuccess());
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
        //change password and try login with new password
        assertTrue(model.changePassword("passNEW"));
        assertTrue(model.login("mail", "passNEW"));
        assertFalse(model.login("mail1", "passWRONG"));

        String[] log1 = {"mail1", "pass1"};
        String[] log5 = {"mail5", "pass5"};
        String[] log10 = {"mail10", "pass10"};

        model.createLogin(log1);
        model.createLogin(log5);
        model.createLogin(log10);
        model.login("mail5", "pass5");
        System.out.println(model.getAccounts());
        model.changePassword("passNEW");
        model.login("mail1", "pass1");
        model.changePassword("pass1NEW");
        System.out.println(model.getAccounts());
    }

    @Test
    void createToDo() {
        assertTrue(model.createToDo("title", "HIGH", "description"));
    }

    /**
     * Create some to do items to test them
     */
    void prepareToDoItems() {
        model.createToDo("todo1", "HIGH", "description1");
        model.createToDo("todo2", "MEDIUM", "description2");
        model.createToDo("todo3", "LOW", "description3");
    }

    @Test
    void getToDo() {
        prepareToDoItems();

        assertEquals("todo1", model.getToDo(1).getTitle());
        assertEquals("todo2", model.getToDo(2).getTitle());
        assertEquals(TodoItem.Priority.HIGH, model.getToDo(1).getPriority());
        assertNotEquals(TodoItem.Priority.HIGH, model.getToDo(2).getPriority());
    }

    @Test
    void deleteToDo() {
        prepareToDoItems();

        assertNotNull(model.getToDo(2));
        assertTrue(model.deleteToDo(2));
        assertNull(model.getToDo(2));

        assertFalse(model.deleteToDo(-3));
    }

    @Test
    void listToDos() {
        assertTrue(model.listToDos().isEmpty());

        prepareToDoItems();
        assertNotNull(model.listToDos());
        assertEquals(3, model.listToDos().size());
    }
}