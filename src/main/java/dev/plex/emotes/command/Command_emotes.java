package dev.plex.emotes.command;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.util.EmoteLoader;
import dev.plex.emotes.util.EmoteMeta;
import dev.plex.emotes.util.MessageUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command_emotes implements CommandExecutor, EmotesBase
{
    public static List<CommandSender> mutedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("help"))
        {
            if (args.length > 2)
            {
                EmoteMeta emote = null;
                try
                {
                    emote = EmoteLoader.getEmoteByCommand(args[1]);
                    if (emote == null)
                    {
                        emote = EmoteLoader.getEmoteByName(args[1]);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (emote != null)
                {
                    sender.sendMessage(MessageUtils.colorize("&6Unknown Command: &c" + args[1]));
                }
                sender.sendMessage(MessageUtils.colorize("&e -- &6Emote Help: &c" + emote.getName() + "&e -- "));
                sender.sendMessage(MessageUtils.colorize("&6Description: &e" + emote.getDescription()));
                sender.sendMessage(MessageUtils.colorize("&6Usage: &e" + emote.getUsage()));
                sender.sendMessage(MessageUtils.colorize("&6Author: &e" + emote.getAuthor()));
                return true;
            }
            List<EmoteMeta> emoteList = EmoteLoader.getAllEmotes();
            sender.sendMessage(MessageUtils.colorize("&e ---- &6Emotes &e----"));
            for (EmoteMeta emote : emoteList)
            {
                sender.sendMessage(ChatColor.GOLD + "/" + emote.getName().toLowerCase() + ChatColor.WHITE + ": " + emote.getDescription());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("mute") || args[0].equalsIgnoreCase("unmute"))
        {
            sender.sendMessage(mutedPlayers.contains(sender) ? (ChatColor.GREEN + "You will now see all Emotes.") : (ChatColor.RED + "You will no longer see any Emotes."));
            if (mutedPlayers.contains(sender))
            {
                mutedPlayers.remove(sender);
            }
            else
            {
                mutedPlayers.add(sender);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("reload"))
        {
            if (plugin.tfmBridge.isAdmin(sender))
            {
                sender.sendMessage(ChatColor.GREEN + "Reloading Emotes!");
                plugin.reloadConfig();
                plugin.loadCommands();
                return true;
            }
            else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Plex") && plugin.plexBridge.checkRank(sender, "ADMIN", "plex.emotes.reload"))
            {
                sender.sendMessage(ChatColor.GREEN + "Reloading Emotes!");
                plugin.reloadConfig();
                plugin.loadCommands();
                return true;
            }
            else if (sender.hasPermission("emotes.reload"))
            {
                sender.sendMessage(ChatColor.GREEN + "Reloading Emotes!");
                plugin.reloadConfig();
                plugin.loadCommands();
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }
        }
        return false;
    }
}
