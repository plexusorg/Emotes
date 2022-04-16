package dev.plex.emotes.util;

import java.util.HashMap;
import java.util.List;

public class EmoteMeta
{
    private String name;

    private String command;

    private String description;

    private String usage;

    private String author;

    private HashMap<List<EmoteVariable>, String[]> lineMap;

    private HashMap<EmoteVariable, String> variableMap;

    public EmoteMeta(String name, String command, String description, String usage, String author, HashMap<List<EmoteVariable>, String[]> lineMap, HashMap<EmoteVariable, String> variableMap)
    {
        setName(name);
        setCommand(command);
        setDescription(description);
        setUsage(usage);
        setAuthor(author);
        setLineMap(lineMap);
        setVariableMap(variableMap);
    }

    public EmoteMeta()
    {
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUsage()
    {
        return this.usage;
    }

    public void setUsage(String usage)
    {
        this.usage = usage;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getCommand()
    {
        return this.command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public HashMap<EmoteVariable, String> getVariableMap()
    {
        return this.variableMap;
    }

    public void setVariableMap(HashMap<EmoteVariable, String> variableMap)
    {
        this.variableMap = variableMap;
    }

    public HashMap<List<EmoteVariable>, String[]> getLineMap()
    {
        return this.lineMap;
    }

    public void setLineMap(HashMap<List<EmoteVariable>, String[]> lineMap)
    {
        this.lineMap = lineMap;
    }
}
