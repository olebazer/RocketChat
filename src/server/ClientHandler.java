package server;

/*
 * Bei jeder neuen Verbindung, die der Server eingeht,
 * wird eine neue Instanz dieser Klasse erzeugt.
 * Zu erst wird der Client nach einem Benutzernamen
 * gefragt und es wird auf eine Antwort gewartet.
 * Danach wird weiterhin auf Nachrichten des Clients
 * gewartet, wobei diese immer an alle anderen
 * verbundenen Clients weitergleitet wird. Entspricht
 * eine Nachricht dem String "quit", wird die
 * Verbindung aufgelöst.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private static ArrayList<ClientHandler> connections = new ArrayList<ClientHandler>();
    private Socket client;

    ClientHandler(Socket client) {
        this.client = client;
        connections.add(this);
    }

    public void broadcast(String message) throws IOException {
        for (ClientHandler connection : connections) {
            if (connection != null) {
                connection.send(message);
            }
        }
    }

    public void send(String message) throws IOException {
        PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println(message);
    }

    public String receive() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        return bufferedReader.readLine();
    }

    @Override
    public void run() {
        System.out.println("New client connected!");

        try {
            send("Enter a username:");
            String username = receive();
            send("Hello, " + username + "!");
            broadcast(username + " entered the chat!");
            String message = "";

            while (!message.equals("quit")) {
                message = receive();
                broadcast(username + ": " + message);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
