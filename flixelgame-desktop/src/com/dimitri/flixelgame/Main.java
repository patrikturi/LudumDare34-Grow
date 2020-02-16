package com.dimitri.flixelgame;

import org.flixel.FlxDesktopApplication;

public class Main
 {
	public static void main(String[] args)
	{
		new FlxDesktopApplication(new FlixelGame(), Game.WIDTH, Game.HEIGHT);
	}
}
