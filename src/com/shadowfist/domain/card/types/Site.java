/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

import java.util.ArrayList;
import java.util.List;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.location.Location;
import com.shadowfist.domain.location.Resident;
import com.shadowfist.domain.playarea.InPlay;

/**
 * @author cgreene
 */
public abstract class Site extends Card implements Resident
{

    private int power;
    private int body;

    /**
     * @param title
     * @param subtitle
     * @param cost
     * @param body
     * @param power
     */
    public Site(String title, String subtitle, int cost, int power, int body)
    {
        super(title, subtitle, cost);
        this.power = power;
        this.body = body;
    }

    /**
     * @return the power
     */
    public int getPower()
    {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(int power)
    {
        this.power = power;
    }

    /**
     * @return the body
     */
    public int getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(int body)
    {
        this.body = body;
    }

    /**
     * @param player
     * @return
     */
    public List<Location> getValidLocations(Player player)
    {
        InPlay inPlay = player.getInPlay();
        List<Location> locations = inPlay.getLocations();
        List<Location> validLocations = new ArrayList<Location>(locations.size());
        for (Location l : locations)
        {
            if (l.getFrontRow() == null || l.getBackRow() == null)
            {
                validLocations.add(l);
            }
        }

        if (validLocations.size() == 1 && validLocations.get(0).isUnspecified())
        {
            return validLocations;
        }

        validLocations.add(new Location(inPlay));
        return validLocations;
    }
}
