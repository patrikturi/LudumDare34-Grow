package com.dimitri.flixelgame.ui;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxText;
import org.flixel.FlxU;

import com.dimitri.flixelgame.Game;
import com.dimitri.flixelgame.Player;

public class UI extends FlxGroup {

	private final Life lifeSprite;
	private final Regen regenSprite;
	private final FlxText timeLeftText;
	private final FlxText scoreText;
	private Integer lastLiceCount = new Integer(0);

	public UI(Player player) {

		FlxText text;
		text = new FlxText(25.f, 10.f, 50, "Life");
		text.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
		add(text);

		text = new FlxText(65.f, 10.f, 100, "Regeneration");
		text.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
		add(text);

		text = new FlxText(FlxG.width - 125, 10.f, 50, "Score");
		text.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
		add(text);

		text = new FlxText(FlxG.width - 60, 10.f, 50, "Time");
		text.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
		add(text);

		timeLeftText = new FlxText(FlxG.width - 60 + 3, 35.f, 50, "6:00");
		timeLeftText.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
		add(timeLeftText);

		scoreText = new FlxText(FlxG.width - 150 + 3, 35.f, 60, "0");
		scoreText.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.BLACK);
		scoreText.setAlignment("right");
		add(scoreText);

		FlxSprite sprite;
		sprite = new FlxSprite(FlxG.width - 85, 44.f);
		sprite.loadGraphic("sprites.pack:louse", true, false, 12, 7);
		sprite.addAnimation("look", new int[] { 1 }, 0, false);
		/*
		 * sprite.addAnimation("blink", new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1,
		 * 1, 1, 1, 1, 1, 0 }, 6, true);
		 */
		add(sprite);
		sprite.play("look");

		lifeSprite = new Life(10, 10, (int) player.life);
		add(lifeSprite);

		regenSprite = new Regen(35, 38);
		regenSprite.setValue(player.regen);
		add(regenSprite);

		FlxText versionText = new FlxText(FlxG.width - 50, FlxG.height - 25, 50, "v" + Game.VERSION);
		versionText.scrollFactor = new FlxPoint(0.f, 0.f);
		versionText.setFormat("text_white.fnt", 16);
		add(versionText);
	}

	public void setLife(float l) {
		lifeSprite.setValue((int) l);
	}

	public void setRegen(float r) {
		regenSprite.setValue(r);
	}

	@Override
	public void update() {
		super.update();
		timeLeftText.setText(FlxU.formatTime((int) Game.timeRemaining));
		if (lastLiceCount.intValue() != Game.liceCount) {
			lastLiceCount = new Integer(Game.liceCount);
			scoreText.setText(lastLiceCount.toString());
		}
	}
}
