package example.commands;

import example.collection.CollectionManager;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class InfoCommand extends Command {
    private CollectionManager<StudyGroup> collectionManager;
    public InfoCommand(CollectionManager<StudyGroup> collectionManager){
        super("info", CommandAccess.NORMAL, CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
       return collectionManager.getInfo();
    }
}
