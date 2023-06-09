package org.example.exceptions;

public class EmptyCollectionException extends InvalidDataException{
    String message;
    public EmptyCollectionException(){
        this("collection is empty");
    }
    public EmptyCollectionException(String message){
        super(message);
    }
}
