package example.commands;

import example.collection.CollectionManager;
import org.example.modules.Person;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
import java.util.stream.Collectors;

public class PrintDescendingGroupAdminCommand extends Command{
    private CollectionManager<StudyGroup> collectionManager;
    public PrintDescendingGroupAdminCommand(CollectionManager<StudyGroup>collectionManager){
        super("print_field_descending_group_admin", CommandAccess.NORMAL,CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        if(collectionManager.descendingGroupAdmin().isEmpty())throw new CommandException("no group admin are currently stored in collection");
        return collectionManager.descendingGroupAdmin().stream().map(Person::toString).collect(Collectors.joining("\n"));
    }
}
