package commands;

import exceptions.CommandException;
import exceptions.DataException;
import exceptions.EmptyCollectionException;

/**
 * Interface for Command 's functionality
 */
public interface CommandExecute {
    /**
     * Method for executing command
     * @param argument
     * @throws DataException
     * @throws EmptyCollectionException
     * @throws CommandException
     */
    void execute(String argument) throws DataException, EmptyCollectionException, CommandException;
}
