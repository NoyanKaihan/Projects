package org.example.exceptions;

public class InvalidDataException extends Exception{
    String message;
    public InvalidDataException(){
        this("data is invalid");
    }
    public InvalidDataException(String message){
        super(message);
    }
}
