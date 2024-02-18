package commands;

import collection.StudyGroupCollectionManager;
import console.ConsoleColor;
import exceptions.CommandException;
import exceptions.DataException;
import exceptions.EmptyCollectionException;
import file.FileManager;
import input.ConsoleInputManager;
import input.FileInputManager;
import input.InputManager;

import java.util.*;

/**
 * CommandManager class for managing commands
 */
public class CommandManager implements Command {

    private Map<String, CommandExecute> commandCollection;
    private StudyGroupCollectionManager collectionManager;
    private InputManager inputManager;
    private FileManager fileManager;
    private boolean isRunning;
    private String currentScriptFileName;
    private static Stack<String> callStack = new Stack<>();

    /**
     * Constructor calls addCommand method adding commands in Map for command store
     *
     * @param cManager
     * @param iManager
     * @param fManager
     */
    public CommandManager(StudyGroupCollectionManager cManager, InputManager iManager, FileManager fManager) {

        isRunning = false;
        this.inputManager = iManager;
        this.collectionManager = cManager;
        this.fileManager = fManager;

        currentScriptFileName = "";
        commandCollection = new HashMap<>();
        addCommand("help", (argument) -> {
            for (int i = 0; i < getCommands().size() && i < getDescription().size(); i++) {
                System.out.print(ConsoleColor.CYAN + getCommands().get(i) + ConsoleColor.RESET + getDescription().get(i));
                System.out.println();
            }
        });
        addCommand("info", (argument) -> System.out.println(collectionManager.getInfo()));
        addCommand("show", (argument) -> {
            if (collectionManager.getCollection().isEmpty())
                System.out.print(ConsoleColor.RED_BACKGROUND + "collection is empty" + ConsoleColor.RESET);
            else {
                System.out.print(ConsoleColor.YELLOW);
                collectionManager.getCollection().forEach(System.out::println);
                System.out.print(ConsoleColor.RESET);
            }
        });
        addCommand("add", (argument) -> {
            try {
                collectionManager.add(inputManager.readStudyGroup());
            } catch (DataException exception) {
                System.out.println(exception.getMessage());
            }
        });
        addCommand("update", (argument) -> {
            int id = 0;
            if (argument == null || argument.equals("")) {
                System.err.println("No arguments");
            }
            try {
                id = Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            if (collectionManager.getCollection().isEmpty())
                throw new EmptyCollectionException(ConsoleColor.RED_BACKGROUND + "Collection is empty" + ConsoleColor.RESET + "'\n");
            if (!collectionManager.isIdInCollection(id))
                throw new DataException(ConsoleColor.RED_BACKGROUND + "No such Id" + ConsoleColor.RESET + "\n");
            collectionManager.update(id, inputManager.readStudyGroup());
        });
        addCommand("remove_by_id", (argument) -> {
            int id = 0;
            if (argument == null || argument.equals("")) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "argument can't be null :( " + ConsoleColor.RESET + "\n");
            }
            try {
                id = Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                throw new DataException("id must be integer");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException("Collection is empty");
            if (!collectionManager.isIdInCollection(id))
                throw new DataException(ConsoleColor.RED_BACKGROUND + argument + " is not id in collection" + ConsoleColor.RESET + "\n");
            collectionManager.removeById(id);
        });
        addCommand("clear", (argument) -> {
            collectionManager.clear();
            System.out.print(ConsoleColor.GREEN_BACKGROUND + "Collection has been successfully cleared :)" + ConsoleColor.RESET + "\n");
        });
        addCommand("save", (argument) -> {
            if (!(argument == null || argument.equals(""))) fileManager.setPath(argument);
            if (!fileManager.write(collectionManager.csvSerializer()))
                throw new CommandException("can't save the file");
            else
                System.out.print(ConsoleColor.GREEN_BACKGROUND + "Data has been successfully saved :)" + ConsoleColor.RESET + "\n");
        });
        addCommand("execute_script", (argument) -> {
            if (argument == null || argument.equals("")) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "File is empty" + ConsoleColor.RESET + "\n");
            }

            if (callStack.contains(currentScriptFileName))
                throw new DataException(ConsoleColor.RED_BACKGROUND + "Already in use" + ConsoleColor.RESET + "\n");

            callStack.push(currentScriptFileName);
            CommandManager process = new CommandManager(collectionManager, inputManager, fileManager);
            process.fileMode(argument);
            callStack.pop();
            System.out.print(ConsoleColor.GREEN_BACKGROUND + "successfully executed script :)" + argument + ConsoleColor.RESET + '\n');

        });
        addCommand("exit", (argument) -> isRunning = false);
        addCommand("add_if_max", (argument) -> collectionManager.addIfMax(inputManager.readStudyGroup()));
        addCommand("head", (argument) -> {
            if (!collectionManager.getCollection().isEmpty())
                System.out.print(ConsoleColor.YELLOW + "" + collectionManager.collectionHead() + ConsoleColor.RESET + "\n");
        });
        addCommand("add_if_max", (argument) -> {
            collectionManager.addIfMax(inputManager.readStudyGroup());
        });
        addCommand("remove_greater", (argument) -> {
            collectionManager.removeGreater(inputManager.readStudyGroup());
        });
        addCommand("count_by_expelled_students", (argument) -> {
            long expelled;
            try {
                expelled = Long.parseLong(argument);
                if (expelled <= 0)
                    throw new DataException(ConsoleColor.RED_BACKGROUND + "expelled students should be greater than 0 :(" + ConsoleColor.RESET + "\n");
            } catch (NumberFormatException exception) {
                throw new DataException(ConsoleColor.RED_BACKGROUND + "Invalid :(" + ConsoleColor.RESET + "\n");
            }
            if (collectionManager.countByExpelledStudents(expelled) == 0)
                throw new DataException(ConsoleColor.RED_BACKGROUND + "No expelled students found with value of #" + argument + ConsoleColor.RESET + "\n");
            System.out.println(collectionManager.countByExpelledStudents(expelled));
        });
        addCommand("print_unique_form_of_education", (argument) -> {
            if (collectionManager.getCollection().isEmpty())
                System.out.print(ConsoleColor.RED_BACKGROUND + "Collection is empty" + ConsoleColor.RESET + "\n");
            else {
                System.out.print(ConsoleColor.GREEN_BOLD);
                System.out.println("Unique form of education 's value : " + ConsoleColor.RESET);
                System.out.print(ConsoleColor.YELLOW);
                collectionManager.uniqueFormOfEducation().forEach(System.out::println);

                System.out.println(ConsoleColor.RESET);
            }
        });
        addCommand("print_field_descending_group_admin", (argument) -> {
            System.out.print(ConsoleColor.YELLOW);
            collectionManager.descendingGroupAdmin().forEach(System.out::println);
            System.out.println(ConsoleColor.RESET);
        });
    }


    public void addCommand(String key, CommandExecute c) {
        commandCollection.put(key, c);
    }


    public void runCommand(String s, String arg) {
        try {
            if (!hasCommand(s))
                throw new CommandException(ConsoleColor.RED_BACKGROUND + "No such command" + ConsoleColor.RESET);
            commandCollection.get(s).execute(arg);
        } catch (CommandException | DataException | EmptyCollectionException e) {
            System.err.println(e.getMessage());
        }
    }

    public void runCommand(String s) {
        runCommand(s, null);
    }

    public boolean hasCommand(String s) {
        return commandCollection.containsKey(s);
    }

    public void consoleMode() {
        inputManager = new ConsoleInputManager();
        isRunning = true;
        while (isRunning) {
            System.out.print(ConsoleColor.CYAN_BOLD);
            System.out.print("\nEnter command (help to get command list): ");
            System.out.println(ConsoleColor.RESET);
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
        }
    }

    /**
     * Method to get input from file
     *
     * @param path
     */
    public void fileMode(String path) {
        currentScriptFileName = path;
        isRunning = true;
        inputManager = new FileInputManager(path);
        isRunning = true;
        while (isRunning && inputManager.getScanner().hasNextLine()) {
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
        }
    }

    /**
     * Command store method
     *
     * @return Arraylist containing command Names
     */
    public ArrayList<String> getCommands() {
        return new ArrayList<>(Arrays.asList("help",
                "info",
                "show ",
                "add",
                "update",
                "remove_by_id",
                "clear",
                "save",
                "execute_script",
                "exit",
                "head",
                "add_if_max",
                "remove_greater",
                "count_by_expelled_students",
                "print_unique_form_of_education",
                "print_field_descending_group_admin"));
    }

    /**
     * Description store method
     *
     * @return Arraylist Containing command Descriptions
     */
    public ArrayList<String> getDescription() {
        return new ArrayList<>(Arrays.asList(": display help on available commands",
                " {element}: print information about the collection to standard output" +
                        " (type, initialization date, number of elements, etc.)",
                ": print to standard output all elements of the collection in string representation",
                ": add a new element to the collection",
                " id {element}: update the value of the collection element whose id is equal to the given one",
                " id: remove an element from the collection by its id",
                ": clear collection",
                ": save collection to file",
                " file_name: read and execute a script from the specified file." +
                        " The script contains commands in the same form in which they are entered by the user in interactive mode.",
                ": end program (without saving to file)",
                ": display the first element of the collection",
                " {element}: add a new element to the collection if its value is" +
                        " greater than the value of the largest element in this collection",
                " {element}: remove from the collection all elements greater than the given",
                " expelledStudents: display the number of elements whose expelledStudents " +
                        " field value is equal to the given one",
                ": display the unique values of the formOfEducation field of all elements in the collection",
                ": display groupAdmin field values of all elements in descending order"));
    }
}
