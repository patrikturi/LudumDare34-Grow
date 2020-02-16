package com.dimitri.flixelgame.sprites;

import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

import com.dimitri.flixelgame.Game;

public class Louse extends FlxSprite {

	public static final float SPAWN_COST = 5.f;
	public static final float UPKEEP = 0.75f;

	TreeSegment branch;

	public Louse(TreeSegment branch) {
		this.branch = branch;
		FlxPoint pos = new FlxPoint();
		branch.getRandomPoint(pos);
		loadGraphic("sprites.pack:louse", true, true, 12, 7);
		addAnimation("look", new int[] { 1 }, 0, false);
		addAnimation("blink", new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 6, true);
		this.x = pos.x - this.width / 2;
		this.y = pos.y - this.height;
		branch.addLouse(this);
		play("blink");
		Game.liceCount++;
	}

	@Override
	public void destroy() {
		super.destroy();
		if (!Game.gameStateLeft) {
			Game.liceCount--;
		}
	}

}
