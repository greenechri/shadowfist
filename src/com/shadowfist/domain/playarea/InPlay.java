/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.playarea;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Edge;
import com.shadowfist.domain.card.types.FengShuiSite;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.GamePart;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.location.Location;
import com.shadowfist.domain.location.Resident;

/**
 * A in-play area for for a single player.
 *
 * @author cgreene
 */
public class InPlay extends GamePart implements PlayArea, Destination
{

    private Set<Edge> edges;
    private List<Location> locations;

    /**
     * @param player
     */
    public InPlay(Player player)
    {
        super(player);
        edges = new HashSet<Edge>();
        locations = new ArrayList<Location>();  
    }

    /**
     * @return the edges
     */
    public Set<Edge> getEdges()
    {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(Set<Edge> edges)
    {
        this.edges = edges;
    }

    /**
     * @return the locations
     */
    public List<Location> getLocations()
    {
        return locations;
    }

    /**
     * @param location
     */
    public boolean addLocation(Location location)
    {
        // the location should already know about us when it was constructed
        return locations.add(location);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#add(com.shadowfist.domain.card.Card)
     */
    @Override
    public boolean add(Card card)
    {
        card.setCurrentPlayArea(this);
        return edges.add((Edge)card);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#remove(Object)
     */
    @Override
    public boolean remove(Object card)
    {
        return edges.remove(card);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.playarea.Destination#size()
     */
    @Override
    public int size()
    {
        return edges.size();
    }

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Card> iterator()
    {
        List<Card> cards = new ArrayList<Card>();
        for (Location l : getLocations())
        {
            for (Resident r : l.getResidents())
            {
                cards.add((Card)r);
            }
            if (l.getFrontRow() != null)
            {
                cards.add(l.getFrontRow());
            }
            if (l.getBackRow() != null)
            {
                cards.add(l.getBackRow());
            }
        }
        cards.addAll(edges);
        return cards.iterator();
    }

    /**
     * Gets all the Characters in this InPlay area.
     *
     * @return
     */
    public List<Character> getCharacters()
    {
        List<Character> characters = new ArrayList<Character>();
        for (Location l : locations)
        {
            characters.addAll(l.getCharacters());
        }
        return characters;
    }

    /**
     * @return
     */
    public List<FengShuiSite> getFengShuiSites()
    {
        List<FengShuiSite> fss = new ArrayList<FengShuiSite>();
        for (Location l : locations)
        {
            if (l.getFrontRow() instanceof FengShuiSite)
            {
                fss.add((FengShuiSite)l.getFrontRow());
            }
            if (l.getBackRow() instanceof FengShuiSite)
            {
                fss.add((FengShuiSite)l.getBackRow());
            }        
        }
        return fss;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("InPlay:");
        for (int i = 0; i < locations.size(); i++)
        {
            builder.append("\n  Location-").append(i + 1).append("\n    ");
            builder.append(locations.get(i));
        }
        builder.append("\n  Edges:");
        for (Edge edge : edges)
        {
            builder.append("\n  ").append(edge);
        }
        return builder.toString();
    }
}