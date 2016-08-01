/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.collections;

import java.util.Collections;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
@SuppressWarnings("serial")
public class DeckImpl extends Pile implements Deck
{

    private String name;

    /**
     * @param name
     */
    public DeckImpl(String name)
    {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.card.collections.Deck#draw()
     */
    @Override
    public Card draw()
    {
        return pop();
    }

    /* (non-Javadoc)
     * @see java.util.Vector#clone()
     */
    @Override
    public Deck clone()
    {
        return (Deck)super.clone();
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.card.collections.Deck#shuffle()
     */
    @Override
    public void shuffle()
    {
        Collections.shuffle(this);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.card.collections.Pile#appendToString(java.lang.StringBuilder)
     */
    @Override
    protected void appendToString(StringBuilder toStringBuilder)
    {
        toStringBuilder.append("name=").append(name).append(",");
    }
}
