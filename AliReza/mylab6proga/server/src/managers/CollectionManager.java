package managers;

import models.Product;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CollectionManager {
    private HashSet<Long> ids;
    private Hashtable<Integer, Product> collection;
    private final ZonedDateTime creationDate;

    public CollectionManager() {
        creationDate = ZonedDateTime.now();
        ids = new HashSet<>();
        collection = new Hashtable<>();
    }


    public void setCollection(Hashtable<Integer, Product> collection) {
        this.collection = collection;
    }

    public void setIds(HashSet<Long> ids) {
        this.ids = ids;
    }

    public HashSet<Long> getIds() {
        return ids;
    }

    public Hashtable<Integer, Product> getCollection() {
        return collection;
    }

    public boolean insert(Integer key, Product product) {
        collection.put(key, product);
        return true;
    }
    public Long generateId() {
        Long id;
        Random random = new Random();
        do {
            id = random.nextLong(1, Long.MAX_VALUE);
        } while (ids.contains(id));
        ids.add(id);
        return id;
    }


    public String collectionInfo() {
        if (collection == null )
            return "collection is empty!";
        return "Hashtable (Integer , Product) of Products       size : " + collection.size() + "       Creation Date : " + creationDate;
    }

    public boolean update(Long id, Product newProduct) {
        AtomicBoolean isUpdated = new AtomicBoolean(false);
        collection.entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(id))
                .findFirst().get();
//                .ifPresent(entry -> {
//                    Product product = entry.getValue();
//                    product.setName(newProduct.getName());
//                    product.setCoordinates(newProduct.getCoordinates());
//                    product.setPrice(newProduct.getPrice());
//                    product.setPartNumber(newProduct.getPartNumber());
//                    product.setManufactureCost(newProduct.getManufactureCost());
//                    product.setUnitOfMeasure(newProduct.getUnitOfMeasure());
//                    product.setOwner(newProduct.getOwner());
//                    collection.put(entry.getKey(), product);
//                    isUpdated.set(true);
//                });
        return isUpdated.get();
//                    .forEach(product -> {
//                        product.setName(newProduct.getName());
//                        product.setCoordinates(newProduct.getCoordinates());
//                        product.setCreationDate(newProduct.getCreationDate());
//                        product.setPrice(newProduct.getPrice());
//                        product.setManufactureCost(newProduct.getManufactureCost());
//                        product.setPartNumber(newProduct.getPartNumber());
//                        product.setUnitOfMeasure(newProduct.getUnitOfMeasure());
//                        product.setOwner(newProduct.getOwner());
//                        isUpdated.set(true);
//                    })
    }
    public boolean updateProduct(long id, Product newProduct) {
        System.out.println("id = " + id);
        System.out.println("newProduct = " + newProduct);
        // Find the product in the collection using Stream API
        Optional<Product> oldProduct = collection.entrySet().stream()
                .filter(entry -> entry.getValue().getId() == id) // Filter by the given id
                .map(entry -> entry.getValue()) // Map to the product value
                .findFirst(); // Find the first match or empty

        // If the product is found, update it with the new product
        if (oldProduct.isPresent()) {
            oldProduct.get().setName(newProduct.getName());
            oldProduct.get().setCoordinates(newProduct.getCoordinates());
            oldProduct.get().setPrice(newProduct.getPrice());
            oldProduct.get().setPartNumber(newProduct.getPartNumber());
            oldProduct.get().setManufactureCost(newProduct.getManufactureCost());
            oldProduct.get().setUnitOfMeasure(newProduct.getUnitOfMeasure());
            oldProduct.get().setOwner(newProduct.getOwner());
            return true; // Return true to indicate success
        } else {
            return false; // Return false to indicate failure
        }
    }

    public boolean removeKey(Integer key) {
        boolean status = false;
        if (!collection.containsKey(key)) return status;
        collection.remove(key);
        return !status;
    }

    public void clear() {
        collection.clear();
        ids.clear();
    }

    public boolean replaceIfLowe(Integer key, Product product) {
        boolean status = false;
        Comparable<Product> comparable = (product2) -> Integer.compare(product2.getPrice(), product.getPrice());
        if (comparable.compareTo(collection.get(key)) > 0) {
            product.setId(collection.get(key).getId());
            collection.replace(key, product);
            status = true;
        }
        return status;
    }

    public boolean removeLowerKey(Integer key) {
        return collection.keySet().removeIf(key1 -> key1 < key);
    }

    public Product maxByUnitOfMeasure() {
        if (!collection.isEmpty()) {
            try{
                return collection.values()
                        .stream()
                        .filter(product -> product.getUnitOfMeasure() != null)
                        .max(Comparator.comparingInt(product -> product.getUnitOfMeasure().getValue())).get();
            }catch(RuntimeException exeption){
                return null;
            }
        } else return null;
    }

    public List<Product> filterContainsPartNumber(String partNumber) {

        return collection.values()
                .stream()
                .filter(product -> product.getPartNumber().contains(partNumber))
                .collect(Collectors.toList());

    }

    public ArrayList<Product> filterStartsWithName(String name) {
        ArrayList<Product> filter = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + name);
        AtomicReference<Matcher> matcher = new AtomicReference<>();
        collection.forEach((key, product) -> {
            matcher.set(pattern.matcher(product.getName()));
            if (matcher.get().find())
                filter.add(product);
        });
        return filter;
    }

}








