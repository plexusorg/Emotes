package dev.plex.emotes.bridge;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.util.ReflectionsHelper;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class PlexBridge implements EmotesBase
{
    private Plugin plex = null;

    public Plugin getPlex()
    {
        if (plex == null)
        {
            try
            {
                final Plugin plexPlugin = plugin.getServer().getPluginManager().getPlugin("Plex");
                if (plexPlugin != null && plexPlugin.isEnabled())
                {
                    plex = plexPlugin;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return plex;
    }

    public boolean checkRank(CommandSender sender, String rankName, String permission)
    {
        if (getPlex() == null)
        {
            Bukkit.getLogger().warning("Plex not detected either. Using standard permission check for permission: " + permission);
            return sender.hasPermission(permission);
        }
        String system = ReflectionsHelper.invokeMethod(plex, "getSystem");
        if (system == null)
        {
            Bukkit.getLogger().severe("Unable to find Plex's system. Reflections may be setup incorrectly. Using standard permission check for permission: " + permission);
            return sender.hasPermission(permission);
        }
        if (system.equalsIgnoreCase("ranks"))
        {
            if (sender instanceof ConsoleCommandSender)
            {
                return true;
            }
            Object plexPlayer = ReflectionsHelper.invokeStaticMethod("dev.plex.cache.DataUtils", "getPlayer", sender.getName());
            Enum<?> rank = ReflectionsHelper.invokeMethod(plexPlayer, "getRankFromString");
            Object rankFromEnum = ReflectionsHelper.getEnumValue("dev.plex.rank.enums.Rank", rankName.toUpperCase(Locale.ROOT));
            return Boolean.TRUE.equals(ReflectionsHelper.invokeMethod(rank, "isAtLeast", rankFromEnum));
        }
        else
        {
            return sender.hasPermission(permission);
        }
    }
}
