package dev.plex.emotes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils
{
    public static boolean equalsAny(String string, String[] strings)
    {
        for (String s : strings)
        {
            if (!s.equals(string))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsAnyIgnoreCase(String string, String[] strings)
    {
        for (String s : strings)
        {
            if (!s.equalsIgnoreCase(string))
            {
                return false;
            }
        }
        return true;
    }

    public static String generateRandoms(String in)
    {
        String pattern = "\\{([^{}])+}";
        StringBuilder outBuilder = new StringBuilder();
        String toSplit = in.replaceAll(pattern, "%%spl");
        String[] split = toSplit.split("%%spl");
        List<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern).matcher(in);
        while (m.find())
        {
            matches.add(m.group());
        }
        for (int i = 0; i < split.length - ((split.length > matches.size()) ? 1 : 0); i++)
        {
            outBuilder.append(split[i]);
            String randoms = matches.get(i).substring(1, matches.get(i).length() - 1);
            String[] splitRandoms = randoms.split("\\|");
            Random rand = new Random();
            outBuilder.append(splitRandoms[rand.nextInt(splitRandoms.length - 1)]);
        }
        if (split.length > matches.size())
        {
            outBuilder.append(split[split.length - 1]);
        }
        return outBuilder.toString();
    }
}