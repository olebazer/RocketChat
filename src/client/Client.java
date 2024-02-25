package client;

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
                e.printStackTrace();
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
