package com.dimitri.flixelgame.states;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.plugin.tweens.TweenPlugin;

import com.badlogic.gdx.Gdx;
import com.dimitri.flixelgame.Game;
import com.dimitri.flixelgame.Player;
import com.dimitri.flixelgame.sprites.Background;
import com.dimitri.flixelgame.sprites.Louse;
import com.dimitri.flixelgame.sprites.Tree;
import com.dimitri.flixelgame.ui.FloatingText;
import com.dimitri.flixelgame.ui.Life;
import com.dimitri.flixelgame.ui.UI;

public class PlayState extends FlxState {
	private FlxText fpsText;

	private FloatingText floatingText;
	private Tree tree;
	private Background bg;
	private FlxGroup lice;
	private UI ui;
	private Player player;

	// private MessageManager manager = MessageManager.getInstance();

	@Override
	public void create() {

		Game.won = false;
		FlxG.timeScale = 1.0f;

		Gdx.graphics.setTitle("Grow");

		// Load this plugin only once ever
		if (!Game.tweenPluginLoaded) {
			Game.tweenPluginLoaded = true;
			FlxG.addPlugin(new TweenPlugin());
		}

		FlxG.setBgColor(FlxG.BLUE);

		bg = new Background();
		add(bg);

		tree = new Tree();
		add(tree);

		player = new Player(tree);

		FlxSprite sprite;
		sprite = new FlxSprite(FlxG.width / 2 - 6, FlxG.height - 75);
		sprite.loadGraphic("sprites.pack:root");
		add(sprite);

		lice = new FlxGroup();
		add(lice);

		ui = new UI(player);
		add(ui);

		floatingText = new FloatingText();
		add(floatingText);

		player.floatingText = floatingText;

		if (Game.DEBUG) {
			fpsText = new FlxText(5, FlxG.height - 25, 50, "");
			fpsText.scrollFactor = new FlxPoint(0.f, 0.f);
			fpsText.setFormat("text_white.fnt", 16);
			add(fpsText);
		}

		Game.liceCount = 0;
		Game.init(60 * 4.5f);

		floatingText.send("Hold G to grow", FlxG.GREEN);
	}

	private float growTime = 0.f;
	private boolean fastForward = false;
	private float repeatedSpawnCost = 0.f;

	private boolean growCanceled = false;

	private float totalTime = 0.f;
	private boolean spawnTip = false;

	@Override
	public void update() {
		super.update();

		Game.update();

		if (Game.DEBUG) {
			Integer delta = new Integer(Game.getFps());
			fpsText.setText(delta.toString());
		}

		if (FlxG.keys.justPressed("T")) {
			fastForward = !fastForward;
			if (fastForward)
				FlxG.timeScale = 4.0f;
			else
				FlxG.timeScale = 1.0f;
		}

		if (FlxG.keys.justPressed("G")) {
			growCanceled = false;
		}

		if (FlxG.keys.pressed("G") && !growCanceled) {
			if (player.life > Life.MAX / 3) {
				if (growTime >= 1.0f || growTime == 0.f) {
					growTime = 0.f;
					floatingText.send("grow+", FlxG.GREEN);
				}
				growTime += FlxG.elapsed;
				player.growSpeed = Tree.GROW_SPEED;
			} else {
				growTime = 0.f;
				player.growSpeed = 0.f;
				growCanceled = true;
			}
		} else {
			growTime = 0.f;
			player.growSpeed = 0.f;
		}

		if (repeatedSpawnCost > 0.00001f) {
			repeatedSpawnCost -= Louse.SPAWN_COST / 4 * FlxG.elapsed;
		}
		if (repeatedSpawnCost < 0.00001f) {
			repeatedSpawnCost = 0.f;
		}

		if (FlxG.keys.justPressed("S")) {
			if (player.life >= Louse.SPAWN_COST + repeatedSpawnCost) {
				tree.addLouse();
				floatingText.send("lice+", FlxG.BLACK);
				player.life -= (Louse.SPAWN_COST + repeatedSpawnCost);
				repeatedSpawnCost += Louse.SPAWN_COST;
			}
		}

		player.update();

		ui.setLife(player.life);
		ui.setRegen(player.regen);

		totalTime += FlxG.elapsed;
		if (totalTime > 10.f && !spawnTip && Game.liceCount == 0) {
			floatingText.send("Press S to spawn a plant louse", FlxG.BLACK);
			spawnTip = true;
		}

		if (player.treeSize < 10.f && player.life < Life.MAX / 8) {
			FlxG.switchState(new GameOverState());
		}
	}

	@Override
	public void destroy() {
		Game.gameStateLeft = true;
		super.destroy();
	}

}