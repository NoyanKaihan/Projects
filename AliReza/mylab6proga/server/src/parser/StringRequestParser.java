package parser;

import connection.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRequestParser {
    private String request;

    public StringRequestParser(String request){
        this.request=request;
    }
    public Request parseRequest(){
        Request request1 = new Request();

        String pattern = "command='(.*?)', argument='(.*?)', product=(.*?)\\n";

        Pattern regexPattern = Pattern.compile(pattern);

        Matcher matcher = regexPattern.matcher(request + "\n");


        String command = matcher.group(1);
        String arg = matcher.group(2);
        String product = matcher.group(3);

        return new Request(command,arg,new StringProductParser(product).parseProduct());


    }

}
