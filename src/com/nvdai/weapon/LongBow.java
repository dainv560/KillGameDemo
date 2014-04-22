package com.nvdai.weapon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.nvdai.killgamedemo.GameView;
import com.nvdai.killgamedemo.R;

public class LongBow {

	private int angle = 0;
	private GameView gameView;
	private Bitmap bitmap;
	private Bitmap changeBitmap;
	private Bitmap resizedBitmap;
	private int x;
	private int xSpeed = 5;
	private int width;
	private int height;
	private int currentFrame;

	public LongBow(GameView gameView, Bitmap bitmap, Bitmap changeBitmap) {
		this.gameView = gameView;
		this.bitmap = bitmap;
		this.changeBitmap = changeBitmap;
		this.resizedBitmap = bitmap;
		this.width = gameView.getWidth();
		this.height = gameView.getHeight();
	}

	public void update() {
		resizedBitmap = rotate(bitmap, angle);
		if (angle >= 360) {
			angle = 0;
		}

		if (angle ==180 || angle ==0){
			changeStatusWeapon();
		}
		angle += 5;
	}

	public void OnDrawer(Canvas canvas) {
		update();
		canvas.drawBitmap(resizedBitmap, 100, 100, null);
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

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public void changeStatusWeapon() {
		Bitmap temp = bitmap;
		bitmap = changeBitmap;
		changeBitmap = temp;
	}

}
