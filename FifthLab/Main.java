import collection.StudyGroupCollectionManager;
import commands.CommandManager;
import console.ConsoleColor;
import file.FileManager;
import input.ConsoleInputManager;
import input.InputManager;
import modules.StudyGroup;

import java.util.LinkedList;

public class Main {
    private final static String FILE = System.getenv("file");

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        StudyGroupCollectionManager studyGroupCollectionManager = new StudyGroupCollectionManager();
        fileManager.setPath(FILE);
        if (studyGroupCollectionManager.csvDeserializer(fileManager.read())) {
            studyGroupCollectionManager.csvDeserializer(fileManager.read());
            System.out.print(ConsoleColor.GREEN_BACKGROUND);
            System.out.print("Data from file has been Successfully loaded :)");
            System.out.println(ConsoleColor.RESET);
            System.out.println();
        } else {
            System.out.print(ConsoleColor.RED_BACKGROUND);
            System.out.print("Data can't be loaded :(");
            System.out.println(ConsoleColor.RESET);
            System.out.println();
        }
        InputManager console = new ConsoleInputManager();
        CommandManager commandManager = new CommandManager(studyGroupCollectionManager, console, fileManager);
        commandManager.consoleMode();

    }
}