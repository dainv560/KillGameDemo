package com.nvdai.character;

import java.util.Random;

import com.nvdai.killgamedemo.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private static final int MAX_SPEED = 3;
	private static final int DEATH_RADIUS = 50;
	private float x = 0;
	private float y = -10;
	private float xSpeed;
	private float ySpeed;
	private GameView gameView;
	private Bitmap bitmap;
	private int currentFrame = 0;
	private int width;
	private int height;
	private static final int LOOP_CHANGE_FRAME = 5;
	private int loops = 0;

	public Sprite(GameView gameView, Bitmap bitmap) {
		this.gameView = gameView;
		this.bitmap = bitmap;
		this.width = bitmap.getWidth() / BMP_COLUMNS;
		this.height = bitmap.getHeight() / BMP_ROWS;

		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		// y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = MAX_SPEED / 3 * rnd.nextFloat();
		while (ySpeed == 0) {
			ySpeed = MAX_SPEED * rnd.nextFloat();
		}
	}

	private void update() {
		if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;

		if (y > gameView.getHeight() - height - ySpeed) {
			gameView.loseGame();
		}
		y = y + ySpeed;
		if (loops == LOOP_CHANGE_FRAME) {
			loops = 0;
			currentFrame = ++currentFrame % BMP_COLUMNS;
		}
		loops++;
	}

	public void OnDrawer(Canvas canvas) {
		update();
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect((int) x, (int) y, (int) x + width, (int) y + height);
		canvas.drawBitmap(bitmap, src, dst, null);
	}

	private int getAnimationRow() {
		double dirDouble = Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2;
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

	public boolean isCollition(float x2, float y2) {
		return Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)) < DEATH_RADIUS;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
