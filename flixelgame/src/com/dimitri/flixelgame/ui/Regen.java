package com.dimitri.flixelgame.ui;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxSprite;
import org.flixel.FlxU;

public class Regen extends FlxGroup {

	private static final float MAX_REGEN = 4.0f;

	private final FlxSprite[] sprites = new FlxSprite[8];

	public Regen(int posX, int posY) {
		for (int i = 0; i < 8; i++) {
			int baseX = posX;
			if (i >= 4) {
				baseX += 3;
			}
			FlxSprite sprite = new FlxSprite(baseX + i * 16, posY);
			if (i < 4) {
				sprite.loadGraphic("sprites.pack:arrow", false, true);
				sprite.setColor(FlxG.RED);
				sprite.setAlpha(1.0f - i * 0.1f);
				sprite.setFacing(FlxSprite.LEFT);
			} else {
				sprite.loadGraphic("sprites.pack:arrow");
				sprite.setColor(FlxG.GREEN);
				sprite.setAlpha(1.0f - (8 - i) * 0.1f);
			}
			sprite.exists = false;
			sprites[i] = sprite;
			add(sprite);
		}
	}

	private float lastValue = -100;

	public void setValue(float val) {

		if (val == lastValue) {
			return;
		}

		lastValue = val;

		for (int i = 0; i < 8; i++) {
			sprites[i].exists = false;
		}
		float percent = val / MAX_REGEN * 100;
		float fN = percent / 25;
		int n;
		if (fN >= 0.0f) {
			n = FlxU.ceil(fN);
		} else {
			n = FlxU.floor(fN);
		}
		if (n > 4)
			n = 4;
		if (n < -4)
			n = -4;
		// TODO: set alpha growing with percent
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				sprites[4 + i].exists = true;
			}
		} else if (n < 0) {
			for (int i = 0; i > n; i--) {
				sprites[3 + i].exists = true;
			}
		}
	}

}
