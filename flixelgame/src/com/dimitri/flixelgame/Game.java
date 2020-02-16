package com.dimitri.flixelgame;

import org.flixel.FlxG;

import com.badlogic.gdx.Gdx;
import com.dimitri.flixelgame.states.GameOverState;

public class Game {

	public static final boolean DEBUG = false;
	public static final String VERSION = "1.00";

	public static final int WIDTH = 320;
	public static final int HEIGHT = 560;
	public static final String UI_FONT = "text_white.fnt";
	public static final int UI_FONT_SIZE = 16;

	public static int liceCount;
	public static boolean won = false;
	public static boolean tweenPluginLoaded = false;

	public static int highScore = 0;

	// hack
	public static boolean gameStateLeft = false;
	public static int textPending = 0;

	public static float timeRemaining;

	// Debug fps
	private static long prevTime;
	private static float dTime;
	private static float fpsTimeElapsed = 0.0f;
	private static int currentFpsCnt = 0;
	private static int lastFpsCnt = 0;

	public static void init(float gameDurationTime) {
		prevTime = System.currentTimeMillis();
		timeRemaining = gameDurationTime;
	}

	public static void update() {
		long time = System.currentTimeMillis();
		long delta = time - prevTime;
		dTime = delta / 1000.0f;
		prevTime = time;

		// Avoid windup
		if (dTime > 1.f) {
			dTime = 1.f;
		}

		fpsTimeElapsed += dTime;
		currentFpsCnt++;
		if (fpsTimeElapsed >= 1.0f) {
			fpsTimeElapsed = 0.0f;
			lastFpsCnt = currentFpsCnt;
			currentFpsCnt = 0;
		}

		if (timeRemaining > FlxG.elapsed) {
			timeRemaining -= FlxG.elapsed;
		} else {
			timeRemaining = 0.0f;
			Game.won = true;
			FlxG.switchState(new GameOverState());
		}
	}

	public static int getFps() {
		return lastFpsCnt;
	}

	public static float getDelta() {
		return dTime;
	}

	public static void log(String msg) {
		Gdx.app.log("", msg);
	}
}
