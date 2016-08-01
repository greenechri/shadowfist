/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.ability;

import com.shadowfist.domain.game.Card;


/**
 * @author cgreene
 */
public class Unique extends Restriction
{

    public static final Unique instance = new Unique(null);
    /**
     * @param owner
     */
    public Unique(Card owner)
    {
        super(owner);
        // TODO Auto-generated constructor stub
    }

}
