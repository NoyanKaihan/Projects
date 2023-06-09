package org.example.commands;


import org.example.collection.CollectionManager;
import org.example.modules.StudyGroup;
import org.example.exceptions.*;
public class CountByExpelledStudents extends Command{
    private CollectionManager<StudyGroup> collectionManager;
    public CountByExpelledStudents(CollectionManager<StudyGroup>collectionManager){
        super("count_by_expelled_students", CommandAccess.NORMAL,CommandType.ARGUMENT);
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if(collectionManager.getCollection().isEmpty())throw new EmptyCollectionException("collection is empty");
        if(!hasStringArg())throw new MissedCommandArgumentException();
        try{
            long expelledStudents = Long.parseLong(getStringArg());
            try {
                int count = collectionManager.countByExpelledStudents(expelledStudents);
                return "there are "+count+ " studyGroups that their number of expelledStudents are the same";
            }catch (InvalidDataException exception){
                throw new InvalidCommandArgumentException("the passed expelled student is not valid");
            }
        }catch (InvalidCommandArgumentException exception) {
            throw new CommandException(exception.getMessage());
        }
    }
}
