/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.collections;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.playarea.PlayArea;

/**
 * @author cgreene
 */
public interface Deck extends Iterable<Card>, PlayArea
{

    /**
     * Retrieve the top card from the deck.
     *
     * @return
     */
    public Card draw();

    /**
     * Shuffle the deck randomly.
     */
    public void shuffle();

    /**
     * @return
     */
    public Deck clone();

    /**
     * @return
     */
    public String getName();
}
