package commands;

/**
 * Class for Command wrapping
 */
public class CommandWrapper {
    private final String argument;
    private final String command;

    /**
     * Argumentative commands
     * @param command
     * @param argument
     */
    public CommandWrapper(String command, String argument){
        this.argument = argument;
        this.command = command;
    }

    /**
     * Non-Argumentative commands
     * @param command
     */
    public CommandWrapper(String command){
        argument = null;
       this.command = command;
    }

    /**
     * Method for getting command
     * @return String representing commands
     */
    public String getCommand(){
        return command;
    }

    /**
     * Method for getting argument
     * @return String representing arguments
     */
    public String getArg(){
        return argument;
    }
}
