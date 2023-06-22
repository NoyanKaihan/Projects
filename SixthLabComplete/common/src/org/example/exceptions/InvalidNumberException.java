package org.example.exceptions;

public class InvalidNumberException extends InvalidDataException{
    public InvalidNumberException(String message){
        super(message);
    }
    public InvalidNumberException(){
        this("Number is invalid");
    }
}
