package example.starter;

import example.collection.CollectionManager;
import example.collection.StudyGroupCollectionManager;
import example.commands.CommandManager;
import example.server.Server;
import org.example.console.ConsoleColor;
import org.example.file.FileManager;
import org.example.modules.StudyGroup;


public abstract class Starter {
    private final static String FILE = System.getenv("file");
    //private final static String PORT = System.getenv("port");
    private final static FileManager fileManager = new FileManager(FILE);
    public static void start(){
        String PORT = "1290";
        int port = Integer.parseInt(PORT);
        try {
            CollectionManager<StudyGroup> collectionManager = new StudyGroupCollectionManager();
            if(!fileManager.read().equals("")) {
                if (collectionManager.deserialize(fileManager.read()))
                    System.out.println(ConsoleColor.GREEN_BACKGROUND + "Collection successfully deserialized" + ConsoleColor.RESET + "\n");
                else
                    System.out.println(ConsoleColor.RED_BACKGROUND + "Can't deserialize collection" + ConsoleColor.RESET + "\n");
            }else System.out.println(ConsoleColor.RED_BACKGROUND+"File was empty"+ConsoleColor.RESET+"\n");
            CommandManager commandManager = new CommandManager(collectionManager);
            Server server = new Server(port, commandManager);
            server.listenToClient();
        }catch (Exception exception){
            System.out.println(ConsoleColor.RED_BACKGROUND+exception.getMessage()+ConsoleColor.RESET+"\n");
        }
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
