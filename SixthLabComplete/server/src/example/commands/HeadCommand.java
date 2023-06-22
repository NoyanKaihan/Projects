package example.commands;

import example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class HeadCommand extends Command {
    private CollectionManager<StudyGroup> collectionManager;
    public HeadCommand(CollectionManager<StudyGroup>collectionManager){
        super("head", CommandAccess.NORMAL,CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        return collectionManager.collectionHead().toString();
    }
}
