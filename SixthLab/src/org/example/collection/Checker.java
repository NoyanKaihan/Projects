package org.example.collection;


import org.example.exceptions.InvalidDataException;

public interface Checker<T> {
    boolean isIdInCollection(Integer id);
    boolean isRepeated(T studyGroup)throws InvalidDataException;
    boolean isCollectionMember(T studyGroup) throws InvalidDataException;
    boolean isGreater(T studyGroup) throws InvalidDataException;
    boolean hasHead()throws InvalidDataException;

}
