/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.cards.cik;

import com.shadowfist.domain.ability.Ability;
import com.shadowfist.domain.card.types.FengShuiSite;
import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public class DragonGraveyard extends FengShuiSite
{

    /**
     */
    public DragonGraveyard()
    {
        super("Dragon Graveyard", 1, 8);
        this.setAbilities(new Gain1PowerAbility(this), new DiscardAndCancelAbility(this));
    }

}

// -------------------------------------------------------------------------

class Gain1PowerAbility extends Ability
{
    public Gain1PowerAbility(Card owner)
    {
        super(owner, "Gain 1 Power when 3 or more combat damage is inflicted on this card.");
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

class DiscardAndCancelAbility extends Ability
{
    public DiscardAndCancelAbility(Card owner)
    {
        super(owner, "If this card is in your hand when an opponent's effect attempts to look at your hand or force you to discard, you may discard it to cancel that effect and gain 2 Power.");
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