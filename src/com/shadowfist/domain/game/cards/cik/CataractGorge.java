/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.cards.cik;

import com.shadowfist.domain.ability.Ability;
import com.shadowfist.domain.ability.Unique;
import com.shadowfist.domain.card.types.FengShuiSite;
import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public class CataractGorge extends FengShuiSite
{

    /**
     */
    public CataractGorge()
    {
        super("Cataract Gorge", 1, 7);
        this.setAbilities(Unique.instance, new GainIndependentAbility(this));
    }

}

// -------------------------------------------------------------------------

class GainIndependentAbility extends Ability
{
    public GainIndependentAbility(Card owner)
    {
        super(owner, "Turn and target a Character :: Target gains Independent until the end of the turn.");
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
