package example.server;


import example.clientHandler.ClientHandler;
import example.commands.CommandManager;
import example.commands.SaveCommand;
import example.starter.Starter;
import org.example.connection.Request;
import org.example.connection.Response;
import org.example.connection.ResponseMessage;
import org.example.console.ConsoleColor;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private CommandManager commandManager;
    private ServerSocket serverSocket;
    private Socket socket;
    private ClientHandler clientHandler;
    private Thread thread;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    public Server(int port, CommandManager commandManager) {
        initialize(port);
        this.commandManager = commandManager;
    }

    private void initialize(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void listenToClient() {
        while (true) {
            try {
                System.out.println(ConsoleColor.YELLOW_BACKGROUND + "Waiting for a client to be connected" + ConsoleColor.RESET + "\n");
                this.socket = serverSocket.accept();
                System.out.println(ConsoleColor.GREEN_BACKGROUND + "Client connected : " + socket.getInetAddress().getHostName() + ConsoleColor.RESET + "\n");
                ResponseMessage response ;
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                input = new ObjectInputStream(socket.getInputStream());
                do {
                    Request request = (Request) input.readObject();
                    System.out.println(ConsoleColor.YELLOW_BOLD+"["+request.getCommandName()+"]"+ConsoleColor.RESET+ConsoleColor.GREEN_BOLD+" has been received from "+socket.getInetAddress().getHostName()+ConsoleColor.RESET+"\n");
                    response = executor(request);
                    send(response);

                } while (socket.isConnected());
                System.out.println(ConsoleColor.GREEN_BACKGROUND+new SaveCommand(commandManager.getCollectionManager(), Starter.getFileManager()).execute() +ConsoleColor.RESET+"\n");
                System.out.println(ConsoleColor.RED_BACKGROUND + "Client disconnected: " + socket.getInetAddress().getHostName() + ConsoleColor.RESET + "\n");
                socket.close();
            } catch (IOException exception) {
                try {
                    System.out.println(ConsoleColor.GREEN_BACKGROUND+new SaveCommand(commandManager.getCollectionManager(), Starter.getFileManager()).execute() +ConsoleColor.RESET+"\n");
                } catch (InvalidDataException e) {
                    System.out.println(ConsoleColor.RED_BACKGROUND+e.getMessage()+ConsoleColor.RESET+"\n");
                } catch (CommandException e) {
                    System.out.println(ConsoleColor.RED_BACKGROUND+e.getMessage()+ConsoleColor.RESET+"\n");
                } catch (FileException e) {
                    System.out.println(ConsoleColor.RED_BACKGROUND+e.getMessage()+ConsoleColor.RESET+"\n");
                } catch (ConnectionException e) {
                    System.out.println(ConsoleColor.RED_BACKGROUND+e.getMessage()+ConsoleColor.RESET+"\n");
                }
                System.out.println(ConsoleColor.RED_BACKGROUND + exception.getMessage() + ConsoleColor.RESET + "\n");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (Exception exception){
                System.out.println(ConsoleColor.RED_BACKGROUND+exception.getMessage()+ConsoleColor.RESET+"\n");
            }
        }
    }

    private ResponseMessage executor(Request request) {
        return commandManager.executeCommand(request);
    }
    private void send(Response response)throws Exception{
        try {
            output.writeObject(response);
            System.out.println(ConsoleColor.CYAN + "---------------------------------------------------------------------------------------------------------" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW_UNDERLINED + "sent to : [" + socket.getInetAddress().getHostName() + "] -> " + response.getMessage() + ConsoleColor.RESET);
            System.out.println(ConsoleColor.CYAN + "---------------------------------------------------------------------------------------------------------" + ConsoleColor.RESET);
        }catch (Exception exception){
            throw new Exception (exception);
        }
    }
}
