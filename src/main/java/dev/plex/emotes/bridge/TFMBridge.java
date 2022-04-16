package dev.plex.emotes.bridge;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.util.ReflectionsHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TFMBridge implements EmotesBase
{
    private Plugin tfm = null;

    public Plugin getTFM()
    {
        if (tfm == null)
        {
            try
            {
                final Plugin tfmPlugin = plugin.getServer().getPluginManager().getPlugin("TotalFreedomMod");
                if (tfmPlugin != null && tfmPlugin.isEnabled())
                {
                    tfm = tfmPlugin;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return tfm;
    }

    public boolean isAdmin(CommandSender sender)
    {
        if (getTFM() == null)
        {
            Bukkit.getLogger().warning("TotalFreedomMod not detected, we're going to try to see if Plex is on the server.");
            return false;
        }
        Object al = ReflectionsHelper.getField(getTFM(), "al");
        Method isAdmin = ReflectionsHelper.getMethod(al, "isAdmin", Player.class);
        try
        {
            return (boolean)isAdmin.invoke(al, sender);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isVanished(Player player)
    {
        if (getTFM() == null)
        {
            Bukkit.getLogger().warning("TotalFreedomMod not detected, vanish will return false.");
            return false;
        }
        Object al = ReflectionsHelper.getField(getTFM(), "al");
        Method isVanished = ReflectionsHelper.getMethod(al, "isVanished", String.class);
        try
        {
            return (boolean)isVanished.invoke(al, player.getName());
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
