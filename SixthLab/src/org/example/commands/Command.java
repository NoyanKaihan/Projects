package org.example.commands;

import org.example.exceptions.*;
import org.example.modules.StudyGroup;

public abstract class Command {
    private String commandName;
    private CommandAccess commandAccess;
    private StudyGroup studyGroup;
    private CommandType commandType;
    private String arg;
    public Command(String commandName, CommandAccess commandAccess,CommandType commandType){
        this.commandName = commandName;
        this.commandAccess = commandAccess;
        this.commandType = commandType;
    }
    public abstract String execute()throws InvalidDataException, CommandException, FileException, ConnectionException;
    public final void setStringArg(String arg){
        this.arg = arg;
    }
    public final CommandType getCommandType(){
        return commandType;
    }
    public final String getStringArg(){
        return arg;
    }
    public final boolean hasStringArg(){
        return arg!=null;
    }
    public final void setStudyGroup(StudyGroup studyGroup){
        this.studyGroup = studyGroup;
    }
    public final StudyGroup getStudyGroup(){
        return studyGroup;
    }
    public final boolean hasStudyGroup(){
        return studyGroup!=null;
    }
}
