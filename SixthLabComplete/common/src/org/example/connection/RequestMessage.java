package org.example.connection;

import org.example.modules.StudyGroup;

public class RequestMessage implements Request {
    private String commandName;
    private String commandArgument;
    private StudyGroup studyGroup;

    public RequestMessage(String commandName, String commandArgument, StudyGroup studyGroup) {
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
    public void setArgument(String argument) {
        this.commandArgument = argument;
    }

    @Override
    public StudyGroup getStudyGroupObject() {
        return studyGroup;
    }

    @Override
    public String toString() {
        return
                "commandName='" + commandName + '\'' +
                        ", commandArgument='" + commandArgument + '\'' +
                        ", studyGroup=" + studyGroup;
    }
}
