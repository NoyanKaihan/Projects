package commands;

import exceptions.*;
import managers.CollectionManager;



public class UpdateAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;



    public UpdateAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        String answer="";
        if (collectionManager.getCollection().isEmpty()) {
            answer =  ("collection is empty , there is no products to replace");
        }

        if (!argument.equals("")) {
            try {
                long id = Long.parseLong(argument);
                if (id <= 0) throw new InvalidKeyException("id should be greater than 0 ");
                if (collectionManager.getIds().contains(id)) {
                    if (collectionManager.updateProduct(id, getProduct())) {
                        answer = ("\n\t\t\t\t\t\t\t\tThe product with ID \"" + id + "\" has been updated");
                    }
                } else {
                    answer =("\nno product has been found with id \"" + id + "\"");
                    answer += ("\nList of product IDs in the collection :\n" + collectionManager.getIds());
                }
            } catch (NumberFormatException exception) {
                answer =("Invalid ID!!!");
            }
        } else answer =("No argument!!!");
        return answer;
    }


    @Override
    public String getDescription() {
        return "update the value of a collection element whose id is equal to the specified one.";
    }
}
