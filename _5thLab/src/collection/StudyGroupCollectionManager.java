package collection;

import console.ConsoleColor;
import csv.CsvBuilder;
import csv.CsvCollectionDeserializer;
import data.Person;
import data.StudyGroup;
import exceptions.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StudyGroupCollectionManager implements CollectionManager<StudyGroup>{
    private PriorityQueue<StudyGroup> collection;
    private HashSet<Integer> uniqueIds;
    private ZonedDateTime initDate;
    public StudyGroupCollectionManager(){
        collection = new PriorityQueue<>();
        uniqueIds = new HashSet<>();
        initDate = ZonedDateTime.now();
    }


    public void setCollection(PriorityQueue<StudyGroup> collection) {
        this.collection = collection;
    }


    public PriorityQueue<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public String getInfo() {
        return "PriorityQueue of StudyGroup , size: " + collection.size() + ", initialization date: " + initDate;
    }

    @Override
    public int autoId() {
        if (collection.isEmpty())
            return 1;
        else {
            Integer id = getLast().getId() + 1;
            if(uniqueIds.contains(id)){
                while (uniqueIds.contains(id)) id+=1;
            }
            uniqueIds.add(id);
            return id;
        }
    }

    @Override
    public void add(StudyGroup studyGroup) {
        try {
            studyGroup.setId(autoId());
            if(!isRepeated(studyGroup)) {
                collection.add(studyGroup);
                System.out.print(ConsoleColor.GREEN_BOLD+"New element : \n"+ConsoleColor.RESET);
                System.out.print(ConsoleColor.YELLOW);
                System.out.println(studyGroup);
                System.out.println(ConsoleColor.RESET);
            }
        }catch(RepeatedDataException exception){
            System.out.print(ConsoleColor.RED_BACKGROUND);
            System.out.println("StudyGroup already exists in collection ");
            System.out.print(ConsoleColor.RESET);
        }
    }

    /**
     * Method for checking whether id exists in collection
     * @param id
     * @return True if id exists in collection and False if id doesn't exist in collection
     * @throws NoSuchDataException
     */
    public boolean isIdInCollection(Integer id) throws NoSuchDataException {
        AtomicBoolean found = new AtomicBoolean(false);
        collection.forEach(studyGroup -> {
             if(studyGroup.getId() == id) {
                 found.set(true);
             }
        });
        return found.get();
    }

    /**
     * Method for checking whether new element that is being added is new or repeated
     * @param studyGroup object that needs to be compared
     * @return True if id was repeated, false if id was new
     * @throws RepeatedDataException
     */
    private boolean isRepeated(StudyGroup studyGroup) throws RepeatedDataException {
        boolean answer = true;
        try {
            answer = isCollectionMember(studyGroup);
        } catch (DataException exception) {

        }
        return answer;
    }

    @Override
    public void update(Integer id,StudyGroup studyGroup) {
        try {
            if(isIdInCollection(id)){
                AtomicInteger idx = new AtomicInteger();
                PriorityQueue<StudyGroup> cp = new PriorityQueue<>();
                collection.forEach(studyGroupX -> {
                    if(studyGroupX.getId()==id){
                        idx.set(studyGroupX.getId());
                        studyGroup.setId(idx.get());
                        cp.add(studyGroup);
                        System.out.print(ConsoleColor.GREEN_BACKGROUND+"StudyGroup with Id of #"+idx.get()+" has successfully been updated :)"+ConsoleColor.RESET+"\n");
                    }else {
                        cp.add(studyGroupX);
                    }
                });
                 clear();
                setCollection(cp);
            }
        }catch(NoSuchDataException exception){
            System.err.println(id+" is not an id in collection!!!");
        }
    }

    @Override
    public void removeById(Integer id) {
        try {
            if(isIdInCollection(id))
            for(StudyGroup studyGroup : collection){
                if(studyGroup.getId()==id) {
                    collection.remove(studyGroup);
                    uniqueIds.remove(id);
                    System.out.print(ConsoleColor.GREEN_BACKGROUND+"StudyGroup with #"+id+" has been removed successfully :)"+ConsoleColor.RESET+"\n");
                }
            }
        }catch (NoSuchDataException exception){
        }
    }
    @Override
    public void clear()  {
        collection.clear();
    }

    @Override
    public StudyGroup collectionHead()  {
        StudyGroup studyGroup = null;
        try{
            hasHead();
            studyGroup = collection.peek();
        }catch (NoHeadException exception){
           System.err.println("Collection doesn't have head") ;
        }
        return studyGroup;
    }
    /**
     * Method for checkin whether object is in collection
     * @param studyGroup
     * @return True if object exist in collection, False if object doesn't exist in the collection
     * @throws DataException
     */
    private boolean isCollectionMember(StudyGroup studyGroup) throws DataException {
        return collection.contains(studyGroup);
    }

    /**
     * Method for comparing given object with objects in collection
     * @param studyGroup Representing Object that it's value needs to be compared
     * @return True if values of given object was greater, False if values of given object wasn't great
     */
    private boolean isGreater(StudyGroup studyGroup) {
        AtomicBoolean found = new AtomicBoolean(false);
        collection.forEach(studyGroup1 -> {
            if(studyGroup1.compareTo(studyGroup)<0)
                found.set(true);
        });
        return found.get();
    }

    /**
     * Method  for checking if collection has head
     * @return True if collection had head, False if collection hadn't head
     * @throws NoHeadException
     */
    private boolean hasHead()throws NoHeadException{
        StudyGroup studyGroup = collection.peek();
        return studyGroup!=null&&!collection.isEmpty();
    }
    @Override
    public void addIfMax(StudyGroup studyGroup) {
        if(isGreater(studyGroup)) {
            add(studyGroup);

        }
        else System.err.print(ConsoleColor.RED_BACKGROUND+"Element is not greater"+ConsoleColor.RESET+"\n");
    }

    @Override
    public void removeGreater(StudyGroup studyGroup) {
        PriorityQueue<StudyGroup> priorityQueue = new PriorityQueue<>();
        collection.forEach(studyGroup1 -> {
                if (studyGroup1.compareTo(studyGroup) > 0) {
                    System.out.println(ConsoleColor.GREEN_BACKGROUND+"StudyGroup with #"+studyGroup1.getId()+" has been remove from the collection"+ConsoleColor.RESET+"\n");
                }else{
                    priorityQueue.add(studyGroup1);
                }
        });
        clear();
        collection = priorityQueue;

    }
    @Override
    public int countByExpelledStudents(long expelled) {
        AtomicInteger count = new AtomicInteger(0);
        collection.forEach(studyGroup1 -> {
            if(studyGroup1.getExpelledStudents() == expelled)
                count.addAndGet(1);
        });
        return count.get();
    }
    @Override
    public LinkedList<Integer> uniqueFormOfEducation() {
        LinkedList<Integer> unique = new LinkedList<>();
        unique.add(collection.peek().getFormOfEducation().quality);
        collection.forEach(studyGroup -> {
            if(!unique.contains(studyGroup.getFormOfEducation().quality)){
                unique.add(studyGroup.getFormOfEducation().quality);
            }
        });
        return unique;
    }

    @Override
    public ArrayList<Person> descendingGroupAdmin() {
        ArrayList<Person>descend = new ArrayList<>();
        AtomicReference<Person> max  = new AtomicReference<>(collection.peek().getGroupAdmin());
        collection.forEach(studyGroup -> {
            if(studyGroup.getGroupAdmin().compareTo(max.get())<0){
                max.set(studyGroup.getGroupAdmin());
            }
        });
        collection.forEach(studyGroup -> {
            if(studyGroup.getGroupAdmin().compareTo(max.get())>=0)
                descend.add(studyGroup.getGroupAdmin());
        });
        return descend;
    }

    @Override
    public String csvSerializer() throws EmptyCollectionException {
        String csv ="";
        if(collection!=null&&!collection.isEmpty()) {
            CsvBuilder builder = new CsvBuilder(collection);
            csv = builder.build();
        }else{
            throw new EmptyCollectionException(ConsoleColor.RED_BACKGROUND+"Collection is Empty!!!"+ConsoleColor.RESET+"\n");
        }
        return csv;
    }

    @Override
    public boolean csvDeserializer(String csv) {
        boolean success = true;
        if(csv.equals("")){
            collection = new PriorityQueue<>();
        }
        else{
           CsvCollectionDeserializer csvDeserializer = new CsvCollectionDeserializer(uniqueIds);
           try {
                if(csvDeserializer.deserialize(csv).size()!=0){
                    collection = csvDeserializer.deserialize(csv);
                }
                if(collection.isEmpty())success=false;
           }catch (DataParseException exception){
                success = false;
                System.err.print(ConsoleColor.RED_BACKGROUND+"Wrong csv data"+ConsoleColor.RESET+'\n');
           }
        }
        return success;
    }
    private StudyGroup getLast(){
        Object[] queue = collection.toArray();
        StudyGroup last = (StudyGroup) queue[0];
        Comparator<StudyGroup> comparator = (Comparator<StudyGroup>) collection.comparator();
        if (comparator !=null)
            for(int i = 1; i < collection.size(); i++)
                if ( comparator.compare(last, (StudyGroup) queue[i]) < 0)
                    last = (StudyGroup) queue[i];
                else
                    for(int j = 1; j < collection.size(); j++)
                        if ( ((Comparable)last).compareTo( ((Comparable)queue[j]) ) < 0 )
                            last = (StudyGroup) queue[j];
            return last;
        }
}

