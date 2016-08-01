/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.collections;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.playarea.Destination;
import com.shadowfist.domain.playarea.PlayArea;

/**
 * @author cgreene
 */
public interface Hand extends Iterable<Card>, PlayArea, Destination
{
    
}
