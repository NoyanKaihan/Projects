package org.example.commands;

import org.example.collection.CollectionManager;
import org.example.connection.Request;
import org.example.connection.Response;
import org.example.connection.ResponseMessage;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.StudyGroup;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {
    private Map<String,Command> commandMap;
    private CollectionManager<StudyGroup> collectionManager;
    public CommandManager(CollectionManager<StudyGroup> collectionManager){

        this.collectionManager = collectionManager;
        commandMap = new LinkedHashMap<>();
        addCommand();
    }
    private void addCommand(){
        commandMap.put("help",new HelpCommand());
        commandMap.put("info",new InfoCommand(collectionManager));
        commandMap.put("clear",new ClearCommand(collectionManager));
        commandMap.put("head",new HeadCommand(collectionManager));
        commandMap.put("remove_by_id",new RemoveByIdCommand(collectionManager));
        commandMap.put("remove_greater",new RemoveGreaterCommand(collectionManager));
        commandMap.put("show",new ShowCommand(collectionManager));
        commandMap.put("update",new UpdateCommand(collectionManager));
        commandMap.put("print_field_descending_group_admin",new PrintDescendingGroupAdminCommand(collectionManager));
        commandMap.put("print_unique_form_of_education",new PrintUniqueFormOfEducationCommand(collectionManager));
        commandMap.put("count_by_expelled_students",new CountByExpelledStudents(collectionManager));
        commandMap.put("add",new AddCommand(collectionManager));
        commandMap.put("add_if_max",new AddIfMax(collectionManager));
        commandMap.put("execute_script",new ExecuteScriptCommand(this));
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public CollectionManager<StudyGroup> getCollectionManager() {
        return collectionManager;
    }
    private boolean isCommand(String command){
        return commandMap.containsKey(command);
    }
   public ResponseMessage executeCommand(Request request){
        ResponseMessage response = new ResponseMessage();
        String command = request.getCommandName();
        if(isCommand(command)){
            try{
                if(commandMap.get(command).getCommandType() == CommandType.ARGUMENT) {
                    String arg = request.getCommandArgument();
                    commandMap.get(command).setStringArg(arg);
                }
                if(commandMap.get(command).getCommandType() == CommandType.OBJECT_ARGUMENT){
                    StudyGroup studyGroup = request.getStudyGroupObject();
                    commandMap.get(command).setStudyGroup(studyGroup);
                }
                if(commandMap.get(command).getCommandType() == CommandType.ARGUMENT_AND_OBJECT_ARGUMENT){
                    StudyGroup studyGroup = request.getStudyGroupObject();
                    String arg = request.getCommandArgument();
                    commandMap.get(command).setStudyGroup(studyGroup);
                    commandMap.get(command).setStringArg(arg);
                }
                response.setMessage(commandMap.get(command).execute());
            }catch (InvalidDataException | FileException | CommandException | ConnectionException exception){
                response.setMessage(exception.getMessage());
                response.setStatusToError();
            }
        }
        else{
            response.setMessage("no such command");
            response.setStatusToError();
        }
        return response;
   }
}
