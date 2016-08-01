/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

import java.util.List;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.location.Location;
import com.shadowfist.domain.location.Resident;
import com.shadowfist.domain.resource.Resource;

/**
 * @author cgreene
 */
public abstract class Character extends Card implements Resident
{

    private int fighting;
    private int damage;

    /**
     * @param title
     * @param subtitle
     * @param fighting
     * @param cost
     * @param resourceRequirements
     * @param resourceProvisions
     */
    public Character(String title, String subtitle, int fighting, int cost,
            Resource[] resourceRequirements,
            Resource[] resourceProvisions)
    {
        super(title, subtitle, cost, resourceRequirements, resourceProvisions);
        this.fighting = fighting;
    }

    /**
     * @return the fighting
     */
    public int getFighting()
    {
        return fighting;
    }

    /**
     * @param fighting the fighting to set
     */
    public void setFighting(int fighting)
    {
        this.fighting = fighting;
    }

    /**
     * @param i
     */
    public void addDamage(int inflictedDamage)
    {
        addDamage(inflictedDamage, false);
    }

    /**
     * @param i
     */
    public void addDamage(int inflictedDamage, boolean nonCombatDamage)
    {
        damage += inflictedDamage;
    }

    /**
     * @return the damage
     */
    public int getDamage()
    {
        return damage;
    }

    /**
     * @param player
     * @return
     */
    public List<Location> getValidLocations(Player player)
    {
        List<Location> validLocations = player.getInPlay().getLocations();
        if (validLocations.size() == 0)
        {
            validLocations.add(new Location(player.getInPlay()));
        }
        return validLocations;
    }

}
