package commands;

import exceptions.ArgumentException;
import managers.CommandManager;

import java.util.concurrent.atomic.AtomicReference;

public class HelpAbstractCommand extends AbstractCommand {

    private final CommandManager commandManager;


    public HelpAbstractCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String execute(String argument) {
        AtomicReference<String> answer = new AtomicReference<>();
        if (!argument.equals("")) throw new ArgumentException("this command can't have argument");
        answer.set("help" + "\t".repeat(6) + getDescription());
        commandManager.getCommandMap().forEach((s, command) -> {
            if (!s.equals("help"))  answer .set( answer.get()+"\n"+s+"\t".repeat((31-s.length())/4)+command.getDescription());
        });
        return answer.get();
    }


    @Override
    public String getDescription() {
        return "display help for available commands.";
    }
}
