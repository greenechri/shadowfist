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
public class ProvingGround extends FengShuiSite
{

    /**
     */
    public ProvingGround()
    {
        super("Proving Ground", 0, 6);
        this.setAbilities(new TurnedWhenRevealed(this), new PlayCharacterAbility(this));
    }


}

// -------------------------------------------------------------------------

class TurnedWhenRevealed extends Ability
{
    public TurnedWhenRevealed(Card owner)
    {
        super(owner, "When Proving Ground is revealed, immediately turn it for no effect.");
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

class PlayCharacterAbility extends Ability
{
    public PlayCharacterAbility(Card owner)
    {
        super(owner, "If Proving Ground is face-up, turn :: Play a Character at -2 cost.");
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