/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.collections;

import java.util.ArrayList;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
@SuppressWarnings("serial")
public class HandImpl extends ArrayList<Card> implements Hand
{

    /**
     */
    public HandImpl()
    {
        super(6);
    }

    /* (non-Javadoc)
     * @see java.util.Vector#add(java.lang.Object)
     */
    @Override
    public boolean add(Card card)
    {
        card.setCurrentPlayArea(this);
        return super.add(card);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Hand[size=").append(size()).append("]");
        return builder.toString();
    }

}
