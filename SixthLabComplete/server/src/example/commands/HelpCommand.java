package example.commands;

import org.example.exceptions.CommandException;
import org.example.exceptions.ConnectionException;
import org.example.exceptions.FileException;
import org.example.exceptions.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class HelpCommand extends Command{
    public HelpCommand(){
        super("help", CommandAccess.NORMAL,CommandType.NON_ARGUMENT);
    }
    @Override
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException {
        AtomicReference<String> commands = new AtomicReference<>("");
        Map<String,String> map = new HashMap<>();
        for(int i=0;i< getCommands().size();i++){
            map.put(getCommands().get(i), getDescription().get(i));
        }
        map.forEach((command,description)->{
            commands.set(commands.get()+"\n"+command+description);
        });
        return commands.get();
    }
    private List<String> getCommands() {
        return new ArrayList<>(Arrays.asList("help",
                "info",
                "show ",
                "add",
                "update",
                "remove_by_id",
                "clear",
                "execute_script",
                "head",
                "add_if_max",
                "remove_greater",
                "count_by_expelled_students",
                "print_unique_form_of_education",
                "print_field_descending_group_admin"));
    }
    /**
     * Description store method
     *
     * @return Arraylist Containing command Descriptions
     */
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList(": display help on available commands",
                " {element}: print information about the collection to standard output" +
                        " (type, initialization date, number of elements, etc.)",
                ": print to standard output all elements of the collection in string representation",
                ": add a new element to the collection",
                " id {element}: update the value of the collection element whose id is equal to the given one",
                " id: remove an element from the collection by its id",
                ": clear collection",
                " file_name: read and execute a script from the specified file." +
                        " The script contains commands in the same form in which they are entered by the user in interactive mode.",
                ": display the first element of the collection",
                " {element}: add a new element to the collection if its value is" +
                        " greater than the value of the largest element in this collection",
                " {element}: remove from the collection all elements greater than the given",
                " expelledStudents: display the number of elements whose expelledStudents " +
                        " field value is equal to the given one",
                ": display the unique values of the formOfEducation field of all elements in the collection",
                ": display groupAdmin field values of all elements in descending order"));
    }

}
