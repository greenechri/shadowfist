/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Site;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.GamePart;
import com.shadowfist.domain.playarea.Destination;
import com.shadowfist.domain.playarea.InPlay;

/**
 * @author cgreene
 */
public class Location extends GamePart implements Destination
{
    /** Residents include Characters and States. */
    private Set<Resident> residents;
    private Site frontRow;
    private Site backRow;

    /**
     * @param inPlay
     */
    public Location(InPlay inPlay)
    {
        super(inPlay);
        residents = new HashSet<Resident>();
    }

    /**
     * @return the residents
     */
    public Set<Resident> getResidents()
    {
        return residents;
    }

    /**
     * Gets all the Characters in this Location.
     *
     * @return
     */
    public List<Character> getCharacters()
    {
        List<Character> characters = new ArrayList<Character>();
        for (Resident r : getResidents())
        {
            if (r instanceof Character)
            {
                characters.add((Character)r);
            }
        }
        return characters;
    }

    /**
     * @return the frontRow
     */
    public Site getFrontRow()
    {
        return frontRow;
    }

    /**
     * @param frontRow the frontRow to set
     */
    public void setFrontRow(Site frontRow)
    {
        this.frontRow = frontRow;
    }

    /**
     * @return the backRow
     */
    public Site getBackRow()
    {
        return backRow;
    }

    /**
     * @param backRow the backRow to set
     */
    public void setBackRow(Site backRow)
    {
        this.backRow = backRow;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#add(com.shadowfist.domain.card.Card)
     */
    @Override
    public boolean add(Card card)
    {
        boolean added = false;
        if (card instanceof Site)
        {
            if (frontRow == null)
            {
                setFrontRow((Site)card);
                added = true;
            }
            else
            {
                setBackRow((Site)card);
                added = true;
            }
        }
        else
        {
            added = residents.add((Resident)card);
        }
        if (added)
        {
            card.setCurrentPlayArea((InPlay)getParent());
        }
        return added;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object card)
    {
        if (card == frontRow)
        {
            frontRow = null;
            return true;
        }
        else if (card == backRow)
        {
            backRow = null;
            return true;
        }
        return residents.remove((Resident)card);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#size()
     */
    @Override
    public int size()
    {
        int size = residents.size();
        if (frontRow != null)
        {
            size++;
        }
        if (backRow != null)
        {
            size++;
        }
        return size;
    }

    /**
     * Returns true if there are no sites at this location.
     *
     * @return
     */
    public boolean isUnspecified()
    {
        return (frontRow == null && backRow == null);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("characters=").append(getCharacters()).append("\n");
        builder.append("    frontRow=").append(frontRow).append("\n");
        builder.append("    backRow=").append(backRow);
        return builder.toString();
    }
}