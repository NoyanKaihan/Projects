package scriptHandler;

import clientDataManager.FileInputManager;
import connection.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ScriptHandler {
    private final static Stack<String> paths = new Stack<>();
    private String path;

    public ScriptHandler(String path){
        this.path = path;
        paths.add(path);
    }
    public Request handle(Request request){
        request.setArgument("");
        requestList().forEach(r -> {
            if (r.getCommand().equals("execute_script")) {
                if (isRecursiveScript(r.getArgument())) {
                    System.out.println("Enter (1) if you want to execute the recursive script or (2) if you don't : ");
                    String action = new Scanner(System.in).nextLine();
                    if (action.equals("1")) {
                        request.setArgument(request.getArgument()+handle(r).getArgument());
                    }
                    else if (action.equals("2")) {
                        return;
                    }
                } else {
                    request.setArgument(request.getArgument()+handle(r).getArgument());
                }
            } else {
                request.setArgument(request.getArgument() + "\n" + r);
            }
        });
        return request;

    }



    //TODO: Check recursion
    public boolean isRecursiveScript (String path){
        return paths.contains(path);
    }
    public List<Request> requestList(){
        List<Request> requestList = new ArrayList<>();
        try{
            FileInputManager inputManager = new FileInputManager(path);
            while(inputManager.getInput().hasNextLine()){
                Request request =  inputManager.readCommand();
                if(request.getCommand().equals("update")) request.getProduct().setId(Long.parseLong(request.getArgument()));
                requestList.add(request);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return requestList;
    }

}
