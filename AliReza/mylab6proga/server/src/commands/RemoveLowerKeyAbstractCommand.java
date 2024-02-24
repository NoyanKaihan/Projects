package commands;

import managers.CollectionManager;


public class RemoveLowerKeyAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public RemoveLowerKeyAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        String answer;
        if (collectionManager.getCollection().isEmpty())
            return "collection is empty , there is no products to remove";
        if (!argument.equals("")) {
            int primarySize = collectionManager.getCollection().size();
            try {
                Integer key = Integer.parseInt(argument);
                if (collectionManager.removeLowerKey(key)) {
                    int productRemoved = primarySize - collectionManager.getCollection().size();
                    answer = "\n\t\t\t\t\t\t\t\t" + productRemoved + " product(s) with key lower than \"" + argument + "\" were successfully removed from the collection";
                } else answer = "No product found that its key is lower then the \"" + argument + "\"";
            } catch (NumberFormatException exception) {
                answer = ("Invalid KEY!!!");
            }
        } else answer = ("No argument!!!");
        return answer;
    }


    @Override
    public String getDescription() {
        return "remove from the collection all elements whose key is less than the specified one.";
    }
}
