package dev.plex.emotes;

import dev.plex.emotes.bridge.PlexBridge;
import dev.plex.emotes.bridge.TFMBridge;
import dev.plex.emotes.command.CommandLoader;
import dev.plex.emotes.command.Command_emotes;
import dev.plex.emotes.util.EmoteLoader;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class Emotes extends JavaPlugin
{
    private static Emotes plugin;
    public TFMBridge tfmBridge;

    public PlexBridge plexBridge;
    public CooldownTask cooldownTask;

    public static Emotes get()
    {
        return plugin;
    }

    public void onEnable()
    {
        plugin = this;
        saveDefaultConfig();
        plugin.getCommand("emotes").setExecutor(new Command_emotes());
        if (!getServer().getPluginManager().isPluginEnabled("TotalFreedomMod")
                && !getServer().getPluginManager().isPluginEnabled("Plex"))
        {
            getLogger().log(Level.WARNING, "Plex nor TotalFreedomMod was found. Defaulting to if the player is op for permissions");
        }
        this.tfmBridge = new TFMBridge();
        tfmBridge.getTFM();
        this.plexBridge = new PlexBridge();
        plexBridge.getPlex();
        this.cooldownTask = new CooldownTask();
        this.cooldownTask.runTaskTimerAsynchronously(plugin, 0L, 20L);
        try
        {
            EmoteLoader.registerDefaultEmote("greet");
            EmoteLoader.registerDefaultEmote("highfive");
            EmoteLoader.registerDefaultEmote("dance");
            EmoteLoader.registerDefaultEmote("hug");
            EmoteLoader.registerDefaultEmote("smile");
            EmoteLoader.registerDefaultEmote("thumbsup");
            EmoteLoader.registerOtherDefault("emotes", "_Emote Reference Sheet_.txt", "emotes");
            EmoteLoader.saveOtherDefaults(getDataFolder());
            EmoteLoader.saveDefaultEmotes(new File(getDataFolder() + "/emotes"));
            EmoteLoader.loadEmotes(new File(getDataFolder() + "/emotes"));
            CommandLoader.loadAllCommands();
        }
        catch (IOException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public void onDisable()
    {
        this.cooldownTask.cancel();
    }
}
