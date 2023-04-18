package exceptions;

import java.io.FileNotFoundException;

public class FileException extends FileNotFoundException {
    public FileException(){

    }
    public FileException(String message){
        super(message);
    }
}
