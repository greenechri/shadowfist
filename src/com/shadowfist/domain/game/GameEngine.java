/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.domain.game;

import java.util.Queue;

import com.shadowfist.domain.scene.Effect;
import com.shadowfist.domain.scene.Scene;
import com.shadowfist.domain.scene.SceneImpl;

/**
 * @author cgreene
 */
public class GameEngine extends Thread
{
    /** A simple counter for threads that are being created.  **/
    private static int threadCounter = 0;

    private Game game;

    /**
     * Construct a game thread.
     */
    public GameEngine(Game game)
    {
        super("Shadowfist TurnEngine Thread-" + ++threadCounter);
        setDaemon(true);
        this.game = game;
    }

    /**
     * @param game
     */
    public boolean startGame()
    {
        game.start();
        if (game.isStarted())
        {
            start();
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while (!game.isWon())
        {
            Player p = game.getCurrentPlayer();
            runEstablishingShot(p);
            runMainShot(p);
            game.nextPlayer();
        }
    }

    /**
     * @param p
     */
    private void runEstablishingShot(Player p)
    {
        game.setTurnState(TurnState.START_OF_TURN);
        p.startTurn();
        game.setTurnState(TurnState.POWER_GENERATION);
        p.generatePower();
        game.setTurnState(TurnState.UNTURN);
        p.unturnCards();
        game.setTurnState(TurnState.DISCARD);
        p.discard();
        game.setTurnState(TurnState.DRAW);
        p.drawCards();
    }

    /**
     * @param p
     */
    private void runMainShot(Player p)
    {
        game.setTurnState(TurnState.MAIN_SHOT);
        while (true)
        {
            // call back to UI for input
            p.pollMainShotAction();
            try
            {
                sleep(50);
                if (game.hasQueuedEffects())
                {
                    runScene(p);
                }
                if (game.isEndOfTurn())
                {
                    runEndOfTurnScene();
                    break;
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param p
     */
    private void runScene(Player p)
    {
        Scene scene = new SceneImpl();
        Queue<Effect> queuedEffects = game.getQueuedEffects();
        while (!queuedEffects.isEmpty())
        {
            Effect effect = queuedEffects.poll();
            if (effect != null)
            {
                runGenerateEffect(scene, effect, p);
            }
        }
    }

    /**
     * @param effect
     */
    private void runGenerateEffect(Scene scene, Effect effect, Player effectOwner)
    {
        scene.generate(effect);
        // do players have responses?
        runResponses(scene, effectOwner);

        // no more responses to generation
        while (!scene.isEmpty())
        {
            Effect resolved = scene.resolveNext();
            System.out.println(resolved + " resolved.");
            // do players have responses?
            runResponses(scene, effectOwner);
        }
    }

    /**
     * @param scene
     * @param effectOwner
     */
    protected void runResponses(Scene scene, Player effectOwner)
    {
        for (Player opponent : effectOwner.getOpponents())
        {
            Effect response = opponent.pollResponse();
            // yes = runGenerateEffect()
            if (response != null)
            {
                runGenerateEffect(scene, response, opponent);
            }
        }
    }

    /**
     */
    private void runEndOfTurnScene()
    {
        // check for responses, if so, maybe go abck to main shot
    }

}
