package dev.plex.emotes.util;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.command.Command_emotes;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils implements EmotesBase
{
    public static void globalMessage(String string)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(string);
        }
    }

    public static void globalMessage(String[] strings)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            for (String string : strings)
            {
                player.sendMessage(string);
            }
        }
    }

    public static void globalEmoteMessage(String string)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (Command_emotes.mutedPlayers.contains(player))
            {
                continue;
            }
            player.sendMessage(StringUtils.generateRandoms(string));
        }
    }

    public static void globalEmoteMessage(String[] strings)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (Command_emotes.mutedPlayers.contains(player))
            {
                continue;
            }
            for (String string : strings)
            {
                player.sendMessage(StringUtils.generateRandoms(string));
            }
        }
    }

    public static void debugMessage(Level level, String message)
    {
        if (!plugin.getConfig().getBoolean("debug"))
        {
            return;
        }
        plugin.getLogger().log(level, message);
    }

    public static String colorize(String string, char symbol)
    {
        return ChatColor.translateAlternateColorCodes(symbol, string);
    }

    public static String colorize(String string)
    {
        return colorize(string, '&');
    }
}
