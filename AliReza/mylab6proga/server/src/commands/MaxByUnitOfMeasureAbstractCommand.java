package commands;

import managers.CollectionManager;
import models.Product;


public class MaxByUnitOfMeasureAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;


    public MaxByUnitOfMeasureAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        String answer;
        if (!argument.equals("")) return "this command can't have argument";
        if (collectionManager.getCollection().isEmpty()) return  "collection is empty";
        Product productWithMaxUnitOfMeasure = collectionManager.maxByUnitOfMeasure();
        if (productWithMaxUnitOfMeasure != null) {
            answer = "\n"+"\t".repeat(8)+"The product bottom has maximum UnitOfMeasure value : \n" + productWithMaxUnitOfMeasure;
        } else answer = ("The unit of measure field value for all products in collection is null.");
        return answer;
    }


    @Override
    public String getDescription() {
        return "output any object from the collection whose UnitOfMeasure field value is the maximum.";
    }
}
