/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.scene;

import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.card.types.Edge;
import com.shadowfist.domain.card.types.Site;
import com.shadowfist.domain.card.types.State;
import com.shadowfist.domain.game.Card;

/**
 * @author cgreene
 */
public class PlayCardEffect extends Effect
{

    private Card card;

    /**
     * @param card
     */
    public PlayCardEffect(Card card)
    {
        this.card = card;
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.scene.Effect#generate()
     */
    @Override
    public void generate()
    {
        if (card instanceof Character || card instanceof Site)
        {
            card.activateRulesText();
        }
        else if (card instanceof State || card instanceof Edge)
        {
        }
        card.effectGenerated();
    }

    /* (non-Javadoc)
     * @see com.shadowfist.domain.scene.Effect#resolve()
     */
    @Override
    public void resolve()
    {
        if (card instanceof State || card instanceof Edge)
        {
            card.activateRulesText();
        }
        if (card instanceof Character)
        {
            // no effect, card is in destination
        }
        card.resolveEffect();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("PlayCardEffect[card=");
        builder.append(card).append("]");
        return builder.toString();
    }

}
