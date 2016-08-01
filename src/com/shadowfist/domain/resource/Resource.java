/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.resource;

/**
 * @author cgreene
 */
public abstract class Resource
{

    private String key;
    private String fullName;

    /**
     * Construct a Resource enum.
     *
     * @param key
     * @param fullName
     */
    Resource(String key, String fullName)
    {
        this.key = key;
        this.fullName = fullName;
    }

    public String getKey()
    {
        return key;
    }

    public String getFullName()
    {
        return fullName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return key;
    }
}
