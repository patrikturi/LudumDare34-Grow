package com.dimitri.flixelgame.sprites;

import java.util.ArrayList;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxU;

public class TreeSegment extends FlxGroup {

	public static final int MAX_LEN = 160;
	private static final int MAX_W = 150;
	private static final int MAX_H = 44;

	private int color = FlxG.GREEN;

	private final FlxPoint globalRoot;
	private final FlxPoint globalEnd;
	private float segLength;
	private float segAngle;

	private final boolean isLeft;

	public ArrayList<Louse> lice = new ArrayList<Louse>();

	private FlxSprite sprite = null;

	public TreeSegment(FlxPoint root, float length, float angleDeg) {
		super();

		this.segAngle = angleDeg;
		this.segLength = length;
		this.globalRoot = new FlxPoint(root.x, root.y);

		if (segAngle < -90) {
			// left
			isLeft = true;
		} else {
			isLeft = false;
		}

		redraw();

		globalEnd = new FlxPoint(root.x + localEnd.x, root.y + localEnd.y);
	}

	private FlxPoint localEnd = new FlxPoint();

	public void setLength(float len) {
		segLength = len;

		// clear();
		redraw();
		globalEnd.x = globalRoot.x + localEnd.x;
		globalEnd.y = globalRoot.y + localEnd.y;

		for (Louse louse : lice) {
			int sideMult = 1;
			if (isLeft)
				sideMult = -1;
			if (louse.x * sideMult > (getEndPoint().x - louse.width / 2) * sideMult) {

				FlxPoint pos = new FlxPoint();
				getRandomPoint(pos);
				louse.x = pos.x - louse.width / 2;
				louse.y = pos.y - louse.height;

				// Falls off
				// remove(louse);
				// louse.destroy();
			}
		}
	}

	private void redraw() {

		// Clearing graphics doesn't work due to fucked up bugs
		// So only workaround left is to destroy the sprite and create a new one
		if (segLength >= MAX_LEN) {
			return;
		}

		// sprite.fill(FlxG.getBgColor());

		if (sprite != null) {
			remove(sprite);
			sprite.destroy();
		}
		int xOffset = 0;
		if (isLeft) {
			xOffset = -MAX_W;
		}

		sprite = new FlxSprite();
		add(sprite);

		int rootXOffs = 1;
		if (isLeft) {
			rootXOffs = -1;
		}
		sprite.x = globalRoot.x + rootXOffs + xOffset;
		sprite.y = globalRoot.y - MAX_H;
		sprite.makeGraphic(MAX_W, MAX_H, 0, true);

		int rootX = 0;
		if (isLeft) {
			rootX = MAX_W;
		}

		localEnd.x = segLength;
		localEnd.y = 0;

		FlxU.rotatePoint(localEnd.x, 0, 0, 0, segAngle, localEnd);

		// drawLine(0+50, 0+50, test.x+50, test.y+50, FlxG.WHITE);
		sprite.drawLine(rootX, MAX_H - 1, localEnd.x + rootX, localEnd.y + MAX_H - 1, color);

	}

	/*
	 * // does not work with HTML5
	 *
	 * @Override private void clear() {
	 * Pixmap.setBlending(Pixmap.Blending.SourceOver);
	 * Pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
	 *
	 * TextureData textureData = null; if (_newTextureData != null) textureData
	 * = _newTextureData; else textureData =
	 * _pixels.getTexture().getTextureData();
	 *
	 * if (!textureData.isPrepared()) textureData.prepare();
	 *
	 * Pixmap pixmap = textureData.consumePixmap();
	 * pixmap.setColor(Color.CLEAR); pixmap.fill();
	 *
	 * _newTextureData = new ManagedTextureData(pixmap); }
	 */
	public FlxPoint getEndPoint() {
		return globalEnd;
	}

	public void getRandomPoint(FlxPoint p) {
		float diffX = globalEnd.x - globalRoot.x;
		float diffY = globalEnd.y - globalRoot.y;
		float rand = FlxG.random();
		p.x = globalRoot.x + diffX * rand;
		p.y = globalRoot.y + diffY * rand;
	}

	public void addLouse(Louse l) {
		lice.add(l);
		add(l);
	}

	@Override
	public void destroy() {

		for (Louse louse : lice) {
			/*
			 * Tween.to(this, TweenSprite.Y,
			 * 3.0f).target(0.f).start(TweenPlugin.manager).ease(Quad.OUT)//
			 * .setCallback(new TweenCallback() {
			 * 
			 * @Override public void onEvent(int type, BaseTween<?> source) {
			 * louse.destroy(); }
			 * 
			 * });
			 */
			// this.remove(louse);
		}
		lice.clear();

		super.destroy();
	}

}
