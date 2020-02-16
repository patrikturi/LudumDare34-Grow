package com.dimitri.flixelgame.ui;

import org.flixel.FlxG;

import com.dimitri.flixelgame.sprites.DrawSprite;

public class Life extends DrawSprite {

	public static final int MAX = 100;
	private final int W = 10;
	private final int H = 80;

	public Life(float X, float Y, int val) {
		super(X, Y);
		makeGraphic(W, H, FlxG.getBgColor());
		setValue(val);
	}

	private float lastValue = -100;

	// TODO: change color on value
	public void setValue(int value) {
		if (value == lastValue) {
			return;
		}

		lastValue = value;

		fill(FlxG.getBgColor());
		int fillH = (int) ((float) (MAX - value) / MAX * H);

		int lifeColor;
		if (value < MAX / 3)
			lifeColor = FlxG.RED;
		else if (value < 2 * MAX / 3) {
			lifeColor = 0xffffFF12;
		} else {
			lifeColor = FlxG.GREEN;
		}

		fillRect(1, fillH - 1, W - 2, H - fillH, lifeColor);
		this.drawLine(0, 0, this.width - 1, 0, FlxG.BLACK);
		this.drawLine(0, this.height - 1, this.width - 1, this.height - 1, FlxG.BLACK);
		this.drawLine(0, 0, 0, this.height - 1, FlxG.BLACK);
		this.drawLine(this.width - 1, 0, this.width - 1, this.height - 1, FlxG.BLACK);
	}

}
