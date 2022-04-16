package dev.plex.emotes.command;

import dev.plex.emotes.util.EmoteMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface EmoteCommandExecutor
{
    boolean onCommand(CommandSender paramCommandSender, Command paramCommand, EmoteMeta paramEmoteMeta, String paramString, String[] paramArrayOfString);
}
