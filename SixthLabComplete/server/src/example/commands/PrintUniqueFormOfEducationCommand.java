package example.commands;

import example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class PrintUniqueFormOfEducationCommand extends Command {
    private CollectionManager<StudyGroup> collectionManager;
    public PrintUniqueFormOfEducationCommand(CollectionManager<StudyGroup>collectionManager){
        super("print_unique_form_of_education", CommandAccess.NORMAL, CommandType.NON_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        if(collectionManager.uniqueFormOfEducation().isEmpty())throw new CommandException("no unique form of education found ");
        return collectionManager.uniqueFormOfEducation().toString();
    }
}
