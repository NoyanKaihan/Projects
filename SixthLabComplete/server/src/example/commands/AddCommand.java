package example.commands;

import example.collection.CollectionManager;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.StudyGroup;

public class AddCommand extends Command {
    private CollectionManager<StudyGroup> collectionManager;
    public AddCommand(CollectionManager<StudyGroup>collectionManager){
        super("add", CommandAccess.NORMAL,CommandType.OBJECT_ARGUMENT);
        this.collectionManager = collectionManager;
    }
    public CollectionManager<StudyGroup> getManager (){
        return collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        getManager().add(getStudyGroup());
        return "Added element: "+getStudyGroup().toString();
    }
}
