package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import exceptions.ArgumentException;
import exceptions.SerializeException;
import json.ZonedDateTimeSerializer;
import managers.CollectionManager;
import managers.FileManager;
import models.Product;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Hashtable;


public class SaveAbstractCommand extends AbstractCommand {

    private final FileManager fileManager;

    private final CollectionManager collectionManager;


    public SaveAbstractCommand(FileManager fileManager, CollectionManager collectionManager) {
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        Type collectionType = new TypeToken<Hashtable<Integer, Product>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
                .setPrettyPrinting()
                .create();
        if (!fileManager.write(gson.toJson(collectionManager.getCollection(), collectionType)))
            throw new SerializeException("can't serialize collection ");
        return "collection has been saved in file\n"+"-".repeat(90)+"\n";
    }


    @Override
    public String getDescription() {
        return "save the collection to a file.";
    }
}
