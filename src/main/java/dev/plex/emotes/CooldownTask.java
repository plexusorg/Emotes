package dev.plex.emotes;

import dev.plex.emotes.command.Command_emote;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

class CooldownTask extends BukkitRunnable
{
    public void run()
    {
        try
        {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ignored)
        {
        }
        for (CommandSender player : Command_emote.cooldownPlayers.keySet())
        {
            int time = Command_emote.cooldownPlayers.get(player);
            if (time - 1 <= 0)
            {
                Command_emote.cooldownPlayers.remove(player);
                continue;
            }
            Command_emote.cooldownPlayers.put(player, time - 1);
        }
    }
}