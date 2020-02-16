package com.dimitri.flixelgame.sprites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxPoint;

public class Tree extends FlxGroup {

	public static final float GROW_SPEED = 2.f;

	private final static int LEVEL_H = 20;
	private final static int MAX_H = 520;

	private final FlxPoint root;
	private DrawSprite bodySprite;

	private int level = 1;
	private List<TreeSegment> segments = new ArrayList<TreeSegment>();

	public Tree() {

		root = new FlxPoint(FlxG.width / 2, FlxG.height - 75);

		updateBody();

		updateStructure();
	}

	public void setLevel(int newLevel) {
		if (level < newLevel) {
			grow();
		} else if (level > newLevel) {
			shrink();
		}
	}

	public void grow() {
		level += 1;
		updateBody();
		updateStructure();
	}

	public void shrink() {
		if (level > 1) {
			level -= 1;
			updateBody();
			updateStructure();
		}
	}

	private void updateBody() {
		int height = LEVEL_H / 2 * ((level - 1) / 2) * 2 + LEVEL_H / 2;
		if (bodySprite != null) {
			remove(bodySprite);
			bodySprite.destroy();
		}
		bodySprite = new DrawSprite(root.x - 1, root.y - MAX_H);
		if (height > MAX_H) {
			height = MAX_H;
		}
		bodySprite.makeGraphic(3, MAX_H, 0, true);
		bodySprite.fillRect(0, MAX_H - height, (int) bodySprite.width, MAX_H - 1, FlxG.GREEN);
		add(bodySprite);
	}

	private float getSegLength(float segLevel) {
		final float LEN_STEP = 8.f;
		float len = LEN_STEP * (level / 2 - segLevel) + LEN_STEP / 2 * (level % 2);
		if (len > TreeSegment.MAX_LEN) {
			len = TreeSegment.MAX_LEN;
		}
		return len;
	}

	private void updateSize() {
		float segLevel = segments.size() / 2;

		int j = 0;
		for (TreeSegment s : segments) {
			segLevel = j / 2;
			float len = getSegLength(segLevel);
			s.setLength(len);
			j++;
		}
	}

	public void addLouse() {
		TreeSegment branch = getRandomBranch();
		branch.addLouse(new Louse(branch));
	}

	private Random random = new Random();

	public TreeSegment getRandomBranch() {
		int i = random.nextInt(segments.size());
		return segments.get(i);
	}

	public void updateStructure() {
		float len;
		int segLevel = segments.size() / 2;
		int treeLevel = (level + 1) / 2;
		// grow length only
		if (segments.size() < level + 1) {
			updateSize();
		}

		TreeSegment seg;
		// grow
		if (segLevel < treeLevel) {

			segLevel = segments.size() / 2;
			float ang = -15;
			// MAX_SEG_LEN * 0.08f;
			FlxPoint pos = new FlxPoint(root.x, 0);

			for (int i = 0; i < (treeLevel - segLevel); i++) {
				segLevel = segments.size() / 2;

				len = getSegLength(segLevel);
				pos.y = root.y - segLevel * LEVEL_H;
				seg = new TreeSegment(pos, len, ang);
				add(seg);
				segments.add(seg);

				seg = new TreeSegment(pos, len, -180 - ang);
				add(seg);
				segments.add(seg);

				len *= 0.5f;
				pos.y -= 44;

				segLevel = segments.size() / 2;
			}
		} else if (segLevel > treeLevel) { // shrink
			while (segLevel > treeLevel) {

				seg = segments.remove(segments.size() - 1);
				remove(seg);
				seg.destroy();
				seg = segments.remove(segments.size() - 1);
				remove(seg);
				seg.destroy();
				segLevel = segments.size() / 2;
			}
			updateSize();
		}

	}
}
