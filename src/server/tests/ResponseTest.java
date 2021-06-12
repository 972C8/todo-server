import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {

    private String responseStringTrue, responseStringFalse, responseStringWithData1, responseStringWithData2;

    @BeforeEach
    void setUp() {
        responseStringTrue = "Result|true\n";
        responseStringFalse = "Result|false\n";
        responseStringWithData1 = "Result|true|A\n";
        responseStringWithData2 = "Result|true|A|B\n";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createResponse() {
        Response responseTrue = new Response(true);
        Response responseFalse = new Response(false);

        assertEquals(responseTrue.toString(), responseStringTrue);
        assertEquals(responseFalse.toString(), responseStringFalse);
    }

    @Test
    void createResponseWithData() {
        String[] responseData1 = {"A"};
        String[] responseData2 = {"A", "B"};

        Response response1 = new Response(true, responseData1);
        Response response2 = new Response(true, responseData2);

        assertEquals(response1.toString(), responseStringWithData1);
        assertEquals(response2.toString(), responseStringWithData2);
    }
}