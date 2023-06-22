package org.example.exceptions;

public class InvalidCommandArgumentException extends CommandException{
    public InvalidCommandArgumentException(){

    }
    public InvalidCommandArgumentException(String message){
        super(message);
    }
}
