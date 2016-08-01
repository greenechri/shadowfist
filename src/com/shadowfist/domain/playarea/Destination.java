/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.playarea;

import com.shadowfist.domain.card.collections.Hand;
import com.shadowfist.domain.card.collections.ToastedPile;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.location.Location;

/**
 * Represents a {@link Hand}, a {@link Location}, a {@link ToastedPile}, etc.
 * Anywhere where cards are grouped and can be played into. It is similar to but
 * slightly more precise than a {@link PlayArea} (because the in play area is
 * split into {@link Location}s.
 *
 * @author cgreene
 */
public interface Destination
{

    /**
     * Add a card to the card destination.
     *
     * @param card
     * @return true if successful.
     */
    public boolean add(Card card);

    /**
     * Remove a card from the destination.
     *
     * @param card
     * @return true if successful.
     */
    public boolean remove(Object card);

    /**
     * Return the number of cards in the destination.
     * 
     * @return
     */
    public int size();
}
