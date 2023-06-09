package org.example.commands;

import org.example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class RemoveGreaterCommand extends Command{
    private CollectionManager<StudyGroup>collectionManager;
    public RemoveGreaterCommand(CollectionManager<StudyGroup> collectionManager){
        super("remove_greater", CommandAccess.NORMAL,CommandType.OBJECT_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        if(!hasStudyGroup()) throw new MissedCommandArgumentException();
        try {
            collectionManager.removeGreater(getStudyGroup());
            return "all elements greater than the given has been removed";
        }catch (InvalidDataException e) {
            throw new CommandException("can't execute the command");
        }
    }
}
