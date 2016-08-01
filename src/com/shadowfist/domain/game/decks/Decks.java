/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game.decks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.shadowfist.domain.card.collections.Deck;
import com.shadowfist.domain.card.collections.DeckImpl;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.cards.Sets;

/**
 * @author cgreene
 */
public class Decks
{

    private static List<Deck> decks;

    /**
     * Lazily inits and returns all decks available to play.
     *
     * @return
     */
    public static List<Deck> getDecks()
    {
        if (decks == null)
        {
            decks = loadAllDecks();
        }
        return decks;
    }

    /**
     * @return
     */
    private static List<Deck> loadAllDecks()
    {
        List<Deck> allDecks = new ArrayList<Deck>();
        // load Dragons deck
        allDecks.add(loadDeck(null, "CIK-Dragons.properties"));

        // load Lotus deck
        allDecks.add(loadDeck(null, "CIK-Lotus.properties"));

        return allDecks;
    }

    /**
     * @param resourcePath
     * @param deckProperties
     * @return
     */
    private static Deck loadDeck(String resourcePath, String deckProperties)
    {
        if (resourcePath == null)
        {
            resourcePath = Decks.class.getPackage().getName().replace('.', '/');
        }
        try
        {
            Properties props = new Properties();
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null)
            {
                cl = Decks.class.getClassLoader();
            }
            props.load(cl.getResourceAsStream(resourcePath + "/" + deckProperties));
            DeckImpl deck = new DeckImpl(deckProperties.replace(".properties", ""));
            for (Object key : props.keySet())
            {
                Card card = getCard((String)key);
                int count = Integer.parseInt((String)props.get(key));
                for (int i = 0; i < count; i++)
                {
                    deck.add(card.clone());
                }
            }
            return deck;
        }
        catch (NumberFormatException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Assume the card key is the simple class name in either
     * <code>com.shadowfist.domain.game.cards.modern</code> or
     * <code>com.shadowfist.domain.game.cards.classic</code>. Else
     * try to load it as the fully qualified class name.
     *
     * @param cardKey
     * @return
     */
    private static Card getCard(String cardKey)
    {
        Card card = null;
        if (cardKey.contains("."))
        {
            card = getCard(null, cardKey);
        }
        for (String packageName : Sets.PACKAGES)
        {
            if (card != null)
            {
                break;
            }
            card = getCard(packageName, cardKey);
        }

        // lastly just try a non-packaged class
        if (card == null)
        {
            card = getCard(null, cardKey);
        }
        
        return card;
    }

    private static Card getCard(String packageName, String cardKey)
    {
        try
        {
            String className = (packageName != null)? packageName + "." + cardKey : cardKey;
            return (Card)Class.forName(className).newInstance();
        }
        catch (ClassNotFoundException e)
        {
            return null;
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
