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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createLogin() {
        assertTrue(model.createLogin("mail1", "pass1"));
    }

    @Test
    void login() {
        model.createLogin("mail1", "pass1");

        //Must return true as login is valid
        assertTrue(model.login("mail1", "pass1"));

        //Must return false as login is invalid
        assertFalse(model.login("mail1", "pass2"));
        assertFalse(model.login("mail2", "pass1"));
    }

    @Test
    void logout() {
        assertTrue(model.logout());
    }

    @Test
    void changePassword() {
        model.createLogin("mail1", "pass1");
        model.login("mail1", "pass1");

        //change password and try login with new password
        assertTrue(model.changePassword("pass323"));
        assertTrue(model.login("mail1", "pass323"));
        assertFalse(model.login("mail1", "passWRONG"));
    }
}