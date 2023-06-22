package org.example.inputManager;



import org.example.exceptions.FileException;
import org.example.file.FileManager;

import java.util.Scanner;

/**
 * Class for inputting form file
 */
public class FileInputManager extends InputManager {
    public FileInputManager(String path) throws FileException {

        super(new Scanner(new FileManager(path).read()));
        getScanner().useDelimiter("\n");

    }
}
