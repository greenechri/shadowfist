/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.ability;

import com.shadowfist.domain.game.Card;


/**
 * @author cgreene
 */
public class ToastIt extends Restriction
{

    public static final ToastIt instance = new ToastIt(null);
    /**
     * @param owner
     */
    public ToastIt(Card owner)
    {
        super(owner);
        // TODO Auto-generated constructor stub
    }

}
