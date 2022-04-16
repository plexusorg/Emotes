package dev.plex.emotes.command;

import dev.plex.emotes.EmotesBase;
import dev.plex.emotes.util.EmoteMeta;
import dev.plex.emotes.util.EmoteVariable;
import dev.plex.emotes.util.MessageUtils;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_emote implements EmoteCommandExecutor, EmotesBase
{
    public static HashMap<CommandSender, Integer> cooldownPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, EmoteMeta emoteMeta, String label, String[] args)
    {
        if (cooldownPlayers.containsKey(sender))
        {
            sender.sendMessage(ChatColor.GRAY + MessageUtils.colorize(plugin.getConfig().getString("emotes.cooldown-message").replace("%cooldown%", String.valueOf(cooldownPlayers.get(sender)))));
            return true;
        }
        HashMap<EmoteVariable, String> variableMap = emoteMeta.getVariableMap();
        HashMap<String, String> toReplace = new HashMap<>();
        for (EmoteVariable var : variableMap.keySet())
        {
            int last;
            String custom = variableMap.get(var);
            switch (var)
            {
                case USER:
                    toReplace.put("%" + custom + "%", sender.getName());
                case ARGUMENT_0:
                    if (args.length < 1)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[0]);
                case ARGUMENT_1:
                    if (args.length < 2)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[1]);
                case ARGUMENT_2:
                    if (args.length < 3)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[2]);
                case ARGUMENT_3:
                    if (args.length < 4)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[3]);
                case ARGUMENT_4:
                    if (args.length < 5)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[4]);
                case ARGUMENT_5:
                    if (args.length < 6)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[5]);
                case ARGUMENT_6:
                    if (args.length < 7)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[6]);
                case ARGUMENT_7:
                    if (args.length < 8)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[7]);
                case ARGUMENT_8:
                    if (args.length < 9)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[8]);
                case ARGUMENT_9:
                    if (args.length < 10)
                    {
                        continue;
                    }
                    toReplace.put("%" + custom + "%", args[9]);
                case REST:
                    last = 0;
                    for (EmoteVariable v : variableMap.keySet())
                    {
                        if (v.toString().startsWith("ARGUMENT") &&
                                Integer.parseInt(v.toString().substring(9, 10)) > last)
                        {
                            last = Integer.parseInt(v.toString().substring(9, 10));
                        }
                    }
                    toReplace.put("%" + custom + "%", StringUtils.join(ArrayUtils.subarray((Object[])args, last + 1, args.length), " "));
            }
        }
        HashMap<List<EmoteVariable>, String[]> lines = emoteMeta.getLineMap();
        List<EmoteVariable> variableList = null;
        String[] stringArray = null;
        for (List<EmoteVariable> emoteVariableList : lines.keySet())
        {
            if (!emoteVariableList.contains(EmoteVariable.USER))
            {
                sender.sendMessage(ChatColor.RED + "This emote is badly formatted and cannot be ran.");
                sender.sendMessage(ChatColor.RED + "For more information please check the error message in the console.");
                MessageUtils.debugMessage(Level.SEVERE, "The emote " + emoteMeta.getName() + " does not have 'USER' as a parameter in all lines and thus cannot be ran.");
                return true;
            }
            if (emoteVariableList.contains(EmoteVariable.REST) &&
                    args.length > emoteVariableList.size())
            {
                variableList = emoteVariableList;
                stringArray = lines.get(emoteVariableList);
                break;
            }
            if (args.length == emoteVariableList.size() - 1)
            {
                variableList = emoteVariableList;
                stringArray = lines.get(emoteVariableList);
                break;
            }
        }
        if (variableList == null)
        {
            sender.sendMessage(emoteMeta.getUsage());
            return false;
        }
        for (String string : stringArray)
        {
            String message = string;
            for (String custom : toReplace.keySet())
            {
                message = message.replace(custom, toReplace.get(custom));
            }
            MessageUtils.globalEmoteMessage(MessageUtils.colorize(message));
        }
        cooldownPlayers.put(sender, plugin.getConfig().getInt("emotes.cooldown"));
        return true;
    }
}
