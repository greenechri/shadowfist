/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public abstract class Edge extends Card
{

    /**
     * @param title
     * @param cost
     */
    public Edge(String title, int cost)
    {
        super(title, "Edge", cost);
    }
}
