/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.shadowfist.domain.card.collections.Deck;
import com.shadowfist.domain.card.collections.Hand;
import com.shadowfist.domain.card.collections.ToastedPile;
import com.shadowfist.domain.card.types.Event;
import com.shadowfist.domain.playarea.InPlay;
import com.shadowfist.domain.playarea.OutOfPlay;
import com.shadowfist.domain.resource.Resource;
import com.shadowfist.domain.scene.Effect;
import com.shadowfist.ui.events.PlayerActionRequestEvent;
import com.shadowfist.ui.events.PlayerActionRequestEvent.PlayerAction;
import com.shadowfist.ui.events.PlayerActionRequestListener;

/**
 * @author cgreene
 */
public class Player extends GamePart
{

    private String name;
    private InPlay inPlay;
    private OutOfPlay outOfPlay;
    private ToastedPile toastedPile;
    private boolean skippingPowerGeneration;
    private boolean dummy;
    private boolean thinking;
    private transient Effect response;

    /**
     * Construct a dummy player.
     *
     * @param game
     */
    public Player(Game game)
    {
        this(game, null);
    }

    /**
     * @param game
     * @param readln
     */
    public Player(Game game, String name)
    {
        super(game);
        if (name != null)
        {
            this.name = name;
        }
        else
        {
            dummy = true;
            // starts with capital 'A'
            char alias = (char)(++game.dummyCount + 64);
            setName("Player-" + alias);
        }
        inPlay = new InPlay(this);
        outOfPlay = new OutOfPlay(this);
        toastedPile = new ToastedPile();
    }

    /**
     * 
     */
    public void declareEndOfTurn()
    {
        getGame().endOfTurn();
    }

    /**
     * @return the name
     */
    public Game getGame()
    {
        return (Game)getParent();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the inPlay
     */
    public InPlay getInPlay()
    {
        return inPlay;
    }

    /**
     * @param inPlay the inPlay to set
     */
    public void setInPlay(InPlay inPlay)
    {
        this.inPlay = inPlay;
    }

    /**
     * @return the outOfPlay
     */
    public OutOfPlay getOutOfPlay()
    {
        return outOfPlay;
    }

    /**
     * @param outOfPlay the outOfPlay to set
     */
    public void setOutOfPlay(OutOfPlay outOfPlay)
    {
        this.outOfPlay = outOfPlay;
    }

    /**
     * @return the toastedPile
     */
    public ToastedPile getToastedPile()
    {
        return toastedPile;
    }

    /**
     * @param toastedPile the toastedPile to set
     */
    public void setToastedPile(ToastedPile toastedPile)
    {
        this.toastedPile = toastedPile;
    }

    /**
     * Returns true if this is not the primary player, but a dummy.
     *
     * @return
     */
    public boolean isDummy()
    {
        return dummy;
    }

    /**
     * @return the thinking
     */
    public boolean isThinking()
    {
        return thinking;
    }

    /**
     * @param thinking the thinking to set
     */
    public void setThinking(boolean thinking)
    {
        boolean oldValue = this.thinking;
        this.thinking = thinking;
        firePropertyChange("thinking", oldValue, thinking);
        getGame().notify();
    }

    /**
     * Return the hand size plus or minus any abilities applicable to this
     * player. Defaults to 6.
     *
     * @return
     */
    public int getHandSize()
    {
        return 6;
    }

    /**
     * @return
     */
    public int getPower()
    {
        return getOutOfPlay().getPower();
    }

    /**
     * @return
     */
    protected boolean isSkippingPowerGeneration()
    {
        return skippingPowerGeneration;
    }

    /**
     * @param deck
     */
    public void useDeck(Deck deck)
    {
        if (deck != null && getGame().isStarted())
        {
            throw new IllegalStateException("Player already has deck set.");
        }
        for (Card card : deck)
        {
            card.setOwner(this);
        }
        getOutOfPlay().setDeck(deck);
    }

    /**
     */
    protected void startTurn()
    {
    }

    /**
     */
    protected void generatePower()
    {
    }

    /**
     */
    protected void unturnCards()
    {
        getInPlay();
    }

    /**
     */
    protected void discard()
    {
    }

    /**
     * Draw to the default number of cards, six.
     */
    protected void drawCards()
    {
        if (!isSkippingPowerGeneration())
        {
            int handSize = getHandSize();
            Hand hand = getOutOfPlay().getHand();
            drawCards(handSize - hand.size());
        }
    }

    /**
     * @param additionalPower
     */
    public void gainPower(int additionalPower)
    {
        int currentPower = getOutOfPlay().getPower();
        getOutOfPlay().setPower(currentPower + additionalPower);
    }

    /**
     * @param cost
     */
    public void payPower(int cost)
    {
        int currentPower = getOutOfPlay().getPower();
        if (currentPower < cost)
        {
            throw new IllegalArgumentException("Not enough Power to pay cost.");
        }
        getOutOfPlay().setPower(currentPower - cost);
    }

    /**
     * @param card
     * @return
     */
    public boolean isRequirementsMet(Card card)
    {
        List<Resource> reqsToMeet = new LinkedList<Resource>(Arrays.asList(card.getResourceRequirements()));
        if (checkRequirementsMet(reqsToMeet, getOutOfPlay().getSmokedPile()))
        {
            return true;
        }
        if (checkRequirementsMet(reqsToMeet, getInPlay()))
        {
            return true;
        }
        return (reqsToMeet.size() == 0);
    }

    /**
     * @param reqsToMeet
     */
    private boolean checkRequirementsMet(List<Resource> reqsToMeet, Iterable<Card> resourceProviders)
    {
        for (Card card : resourceProviders)
        {
            for (Resource r : card.getResourceProvisions())
            {
                if (reqsToMeet.remove(r))
                {
                    if (reqsToMeet.size() == 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param numberOfCards
     */
    public void drawCards(int numberOfCards)
    {
        for (int i = 0; i < numberOfCards; i++)
        {
            Card card = getOutOfPlay().getDeck().draw();
            getOutOfPlay().getHand().add(card);
        }
    }

    /**
     * @param response
     */
    public void setResponse(Effect response)
    {
        Effect oldValue = this.response;
        this.response = response;
        firePropertyChange("response", oldValue, response);
    }

    /**
     * Tells the UI to prompt for a main shot action.
     */
    protected void pollMainShotAction()
    {
        firePlayerActionRequest(PlayerAction.MAIN_SHOT_ACTION);
    }

    /**
     * Return and clears the player's response, if any. This method blocks 
     * until the timeout is reached or a response is available.
     */
    protected Effect pollResponse()
    {
        Effect response = this.response;
        if (response == null)
        {
            // call back to UI for input
            firePlayerActionRequest(PlayerAction.RESPONSE);
            response = this.response;
        }
        this.response = null;
        return response;
    }

    /**
     * Return a list of opponents, starting from the player to the left of this
     * player.
     *
     * @return
     */
    public List<Player> getOpponents()
    {
        List<Player> everyone = getGame().getPlayers();
        List<Player> opponents = new ArrayList<Player>(everyone.size() - 1);
        int idx = everyone.indexOf(this);
        for (int i = idx + 1; i < everyone.size(); i++)
        {
            opponents.add(everyone.get(i));
        }
        for (int i = 0; i < idx; i++)
        {
            opponents.add(everyone.get(i));
        }
        return opponents;
    }

    /**
     * Get a list of valid cards that can be played at this time.
     *
     * @return
     */
    public List<Card> getValidCards()
    {
        boolean isMyTurn = (getGame().getCurrentPlayer() == this);
        List<Card> validCards = new ArrayList<Card>(6);
        for (Card card : getOutOfPlay().getHand())
        {
            if (!isMyTurn && !(card instanceof Event))
            {
                continue;
            }
            validCards.add(card);
        }
        return validCards;
    }

    /**
     * Attach a non-null PlayerActionRequestListener to this object.
     *
     * @param l a non-null PlayerActionRequestListener instance
     */
    public synchronized void addPlayerActionRequestListener(PlayerActionRequestListener l)
    {
        listenerList.add(PlayerActionRequestListener.class, l);
    }

    /**
     * Request a player action to registered listeners.
     *
     * @param playerAction
     * @param timeout
     */
    protected void firePlayerActionRequest(PlayerAction playerAction)
    {
        PlayerActionRequestEvent event = new PlayerActionRequestEvent(this, playerAction);
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == PlayerActionRequestListener.class)
            {
                ((PlayerActionRequestListener)listeners[i + 1]).playerActionRequested(event);
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Player[name=").append(name);
        builder.append(",id=").append(getId()).append("]\n");
        builder.append(inPlay).append("\n");
        builder.append(outOfPlay).append("\n");
        builder.append(toastedPile).append("\n");
        return builder.toString();
    }

}