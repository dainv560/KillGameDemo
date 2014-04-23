package com.nvdai.weapon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.nvdai.killgamedemo.GameView;

public class Ball {

	private GameView gameView;
	private Bitmap bitmap;
	private Bitmap resizedBitmap;
	private float x = 0;
	private float y = 0;
	private float xSpeed;
	private float ySpeed;
	private float xGoal = -1;
	private float yGoal = -1;
	private float xCenter = -1;
	private float yCenter = -1;
	private static final int MAX_SPEED = 70;
	private int width;
	private int height;

	public Ball(GameView gameView, Bitmap bitmap) {
		this.gameView = gameView;
		this.bitmap = bitmap;
		this.resizedBitmap = bitmap;
		this.width = gameView.getWidth();
		this.height = gameView.getHeight();
		this.x = (this.width - this.bitmap.getWidth()) / 2;
		this.y = this.height - this.bitmap.getHeight();
	}

	public void update() {
		if (xCenter >= 0 && yCenter >= 0) {
			float xLength = Math.abs(xGoal - x);
			float yLength = Math.abs(yGoal - y);
			if (xLength > yLength) {
				if (xGoal > x) {
					xSpeed = MAX_SPEED;
				} else {
					xSpeed = -MAX_SPEED;
				}
				ySpeed = -MAX_SPEED * yLength / xLength;
			} else {
				ySpeed = -MAX_SPEED;
				if (xGoal > x) {
					xSpeed = MAX_SPEED * xLength / yLength;
				} else {
					xSpeed = -MAX_SPEED * xLength / yLength;
				}
			}
		}
		if (y >= yCenter && !isCollition(xCenter, yCenter)) {
			x = x + xSpeed;
			y = y + ySpeed;
		} else {
			gameView.killCharacter(x, y);
			gameView.destroyBall();
		}
	}

	public void setGoal(float xCenter, float yCenter) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.xGoal = xCenter - bitmap.getWidth() / 2;
		this.yGoal = yCenter - bitmap.getHeight() / 2;
	}

	public void OnDrawer(Canvas canvas) {
		update();
		canvas.drawBitmap(resizedBitmap, x, y, null);
	}

	public boolean isCollition(float x2, float y2) {
		return x2 > x && x2 < x + bitmap.getWidth() && y2 > y
				&& y2 < y + bitmap.getHeight();
	}

	public Bitmap rotate(Bitmap bitmap, int degrees) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int newWidth = width;
		int newHeight = height;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(degrees, width / 2, height / 2);
		;
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}


}
