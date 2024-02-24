package commands;

import java.util.ArrayList;


public class HistoryAbstractCommand extends AbstractCommand {

    private final ArrayList<String> commandsHistory;

    public HistoryAbstractCommand() {
        commandsHistory = new ArrayList<>();
    }


    @Override
    public String execute(String argument) {
        String answer="";
        if (!argument.equals("")) answer = "this command can't have argument";
        if (!commandsHistory.isEmpty()) {
            answer = "\n\t\t\t\t\t\t\t\tList of last 9 commands (without their arguments)\n";
            for (int i = 1; i <= commandsHistory.size(); i++) {
                answer += "\n"+ i  + "th command : "+ commandsHistory.get(i-1);
            }
        } else {
            answer = ("No commands in History");
        }
        return answer;
    }


    public void addToHistory(String command) {
        if (historyIsFull()) commandsHistory.remove(0);
        commandsHistory.add(command);
    }


    private boolean historyIsFull() {
        int MAX = 9;
        return commandsHistory.size() == MAX;
    }


    @Override
    public String getDescription() {
        return "output the last 9 commands (without their arguments).";
    }

}
