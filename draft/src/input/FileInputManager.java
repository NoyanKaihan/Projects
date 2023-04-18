package input;

import exceptions.FileException;
import file.FileManager;

import java.util.Scanner;

/**
 * Class for inputting form file
 */
public class FileInputManager extends InputManager {
    public FileInputManager(String path) {
        super(new Scanner(new FileManager(path).read()));
        getScanner().useDelimiter("\n");
    }
}
