package commands;

import managers.CollectionManager;


public class InfoAbstractCommand extends AbstractCommand {

    private final CollectionManager collectionManager;


    public InfoAbstractCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }



    public String execute(String argument) throws RuntimeException {
        String answer;
        if (!argument.equals("")) return "this command can't have argument";
        answer = collectionManager.collectionInfo();
        return answer;
    }


    @Override
    public String getDescription() {
        return "out to the standard output stream information about the collection (type, initialization date, number of elements, etc.).";
    }
}
