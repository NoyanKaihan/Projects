package org.example.collection;

import org.example.console.ConsoleColor;
import org.example.csv.CsvBuilder;
import org.example.csv.CsvCollectionDeserializer;
import org.example.exceptions.EmptyCollectionException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.FormOfEducation;
import org.example.modules.Person;
import org.example.modules.StudyGroup;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class StudyGroupCollectionManager implements CollectionManager<StudyGroup>, Checker<StudyGroup> {
    private PriorityQueue<StudyGroup> collection;
    private HashSet<Integer> uniqueIds;
    private ZonedDateTime initDate;

    public StudyGroupCollectionManager() {
        collection = new PriorityQueue<>(Comparator.comparing(StudyGroup::getId));
        uniqueIds = new HashSet<>();
        initDate = ZonedDateTime.now();
    }

    @Override
    public boolean isIdInCollection(Integer id) {
        return collection.stream().anyMatch(studyGroup -> studyGroup.getId() == id);
    }

    @Override
    public boolean isRepeated(StudyGroup studyGroup) throws InvalidDataException {
        return collection.contains(studyGroup);
    }

    @Override
    public boolean isCollectionMember(StudyGroup studyGroup) throws InvalidDataException {
        return collection.contains(studyGroup);
    }

    @Override
    public boolean isGreater(StudyGroup studyGroup) throws InvalidDataException {
        return collection.stream().max(Comparator.comparing(StudyGroup::getStudentsCount)).isPresent();
    }

    @Override
    public boolean hasHead() throws InvalidDataException {
        return Optional.ofNullable(collection.peek()).isPresent();
    }

    @Override
    public void setCollection(PriorityQueue<StudyGroup> collection) {
        this.collection = collection;
    }

    @Override
    public PriorityQueue<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public HashSet<Integer> getUniqueIds() {
        return uniqueIds;
    }

    @Override
    public String getInfo() {
        return ConsoleColor.YELLOW + "PriorityQueue of StudyGroup , size : " + collection.size() + ", initialization date : " + initDate + ConsoleColor.RESET + "\n";
    }

    @Override
    public int autoId() {
        if (collection.isEmpty()) {
            return 1;
        } else {
            try {
                Integer id = getLast().getId() + 1;
                if (uniqueIds.contains(id)) {
                    while (uniqueIds.contains(id)) id += 1;
                }
                uniqueIds.add(id);
                return id;
            }catch (InvalidDataException exception){
                System.err.println("can't do the operation");
            }
        }
        return 0;
    }

    @Override
    public boolean add(StudyGroup studyGroup) {
        studyGroup.setId(autoId());
        boolean isAdded = collection.add(studyGroup);
        System.out.println(ConsoleColor.GREEN_BOLD + "New element : " + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "" + studyGroup + ConsoleColor.RESET);
        return isAdded;
    }

    @Override
    public boolean update(Integer id, StudyGroup studyGroup) throws InvalidDataException {
        boolean success = true;
        if (isIdInCollection(id)){
            AtomicBoolean isUpdated = new AtomicBoolean(false);
            collection.stream()
                    .filter(studyGroup1 -> studyGroup1.getId() == id)
                    .forEach(studyGroup1 -> {
                            studyGroup1.setName(studyGroup.getName());
                            studyGroup1.setCoordinates(studyGroup.getCoordinates());
                            studyGroup1.setCreationDate(studyGroup.getCreationDate());
                            studyGroup1.setStudentsCount(studyGroup.getStudentsCount());
                            studyGroup1.setExpelledStudents(studyGroup.getExpelledStudents());
                            studyGroup1.setFormOfEducation(studyGroup.getFormOfEducation());
                            studyGroup1.setSemesterEnum(studyGroup.getSemesterEnum());
                            studyGroup1.setGroupAdmin(studyGroup.getGroupAdmin());
                            isUpdated.set(true);
                    });
            success = isUpdated.get();
        }else success = false;
        return success;
    }

    @Override
    public boolean removeById(Integer id) throws InvalidDataException {
        return collection.stream()
                .filter(sg -> sg.getId() == id)
                .peek(sg -> collection.remove(sg))
                .findFirst()
                .isPresent();
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public StudyGroup collectionHead() throws EmptyCollectionException {
        if (collection.isEmpty()) throw new EmptyCollectionException("Collection is empty");
        return collection.peek();
    }

    @Override
    public boolean addIfMax(StudyGroup studyGroup) throws InvalidDataException {
        Optional<StudyGroup> max = collection.stream()
                .max(Comparator.comparingLong(StudyGroup::getStudentsCount));
        if (max.isPresent() && studyGroup.getStudentsCount() > max.get().getStudentsCount())
            return add(studyGroup);
        else return false;
    }

    @Override
    public boolean removeGreater(StudyGroup studyGroup) throws InvalidDataException {
        List<StudyGroup> toBeRemoved = collection.stream()
                .filter(sg-> sg.getStudentsCount()<studyGroup.getStudentsCount())
                .toList();
        return collection.removeAll(toBeRemoved);

    }

    @Override
    public int countByExpelledStudents(long expelled) throws InvalidDataException {
        return (int)collection.stream()
                .filter(sg->sg.getExpelledStudents() == expelled)
                .count();
    }

    @Override
    public List<FormOfEducation> uniqueFormOfEducation() {
        Map<FormOfEducation,List<StudyGroup>> groupsByFormOfEducation = collection.stream()
                .collect(Collectors.groupingBy(StudyGroup::getFormOfEducation));
        return groupsByFormOfEducation.entrySet()
                .stream().filter(entry->entry.getValue().size()==1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    @Override
    public List<Person> descendingGroupAdmin() {
        return collection.stream()
                .map(StudyGroup::getGroupAdmin)
                .sorted(Comparator.comparingDouble(Person::getWeight).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public String serialize() throws EmptyCollectionException {
        String csv = "";
        if(collection !=null &&!collection.isEmpty()){
            CsvBuilder builder = new CsvBuilder(collection,this);
            csv = builder.build();
        }else{
            throw new EmptyCollectionException(ConsoleColor.RED_BACKGROUND+"Collection is Empty!!!"+ConsoleColor.RESET+"\n");
        }
        return csv;
    }

    @Override
    public boolean deserialize(String csv) {
       boolean success = true;
       if(csv.equals("")){
           collection = new PriorityQueue<>();
       }else{
           CsvCollectionDeserializer csvDeserializer = new CsvCollectionDeserializer(uniqueIds);
           try{
               if(csvDeserializer.deserialize(csv).size()!=0)
                   collection = csvDeserializer.deserialize(csv);
               if(collection.isEmpty()) success = false;
           }catch (InvalidDataException exception){
               success = false;
               System.out.println(exception.getMessage());
           }
       }
       return success;
    }

    @Override
    public StudyGroup getLast() throws EmptyCollectionException, InvalidDataException {
        if(collection.isEmpty())throw new EmptyCollectionException(ConsoleColor.RED_BACKGROUND+"Collection is Empty!!!"+ConsoleColor.RESET+"\n");
        return collection.stream()
                .reduce((first,second)->second)
                .orElse(null);
    }
}
