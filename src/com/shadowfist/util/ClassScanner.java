/* @Copyright 2014 parkr@http://stackoverflow.com/questions/862106/how-to-find-annotated-methods-in-a-given-package
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @TODO
 * @deprecated
 */
public class ClassScanner
{
    /**
     * Same as calling <code>getClasses(packageName, null)</code>.
     */
    @SuppressWarnings("rawtypes")
    public static Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException
    {
        return getClasses(packageName, null);
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     * 
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static Iterable<Class> getClasses(String packageName, Class<?> superClass) throws ClassNotFoundException, IOException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements())
        {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs)
        {
            List<Class> foundClasses = findClasses(directory, packageName);
            for (Class foundClass : foundClasses)
            {
                if (superClass.isAssignableFrom(foundClass))
                {
                    classes.add(foundClass);
                }
            }
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     * 
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException
    {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists())
        {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files)
        {
            if (file.isDirectory())
            {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class"))
            {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
