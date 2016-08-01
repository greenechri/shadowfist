/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.resource;

/**
 * @author cgreene
 */
public final class Faction extends Resource
{

    public static final Faction ASCENDED = new Faction("ASC", "Ascended");
    public static final Faction DRAGONS = new Faction("DRA", "Dragons");
    public static final Faction HAND = new Faction("HAN", "GUiding Hand");
    public static final Faction JAMMERS = new Faction("JAM", "Jammers");
    public static final Faction LOTUS = new Faction("LOT", "Eaters of the Lotus");
    public static final Faction MONARCHS = new Faction("MON", "Four Monarch");
    
    /**
     * Construct a Faction enum.
     *
     * @param key
     * @param fullName
     */
    private Faction(String key, String fullName)
    {
        super(key, fullName);
    }
}
