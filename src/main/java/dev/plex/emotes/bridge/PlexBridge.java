package dev.plex.emotes.bridge;

import dev.plex.Plex;
import dev.plex.cache.DataUtils;
import dev.plex.emotes.EmotesBase;
import dev.plex.player.PlexPlayer;
import dev.plex.rank.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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

    public boolean checkRank(CommandSender sender, Rank rank, String permission)
    {
        if (getPlex() == null)
        {
            Bukkit.getLogger().warning("Plex not detected either. Using standard permission check for permission: " + permission);
            return sender.hasPermission(permission);
        }
        if (Plex.get().getSystem().equalsIgnoreCase("ranks"))
        {
            PlexPlayer plexPlayer = DataUtils.getPlayer(sender.getName());
            return plexPlayer.getRankFromString().isAtLeast(rank);
        }
        else
        {
            return sender.hasPermission(permission);
        }
    }
}
