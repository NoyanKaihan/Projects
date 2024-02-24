package commands;

import managers.CollectionManager;


public class ClearCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        String answer ;
        if (collectionManager.getCollection().isEmpty())
           answer = "The collection is already empty";
        else {
            collectionManager.clear();
            answer = "\n\t\t\t\t\t\t\t\tcollection successfully cleared";
        }
        return answer;
    }

    @Override
    public String getDescription() {
        return "Clear the collection.";
    }
}
