package org.example.scriptHandler;

import org.example.connection.Request;
import org.example.connection.RequestMessage;
import org.example.console.ConsoleColor;
import org.example.file.FileManager;
import org.example.inputManager.FileInputManager;
import org.example.inputManager.InputManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ScriptHandler {
    private static Stack<String> scripts = new Stack<>();
    private static FileManager fileManager =  new FileManager();
    private String address;
    public ScriptHandler(String address){
        this.address = address;
    }
    //TODO: Check recursion
    public boolean isRecursiveScript(){
        return scripts.contains(address);
    }
    public List<Request> actionIfNotRecursive(){
        List<Request> requestList = new ArrayList<>();
        try{
            scripts.add(address);
            InputManager inputManager = new FileInputManager(address);
            while(inputManager.getScanner().hasNextLine()){
               Request request =  inputManager.readCommand();
               requestList.add(request);
            }
        }catch (Exception exception){
            System.out.println(ConsoleColor.RED_BACKGROUND+ exception.getMessage()+ConsoleColor.RESET);
        }
        return requestList;
    }
    public List<Request> actionIfRecursive(){
        List<Request> requestList = new ArrayList<>();
        while (true){
            System.out.println(ConsoleColor.YELLOW_BACKGROUND+"Enter (1) if you want to execute the recursive script (2) if you don't : "+ConsoleColor.RESET);
            String action = new Scanner(System.in).nextLine();
            if(action.equals("1")){
                try{
                    InputManager inputManager = new FileInputManager(address);
                    while(inputManager.getScanner().hasNextLine()){
                        Request request =  inputManager.readCommand();
                        requestList.add(request);
                    }
                    return requestList;
                }catch (Exception exception){
                    System.out.println(ConsoleColor.RED_BACKGROUND+ exception.getMessage()+ConsoleColor.RESET);
                }
            }
            if(action.equals("2")){
                requestList.add(new RequestMessage(null,null,null));
                return requestList;
            }
        }
    }
}
