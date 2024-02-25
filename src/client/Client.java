package client;

/*
 * Diese Klasse erstellt einen Client-Socket, der
 * sich durch die Methode connect() mit dem Server
 * verbinden kann. Es wird auf die Frage des Servers
 * nach dem Benutzernamen gewartet und mit Hilfe
 * eines Scanners die Antwort bereitsgestellt. 
 * Um gleichzeitiges Schreiben und Lesen von Nachrichten
 * zu ermöglichen startet parallel ein anderer
 * Thread. Dieser wartet auf eingehende Nachrichten
 * und gibt diese aus. Der Haupt-Thread wartet 
 * auf Eingabe und sendet diese an den Server.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket client;
    private String ip;
    private int port;

    Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void send(String message) throws IOException {
        PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println(message);
    }

    public String receive() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        return bufferedReader.readLine();
    }

    public void connect() throws IOException {
        client = new Socket(ip, port);
        Scanner scanner = new Scanner(System.in);
        String usernamePrompt = receive();
        System.out.println(usernamePrompt);
        String username = scanner.nextLine();
        send(username);
        String response = receive();
        System.out.println(response);
        String chatPrompt = "";
        Thread messageThread = new Thread(() -> {
            try {
                while (true) {
                    String message = receive();
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
        messageThread.start();

        while (!chatPrompt.equals("quit")) {
            chatPrompt = scanner.nextLine();
            send(chatPrompt);
        }

        scanner.close();
    }
}
