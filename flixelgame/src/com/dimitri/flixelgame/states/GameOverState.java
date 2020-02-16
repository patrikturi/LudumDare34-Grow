package com.dimitri.flixelgame.states;

import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;

import com.dimitri.flixelgame.Game;

public class GameOverState extends FlxState {

	public GameOverState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {

		FlxG.timeScale = 100.0f;

		FlxText text;

		if (Game.won) {
			if (Game.liceCount > Game.highScore)
				Game.highScore = Game.liceCount;
		}

		String overMsg;
		String overMsg2 = "";
		if (Game.won) {
			overMsg = "Current score: " + new Integer(Game.liceCount).toString();
			overMsg2 = "Highest score: " + new Integer(Game.highScore).toString();
		} else {
			overMsg = "Your plant has dried out.";
			overMsg2 = "Press R to try again.";
		}

		text = new FlxText(FlxG.width / 2 - 100, 150, 200, overMsg);
		text.setFormat("text_white.fnt", 16);
		text.setAlignment("center");
		add(text);

		text = new FlxText(FlxG.width / 2 - 100, 150 + 30, 200, overMsg2);
		text.setFormat("text_white.fnt", 16);
		text.setAlignment("center");
		add(text);

		if (Game.won) {
			text = new FlxText(FlxG.width / 2 - 100, 150 + 100, 200, "Press R to try again.");
			text.setFormat("text_white.fnt", 16);
			text.setAlignment("center");
			add(text);

			text = new FlxText(FlxG.width / 2 - 150, 150 + 130, 300, "(Leaderboard is coming soon)");
			text.setFormat("text_white.fnt", 16);
			text.setAlignment("center");
			add(text);
		}
	}

	private boolean restart = false;

	@Override
	public void update() {
		if (FlxG.keys.justPressed("R") || restart) {
			if (Game.textPending == 0) {
				Game.gameStateLeft = false;
				FlxG.switchState(new PlayState());
			} else {
				restart = true;
			}
		}
	}

}
