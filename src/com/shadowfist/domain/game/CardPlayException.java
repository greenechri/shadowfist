/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

/**
 * @author cgreene
 */
public class CardPlayException extends Exception
{
    private static final long serialVersionUID = -8167039182203048246L;

    /**
     * @param message
     */
    public CardPlayException(String message)
    {
        super(message);
    }

}
