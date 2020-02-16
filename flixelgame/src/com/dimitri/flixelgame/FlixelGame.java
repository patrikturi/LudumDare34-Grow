package com.dimitri.flixelgame;

import org.flixel.FlxGame;

import com.dimitri.flixelgame.states.IntroState;

public class FlixelGame extends FlxGame {
	public FlixelGame() {
		super(Game.WIDTH, Game.HEIGHT, IntroState.class, 1, 50, 50, false);
	}
}
