package com.nvdai.weapon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

public class WeaponView extends View {

	private Matrix mMatrix = new Matrix();

	public WeaponView(Context context) {
		super(context);
	}

	public WeaponView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WeaponView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
	}

	private Bitmap pBitmap;

	public Bitmap getBitmap() {
		return pBitmap;
	}

	public void setBitmap(Bitmap newValue) {
		pBitmap = newValue;
	}

	private int pDegrees;

	public int getDegrees() {
		return pDegrees;
	}

	public void setDegrees(int newValue) {
		pDegrees = newValue;
	}

	private float pOffsetX;

	public float getOffsetX() {
		return pOffsetX;
	}

	public void setOffsetX(float newValue) {
		pOffsetX = newValue;
	}

	private float pOffsetY;

	public float getOffsetY() {
		return pOffsetY;
	}

	public void setOffsetY(float newValue) {
		pOffsetY = newValue;
	}

	private float pScale = 1.0f;

	public float getScale() {
		return pScale;
	}

	public void setScale(float newValue) {
		pScale = newValue;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (pBitmap == null)
			return;

		Matrix matrix = mMatrix;
		matrix.reset();

		float vw = this.getWidth();
		float vh = this.getHeight();
		float hvw = vw / 2;
		float hvh = vh / 2;
		float bw = (float) pBitmap.getWidth();
		float bh = (float) pBitmap.getHeight();
		float s1x = vw / bw;
		float s1y = vh / bh;
		float s1 = (s1x < s1y) ? s1x : s1y;
		matrix.postScale(s1, s1);

		matrix.postTranslate(-hvw, -hvh);

		int rotation = getDegrees();
		matrix.postRotate(rotation);

		float offsetX = getOffsetX(), offsetY = getOffsetY();
		if (pScale != 1.0f) {
			matrix.postScale(pScale, pScale);

			float sx = (0.0f + pScale) * vw / 2;
			float sy = (0.0f + pScale) * vh / 2;

			offsetX += sx;
			offsetY += sy;

		} else {
			offsetX += hvw;
			offsetY += hvh;
		}

		matrix.postTranslate(offsetX, offsetY);

		canvas.drawBitmap(pBitmap, matrix, null);

	}

	public void setBitmapFromResource(int drawableId) {
		Resources res = getResources();
		setBitmap(BitmapFactory.decodeResource(res, drawableId));
	}

}