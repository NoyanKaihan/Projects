package example.commands;

import example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class UpdateCommand extends Command {
    private CollectionManager<StudyGroup>collectionManager;
    public UpdateCommand(CollectionManager<StudyGroup> collectionManager){
        super("update", CommandAccess.NORMAL, CommandType.ARGUMENT_AND_OBJECT_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        if(!hasStringArg() || !hasStudyGroup())throw new MissedCommandArgumentException();
        try {
            Integer id = Integer.parseInt(getStringArg());
            if(!collectionManager.getUniqueIds().contains(id))throw new InvalidCommandArgumentException("no such id");
            boolean success = collectionManager.update(id,getStudyGroup());
            if(success) return "element #"+id + " has been updated!!";
            else throw new CommandException("can't update");
        }catch (InvalidNumberException  exception){
            throw new InvalidCommandArgumentException("can't parse id");
        }catch (InvalidDataException exception){
            throw new CommandException("can't update the object");
        }
    }
}
