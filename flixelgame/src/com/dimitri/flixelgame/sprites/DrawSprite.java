package com.dimitri.flixelgame.sprites;

import org.flixel.FlxSprite;
import org.flixel.FlxU;
import org.flixel.system.gdx.ManagedTextureData;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;

public class DrawSprite extends FlxSprite {

	public DrawSprite() {
	}

	public DrawSprite(float X) {
		super(X);
	}

	public DrawSprite(float X, float Y) {
		super(X, Y);
	}

	public DrawSprite(float X, float Y, String SimpleGraphic) {
		super(X, Y, SimpleGraphic);
	}

	public void fillRect(int startX, int startY, int w, int h, int Color) {
		Pixmap.setBlending(Pixmap.Blending.SourceOver);
		Pixmap.setFilter(Pixmap.Filter.NearestNeighbour);

		TextureData textureData = null;
		if (_newTextureData != null)
			textureData = _newTextureData;
		else
			textureData = _pixels.getTexture().getTextureData();

		if (!textureData.isPrepared())
			textureData.prepare();

		Pixmap pixmap = textureData.consumePixmap();
		pixmap.setColor(FlxU.argbToRgba(Color));
		pixmap.fillRectangle(startX, startY, w, h);

		_newTextureData = new ManagedTextureData(pixmap);
	}
}
