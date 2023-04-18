package commands;

public interface Command {
    /**
     * adds command
     * @param commandName command name
     * @param commandFunctionality command callback
     */
    public void addCommand(String commandName, CommandExecute commandFunctionality);

    /**
     * executes command with argument
     * @param key command name
     * @param arg
     */
    public void runCommand(String key, String arg);

    /**
     * executes command without argument
     * @param key
     */
    public void runCommand(String key);

    public boolean hasCommand(String s);

    /**
     * runs in command interpreter in console
     */
    public void consoleMode();

    /**
     * executes script from file
     * @param path
     */
    public void fileMode(String path);
}
