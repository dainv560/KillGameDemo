package com.nvdai.weapon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.nvdai.killgamedemo.GameView;

public class LongBow {
	
	private long angle;
	private GameView gameView;
	private Bitmap bitmap;
	private int width;
	private int height;
	private int currentFrame;
	
	public LongBow(GameView gameView, Bitmap bitmap) {
		this.gameView = gameView;
		this.bitmap = bitmap;
		this.width = gameView.getWidth();
		this.height = gameView.getHeight();
	}
	
	public void update(){
		
	}
	
	public void OnDrawer(Canvas canvas) {
//		update();
		canvas.drawBitmap(bitmap, 0, height - bitmap.getHeight(), null);
	}

}
