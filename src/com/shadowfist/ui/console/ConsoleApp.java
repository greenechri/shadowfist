/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.ui.console;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.shadowfist.domain.card.collections.Deck;
import com.shadowfist.domain.card.types.Character;
import com.shadowfist.domain.game.Card;
import com.shadowfist.domain.game.CardPlayEngine;
import com.shadowfist.domain.game.CardPlayException;
import com.shadowfist.domain.game.Game;
import com.shadowfist.domain.game.GameEngine;
import com.shadowfist.domain.game.Player;
import com.shadowfist.domain.game.decks.Decks;
import com.shadowfist.domain.location.Location;
import com.shadowfist.ui.PlayerUI;
import com.shadowfist.ui.events.PlayerActionRequestEvent;
import com.shadowfist.ui.events.PlayerActionRequestEvent.PlayerAction;
import com.shadowfist.ui.events.PlayerActionRequestListener;

/**
 * @author cgreene
 */
public class ConsoleApp extends Thread
    implements PlayerUI, PropertyChangeListener, PlayerActionRequestListener
{
    @SuppressWarnings("unused")
    private static boolean debug;

    private Console console;
    private Player appPlayer;
    private Game game;

    /**
     * Constructor
     */
    public ConsoleApp()
    {
        console = System.console();
        if (console != null)
        {
            console.printf(Prompts.WELCOME_TO_SHADOWFIST);
        }
        else
        {
            System.out.println();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while (true)
        {
            List<Command> commands = buildPreGameCommands();
            promptAndHandleInput(commands);

            if (game != null && game.isStarted())
            {
                try
                {
                    synchronized (this)
                    {
                        wait();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            // else if game is started, switch to event-driven input and wait for game to end.
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (game.isWon())
        {
            synchronized (this)
            {
                notify();
            }
        }
        else
        {
            console.printf(String.valueOf(evt.getNewValue()) + "\n");
        }
    }

    /* (non-Javadoc)
     * @see com.shadowfist.ui.events.PlayerActionRequestListener#playerActionRequested(com.shadowfist.ui.events.PlayerActionRequestEvent)
     */
    @Override
    public void playerActionRequested(PlayerActionRequestEvent event)
    {
        if (event.getPlayerAction() == PlayerAction.MAIN_SHOT_ACTION)
        {
            promptAndHandleInput(buildMainShotCommands());
        }
        else if (event.getPlayerAction() == PlayerAction.RESPONSE)
        {
            promptAndHandleInput(buildResponseCommands(event.getPlayer()));
        }
    }

    /* (non-Javadoc)
     * @see com.shadowfist.ui.PlayerUI#chooseSubject(com.shadowfist.domain.game.Player, java.util.List)
     */
    @Override
    public Character chooseSubject(Player player, List<Character> characters)
    {
        console.printf(Prompts.CHOOSE_SUBJECT);
        // build list of commands
        List<Command> commands = new ArrayList<Command>(characters.size());
        for (Character character : characters)
        {
            commands.add(new Command(character.getTitle()));
        }
        int selection = promptAndHandleInput(commands);
        return characters.get(selection - 1);
    }

    /* (non-Javadoc)
     * @see com.shadowfist.ui.PlayerUI#chooseLocation(com.shadowfist.domain.game.Player)
     */
    @Override
    public Location chooseLocation(Player player, List<Location> locations)
    {
        console.printf(Prompts.CHOOSE_LOCATION);
        // build list of commands
        List<Command> commands = new ArrayList<Command>(locations.size());
        for (int i = 0; i < locations.size(); i++)
        {
            Location location = locations.get(i);
            if (location.size() == 0)
            {
                commands.add(new Command((i + 1) + ": " + Prompts.NEW_LOCATION));
            }
            else
            {
                commands.add(new Command((i + 1) + ": " + location));
            }
        }
        int selection = promptAndHandleInput(commands);
        if (selection == locations.size() + 1)
        {
            return new Location(player.getInPlay());
        }
        else if (selection < locations.size() + 1)
        {
            return locations.get(selection - 1);
        }
        return null;
    }

    /**
     * @return
     */
    protected List<Command> buildPreGameCommands()
    {
        // build list of commands
        List<Command> commands = new ArrayList<Command>(5);
        if (appPlayer == null)
        {
            commands.add(new Command(Prompts.PLAYER_SIGN_IN, () -> {
                console_signInPlayer();
            }));
        }
        if (game != null)
        {
            if (!game.isStarted())
            {
                if (game.getPlayerCount() < 6)
                {
                    commands.add(new Command(Prompts.ADD_DUMMY_PLAYER, () -> {
                        console_addPlayerToGame(null);
                    }));
                }
            }
            if (game.isReady())
            {
                commands.add(new Command(Prompts.START_GAME, () -> {
                    console_startGame();
                }));
            }

            commands.add(new Command(Prompts.PRINT_GAME_STATE, () -> {
                console_printGameState();
            }));
        }
        commands.add(new Command(Prompts.QUIT, () -> {
            console_quit();
        }));
        return commands;
    }

    /**
     * @return
     */
    protected List<Command> buildMainShotCommands()
    {
        final Player player = game.getCurrentPlayer();
        console.printf(Prompts.PLAYER_OPTIONS, player.getName());
        // build list of commands
        List<Command> commands = new ArrayList<Command>(7);
        commands.add(new Command(Prompts.PLAY_CARD, () -> {
            console_chooseWhichCard(player);
        }));
        commands.add(new Command(Prompts.DECLARE_ATTACK, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.USE_ABILITY, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.TURN_TO_HEAL, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.TURN_TO_CHANGE_LOCATION, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.DECLARE_END_OF_TURN, () -> {
            player.declareEndOfTurn();
        }));
        commands.add(new Command(Prompts.PRINT_GAME_STATE, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.QUIT, () -> {
            console_quit();
        }));
        return commands;
    }

    /**
     * @param player 
     * @return
     */
    protected List<Command> buildResponseCommands(Player player)
    {
        console.printf(Prompts.PLAYER_RESPONSE, player.getName());
        List<Command> commands = new ArrayList<Command>(3);
        commands.add(new Command(Prompts.PLAY_CARD, () -> {
            console_chooseWhichCard(player);
        }));
        commands.add(new Command(Prompts.USE_ABILITY, () -> {
            console_printGameState();
        }));
        commands.add(new Command(Prompts.PASS, () -> {
            player.setResponse(null);
        }));
        return commands;
    }

    /**
     * @param commands
     * @return
     */
    protected int promptAndHandleInput(List<Command> commands)
    {
        int selection = -1;
        // prompt and read input
        console.printf("\n");
        String readln = console.readLine(toConsoleMenu(commands), toConsoleArguments(commands));
        Scanner scanner = new Scanner(readln);
        if (scanner.hasNextInt())
        {
            selection = scanner.nextInt();
            // process input
            if (selection > -1 && selection <= commands.size())
            {
                Runnable r = commands.get(selection - 1).getAction();
                if (r != null)
                {
                    r.run();
                }
            }
        }
        scanner.close();
        return selection;
    }

    /**
     * @param commands
     * @return
     */
    protected String toConsoleMenu(List<Command> commands)
    {
        StringBuilder builder = new StringBuilder("\n");
        for (int i = 0; i < commands.size(); i++)
        {
            Command c = commands.get(i);
            builder.append("[").append(i + 1).append("] ");
            builder.append(c.getPrompt());
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * @param commands
     * @return
     */
    protected Object toConsoleArguments(List<Command> commands)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     */
    private void console_printGameState()
    {
        console.printf(game.toString());
    }

    /**
     */
    private void console_signInPlayer()
    {
        String readln = console.readLine(Prompts.ENTER_PLAYER_NAME);
        appPlayer = console_addPlayerToGame(readln);
    }

    /**
     * @param appPlayer2
     */
    private Player console_addPlayerToGame(String name)
    {
        Game game = (this.game != null)? this.game : new Game(this);
        Player player = new Player(game, name);
        Deck chosenDeck = console_chooseDeckForPlayer(player);
        if (chosenDeck != null)
        {
            this.game = game;
            player.addPropertyChangeListener(this);
            player.addPlayerActionRequestListener(this);
            game.addPlayer(player);
            player.useDeck((Deck)chosenDeck.clone());
            return player;
        }
        return null;
    }

    /**
     */
    private Deck console_chooseDeckForPlayer(Player player)
    {
        List<Deck> decks = Decks.getDecks();
        List<Command> commands = new ArrayList<Command>(decks.size());
        for (Deck deck : decks)
        {
            commands.add(new Command(deck.getName()));
        }
        commands.add(new Command(Prompts.CANCEL));
        console.printf(Prompts.CHOOSE_DECK, player.getName());
        int selection = promptAndHandleInput(commands);
        if (selection <= decks.size())
        {
            return decks.get(selection - 1);
        }
        return null;
    }

    /**
     * Prompt to select first player or roll dice
     */
    private void console_startGame()
    {
        List<Command> commands = new ArrayList<Command>(game.getPlayerCount() + 1);
        commands.add(new Command(Prompts.RANDOMLY_CHOOSE_PLAYER));
        for (Player p : game.getPlayers())
        {
            commands.add(new Command(p.getName()));
        }
        console.printf(Prompts.CHOOSE_WHO_WILL_GO_FIRST);
        int selection = promptAndHandleInput(commands);
        if (selection == 1)
        {
            selection = new Random().nextInt(game.getPlayerCount());
            game.setCurrentPlayerIndex(selection);
            new GameEngine(game).startGame();
        }
        else if (selection - 2 < game.getPlayerCount())
        {
            game.setCurrentPlayerIndex(selection - 2);            
            new GameEngine(game).startGame();
        }
    }

    /**
     */
    private void console_quit()
    {
        String readln = console.readLine(Prompts.ARE_YOU_SURE);
        if ("y".equalsIgnoreCase(readln))
        {
            console.printf("Exiting...");
            System.exit(0);
        }
    }

    /**
     */
    private void console_chooseWhichCard(Player player)
    {
        List<Command> commands = new ArrayList<Command>();
        for (final Card card : player.getValidCards())
        {
            commands.add(new Command(card.getTitle(), () -> {
                try
                {
                    new CardPlayEngine(this, card).play(player);
                }
                catch (CardPlayException e)
                {
                    console.printf(e.getMessage());
                }
            }));
        }
        commands.add(new Command(Prompts.CANCEL));
        console.printf(Prompts.CHOOSE_CARD);
        promptAndHandleInput(commands);
    }

    /**
     * Launch the Shadowfist game thread with a console front end.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        debug = Boolean.getBoolean("debug");
        new ConsoleApp().start();
    }

    // -------------------------------------------------------------------------

    private static class Command
    {
        private String prompt;
        private Runnable action;

        public Command(String prompt)
        {
            this(prompt, null);
        }

        public Command(String prompt, Runnable action)
        {
            this.action = action;
            this.prompt = prompt;
        }

        /**
         * @return the prompt
         */
        public String getPrompt()
        {
            return prompt;
        }

        /**
         * @return the action
         */
        public Runnable getAction()
        {
            return action;
        }
    }
}
