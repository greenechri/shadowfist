/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.ability;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public abstract class Ability
{
    private Card owner;
    private boolean active;
    private boolean blanked;
    private String text;

    /**
     * @param owner
     * @param string
     */
    public Ability(Card owner, String text)
    {
        this.owner = owner;
        this.text = text;
    }

    /**
     * @return the owner
     */
    public Card getOwner()
    {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Card owner)
    {
        this.owner = owner;
    }

    /**
     * @return the blanked
     */
    public boolean isBlanked()
    {
        return blanked;
    }

    /**
     * @param blanked the blanked to set
     */
    public void setBlanked(boolean blanked)
    {
        this.blanked = blanked;
    }

    
    /**
     * @return the active
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Use this ability.
     */
    public abstract void use();

}
