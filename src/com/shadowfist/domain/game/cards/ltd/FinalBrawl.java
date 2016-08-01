/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.cards.ltd;

import com.shadowfist.domain.ability.Ability;
import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Event;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.resource.Faction;
import com.shadowfist.domain.resource.Resource;

/**
 * @author cgreene
 */
public class FinalBrawl extends Event
{

    /**
     * @param title
     * @param subtitle
     * @param cost
     */
    public FinalBrawl()
    {
        super("Final Brawl", 0, new Resource[] { Faction.DRAGONS });
        this.setAbilities(new Inflict2DamageOnAllCharacters(this));
    }

}

// -------------------------------------------------------------------------

class Inflict2DamageOnAllCharacters extends Ability
{
    public Inflict2DamageOnAllCharacters(Card owner)
    {
        super(owner, "Inflict 2 non-combat damage on all Characters.");
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.ability.Ability#use()
     */
    @Override
    public void use()
    {
        for (Player p : getOwner().getOwner().getGame().getPlayers())
        {
            for (Character c : p.getInPlay().getCharacters())
            {
                c.addDamage(2);
            }
        }
    }
}
