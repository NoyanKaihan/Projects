package commands;

import managers.CollectionManager;


public class RemoveKeyAbstractCommand extends AbstractCommand {


    private final CollectionManager collectionManager;


    public RemoveKeyAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        String answer;
        if (collectionManager.getCollection().isEmpty())
            return  ("collection is empty , there is no products to remove");
        if (!argument.equals("")) {
            try {
                Integer key = Integer.parseInt(argument);
                if (collectionManager.removeKey(key)) {
                    answer = "\n\t\t\t\t\t\t\t\tA product with the key \""+ argument+"\" was successfully removed from the collection\n";
                } else {
                    answer = "There is no product with key \""+ argument+"\" in the collection\n";
                    answer += "List of keys that exist in collection : \n" + collectionManager.getCollection().keySet();
                }
            } catch (NumberFormatException exception) {
                answer = ("Invalid key!!!");
            }
        } else answer=("No argument!!!");
        return answer;
    }


    @Override
    public String getDescription() {
        return "remove an item from the collection by its key.";
    }
}
