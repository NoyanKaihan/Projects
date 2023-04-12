package csv;

import data.StudyGroup;
import exceptions.DataException;
import exceptions.DataParseException;

import java.util.*;

/**
 * Class for CSV  Deserialization
 */

public class CsvCollectionDeserializer {
    private PriorityQueue<StudyGroup> csvDS;
    private StudyGroupParser parser;
    private HashSet<Integer> uniqueIds;
    public CsvCollectionDeserializer(HashSet<Integer> uniqueIds){
        csvDS = new PriorityQueue<>(Collections.reverseOrder());
        parser = new StudyGroupParser();
        this.uniqueIds = uniqueIds;
    }

    /**
     * Method for saving objects in PriorityQueue
     * @param csv
     * @return PriorityQueue containing object of StudyGroup class
     * @throws DataException
     */
    public PriorityQueue<StudyGroup> deserialize(String csv) throws DataParseException {
        String[] objects  = csv.split("\n");
        String[] allObjects = new String[objects.length-1];
        for(int i=1;i< objects.length;i++){
            allObjects[i-1]= objects[i];
        }
        ArrayList<String> csvObjects = new ArrayList<>();
        for(int i=0;i<allObjects.length;i++){
            if(!allObjects[i].equals(""))
                csvObjects.add(allObjects[i]);
        }
        for (String object : csvObjects) {
            try {
                if (CsvDeserializeValidate(parser.objectParse(object))
                        && !isUniqueId(parser.objectParse(object).getId())) {
                    csvDS.add(parser.objectParse(object));
                    addId(parser.objectParse(object).getId());
                }
           }catch(DataParseException | DataException exception){
                System.out.println(exception.getMessage());
            }
        }
        return csvDS;
    }
    private boolean CsvDeserializeValidate(StudyGroup studyGroup){
        return studyGroup.valid();
    }
    private boolean isUniqueId(Integer id){
        return uniqueIds.contains(id);
    }
    private void addId (Integer id){
        uniqueIds.add(id);
    }
}
