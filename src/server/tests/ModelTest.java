import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Account;
import server.Model;
import server.TodoItem;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();

        //create login for all methods to use
        String[] loginData = {"mail", "pass"};
        model.createLogin(loginData);
        model.login(loginData);
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
     * <p>
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

        String[] log = {"mail", "pass"};
        String[] passWrong = {"mail", "passWRONG"};
        String[] mailWrong = {"mailWRONG", "pass"};

        //Must return true as login is valid
        assertTrue(model.login(log).isSuccess());

        //Must return false as login is invalid
        assertFalse(model.login(passWrong).isSuccess());
        assertFalse(model.login(mailWrong).isSuccess());
    }

    @Test
    void logout() {
        assertTrue(model.logout());
    }

    @Test
    void changePassword() {
        String[] logNew = {"mail", "passNEW"};
        String[] passWrong = {"mail", "passWRONG"};

        String[] newPass = {"passNEW"};

        //change password and try login with new password
        assertTrue(model.changePassword(newPass).isSuccess());
        assertTrue(model.login(logNew).isSuccess());
        assertFalse(model.login(passWrong).isSuccess());

        String[] log1 = {"mail1", "pass1"};
        String[] log5 = {"mail5", "pass5"};
        String[] log10 = {"mail10", "pass10"};

        model.createLogin(log1);
        model.createLogin(log5);
        model.createLogin(log10);

        assertTrue(model.login(log5).isSuccess());
        assertTrue(model.changePassword(newPass).isSuccess());
    }

    @Test
    void createToDo() {
        String[] todo = {"title", "HIGH", "description"};
        assertTrue(model.createToDo(todo).isSuccess());
    }

    /**
     * Create some to do items to test them
     */
    void prepareToDoItems() {
        String[] todo1 = {"todo1", "HIGH", "description1"};
        String[] todo2 = {"todo2", "MEDIUM", "description2"};
        String[] todo3 = {"todo3", "LOW", "description3"};

        assertTrue(model.createToDo(todo1).isSuccess());
        assertTrue(model.createToDo(todo2).isSuccess());
        assertTrue(model.createToDo(todo3).isSuccess());
    }

    @Test
    void getToDo() {
        prepareToDoItems();

        String[] item1 = {"1"};
        String[] item2 = {"2"};

        assertTrue(model.getToDo(item1).isSuccess());
        assertNotNull(model.getToDo(item1).getData());

        //TODO: test content of response data correctly
        /*
        assertEquals(TodoItem.Priority.HIGH, model.getToDo(item1).getData());
        assertNotEquals(TodoItem.Priority.HIGH, model.getToDo(2).getPriority());
        */
    }

    @Test
    void deleteToDo() {
        prepareToDoItems();

        String[] item2 = {"2"};
        String[] itemFalse = {"-3"};

        assertNotNull(model.getToDo(item2));
        assertTrue(model.deleteToDo(item2).isSuccess());
        assertFalse(model.getToDo(item2).isSuccess());

        assertFalse(model.deleteToDo(itemFalse).isSuccess());
    }

    @Test
    void listToDos() {
        assertTrue(model.listToDos().isEmpty());

        prepareToDoItems();
        assertNotNull(model.listToDos());
        assertEquals(3, model.listToDos().size());
    }
}