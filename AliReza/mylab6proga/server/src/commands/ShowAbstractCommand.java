package commands;

import managers.CollectionManager;

import java.util.concurrent.atomic.AtomicReference;

public class ShowAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public ShowAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }



    public String execute(String argument) {
        AtomicReference<String> answer = new AtomicReference<>();
        if (!argument.equals("")) return ("this command can't have argument");
        if (collectionManager.getCollection().isEmpty()) return ("collection is empty");
        answer.set ("\n\t\t\t\t\t\t\t\tAll products in collection : ");
        collectionManager.getCollection().forEach((key, product) -> {
            answer.set( answer.get()+"\n"+"-".repeat(70)+"\nkey of product in collection: "+key+"\n"+product);
        });
        return answer.get();
    }

    @Override
    public String getDescription() {
        return "output to the standard output stream all the elements of the collection in a string representation.";
    }
}
