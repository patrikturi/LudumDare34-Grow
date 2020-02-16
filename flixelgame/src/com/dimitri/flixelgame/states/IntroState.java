package com.dimitri.flixelgame.states;

import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;

public class IntroState extends FlxState {

	public IntroState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {

		FlxG.setBgColor(FlxG.BLUE);

		FlxText text;
		text = new FlxText(FlxG.width / 2 - 100, 150, 200, "Press any key to start.");
		text.setFormat("text_white.fnt", 16);
		text.setAlignment("center");
		add(text);

		text = new FlxText(FlxG.width / 2 - 150, 210, 300, "(Tip: toggle fast forward with T)");
		text.setFormat("text_white.fnt", 16);
		text.setAlignment("center");
		add(text);
	}

	@Override
	public void update() {
		if (FlxG.keys.justPressedAny()) {
			FlxG.switchState(new PlayState());
		}
	}

}
