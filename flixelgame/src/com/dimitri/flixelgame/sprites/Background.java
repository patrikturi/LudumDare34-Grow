package com.dimitri.flixelgame.sprites;

import org.flixel.FlxBasic;
import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxSprite;
import org.flixel.FlxU;

import com.badlogic.gdx.utils.Pool;

public class Background extends FlxGroup {

	private static final float SPAWN_DIFF = 10.0f;
	private static final float SPAWN_MIN = 10.0f;

	private static final float Y_DIFF = 80.0f;
	private static final float Y_MIN = 60.0f;

	private float spawnTime;

	public final Pool<FlxSprite> cloudPool = new Pool<FlxSprite>() {

		@Override
		protected FlxSprite newObject() {
			FlxSprite sprite;
			sprite = new FlxSprite(0, 0);
			return sprite;
		}

	};

	public Background() {
		FlxSprite sprite;
		sprite = new FlxSprite(0, FlxG.height - 77);
		sprite.loadGraphic("sprites.pack:ground");
		add(sprite);

		setSpawnTime();
	}

	@Override
	public void update() {
		super.update();
		spawnTime -= FlxG.elapsed;
		if (spawnTime <= 0.f) {
			FlxSprite sprite = cloudPool.obtain();
			Integer type = new Integer(FlxU.floor(FlxG.random() * 3.98f));
			sprite.loadGraphic("sprites.pack:cloud" + type.toString());
			sprite.velocity.x = 10 + FlxG.random() * 5;
			sprite.setAlpha(0.5f + FlxG.random() / 2);
			sprite.x = -sprite.width;
			sprite.y = Y_MIN + FlxG.random() * Y_DIFF;
			add(sprite);
			setSpawnTime();
		}

		for (FlxBasic basic : this.members) {
			if (basic instanceof FlxSprite) {
				FlxSprite sprite = (FlxSprite) basic;
				if (sprite.x > FlxG.width) {
					remove(sprite);
					cloudPool.free(sprite);
				}
			}

		}
	}

	private void setSpawnTime() {
		spawnTime = SPAWN_MIN + FlxG.random() * SPAWN_DIFF;
	}

}
