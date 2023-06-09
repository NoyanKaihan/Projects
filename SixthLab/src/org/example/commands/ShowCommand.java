package org.example.commands;

import org.example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
import java.util.stream.Collectors;

public class ShowCommand extends Command{
    private CollectionManager<StudyGroup> collectionManager;
    public ShowCommand(CollectionManager<StudyGroup>collectionManager){
        super("show", CommandAccess.NORMAL,CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        return collectionManager.getCollection().stream().map(StudyGroup::toString).collect(Collectors.joining("\n"));
    }
}
