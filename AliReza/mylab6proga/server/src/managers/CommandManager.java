package managers;

import commands.*;
import connection.Request;
import connection.Response;

import java.io.IOException;
import java.util.*;

public class CommandManager {

    private final Map<String, AbstractCommand> commandMap;
    private static HistoryAbstractCommand historyCommand;

    public CommandManager( CollectionManager collectionManager) {
        commandMap = new HashMap<>();
        historyCommand = new HistoryAbstractCommand();
        commandMap.put("insert", new InsertAbstractCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("info", new InfoAbstractCommand(collectionManager));
        commandMap.put("history", historyCommand);
        commandMap.put("remove_key", new RemoveKeyAbstractCommand(collectionManager));
        commandMap.put("replace_if_lowe", new ReplaceIfLoweAbstractCommand(collectionManager));
        commandMap.put("max_by_unit_of_measure", new MaxByUnitOfMeasureAbstractCommand(collectionManager));
        commandMap.put("update", new UpdateAbstractCommand(collectionManager));
        commandMap.put("remove_lower_key", new RemoveLowerKeyAbstractCommand(collectionManager));
        commandMap.put("filter_starts_with_name", new FilterStartsWithNameAbstractCommand(collectionManager));
        commandMap.put("filter_contains_partNumber", new FilterContainsPartNumberAbstractCommand(collectionManager));
        commandMap.put("execute_script", new ExecuteScriptAbstractCommand(this));
        commandMap.put("help", new HelpAbstractCommand(this));
        commandMap.put("show", new ShowAbstractCommand(collectionManager));
    }

    public Map<String, AbstractCommand> getCommandMap() {
        return commandMap;
    }


    public Response runCommand(Request request) throws IOException {
        Response response = null;
        if(commandMap.containsKey(request.getCommand())){
            String answer = "";
            if(request.getArgument() == null){
                answer = commandMap.get(request.getCommand()).execute("");
            }else if(request.getArgument()!=null && request.getProduct() != null){

                commandMap.get(request.getCommand()).setProduct(request.getProduct());
                System.out.println(request.getCommand());
                System.out.println(request.getArgument());
                answer = commandMap.get(request.getCommand()).execute(request.getArgument());
            }
            else{
                answer = commandMap.get(request.getCommand()).execute(request.getArgument());
            }
            response = new Response(answer);
            historyCommand.addToHistory(request.getCommand());
        }
        else response = new Response("no such command");
        return response;
    }

}