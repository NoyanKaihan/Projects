package org.example.exceptions;

public class EmptyStringException extends InvalidDataException{
    public EmptyStringException(){
        this("String can't be empty");
    }
    public EmptyStringException(String message){
        super(message);
    }
}
