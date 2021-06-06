import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createResponse() {
        Response responseTrue = new Response(true);
        Response responseFalse = new Response(false);

        String responseTrueString = "Result|true\n";
        String responseFalseString = "Result|false\n";


        assertEquals(responseTrue.toString(), responseTrueString);
        assertEquals(responseFalse.toString(), responseFalseString);
    }

    @Test
    void createResponseWithData() {
        String[] responseData = {"A", "B"};
        Response response = new Response(true, responseData);
        String responseString = "Result|true|A|B\n";

        assertEquals(response.toString(), responseString);
    }
}