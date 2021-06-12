package server;

public class Response {

    private boolean success;
    private String[] data;

    /**
     * Constructor for a response.
     *
     * @param success success of request
     */
    public Response(boolean success) {
        this.success = success;
    }

    /**
     * Constructor for a response with data.
     *
     * @param success success of request
     * @param data response data
     */
    public Response(boolean success, String[] data) {
        this.success = success;
        this.data = data;
    }

    /**
     * Returns a response string for the request.
     *
     * @return responseString
     */
    public String toString() {
        // If the response has data, join it with a |, otherwise leave blank.
        String data = (this.data != null) ? "|" + String.join("|", this.data) : "";

        // Build the response string and return it.
        return "Result|" + this.success + data + "\n";
    }

    public boolean isSuccess() {
        return success;
    }

    public String[] getData() {
        return data;
    }
}
