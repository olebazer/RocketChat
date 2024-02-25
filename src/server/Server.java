package server;

/*
 * Diese Klasse erstellt einen Server-Socket und kann
 * durch die Methode serve() immer wieder neue Verbindungen
 * zu Client-Sockets eingehen. Die Klasse ClientHandler
 * hilft dabei, jedem neuen Client einen eigenen Thread
 * zuzuweisen und regelt die Kommunikation.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket server;

    Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void serve() throws IOException {
        while (true) {
            Socket client = server.accept();
            ClientHandler handler = new ClientHandler(client);
            Thread clientThread = new Thread(handler);
            clientThread.start();
        }
    }
}
