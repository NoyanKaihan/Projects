package commands;

import connection.Request;
import connection.Response;
import exceptions.ArgumentException;
import exceptions.RecursionException;
import managers.*;
import models.Product;
import parser.StringProductParser;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExecuteScriptAbstractCommand extends AbstractCommand {

    private final CommandManager commandManager;

    public ExecuteScriptAbstractCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String execute(String argument) throws RuntimeException {
        AtomicReference<String> result = new AtomicReference<>("");
        String a = argument.replaceAll("\\n", "\\\\n");
//        System.out.println("argument = " + argument + "\nfinish\n");


        // TODO: Create a regular expression pattern to match the desired parts
        String pattern = "(command|argument|product)='(.*?)'";
        String pat = "";

        Pattern regexPattern = Pattern.compile(pattern);

        Matcher matcher = regexPattern.matcher(a);

        // TODO:Iterate over the matches and extract the desired parts
        int c = 0;
        while (matcher.find()) {
            String command = matcher.group(2);
            String arg = "";
            String product = "";
            if (matcher.find()) {
                arg = matcher.group(2);
            }
            if (matcher.find()) {
                product = matcher.group(2);
            }


//            System.out.println("command = " + command);
//            System.out.println("arg = " + arg);
//            String product = matcher.group(3);
//            System.out.println("\n\nproduct = " + product);
            try {
                result.set(result.get() + "\n" + commandExecutor(command, arg, product));
            } catch (Exception exception) {
                result.set(result.get() + "\n" + "Invalid request for command : " + command);
            }
        }
        return result.get();
    }

    private String commandExecutor(String command, String argument, String product) throws IOException {
        String s = "->" + command;
        //insert, update, replace if lowe
        if (!argument.equals("null") && !product.equals("null")) {
            Request request = new Request(command, argument, parser(product));
            Response response = commandManager.runCommand(request);
            s += " " + argument + " :" + response.getMessage();
        }
        //filter_starts_with_name, filter_contains_partNumber, remove_key, remove_lower_key
        else if (!argument.equals("null") && product.equals("null")) {
            Request request = new Request(command, argument, null);
            Response response = commandManager.runCommand(request);
            s += " " + argument + " :" + response.getMessage();
        } else {
            Request request = new Request(command, null, null);
            Response response = commandManager.runCommand(request);
            s += " :\n" + response.getMessage();
        }
        return s;
    }

    public Product parser(String product) {
        Product p = new StringProductParser(product).parseProduct();
//        System.out.println("String product :\n" + product);
//        System.out.println("Parser product :\n" + p);
        return p;
    }


    @Override
    public String getDescription() {
        return "read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.";
    }
}
