package commands;

import managers.CollectionManager;
import models.Product;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FilterContainsPartNumberAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public FilterContainsPartNumberAbstractCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        AtomicReference<String> answer = new AtomicReference<>();
        if(collectionManager.getCollection().isEmpty()) answer .set("collection is empty");
        if(!argument.equals("")) {
            List<Product> products = collectionManager.filterContainsPartNumber(argument);
            if (!products.isEmpty()) {
                answer .set( "\n\t\t\t\t\t\t\t\tpartNumber field the following product(s) have the String \"" +argument +"\" :");
                products.forEach(product->{
                    answer.set(answer.get()+"\n"+("-".repeat(70)+"\n"+product));
                });
                answer .set( products.toString());
            } else answer .set( "no product found which it's partNumber field value contains \"" + argument+"\"");
        }else answer.set("No argument!!!");
        return answer.get();
    }


    @Override
    public String getDescription() {
        return "output elements whose partNumber field value contains the specified substring.";
    }
}
