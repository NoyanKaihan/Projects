package org.example.connection;

import org.example.modules.StudyGroup;

public class RequestMessage implements Request{
    private String commandName;
    private String commandArgument;
    private StudyGroup studyGroup;
    public RequestMessage(String commandName,String commandArgument,StudyGroup studyGroup){
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.studyGroup = studyGroup;
    }
    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandArgument() {
        return commandArgument;
    }

    @Override
    public StudyGroup getStudyGroupObject() {
        return studyGroup;
    }
}
