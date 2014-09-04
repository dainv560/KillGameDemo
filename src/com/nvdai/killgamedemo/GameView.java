package com.nvdai.killgamedemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nvdai.character.Sprite;
import com.nvdai.character.TempSprite;
import com.nvdai.weapon.Ball;

public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private List<TempSprite> temps = new ArrayList<TempSprite>();
	private Ball ball;
	private long lastClick;
	private Bitmap bitmapBlood;
	private int score = 0;
	private int image[] = { R.drawable.bad1, R.drawable.bad2, R.drawable.bad3,
			R.drawable.bad4, R.drawable.good1, R.drawable.good2 };

	// private Bitmap background =
	// BitmapFactory.decodeResource(getResources(),R.drawable.game_background);

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				ball = createWeapon(R.drawable.ball);
				gameLoopThread.setRunning(true);
				gameLoopThread.start();

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}
		});

		bitmapBlood = BitmapFactory.decodeResource(getResources(),
				R.drawable.blood1);
	}

	public void createSprites() {
		Random rnd = new Random(System.currentTimeMillis());
		int check;
		for (int i = 0; i < 3; i++) {
			check = rnd.nextInt(100);
			if (check < 80) {
				sprites.add(createSprite(image[check % 6]));
			}
		}
	}

	@SuppressLint("WrongCall")
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		Paint paint = new Paint();

		// canvas.drawBitmap(background, 0, 0, null);
		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).onDraw(canvas);
		}
		for (Sprite sprite : sprites) {
			sprite.OnDrawer(canvas);
		}

		ball.OnDrawer(canvas);

		paint.setColor(Color.BLACK);
		paint.setTextSize(20);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText("SCORE: " + score, (int) (this.getWidth() * 0.6), 50,
				paint);

	}

	private Sprite createSprite(int resouce) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this, bitmap);
	}

	private Ball createWeapon(int resouce) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resouce);
		return new Ball(this, bitmap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - lastClick > 500) {
			lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			ball.setGoal(x, y);
		}
		return true;
	}

	public void destroyBall() {
		ball = createWeapon(R.drawable.ball);
	}

	public void killCharacter(float x, float y) {
		synchronized (getHolder()) {
			for (int i = sprites.size() - 1; i >= 0; i--) {
				Sprite sprite = sprites.get(i);
				if (sprite.isCollition(x, y)) {
					float xTemp = sprite.getX();
					float yTemp = sprite.getY();
					sprites.remove(sprite);
					score++;
					gameLoopThread.setMaxCount(score);
					temps.add(new TempSprite(temps, this, xTemp, yTemp,
							bitmapBlood));
				}
			}
		}
	}

	public void loseGame() {
		gameLoopThread.setLose();
	}

	public void endGame(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText("End Game", (int) (this.getWidth() *0.5),
				(int) (this.getHeight() * 0.4), paint);
		canvas.drawText("Score: " + score, (int) (this.getWidth() *0.5),
				(int) (this.getHeight() * 0.6), paint);
	}

}
