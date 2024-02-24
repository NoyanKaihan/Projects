package commands;

import managers.CollectionManager;


public class ReplaceIfLoweAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;



    public ReplaceIfLoweAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        String answer = null;
        if (collectionManager.getCollection().isEmpty())
            answer = ("collection is empty , there is no products to replace");
        if (!argument.equals("")) {
            try {
                int key = Integer.parseInt(argument);
                if (containsKey(key)) {
                    answer = ("Product information with key \"" + key + "\" :");
                    answer += (collectionManager.getCollection().get(key) + "\n" + "-".repeat(70));
                    if (collectionManager.replaceIfLowe(key, getProduct())) {
                        answer +=("\n\t\t\t\t\t\t\t\tThe inputted product successfully replaced");
                    } else
                        answer +=("The inputted product is NOT LOWER than a product with key \"" + key + "\"");
                } else {
                        answer +=("" + collectionManager.getCollection().keySet());
                   answer +=("This key \"" + argument + "\" not exist in the collection!!!");
                }
            } catch (NumberFormatException exception) {
                answer= ("Invalid KEY!!!");
            }
        } else answer = ("No argument!!!");
        return answer;
    }


    private boolean containsKey(int key) {
        return collectionManager.getCollection().keySet().contains(key);
    }


    @Override
    public String getDescription() {
        return "replace the value by key if the new value is less than the old.";
    }
}
