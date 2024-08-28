package com.makeandbreak.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.FileNotFoundException;

import Screens.BufferScreen;
import Screens.ClassicScreen;
import Screens.ControlScreen;
import Screens.MainMenuScreen;

public class MakeAndBreak extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shape;

	public int WIDTH;
	public int HEIGHT;
	@Override
	public void create () {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT= Gdx.graphics.getHeight();

		setScreen(new MainMenuScreen(this));
	}



	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
