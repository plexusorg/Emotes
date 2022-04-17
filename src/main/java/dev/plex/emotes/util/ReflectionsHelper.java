package dev.plex.emotes.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionsHelper
{
    public static <T> Object getField(T instance, String name)
    {
        try
        {
            Field f = instance.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(instance);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Method getMethod(T instance, String name, Class<?>... parameterTypes)
    {
        try
        {
            Method f = instance.getClass().getDeclaredMethod(name, parameterTypes);
            f.setAccessible(true);
            return f;
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T, I> I invokeMethod(T instance, String name, Object... parameters)
    {
        try
        {
            Method f = instance.getClass().getDeclaredMethod(name, Arrays.stream(parameters).map(Object::getClass).toList().toArray(Class[]::new));
            f.setAccessible(true);
            return (I)f.invoke(instance, parameters);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T, I> I invokeStaticMethod(String classPath, String name, Object... parameters)
    {
        try
        {
            Method f = Class.forName(classPath).getDeclaredMethod(name, Arrays.stream(parameters).map(Object::getClass).toList().toArray(Class[]::new));
            f.setAccessible(true);
            return (I)f.invoke(null, parameters);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Enum<?> getEnumValue(String enumClassPath, String enumVal)
    {
        try
        {
            Class<Enum> clazz = (Class<Enum>)Class.forName(enumClassPath);
            return Enum.valueOf(clazz, enumVal);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
