package dev.plex.emotes.command;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.util.EmoteLoader;
import dev.plex.emotes.util.EmoteMeta;
import java.lang.reflect.Field;
import org.bukkit.command.CommandMap;

public class CommandLoader implements EmotesBase
{
    public static void loadCommand(EmoteMeta emoteMeta) throws NoSuchFieldException, IllegalAccessException
    {
        Field bukkitCommandMap = plugin.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap)bukkitCommandMap.get(plugin.getServer().getPluginManager());
        EmoteCommand command = new EmoteCommand(emoteMeta.getName(), emoteMeta.getDescription(), emoteMeta.getUsage());
        command.setExecutor(new Command_emote());
        commandMap.register(emoteMeta.getCommand(), command);
    }

    public static void loadAllCommands() throws NoSuchFieldException, IllegalAccessException
    {
        for (EmoteMeta emoteMeta : EmoteLoader.getAllEmotes())
        {
            loadCommand(emoteMeta);
        }
    }
}
