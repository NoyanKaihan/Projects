package org.example.client;

import org.example.connection.*;
import org.example.console.ConsoleColor;
import org.example.inputManager.ConsoleInputManager;
import org.example.inputManager.FileInputManager;
import org.example.inputManager.InputManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.example.console.ConsoleColor.MAGENTA_BOLD;

public class Client {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private InputManager inputManager;

    public Client(String host, int port) {
        connect(host, port);
        inputManager = new ConsoleInputManager();
    }

    private void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException exception) {
            System.out.println(ConsoleColor.RED_BACKGROUND + exception.getMessage() + host + ConsoleColor.RESET + "\n");
        } catch (IOException exception) {
            System.out.println(ConsoleColor.RED_BACKGROUND + exception.getMessage() + ConsoleColor.RESET + "\n");
        }
    }

    private String command = "";

    public void run() {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            String command = "";
            Response response;
            while (!command.equals("exit")) {
                Request request = request();
                if (request.getCommandName().equals("exit"))
                    break;
                if (request.getCommandName().equals("execute_script")) {
                    System.out.println(request.getCommandArgument());
                    InputManager fileInput = new FileInputManager(request().getCommandArgument());
                    while (fileInput.getScanner().hasNext()) {
                        System.out.println("Request");
                        Request req = fileInput.readCommand();
                        objectOutputStream.writeObject(req);
                        objectOutputStream.flush();
                        response = (Response) objectInputStream.readObject();
                        if (response.getStatus() == Status.OK)
                            System.out.println(ConsoleColor.YELLOW + response.getMessage() + ConsoleColor.RESET + "\n");
                        else
                            System.out.println(ConsoleColor.RED_BACKGROUND + response.getMessage() + ConsoleColor.RESET + "\n");
                    }
                }
                command = request.getCommandName();
                objectOutputStream.writeObject(request);
                response = (Response) objectInputStream.readObject();
                if (response.getStatus() == Status.OK) {
                    System.out.println(ConsoleColor.YELLOW + response.getMessage() + ConsoleColor.RESET + "\n");
                } else
                    System.out.println(ConsoleColor.RED_BACKGROUND + response.getMessage() + ConsoleColor.RESET + "\n");
            }
        } catch (Exception exception) {
            System.out.println(ConsoleColor.RED_BACKGROUND + exception.getMessage() + ConsoleColor.RESET + "\n");
        }
    }

    private Request request() {
        System.out.println(ConsoleColor.MAGENTA_BOLD + "enter a command (help to get help) : " + ConsoleColor.RESET);
        return inputManager.readCommand();
    }
}
