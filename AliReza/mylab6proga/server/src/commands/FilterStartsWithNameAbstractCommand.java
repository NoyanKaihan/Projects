package commands;

import managers.CollectionManager;
import models.Product;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class FilterStartsWithNameAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;


    public FilterStartsWithNameAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        AtomicReference<String> answer = new AtomicReference<>("");
        if (collectionManager.getCollection().isEmpty()) answer .set("collection is empty");
        if (!argument.equals("")) {
            ArrayList<Product> products = collectionManager.filterStartsWithName(argument);
            if (!products.isEmpty()) {
                answer.set(answer.get()+"\n\t\t\t\t\t\t\t\tName the following product(s) starts whit the String \"" + argument + "\" :");
                products.forEach(product -> {
                    answer.set(answer.get()+"\n"+("-".repeat(70) + "\n" + product));
                });
            } else answer.set("no product found which it's name field value starts with \"" + argument + "\"");
        } else answer .set("No argument!!!");
        return answer.get();
    }


    @Override
    public String getDescription() {
        return "output elements whose name field value starts with the specified substring.";
    }
}
