package commands;

import managers.CollectionManager;
import models.Product;


public class InsertAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;


    public InsertAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;

    }

    @Override
    public String execute(String argument) {
        String answer="";
        if (!argument.equals("")) {
            try {
                int key = Integer.parseInt(argument);
                Product product = getProduct();
                product.setId(collectionManager.generateId());
                if (collectionManager.insert(key, product)) {
                    answer +=("\n"+"\t".repeat(7)+"The inputted product has been added to the collection");
                }
            } catch (NumberFormatException exception) {
               answer = "Invalid \"KEY\" , The key must be Integer!!!";
            }
        } else answer =  "No argument!!!";
        return answer;
    }

    public String getDescription() {
        return "add a new element with the specified key.";
    }
}

