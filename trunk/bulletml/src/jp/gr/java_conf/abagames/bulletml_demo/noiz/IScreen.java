package jp.gr.java_conf.abagames.bulletml_demo.noiz;

import android.graphics.Bitmap;

public interface IScreen
{
	public void clear();
	public void drawLine(int x1, int y1, int x2, int y2, int color);
	public void drawBitmap(Bitmap bitmap, float left, float top);
}
