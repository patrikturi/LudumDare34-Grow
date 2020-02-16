package com.dimitri.flixelgame.ui;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxText;
import org.flixel.plugin.tweens.TweenPlugin;
import org.flixel.plugin.tweens.TweenSprite;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.utils.Pool;
import com.dimitri.flixelgame.Game;

public class FloatingText extends FlxGroup {

	private static final int SPAWN_X = 100;
	private static final int SPAWN_Y = 250;
	private static final int WIDTH = 125;

	public final Pool<FlxText> pool = new Pool<FlxText>() {

		@Override
		protected FlxText newObject() {
			FlxText newText = new FlxText(0, 0, WIDTH);
			newText.setFormat(Game.UI_FONT, Game.UI_FONT_SIZE, FlxG.WHITE);
			newText.setAlignment("center");
			return newText;
		}

	};

	public FloatingText() {
	}

	public void send(String msg, int textColor) {
		final FlxText text = pool.obtain();
		text.x = SPAWN_X;
		text.y = SPAWN_Y;
		text.setAlpha(1.0f);
		text.setText(msg);
		text.setColor(textColor);
		text.velocity.y = -75;
		add(text);

		final FloatingText that = this;

		Game.textPending++;

		Tween.to(text, TweenSprite.ALPHA, 3.0f).target(0.f).start(TweenPlugin.manager).ease(Quad.IN)
				.setCallback(new TweenCallback() {

					@Override
					public void onEvent(int type, BaseTween<?> source) {
						if (!Game.gameStateLeft) {
							text.velocity.y = 0;
							remove(text);
							that.pool.free(text);
						}
						Game.textPending--;
					}

				});

	}

}
