package dev.plex.emotes.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

public class FileUtils
{
    public static String readFile(File file) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    @Deprecated
    public static String readStream(InputStream stream) throws IOException
    {
        BufferedInputStream in = new BufferedInputStream(stream);
        byte[] contents = new byte[1024];
        int bytesRead = 0;
        String strFileContents = null;
        while ((bytesRead = in.read(contents)) != -1)
        {
            strFileContents = strFileContents + new String(contents, 0, bytesRead);
        }
        return strFileContents;
    }

    public static void writeToFile(File file, String text) throws FileNotFoundException
    {
        PrintWriter out = new PrintWriter(file);
        out.println(text);
        out.close();
    }

    public static void copyFile(File origin, File file) throws IOException
    {
        org.apache.commons.io.FileUtils.copyFile(origin, file);
    }

    public static void copyFile(URL origin, File file) throws IOException
    {
        org.apache.commons.io.FileUtils.copyURLToFile(origin, file);
    }
}
