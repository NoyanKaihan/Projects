package example.commands;

import org.example.connection.Request;
import org.example.connection.RequestMessage;
import org.example.connection.Response;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.StudyGroup;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import example.stringParser.*;
public class ExecuteScriptCommand extends Command {
    private CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", CommandAccess.NORMAL, CommandType.ARGUMENT);
        this.commandManager = commandManager;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        AtomicReference<String> result = new AtomicReference<>("");

        String arg = getStringArg();

        // TODO: Create a regular expression pattern to match the desired parts
        String pattern = "commandName='(.*?)', commandArgument='(.*?)', studyGroup=(.*?)\\n";

        Pattern regexPattern = Pattern.compile(pattern);

        Matcher matcher = regexPattern.matcher(arg + "\n");

        // TODO:Iterate over the matches and extract the desired parts
        while (matcher.find()) {
            String commandName = matcher.group(1);
            String commandArgument = matcher.group(2);
            String studyGroup = matcher.group(3);

            result.set(result.get() + "\n" + commandExecutor(commandName, commandArgument, studyGroup));

        }

        return result.get();
    }

    //TODO: Returns result of execution of every Command
    private String commandExecutor(String command, String argument, String studyGroup) {
        String s = "";
        if (!argument.equals("null") && !studyGroup.equals("null")) {
            Request request = new RequestMessage(command, argument, parser(studyGroup));
            Response response = commandManager.executeCommand(request);
            s = response.getMessage();
        }
        if (!argument.equals("null") && studyGroup.equals("null")) {
            Request request = new RequestMessage(command, argument, null);
            Response response = commandManager.executeCommand(request);
            s = response.getMessage();
        } if(command.equals("null")){
            s = "what happened ??";
        }
        else {
            Request request = new RequestMessage(command, null, null);
            Response response = commandManager.executeCommand(request);
            s = response.getMessage();
        }
        return s;
    }

    private StudyGroup parser(String studyGroup) {
        return new StringStudyGroupParser(studyGroup).parseStudyGroup();
    }
}
