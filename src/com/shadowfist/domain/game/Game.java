/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.shadowfist.domain.scene.Effect;

/**
 * @author cgreene
 */
public class Game extends GamePart
{
    private List<Player> players;
    private int currentPlayer;
    private GameStatus gameStatus;
    private TurnState turnState;
    private Queue<Effect> queuedEffects;

    int dummyCount = 0;

    /**
     * @param l
     */
    public Game(PropertyChangeListener l)
    {
        super(null);
        super.addPropertyChangeListener(l);
        players = new ArrayList<Player>();
        gameStatus = GameStatus.NOT_READY;
        queuedEffects = new LinkedList<Effect>();
    }

    /**
     * @return whether player was added.
     */
    public boolean addPlayer(Player player)
    {
        boolean added = players.add(player);
        if (added)
        {
            if (gameStatus == GameStatus.NOT_READY && players.size() > 1)
            {
                gameStatus = GameStatus.READY;
            }
        }
        return added;
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers()
    {
        return players;
    }

    /**
     * @return the player count
     */
    public int getPlayerCount()
    {
        return players.size();
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    /**
     * @return the currentPlayer
     */
    public Player getCurrentPlayer()
    {
        return (currentPlayer < players.size())? players.get(currentPlayer) : null;
    }

    /**
     * @param currentPlayerIndex
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex)
    {
        currentPlayer = currentPlayerIndex;
    }

    public void endOfTurn()
    {
        setTurnState(TurnState.END_OF_TURN);
    }

    /**
     * Cycles though the list, making sure {@link #currentPlayer} stays within
     * bounds.
     */
    protected Player nextPlayer()
    {
        currentPlayer = (currentPlayer < players.size() - 1)? currentPlayer + 1 : 0;
        return players.get(currentPlayer);
    }

    /**
     * @return
     */
    public boolean isStarted()
    {
        return GameStatus.STARTED == gameStatus;
    }

    /**
     * @return
     */
    public boolean isReady()
    {
        return GameStatus.READY == gameStatus;
    }

    /**
     * @return
     */
    public boolean isEndOfTurn()
    {
        return TurnState.END_OF_TURN == turnState;
    }

    /**
     * @return
     */
    public boolean isWon()
    {
        return GameStatus.WON == gameStatus;
    }

    /**
     * @return the game, this.
     */
    @Override
    public GamePart getParent()
    {
        return this;
    }

    /**
     * @return the turnState
     */
    public TurnState getTurnState()
    {
        return turnState;
    }

    /**
     * @param turnState the turnState to set
     */
    protected void setTurnState(TurnState turnState)
    {
        TurnState oldValue = this.turnState;
        this.turnState = turnState;
        firePropertyChange("turnState", oldValue, turnState);
    }

    /**
     * If game status is ready, all players get one power, their decks are
     * shuffled and everyone draws six cards. Game status is set to started.
     */
    public GameStatus start()
    {
        if (gameStatus == GameStatus.READY)
        {
            for (Player p : players)
            {
                p.getOutOfPlay().setPower(1);
                p.getOutOfPlay().getDeck().shuffle();
                p.drawCards(6);
            }
            gameStatus = GameStatus.STARTED;
        }
        else
        {
            throw new IllegalStateException("Game is not ready.");
        }
        return gameStatus;
    }

    /**
     * @param effect
     */
    public void queueEffect(Effect effect)
    {
        queuedEffects.add(effect);
    }

    /**
     * @return
     */
    public boolean hasQueuedEffects()
    {
        return !queuedEffects.isEmpty();
    }

    /**
     * @return
     */
    protected Queue<Effect> getQueuedEffects()
    {
        return queuedEffects;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Game Status=");
        builder.append(gameStatus).append("\n");
        for (int i = 0; i < players.size(); i++)
        {
            if (i == currentPlayer)
            {
                builder.append("(Current)");
            }
            builder.append(players.get(i));
            builder.append("\n");
        }
        return builder.toString();
    }
}