package org.example.commands;


import org.example.collection.CollectionManager;
import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.InvalidDataException;
import org.example.file.FileManager;
import org.example.modules.StudyGroup;

public class SaveCommand extends Command {
    private FileManager fileManager;
    private CollectionManager<StudyGroup> collectionManager;

    public SaveCommand(CollectionManager<StudyGroup> collectionManager, FileManager fileManager) {
        super("save", CommandAccess.SERVER,CommandType.NON_ARGUMENT);
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        if (hasStringArg()) {
            fileManager.setPath(getStringArg());
        }
        if (fileManager.write(collectionManager.serialize()))
            return "collection has been successfully saved";
        else return "saving was unsuccessful";
    }
}
