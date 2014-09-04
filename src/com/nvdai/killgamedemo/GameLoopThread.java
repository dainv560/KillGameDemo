package com.nvdai.killgamedemo;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	static final long FPS = 10;
	private GameView view;
	private boolean running = false;
	private int count = 0;
	private static int MAX_COUNT = 50;
	private static int MIN_COUNT = 10;
	private static boolean lose = false;

	public GameLoopThread(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@SuppressLint("WrongCall")
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;

		while (running) {
			Canvas c = null;
			count++;
			startTime = System.currentTimeMillis();
			try {
				c = view.getHolder().lockCanvas();
				if (count == MAX_COUNT) {
					count = 0;
					view.createSprites();
				}
				if (lose) {
					view.endGame(c);
					break;
				}
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			} finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			// try {
			// if (sleepTime > 0) {
			// sleep(sleepTime);
			// } else {
			// sleep(10);
			// }
			// } catch (Exception e) {
			// }

		}
	}

	public void setMaxCount(int x) {
		if (MAX_COUNT >= MIN_COUNT) {
			MAX_COUNT = 50 - x/5;
		}
	}

	public void setLose() {
		lose = true;
	}

}
