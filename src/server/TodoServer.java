package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TodoServer {

    private final Integer PORT = 50002;
    private final Logger logger = Logger.getLogger("");

    private static Model model;

    public static void main(String[] args) {
        model = new Model();

        //Read data from specified json file
        model.readData();

        TodoServer server = new TodoServer();
        server.listenForConnections();
    }

    private TodoServer() {
    }

    private void listenForConnections() {
        try (ServerSocket listener = new ServerSocket(PORT, 10, null)) {
            logger.info("Listening on port " + PORT);

            while (true) {
                Socket socket = listener.accept();
                ClientThread client = new ClientThread(socket);

                // Terminate thread on program exit
                client.setDaemon(true);
                client.start();
            }
        } catch (Exception e) {
            logger.severe("An error occurred during socket server initialization.");
            logger.severe(e.toString());
        }
    }
}
