/* @Copyright 2014 Christian Greene
 * Shadowfist is a registered trademark of Inner Kingdom Games.
 */
package com.shadowfist.ui.console;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author cgreene
 */
public class Prompts
{
    public static final String RESOURCE_FILE = Prompts.class.getPackage().getName() + ".prompts";

    // init all the static fields based upon the resource bundle
    static
    {
        ResourceBundle res = ResourceBundle.getBundle(RESOURCE_FILE);
        for (Field f : Prompts.class.getDeclaredFields())
        {
            if (Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()))
            {
                String value = res.getString(f.getName().toLowerCase());
                try
                {
                    f.set(null, value);
                }
                catch (IllegalArgumentException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static String ADD_DUMMY_PLAYER;
    public static String ARE_YOU_SURE;
    public static String CANCEL;
    public static String CHOOSE_CARD;
    public static String CHOOSE_DECK;
    public static String CHOOSE_LOCATION;
    public static String CHOOSE_SUBJECT;
    public static String CHOOSE_WHO_WILL_GO_FIRST;
    public static String DECLARE_ATTACK;
    public static String DECLARE_END_OF_TURN;
    public static String ENTER_PLAYER_NAME;
    public static String NEW_LOCATION;
    public static String NO_CONSOLE_SUPPORT_DETECTED;
    public static String NO_LOCATION_SELECTED;
    public static String NOT_ENOUGH_POWER;
    public static String NOT_ENOUGH_RESOURCES;
    public static String PASS;
    public static String PLAY_CARD;
    public static String PLAYER_SIGN_IN;
    public static String PLAYER_OPTIONS;
    public static String PLAYER_RESPONSE;
    public static String PRINT_GAME_STATE;
    public static String QUIT;
    public static String RANDOMLY_CHOOSE_PLAYER;
    public static String START_GAME;
    public static String THINK_ABOUT_IT;
    public static String TURN_TO_CHANGE_LOCATION;
    public static String TURN_TO_HEAL;
    public static String USE_ABILITY;
    public static String WELCOME_TO_SHADOWFIST;

    private Prompts()
    {
        // no need to contruct
    }

    /**
     * Test method. TODO convert to unit test.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format(PLAYER_OPTIONS, "Me!");
        formatter.close();

        System.out.println(sb);
    }
}
