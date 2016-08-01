/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.playarea;

import com.shadowfist.domain.card.collections.BurnedForVictoryPile;
import com.shadowfist.domain.card.collections.Deck;
import com.shadowfist.domain.card.collections.Hand;
import com.shadowfist.domain.card.collections.HandImpl;
import com.shadowfist.domain.card.collections.SmokedPile;
import com.shadowfist.domain.game.GamePart;
import com.shadowfist.domain.game.Player;

/**
 * @author cgreene
 */
public class OutOfPlay extends GamePart
{

    private SmokedPile smokedPile;
    private Deck deck;
    private Hand hand;
    private int power;
    private BurnedForVictoryPile bfvPile;

    /**
     * @param player 
     */
    public OutOfPlay(Player player)
    {
        super(player);
        smokedPile = new SmokedPile();
        hand = new HandImpl();
        bfvPile = new BurnedForVictoryPile();
    }

    /**
     * @return the smokedPile
     */
    public SmokedPile getSmokedPile()
    {
        return smokedPile;
    }

    /**
     * @param smokedPile the smokedPile to set
     */
    public void setSmokedPile(SmokedPile smokedPile)
    {
        this.smokedPile = smokedPile;
    }

    /**
     * @return the deck
     */
    public Deck getDeck()
    {
        return deck;
    }

    /**
     * @param deck the deck to set
     */
    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }

    /**
     * @return the hand
     */
    public Hand getHand()
    {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(Hand hand)
    {
        this.hand = hand;
    }

    /**
     * @return the power in the pool
     */
    public int getPower()
    {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(int power)
    {
        this.power = power;
    }

    /**
     * @return the bfvPile
     */
    public BurnedForVictoryPile getBfvPile()
    {
        return bfvPile;
    }

    /**
     * @param bfvPile the bfvPile to set
     */
    public void setBfvPile(BurnedForVictoryPile bfvPile)
    {
        this.bfvPile = bfvPile;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("OutOfPlay:\n");
        builder.append("  ").append(deck).append("\n");
        builder.append("  ").append(hand).append("\n");
        builder.append("  Power=").append(power).append("\n");
        builder.append("  ").append(smokedPile).append("\n");
        builder.append("  ").append(bfvPile);
        return builder.toString();
    }
}
