package org.example.collection;

import org.example.exceptions.EmptyCollectionException;
import org.example.exceptions.InvalidDataException;
import org.example.modules.FormOfEducation;
import org.example.modules.Person;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public interface CollectionManager <T>{
    void setCollection(PriorityQueue<T>collection);
    PriorityQueue<T> getCollection();
    HashSet<Integer> getUniqueIds();
    String getInfo();
    int autoId();
    boolean add(T studyGroup);
    boolean update(Integer id,T studyGroup)throws InvalidDataException;
    boolean removeById(Integer id)throws InvalidDataException;
    void clear();
    T collectionHead()throws EmptyCollectionException;
    boolean addIfMax(T studyGroup)throws InvalidDataException;
    boolean removeGreater(T studyGroup)throws InvalidDataException;
    int countByExpelledStudents(long expelled)throws InvalidDataException;
    List<FormOfEducation> uniqueFormOfEducation();
    List<Person> descendingGroupAdmin();
    String serialize()throws EmptyCollectionException;
    boolean deserialize(String csv);
    T getLast()throws EmptyCollectionException, InvalidDataException;
}
