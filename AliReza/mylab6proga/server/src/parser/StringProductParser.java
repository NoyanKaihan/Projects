package parser;

import models.*;

import java.time.ZonedDateTime;

public class StringProductParser {
    private String product;

    public StringProductParser(String product) {
        this.product = product;
    }

    public Product parseProduct() {
        Product product1 = new Product();

        // Remove leading and trailing whitespaces
        product = product.trim();

        // Split the input string by new lines
        String[] lines = product.split("\\\\n");
        for (int i = 0; i < 22; i++) {
            // Split each line by the ":" symbol
            String[] parts = lines[i].split(":");
            if(lines[i].equals("}")) continue;
            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            if (fieldName.equals("id") && !fieldValue.equals("null")) {
                product1.setId(Long.parseLong(fieldValue));

            } else if (fieldName.equals("name")) {
                product1.setName(fieldValue);

            } else if (fieldName.equals("coordinates")) {// Parse coordinates
                Coordinates coordinates = parseCoordinates((lines[++i] +","+ lines[++i]));
                product1.setCoordinates(coordinates);

            } else if (fieldName.equals("creation date")) {// Parse creation date
                product1.setCreationDate(ZonedDateTime.now());

            } else if (fieldName.equals("price")) {
                product1.setPrice(Integer.parseInt(fieldValue));

            } else if (fieldName.equals("part Number")) {
                product1.setPartNumber(fieldValue);

            } else if (fieldName.equals("manufacture cost")) {
                product1.setManufactureCost(Float.parseFloat(fieldValue));

            } else if (fieldName.equals("unit of measure")) {
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(fieldValue);
                product1.setUnitOfMeasure(unitOfMeasure);

            } else if (fieldName.equals("owner")) {
                StringBuilder infoOwner = new StringBuilder("");
                for (int j = i + 1; j <= lines.length; j++) {
                    infoOwner.append(lines[i++]).append("\n");
                }
                Person owner = parsePerson(infoOwner.toString());
                product1.setOwner(owner);
            }
        }

        return product1;
    }

    private Coordinates parseCoordinates(String input) {
        Coordinates coordinates = new Coordinates();
        // Split the input string by new lines
        String[] lines = input.split(",");
        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "x":
                    coordinates.setX(Integer.parseInt(fieldValue));
                    continue;
                case "y":
                    coordinates.setY(Integer.parseInt(fieldValue));
                    continue;
            }
        }

        return coordinates;
    }

    private Person parsePerson(String input) {
        Person person = new Person();

        // Split the input string by new lines
        String[] lines = input.split("\\\\n");
        for (int i = 0; i < lines.length; i++) {

            // Split each line by the ":" symbol
            String[] parts = lines[i].split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "name":
                    person.setName(fieldValue);
                    continue;
                case "birthday":
                    ZonedDateTime creationDate = ZonedDateTime.parse(fieldValue);
                    person.setBirthday(creationDate);
                    continue;
                case "height":
                    person.setHeight(Long.parseLong(fieldValue));
                    continue;
                case "hairColor":

                    Color hairColor = Color.valueOf(fieldValue);
                    person.setHairColor(hairColor);
                    continue;
                case "location":
                    // Parse location
                    Location location = parseLocation(lines[++i]+lines[++i]+lines[++i]);
                    person.setLocation(location);
                    continue;
            }
        }

        return person;
    }

    private Location parseLocation(String input) {
        Location location = new Location();

        // Split the input string by new lines
        String[] lines = input.split("\\\\n");
        for (String line : lines) {
            // Split each line by the ":" symbol
            String[] parts = line.split(":");

            // Remove leading and trailing whitespaces from the field name and value
            String fieldName = parts[0].trim();
            String fieldValue = parts[1].trim();

            // Assign the value to the corresponding field
            switch (fieldName) {
                case "x":
                    location.setX(Long.parseLong(fieldValue));
                    continue;
                case "y":
                    location.setY(Double.parseDouble(fieldValue));
                    continue;
                case "name":
                    location.setName(fieldValue);
                    continue;
            }
        }

        return location;
    }
}
