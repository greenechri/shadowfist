/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.card.types;

import java.util.List;

import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.location.Resident;

/**
 * @author cgreene
 */
public abstract class State extends Card implements Resident
{

    private Card subject;

    /**
     * @param title
     * @param cost
     */
    public State(String title, int cost)
    {
        super(title, "State", cost);
    }

    /**
     * @return the subject
     */
    public Card getSubject()
    {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Card subject)
    {
        this.subject = subject;
    }

    /**
     * @param subject2
     */
    public void play(Character subject)
    {
        super.play(subject.getLocation());
        setSubject(subject);
    }

    /**
     * @param player
     * @return
     */
    public List<Character> getValidSubjects(Player player)
    {
        return player.getInPlay().getCharacters();
    }
}
