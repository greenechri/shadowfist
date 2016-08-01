/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.resource;

/**
 * @author cgreene
 */
public final class Talent extends Resource
{

    public static final Talent CHI = new Talent("CHI", "Chi");
    public static final Talent MAGIC = new Talent("MAG", "Magic");
    public static final Talent TECH = new Talent("TEC", "High Tech");

    /**
     * Construct a Talent enum.
     *
     * @param key
     * @param fullName
     */
    private Talent(String key, String fullName)
    {
        super(key, fullName);
    }
}
