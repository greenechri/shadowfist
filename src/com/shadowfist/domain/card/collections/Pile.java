/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.collections;

import java.util.Stack;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.playarea.PlayArea;

/**
 * @author cgreene
 */
@SuppressWarnings("serial")
public abstract class Pile extends Stack<Card>
{

    /* (non-Javadoc)
     * @see java.util.Vector#add(java.lang.Object)
     */
    @Override
    public boolean add(Card card)
    {
        if (this instanceof PlayArea)
        {
            card.setCurrentPlayArea((PlayArea)this);
        }
        return super.add(card);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String name = getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(name).append("[");
        appendToString(builder);
        builder.append("size=").append(size()).append("]");
        return builder.toString();
    }

    /**
     * Override to add more attributes.
     *
     * @param toStringBuilder
     */
    protected void appendToString(StringBuilder toStringBuilder)
    {
        
    }
}
