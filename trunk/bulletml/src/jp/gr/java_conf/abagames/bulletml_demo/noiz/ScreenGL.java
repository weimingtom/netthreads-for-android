package jp.gr.java_conf.abagames.bulletml_demo.noiz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;

/**
 * Implements OpenGL screen drawing routines.
 * 
 */
public class ScreenGL implements IScreen
{
	private static final float PERCENT_CONVERSION = 1.0f/255.0f;
	
	private GL10 surface = null;

	private ByteBuffer byteBuffer = null;

	private static FloatBuffer floatBuffer = null;

	private float lineWidth = 1.0f;

	private int screenWidth = 0;
	private int screenHeight = 0;

	public ScreenGL(int lineWidth, int screenWidth, int screenHeight)
	{
		this.lineWidth = lineWidth;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// // Buffer
		byteBuffer = ByteBuffer.allocateDirect(24);
		byteBuffer.order(ByteOrder.nativeOrder());
		floatBuffer = byteBuffer.asFloatBuffer();
	}

	/**
	 * Clear the screen.
	 * 
	 */
	@Override
	public void clear()
	{
		// Implement
		surface.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Draw alpha blended line.
	 * 
	 * @param surface
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2, int color)
	{
		float xa = x1;
		float ya = screenHeight - y1;
		float xb = x2;
		float yb = screenHeight - y2;

		floatBuffer.position(0);

		floatBuffer.put(xa);
		floatBuffer.put(ya);
		floatBuffer.put(0.0f);

		floatBuffer.put(xb);
		floatBuffer.put(yb);
		floatBuffer.put(0.0f);

		floatBuffer.position(0);

		surface.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);

		surface.glEnableClientState(GL10.GL_VERTEX_ARRAY); // Enable use of  vertex array

		// Colour values are passed as percentages.
		float red = PERCENT_CONVERSION*((color >> 16) & 0xFF);
		float green = PERCENT_CONVERSION*((color >> 8) & 0xFF);
		float blue = PERCENT_CONVERSION*((color & 0xFF));

		surface.glColor4f(red, green, blue, 0f);

		surface.glLineWidth (lineWidth);

		surface.glDrawArrays(GL10.GL_LINES, 0, 2);

		surface.glDisableClientState(GL10.GL_VERTEX_ARRAY); // Disable use of  vertex array
	}

	/**
	 * Draw bitmap to screen.
	 * 
	 * @param bitmap
	 * @param left
	 * @param top
	 */
	@Override
	public void drawBitmap(Bitmap bitmap, float left, float top)
	{
		// Implement
	}

	/**
	 * No canvas no surface to draw to.
	 * 
	 * @param canvas
	 */
	public void setSurface(GL10 surface)
	{
		this.surface = surface;
	}

	public int getScreenWidth()
	{
		return screenWidth;
	}

	public int getScreenHeight()
	{
		return screenHeight;
	}
}
