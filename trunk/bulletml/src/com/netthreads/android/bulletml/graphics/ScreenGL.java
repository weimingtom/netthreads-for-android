package com.netthreads.android.bulletml.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;

/**
 * Implements OpenGL screen drawing routines.
 * 
 */
public class ScreenGL implements IScreen
{
	private GL10 surface = null;

	private ByteBuffer byteBuffer = null;

	private IntBuffer intBuffer = null;
	
	private float lineWidth = 1.0f;

	private int screenWidth = 0;
	private int screenHeight = 0;
	
    private int[] line = new int[] { 0,0,0,0,0,0 };

	/**
	 * Construct OpenGL Screen.
	 * 
	 * @param lineWidth
	 * @param screenWidth
	 * @param screenHeight
	 */
	public ScreenGL(int lineWidth, int screenWidth, int screenHeight)
	{
		this.lineWidth = lineWidth;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// // Buffer
		byteBuffer = ByteBuffer.allocateDirect(24);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		intBuffer = byteBuffer.asIntBuffer();
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
        line[0] = x1<<16;
        line[1] = (screenHeight - y1)<<16;
        line[2] = 0;
        line[3] = x2<<16;
        line[4] = (screenHeight - y2)<<16;
        line[5] = 0;
        
        intBuffer.clear(); // Workaround
        intBuffer.compact(); // Workaround
        
        intBuffer.put(line);
        intBuffer.position(0);
        
        surface.glVertexPointer(3, GL10.GL_FIXED, 0, intBuffer);
        
        surface.glEnableClientState(GL10.GL_VERTEX_ARRAY); // Enable use of  vertex array

        /*
        private static final float PERCENT_CONVERSION = 1.0f/255.0f;
        
        // Colour values are passed as percentages.
        float red = PERCENT_CONVERSION*((color >> 16) & 0xFF);
        float green = PERCENT_CONVERSION*((color >> 8) & 0xFF);
        float blue = PERCENT_CONVERSION*((color & 0xFF));

        surface.glColor4f(red, green, blue, 0f);
        */

        // Colour values are passed as percentages.
        // Note: We might get even faster if we put this stuff in a lookup table.
        int red = (color&0x00FF0000);
        int green = (color&0x0000FF00)<<8;
        int blue = (color&0x000000FF)<<16;

        surface.glColor4x(red/255, green/255, blue/255, 0);
        
        surface.glLineWidth(lineWidth);

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
