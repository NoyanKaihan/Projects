package example.commands;


import example.collection.CollectionManager;
import org.example.exceptions.*;
import org.example.modules.StudyGroup;

public class ClearCommand extends Command {
    private CollectionManager<StudyGroup> collectionManager;
    public ClearCommand(CollectionManager<StudyGroup>collectionManager){
        super("clear", CommandAccess.NORMAL,CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        collectionManager.clear();
        return "collection has been cleared";
    }
}
