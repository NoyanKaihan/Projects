package example.commands;


import example.collection.CollectionManager;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.StudyGroup;

public class AddIfMax extends Command{
    private CollectionManager<StudyGroup> collectionManager;
    public AddIfMax(CollectionManager<StudyGroup> collectionManager){
        super("add_if_max", CommandAccess.NORMAL,CommandType.OBJECT_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        boolean success;
        try {
            success = collectionManager.addIfMax(getStudyGroup());
            if(success) return ("Added element : "+getStudyGroup().toString());
            else throw new CommandException("can't be added");
        }catch (InvalidDataException exception){
            throw new InvalidDataException("unsuccessful");
        }
    }
}
