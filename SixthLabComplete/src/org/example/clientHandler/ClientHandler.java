package org.example.clientHandler;

import org.example.commands.CommandManager;
import org.example.connection.Request;
import org.example.connection.Response;
import org.example.connection.ResponseMessage;
import org.example.console.ConsoleColor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final CommandManager commandManager;
    public ClientHandler(Socket socket,CommandManager commandManager) {
        this.socket = socket;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try {
            Response response = new ResponseMessage();
            System.out.println("after output");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            System.out.println("after input");

            do {
                response.setMessage("Enter a command (help to get help) = ");
                output.writeObject(response);
                Request request = (Request) input.readObject();
                response = send(request);
                output.writeObject(response);
                output.close();
            } while (socket.isConnected());
            System.out.println(ConsoleColor.RED_BACKGROUND+"Client disconnected: " + socket.getInetAddress().getHostName()+ConsoleColor.RESET+"\n");
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ConsoleColor.RED_BACKGROUND+e.getMessage()+ConsoleColor.RESET+"\n");
        }
    }
    private Response send(Request request){
       return commandManager.executeCommand(request);
    }
}
