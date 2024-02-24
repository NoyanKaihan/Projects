package server;

import connection.Request;
import connection.Response;
import managers.CollectionManager;
import managers.*;

public class RequestHandler {
    private Request request;
    private CommandManager commandManager;
    public RequestHandler (Request request,CommandManager commandManager){
        this.request = request;
        this.commandManager = commandManager;
    }
    public Response handle (){

        try {
            System.out.println("["+request.getCommand()+"]"+" has been received from the client");
            Response response = commandManager.runCommand(request);
            return response;
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
