package com.nvdai.character;

import java.util.List;

import com.nvdai.killgamedemo.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
	private float x;
	private float y;
	private Bitmap bitmap;

	private int life = 10;
	private List<TempSprite> temps;

	public TempSprite(List<TempSprite> temps, GameView gameView, float x,
			float y, Bitmap bitmap) {
		this.x = Math.min(Math.max(x - bitmap.getWidth() / 2, 0),
				gameView.getWidth() - bitmap.getWidth());
		this.y = Math.min(Math.max(y - bitmap.getHeight() / 2, 0),
				gameView.getHeight() - bitmap.getHeight());
		this.bitmap = bitmap;
		this.temps = temps;
	}

	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(bitmap, x, y, null);
	}

	private void update() {
		if (--life < 1) {
			temps.remove(this);
		}
	}

}
