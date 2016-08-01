/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import java.util.Arrays;

import com.shadowfist.domain.ability.Ability;
import com.shadowfist.domain.ability.ToastIt;
import com.shadowfist.domain.location.Location;
import com.shadowfist.domain.playarea.Destination;
import com.shadowfist.domain.playarea.PlayArea;
import com.shadowfist.domain.resource.Resource;


/**
 * @author cgreene
 */
public abstract class Card implements Cloneable
{
    private String title;
    private String subtitle;
    private int cost;
    private boolean turned;
    private Ability[] abilities = new Ability[0];
    private Resource[] resourceRequirements = new Resource[0];
    private Resource[] resourceProvisions = new Resource[0];
    private Location location;
    private Player owner;
    private PlayArea currentPlayArea;

    /**
     * @param title
     * @param subtitle
     * @param cost
     */
    public Card(String title, String subtitle, int cost)
    {
        this(title, subtitle, cost, null, null);
    }

    /**
     * @param title
     * @param subtitle
     * @param cost
     * @param resourceRequirements
     */
    public Card(String title, String subtitle, int cost,
            Resource[] resourceRequirements, Resource[] resourceProvisions)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.cost = cost;
        setResourceRequirements(resourceRequirements);
        setResourceProvisions(resourceProvisions);
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle()
    {
        return subtitle;
    }

    /**
     * @param subtitle the subtitle to set
     */
    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    /**
     * @return the cost
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost)
    {
        this.cost = cost;
    }

    /**
     * @return the turned
     */
    public boolean isTurned()
    {
        return turned;
    }

    /**
     * @param turned the turned to set
     */
    public void setTurned(boolean turned)
    {
        this.turned = turned;
    }

    /**
     * @return the abilities
     */
    public Ability[] getAbilities()
    {
        return abilities;
    }

    /**
     * @param abilities the abilities to set
     */
    public void setAbilities(Ability... abilities)
    {
        if (abilities == null)
        {
            abilities = new Ability[0];
        }
        this.abilities = abilities;
    }

    /**
     * @return the resourceRequirements
     */
    public Resource[] getResourceRequirements()
    {
        return resourceRequirements;
    }

    /**
     * @param resourceRequirements the resourceRequirements to set
     */
    public void setResourceRequirements(Resource... resourceRequirements)
    {
        if (resourceRequirements == null)
        {
            resourceRequirements = new Resource[0];
        }
        this.resourceRequirements = resourceRequirements;
    }

    /**
     * @return the resourceProvisions
     */
    public Resource[] getResourceProvisions()
    {
        return resourceProvisions;
    }

    /**
     * @param resourceProvisions the resourceProvisions to set
     */
    public void setResourceProvisions(Resource... resourceProvisions)
    {
        if (resourceProvisions == null)
        {
            resourceProvisions = new Resource[0];
        }
        this.resourceProvisions = resourceProvisions;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.location.Resident#setLocation(com.shadowfist.domain.location.Location)
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.location.Resident#getLocation()
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * @return the owner
     */
    public Player getOwner()
    {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    /**
     * @return the currentPlayArea
     */
    public PlayArea getCurrentPlayArea()
    {
        return currentPlayArea;
    }

    /**
     * @return
     */
    public boolean isToastIt()
    {
        return Arrays.asList(abilities).contains(ToastIt.instance);
    }

    /**
     * Owner of the card put the card into their smoked (or toasted) pile to
     * activate the ability.
     */
    protected void play()
    {
        if (isToastIt())
        {
            play(owner.getToastedPile());
        }
        else
        {
            play(owner.getOutOfPlay().getSmokedPile());
        }
    }

    /**
     * Owner of the card put the card into the specified destination.
     *
     * @param destination smoked pile, location, subject, or InPlay (edges).
     */
    protected void play(Destination destination)
    {
        destination.add(this);
    }

    /**
     * @param inPlay
     */
    public void setCurrentPlayArea(PlayArea playArea)
    {
        if (currentPlayArea != null)
        {
            currentPlayArea.remove(this);
        }
        currentPlayArea = playArea;
   }

    /**
     */
    public void activateRulesText()
    {
        for (Ability a : abilities)
        {
            a.setActive(true);
        }
    }

    /**
     * Override to do something when the effect is generated. Usually cards do
     * nothing here. It is the ability that may do something. However certain
     * card types, like FSS gain 1 Power, may do something.
     */
    public void effectGenerated()
    {
    }

    /**
     * Resolve the effect create by this card. Events can use their ability.
     */
    public void resolveEffect()
    {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Card clone()
    {
        try
        {
            return (Card)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return this;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Card[title=").append(title).append("]");
        return builder.toString();
    }
}
