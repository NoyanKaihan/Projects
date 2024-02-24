package json;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Hashtable;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import managers.*;
import models.Product;

/**
 * A class is used to deserialize the collection
 */
public class CollectionDeserialize {
    private final CollectionManager collectionManager;

    public CollectionDeserialize(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * A method that extracts product objects from the string it was sent to and places them in the collection
     *
     * @param json It's a string that is the contents of a file from which product objects must be extracted
     * @return If the objects of the products have been successfully
     * extracted from the string and placed in the collection,method returns true
     */
    public boolean deserialize(String json) {
        boolean sucess = false;
        try {
            if (json == null || json.equals("")) {
                collectionManager.setCollection(new Hashtable<>());
            } else {
                Type collectionType = new TypeToken<Hashtable<Integer, Product>>() {
                }.getType();
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer()).
                        registerTypeAdapter(collectionType, new Deserialize(collectionManager))
                        .create();

                collectionManager.setCollection(gson.fromJson(json.trim(), collectionType));
                sucess = true;
            }
        } catch (JsonParseException exception) {
            System.err.println("Invalid json file format!!!");
        }
        return sucess;
    }
}
