package com.dimitri.flixelgame;

import org.flixel.client.FlxHtml5Application;

import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameStarter extends FlxHtml5Application
{

    @Override
    public GwtApplicationConfiguration getConfig () {

    	GwtApplicationConfiguration gwtAppConfig = new GwtApplicationConfiguration(Game.WIDTH, Game.HEIGHT);

        return gwtAppConfig;
    }

    public GameStarter()
    {
        super(new FlixelGame(), Game.WIDTH, Game.HEIGHT);
    }
}