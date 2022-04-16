package dev.plex.emotes.util;

import dev.plex.emotes.Emotes;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmoteLoader
{
    private static List<EmoteMeta> cachedEmoteMetas = new ArrayList<>();
    private static HashMap<String, URL> defaultEmoteJSON = new HashMap<>();
    private static HashMap<String, Object[]> otherDefaults = (HashMap)new HashMap<>();

    public static EmoteMeta getEmote(int index) throws IOException
    {
        return cachedEmoteMetas.get(index);
    }

    public static EmoteMeta getEmoteByName(String name) throws IOException
    {
        for (EmoteMeta emoteMeta : cachedEmoteMetas)
        {
            if (emoteMeta.getName().equalsIgnoreCase(name))
            {
                return emoteMeta;
            }
        }
        return null;
    }

    public static EmoteMeta getEmoteByCommand(String command) throws IOException
    {
        for (EmoteMeta emoteMeta : cachedEmoteMetas)
        {
            if (emoteMeta.getCommand().equalsIgnoreCase(command))
            {
                return emoteMeta;
            }
        }
        return null;
    }

    public static List<EmoteMeta> getAllEmotes()
    {
        return cachedEmoteMetas;
    }

    public static List<EmoteMeta> loadEmotes(File dir) throws IOException
    {
        List<EmoteMeta> emoteMetaList = new ArrayList<>();
        File[] fileArray = dir.listFiles();
        for (File file : fileArray)
        {
            if (file.isFile() &&
                    file.getName().endsWith(".json"))
            {
                EmoteMeta emoteMeta = new EmoteMeta();
                String stringJson = FileUtils.readFile(file);
                JSONObject json = new JSONObject(stringJson);
                emoteMeta.setName(json.getString("name"));
                emoteMeta.setCommand(json.getString("command"));
                emoteMeta.setDescription(json.getString("description"));
                emoteMeta.setAuthor(json.getString("author"));
                emoteMeta.setUsage(json.getString("usage"));
                HashMap<List<EmoteVariable>, String[]> lines = (HashMap)new HashMap<>();
                JSONObject jsonLines = json.getJSONObject("lines");
                for (String key : jsonLines.keySet())
                {
                    JSONArray array = jsonLines.getJSONArray(key);
                    String[] variableNames = key.split(",");
                    List<EmoteVariable> list = new ArrayList<>();
                    for (String variableName : variableNames)
                    {
                        list.add(EmoteVariable.valueOf(variableName.toUpperCase()));
                    }
                    lines.put(list, (String[])array.toList().toArray((Object[])new String[0]));
                }
                emoteMeta.setLineMap(lines);
                HashMap<EmoteVariable, String> variables = new HashMap<>();
                JSONObject jsonVariables = json.getJSONObject("variables");
                for (String key : jsonVariables.keySet())
                {
                    EmoteVariable emoteKey = EmoteVariable.valueOf(key.toUpperCase());
                    String value = jsonVariables.getString(key);
                    variables.put(emoteKey, value);
                }
                emoteMeta.setVariableMap(variables);
                emoteMetaList.add(emoteMeta);
            }
        }
        cachedEmoteMetas = emoteMetaList;
        return emoteMetaList;
    }

    public static void saveDefaultEmotes(File dir) throws IOException
    {
        for (String fileName : defaultEmoteJSON.keySet())
        {
            File file = new File(dir + "/" + fileName + ".json");
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                FileUtils.copyFile(defaultEmoteJSON.get(fileName), file);
            }
        }
    }

    public static void saveOtherDefaults(File dir) throws IOException
    {
        for (String fileName : otherDefaults.keySet())
        {
            File file = new File(dir + "/" + ((Object[])otherDefaults.get(fileName))[1] + "/" + fileName);
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                FileUtils.copyFile((URL)((Object[])otherDefaults.get(fileName))[0], file);
            }
        }
    }

    public static void registerDefaultEmote(String fileName) throws IOException
    {
        ClassLoader classLoader = Emotes.class.getClassLoader();
        URL url = classLoader.getResource("emotes/" + fileName + ".json");
        defaultEmoteJSON.put(fileName, url);
    }

    public static void registerOtherDefault(String path, String fileName, String outputPath) throws IOException
    {
        ClassLoader classLoader = Emotes.class.getClassLoader();
        URL url = classLoader.getResource(path + "/" + fileName);
        otherDefaults.put(fileName, new Object[]{url, outputPath});
    }
}
