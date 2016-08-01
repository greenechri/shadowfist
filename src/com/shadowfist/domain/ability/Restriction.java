/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.ability;

import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public abstract class Restriction extends Ability
{

    /**
     * @param owner
     */
    public Restriction(Card owner)
    {
        super(owner, null);
        setActive(true);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.ability.Ability#use()
     */
    @Override
    public void use()
    {
        // TODO Auto-generated method stub
        
    }
}
