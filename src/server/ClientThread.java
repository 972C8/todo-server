package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    private static int clientNumber = 0;
    private final Logger logger = Logger.getLogger("");
    private Socket socket;
    private static Model model = new Model();

    private String sessionToken;

    public ClientThread(Socket socket) {
        super("Client#" + clientNumber++);
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream())) {

            logger.info("Connection established with client: " + socket.getInetAddress().toString());

            String lineIn;
            while ((lineIn = in.readLine()) != null && lineIn.length() != 0) {

                logger.info("Request from client: " + lineIn);

                // Process single request
                // Returns response string
                Response response = processRequest(lineIn);

                // server.Response string is sent to client
                logger.info("Response to client: " + response.toString());
                out.write(response.toString());

                // Flush the buffer
                out.flush();
            }

            socket.close();
        } catch (IOException e) {
            logger.warning(e.toString());
        }
    }

    /**
     * Processes a single request.
     * - Parses the various parameters of the request
     * - Calls functions for the different messageTypes
     * - Returns a response string that is sent to the client
     *
     * @param request Request from the client as string
     * @return
     */
    private Response processRequest(String request) {
        Response response;
        String[] requestData = null;

        // Decomposite the request
        String[] parts = request.split("\\|");

        // Assign element of request to variables
        String messageType = parts[0];

        //Check if additional parameters exist and add to String[] requestData
        if (parts.length > 1) {
            requestData = Arrays.copyOfRange(parts, 1, parts.length);
        }

        // Switch between allowed messageTypes, otherwise return negative response
        switch (messageType) {
            case "Ping":
                response = model.ping(requestData, sessionToken);
                break;
            case "CreateLogin":
                response = model.createLogin(requestData);
                break;
            case "Login":
                response = model.login(requestData);

                // store token in session
                if (response.isSuccess()) sessionToken = response.getData()[0];

                break;
            case "ChangePassword":
                response = model.changePassword(requestData, sessionToken);
                break;
            case "CreateToDo":
                response = model.createToDo(requestData, sessionToken);
                break;
            case "GetToDo":
                response = model.getToDo(requestData, sessionToken);
                break;
            case "DeleteToDo":
                response = model.deleteToDo(requestData, sessionToken);
                break;
            case "ListToDos":
                response = model.listToDos(requestData, sessionToken);
                break;
            case "Logout":
                response = model.logout(sessionToken);

                // reset token in session
                sessionToken = "";
                break;
            default:
                response = new Response(false);
                break;
        }

        return response;
    }
}
