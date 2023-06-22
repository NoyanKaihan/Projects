package example.commands;

import example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class RemoveByIdCommand extends Command {
    private CollectionManager<StudyGroup>collectionManager;
    public RemoveByIdCommand(CollectionManager<StudyGroup>collectionManager){
        super("remove_by_id", CommandAccess.NORMAL, CommandType.ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException("collection is empty");
        if(!hasStringArg())throw new MissedCommandArgumentException();
        try {
            Integer id = Integer.parseInt(getStringArg());
            if(!collectionManager.getUniqueIds().contains(id))throw new InvalidCommandArgumentException("no such id ");
            boolean success = collectionManager.removeById(id);
            if(success) return "element #"+id+"has been removed from the collection";
            else throw new CommandException("can't remove");
        }catch (InvalidNumberException exception){
            throw new InvalidCommandArgumentException("Invalid id");
        }catch (InvalidDataException  exception){
            throw new CommandException("can't remove element ");
        }
    }
}
