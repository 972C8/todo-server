import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Account;
import server.Model;
import server.Response;
import server.TodoItem;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;
    private String sessionToken;

    @BeforeEach
    void setUp() {
        model = new Model();

        //create login for all methods to use
        String[] loginData = {"mail@domain.tld", "pass"};
        model.createLogin(loginData);
        Response response = model.login(loginData);
        sessionToken = response.getData()[0];
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
        //Reset ids to default value
        Account.setNextId(1);
        TodoItem.setNextId(1);
    }

    @Test
    void createLogin() {
        String[] login = {"mail1@domain.tld", "pass1"};
        String[] loginWrong = {"mail@domain.tld", "pass"};
        assertTrue(model.createLogin(login).isSuccess());
        assertFalse(model.createLogin(loginWrong).isSuccess());
    }

    @Test
    void login() {

        String[] log = {"mail@domain.tld", "pass"};
        String[] passWrong = {"mail@domain.tld", "passWRONG"};
        String[] mailWrong = {"mailWRONG@domain.tld", "pass"};

        //Must return true as login is valid
        assertTrue(model.login(log).isSuccess());

        //Must return false as login is invalid
        assertFalse(model.login(passWrong).isSuccess());
        assertFalse(model.login(mailWrong).isSuccess());
    }

    @Test
    void logout() {
        String token = sessionToken;
        String tokenWrong = "randomToken";

        assertTrue(model.logout(token).isSuccess());
        assertTrue(model.logout(tokenWrong).isSuccess());
    }

    @Test
    void changePassword() {
        String[] logNew = {"mail@domain.tld", "passNEW"};
        String[] passWrong = {"mail@domain.tld", "passWRONG"};

        //token, new password
        String[] newPass = {sessionToken, "passNEW"};

        //change password and try login with new password
        assertTrue(model.changePassword(newPass, sessionToken).isSuccess());
        assertTrue(model.login(logNew).isSuccess());
        assertFalse(model.login(passWrong).isSuccess());

        String[] log1 = {"mail1@domain.tld", "pass1"};
        String[] log5 = {"mail5@domain.tld", "pass5"};
        String[] log10 = {"mail10@domain.tld", "pass10"};

        model.createLogin(log1);
        model.createLogin(log5);
        model.createLogin(log10);

        assertTrue(model.login(log5).isSuccess());
        assertTrue(model.changePassword(newPass, sessionToken).isSuccess());
    }

    @Test
    void validMailAddress() {
        String mailValid = "user@example.com";
        String mailInvalid1 = "user";
        String mailInvalid2 = "user@example";
        String mailInvalid3 = "example.com";

        assertTrue(Model.validMailAddress(mailValid));
        assertFalse(Model.validMailAddress(mailInvalid1));
        assertFalse(Model.validMailAddress(mailInvalid2));
        assertFalse(Model.validMailAddress(mailInvalid3));
    }

    @Test
    void validPassword() {
        String passValid = "password";
        String passInvalid1 = "12";
        String passInvalid2 = "123456789012345678901";

        assertTrue(Model.validPassword(passValid));
        assertFalse(Model.validPassword(passInvalid1));
        assertFalse(Model.validPassword(passInvalid2));
    }

    @Test
    void validTitle() {
        String titleValid1 = "123";
        String titleValid2 = "12345678901234567890";
        String titleValid3 = "New Title";
        String titleInvalid = "123456789012345678901";

        assertTrue(Model.validTitle(titleValid1));
        assertTrue(Model.validTitle(titleValid2));
        assertTrue(Model.validTitle(titleValid3));
        assertFalse(Model.validTitle(titleInvalid));
    }

    @Test
    void validDescription() {
        String descriptionValid1 = "";
        String descriptionValid2 = "Description";
        String descriptionValid3 = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
        String descriptionInvalid = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456";

        assertTrue(Model.validDescription(descriptionValid1));
        assertTrue(Model.validDescription(descriptionValid2));
        assertTrue(Model.validDescription(descriptionValid3));
        assertFalse(Model.validDescription(descriptionInvalid));
    }

    @Test
    void validPriority() {
        String prioValid1 = "low";
        String prioValid2 = "Medium";
        String prioValid3 = "HIGH";
        String prioInvalid1 = "1";
        String prioInvalid2 = "default";

        assertTrue(Model.validPriority(prioValid1));
        assertTrue(Model.validPriority(prioValid2));
        assertTrue(Model.validPriority(prioValid3));
        assertFalse(Model.validPriority(prioInvalid1));
        assertFalse(Model.validPriority(prioInvalid2));
    }

    @Test
    void generateToken() {
        String token = Model.generateToken();
        int expectedLength = Model.getTokenLength();

        assertEquals(token.length(), expectedLength);
    }

    @Test
    void createToDo() {
        String[] todo1 = {sessionToken, "title", "HIGH", "description"};
        String[] todo2 = {sessionToken, "title2", "low", ""};
        assertTrue(model.createToDo(todo1, sessionToken).isSuccess());
        assertTrue(model.createToDo(todo2, sessionToken).isSuccess());
    }

    /**
     * Create some to do items to test them
     */
    void prepareToDoItems() {
        String[] todo1 = {sessionToken, "todo1", "HIGH", "description1"};
        String[] todo2 = {sessionToken, "todo2", "Medium", "description2"};
        String[] todo3 = {sessionToken, "todo3", "low", "description3"};

        assertTrue(model.createToDo(todo1, sessionToken).isSuccess());
        assertTrue(model.createToDo(todo2, sessionToken).isSuccess());
        assertTrue(model.createToDo(todo3, sessionToken).isSuccess());
    }

    @Test
    void getToDo() {
        prepareToDoItems();

        String[] item1 = {sessionToken, "1"};
        String[] item2 = {sessionToken, "2"};
        String[] item3 = {sessionToken, "3"};

        assertTrue(model.getToDo(item1, sessionToken).isSuccess());
        assertNotNull(model.getToDo(item1, sessionToken).getData());

        assertEquals("1", model.getToDo(item1, sessionToken).getData()[0]);
        assertEquals("todo1", model.getToDo(item1, sessionToken).getData()[1]);
        assertEquals("High", model.getToDo(item1, sessionToken).getData()[2]);
        assertEquals("description1", model.getToDo(item1, sessionToken).getData()[3]);

        assertEquals("2", model.getToDo(item2, sessionToken).getData()[0]);
        assertEquals("todo2", model.getToDo(item2, sessionToken).getData()[1]);
        assertEquals("Medium", model.getToDo(item2, sessionToken).getData()[2]);
        assertEquals("description2", model.getToDo(item2, sessionToken).getData()[3]);

        assertEquals("3", model.getToDo(item3, sessionToken).getData()[0]);
        assertEquals("todo3", model.getToDo(item3, sessionToken).getData()[1]);
        assertEquals("Low", model.getToDo(item3, sessionToken).getData()[2]);
        assertEquals("description3", model.getToDo(item3, sessionToken).getData()[3]);
    }

    @Test
    void deleteToDo() {
        String[] token = {sessionToken};
        String[] item2 = {sessionToken, "2"};
        String[] itemFalse = {"-3"};

        prepareToDoItems();

        //Check how many items exist before deleting one
        assertEquals(3, model.listToDos(token, sessionToken).getData().length);

        assertNotNull(model.getToDo(item2, sessionToken));
        assertTrue(model.deleteToDo(item2, sessionToken).isSuccess());

        //Check how many items exist after deleting one
        assertEquals(2, model.listToDos(token, sessionToken).getData().length);

        assertFalse(model.getToDo(item2, sessionToken).isSuccess());
        assertFalse(model.deleteToDo(itemFalse, sessionToken).isSuccess());

    }

    @Test
    void listToDos() {
        String[] requestData = {sessionToken};

        assertFalse(model.listToDos(null, sessionToken).isSuccess());

        prepareToDoItems();

        assertTrue(model.listToDos(requestData, sessionToken).isSuccess());
        assertNotNull(model.listToDos(requestData, sessionToken).getData());

        assertEquals(3, model.listToDos(requestData, sessionToken).getData().length);

    }
}