/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.ability;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public abstract class BoldFacedAbility extends Ability
{

    /**
     * @param owner
     */
    public BoldFacedAbility(Card owner)
    {
        super(owner, null);
    }

}
