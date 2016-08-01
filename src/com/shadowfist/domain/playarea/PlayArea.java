/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.playarea;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public interface PlayArea extends Iterable<Card>
{

    /**
     * @param card
     */
    public boolean remove(Object card);
}
