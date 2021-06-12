import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Account;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new Account("user1@example.com", "passTRUE");
        user2 = new Account("user2@example.com", "passTRUE");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addTodoItem() {
    }

    @Test
    void hashPassword() {
       String passwordTrue = "passTRUE";
       String passwordFalse = "passFALSE";

       String hashTrue1 = user1.hashPassword(passwordTrue);
       String hashTrue2 = user2.hashPassword(passwordTrue);
       String hashFalse1 = user1.hashPassword(passwordFalse);
       String hashFalse2 = user2.hashPassword(passwordFalse);

       assertEquals(hashTrue1, user1.getPassword());
       assertEquals(hashTrue2, user2.getPassword());
       assertNotEquals(hashFalse1, user1.getPassword());
       assertNotEquals(hashFalse2, user2.getPassword());

       // Verify salting works => same password has different hash for different users
       assertNotEquals(hashTrue1, hashTrue2);
       assertNotEquals(hashFalse1, hashFalse2);
    }

    @Test
    void bytesToHex() {
        byte[] byteArray = {0x00, 0x01, 0x02};
        String hexString = "000102";

        String converted = Account.byteArrayToHexString(byteArray);
        assertEquals(converted, hexString);
    }
}