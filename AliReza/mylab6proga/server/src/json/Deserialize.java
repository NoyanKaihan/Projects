package json;

import com.google.gson.*;
import managers.CollectionManager;
import models.Product;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class Deserialize implements JsonDeserializer<Hashtable<Integer, Product>> {
    /**
     * A HashSet used to store products IDs during deserialization
     */
    private final HashSet<Long> ids;
    /**
     * An object of class {@code CollectionManager} to access the method setId(HashSet<long>)
     */
    private final CollectionManager collectionManager;
    /**
     * An integer that shows the number of invalid products
     */
    private int damagedElements = 0;
    public Deserialize(CollectionManager collectionManager) {
        this.ids = new HashSet<>();
        this.collectionManager = collectionManager;
    }

    /**
     * A method for deserializing a json file to collection(HashTable<Integer,Product>
     */
    @Override
    public Hashtable<Integer, Product> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        Hashtable<Integer, Product> collection = new Hashtable<>();
        JsonObject object = jsonElement.getAsJsonObject();
        HashMap<Integer, JsonElement> products = hashMapWithValidKeys(object);

        products.forEach((key, jsonProduct)->{
            Product product = null;
            try{
                isNoEmptyProduct(jsonProduct);
                productHasId(jsonProduct);

                product = (Product) jsonDeserializationContext.deserialize(jsonProduct, Product.class);
                Long id = product.getId();

                isUniqueId(id);
                isValidProduct(id,product);

                ids.add(id);
                collection.put(key,product);
            }catch (JsonParseException exception){
                incrementDamaged();
            }
        });
        isEmptyCollection(collection);
        countDamagedElements();
        collectionManager.setIds(ids);
        return collection;
    }

    /**
     * A method that prints number of damaged products
     */
    private void countDamagedElements(){
        if (damagedElements != 0)
            System.err.println(damagedElements + " elements in database are damaged");

    }
    
    private void isEmptyCollection(Hashtable<Integer, Product> collection){
        if (collection.isEmpty()) {
            if (damagedElements == 0) System.err.println("File is empty");
            else System.err.println("all elements in File are damaged");
            throw new JsonParseException("no data");
        }
    }
    private void incrementDamaged(){
        damagedElements +=1;
    }

    private HashMap<Integer,JsonElement> hashMapWithValidKeys(JsonObject object){
        HashMap<Integer,JsonElement> products = new HashMap<>();
        object.keySet().forEach(key -> {
            try{
                products.put(Integer.parseInt(key),object.get(key));
            }catch (NumberFormatException exception){
                System.err.println("an invalid key has been found #"+ key);
            }
        });
        return products;
    }
    private void isNoEmptyProduct(JsonElement jsonProduct){
        if(jsonProduct.getAsJsonObject().entrySet().isEmpty()){
            System.err.println("empty product found");
            throw new JsonParseException("empty product");
        }
    }
    private void productHasId(JsonElement jsonProduct){
        if (!jsonProduct.getAsJsonObject().has("id")) {
            System.err.println("found product without id");
            throw new JsonParseException("no id");
        }
    }
    private void isUniqueId(Long id){
        if (ids.contains(id)) {
            System.err.println("collection already contains product with id #" + id);
            throw new JsonParseException("id isn't unique");
        }
    }
    private void isValidProduct(Long id,Product product){
        if (!product.validate()) {
            System.err.println("product #" + Long.toString(id) + " doesnt match specific conditions");
            throw new JsonParseException("invalid product data");
        }
    }
}
