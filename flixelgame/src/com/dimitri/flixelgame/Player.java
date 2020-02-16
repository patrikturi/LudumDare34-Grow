package com.dimitri.flixelgame;

import org.flixel.FlxG;
import org.flixel.FlxU;

import com.dimitri.flixelgame.sprites.Tree;
import com.dimitri.flixelgame.ui.FloatingText;
import com.dimitri.flixelgame.ui.Life;

public class Player {

	public float life = 65;
	public float regen;
	public float growSpeed = 0;
	public float treeSize = 12.5f;

	private static final float TREE_SIZE_RATIO = 5.f;

	private Tree tree;

	public FloatingText floatingText;

	public Player(Tree tree) {
		this.tree = tree;
	}

	private float breakCooldown = 0.f;

	public void update() {

		if (growSpeed > 0.0001f)
			regen = -growSpeed;
		else
			regen = 45 / FlxG.getFramerate() + treeSize / FlxG.getFramerate();

		regen -= Game.liceCount / 10;

		life += regen * FlxG.elapsed;
		if (life > Life.MAX) {
			life = Life.MAX;
		} else if (life < 0) {
			life = 0;
		}

		if (breakCooldown > 0.f) {
			breakCooldown -= FlxG.elapsed;
		}

		if (breakCooldown < 0.f) {
			breakCooldown = 0.f;
		}

		if (life < Life.MAX / 4 && breakCooldown == 0.f) {
			treeSize -= 10.f;
			breakCooldown = 1.0f;
			floatingText.send("size-", 0xffffFF12);
		}

		treeSize += growSpeed * FlxG.elapsed;
		if (treeSize < 0.f)
			treeSize = 0.f;
		int newLevel = FlxU.floor(treeSize / TREE_SIZE_RATIO);
		tree.setLevel(newLevel);
	}
}
