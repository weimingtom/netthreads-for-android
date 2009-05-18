/*
 * Copyright (C) 2008 The Android Open Source Project
 * Copyright (C) 2009 Alistair Rutherford, www.netthreads.co.uk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netthreads.android.bulletml.graphics;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import jp.gr.java_conf.abagames.bulletml_demo.noiz.GameManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.netthreads.android.opengl.GLSurfaceView;
import com.netthreads.android.opengl.Grid;
import com.netthreads.android.opengl.GLSurfaceView.EGLConfigChooser;


/**
 * An OpenGL ES renderer based on the GLSurfaceView rendering framework.  This
 * class is responsible for drawing a list of renderables to the screen every
 * frame.  It also manages loading of textures and (when VBOs are used) the
 * allocation of vertex buffer objects.
 */
public class RendererGL implements GLSurfaceView.Renderer, EGLConfigChooser
{
    // Specifies the format our textures should be converted to upon load.
    private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    // Pre-allocated arrays to use at runtime so that allocation during the
    // test can be avoided.
    private int[] textureNameWorkspace;
    private int[] cropWorkspace;

    // A reference to the application context.
    private Context context;

    // Determines the use of vertex arrays.
    private boolean useVerts;

	GameManager gameManager = null;
	
	private ScreenGL screen= null;
	
	/**
	 * Construct.
	 * 
	 * @param Manager object.
	 * 
	 */
	public RendererGL(Context context, GameManager gameManager, int lineWidth, int screenWidth, int screenHeight) 
	{
		this.gameManager = gameManager;
        this.context = context;

        screen = new ScreenGL(lineWidth, screenWidth, screenHeight);
        
        // Pre-allocate and store these objects so we can use them at runtime
        // without allocating memory mid-frame.
        textureNameWorkspace = new int[1];
        cropWorkspace = new int[4];

        // Set our bitmaps to 16-bit, 565 format.
        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        this.useVerts = false; // NO VERTS for now.
	}
	
	@Override
	public void onDrawFrame(GL10 gl) 
	{
		screen.setSurface(gl);
		
		// Update
        gameManager.update();

        if (screen != null)
        {
            gl.glMatrixMode(GL10.GL_MODELVIEW);

            if (useVerts)
            {
                Grid.beginDrawing(gl, true);
            }

            gameManager.draw(screen);

            if (useVerts)
            {
                Grid.endDrawing(gl);
            }
        }
	}

	
    /**
     * Called when the rendering thread shuts down.  This is a good place to
     * release OpenGL ES resources.
     * @param gl
     */
    public void shutdown(GL10 gl)
    {
    }

    /**
     * Loads a bitmap into OpenGL and sets up the common parameters for
     * 2D texture maps.
     * 
     */
    public int loadBitmap(GL10 gl, Bitmap bitmap)
    {
        int textureName = -1;

        if ((context!= null) && (gl != null))
        {
            gl.glGenTextures(1, textureNameWorkspace, 0);

            textureName = textureNameWorkspace[0];
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName);

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

            gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

            cropWorkspace[0] = 0;
            cropWorkspace[1] = bitmap.getHeight();
            cropWorkspace[2] = bitmap.getWidth();
            cropWorkspace[3] = -bitmap.getHeight();

            ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, cropWorkspace, 0);

            int error = gl.glGetError();

            if (error != GL10.GL_NO_ERROR)
            {
                Log.e("SpriteMethodTest", "Texture Load GLError: " + error);
            }
        }

        return textureName;
    }

	/**
	 * Called on surface creation and when phone orientation is flipped.
	 * 
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
    	/*
        * Some one-time OpenGL initialization can be made here probably based
        * on features of this particular context
        */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1); // Black
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        /*
         * By default, OpenGL enables features that improve quality but reduce
         * performance. One might want to tweak that especially on software
         * renderer.
         */
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * Called on surface creation and when phone orientation is flipped.
	 * 
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
        gl.glViewport(0, 0, width, height);

        /*
         * Set our projection matrix. This doesn't have to be done each time we
         * draw, but usually a new projection needs to be set when the viewport
         * is resized.
         */
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0.0f, width, 0.0f, height, 0.0f, 1.0f);

        // In flat-shaded mode, the triangle will be rendered with the color that was given 
        // to the last vertex specified. In smooth-shaded mode, the colors are interpolated 
        // between the vertices where each vertex can be given a different color.
        gl.glShadeModel(GL10.GL_FLAT);

        // ---------------------------------------------------------------
        // This was here in original code when textures were used on the 
        // gl surface. Enabling them seems fine on the emulator but 
        // stops drawing on the phone ::sigh::
        // ---------------------------------------------------------------
        // In RGBA mode, pixels can be drawn using a function that blends the incoming (source) 
        // RGBA values with the RGBA values that are already in the frame buffer (the destination 
        // values). 
        //gl.glEnable(GL10.GL_BLEND);
        //gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        //gl.glColor4x(0x10000, 0x10000, 0x10000, 0x1);
        //gl.glEnable(GL10.GL_TEXTURE_2D);
        // ---------------------------------------------------------------

        // This will make line width work on emulator but not on phone!
        gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_DONT_CARE);
	}

    /**
     * Choose EGL configuration.
     * 
     */
	@Override
	public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) 
	{
		EGLConfig[] configs = new EGLConfig[1];
        int[] num_config = new int[1];
        
        egl.eglChooseConfig(display, getConfigSpec(), configs, 1, num_config);
        
        EGLConfig mEglConfig = configs[0];
        
		return mEglConfig;
	}
	
	/**
	 * Returns EGL config.
	 * 
	 * @return config list.
	 */
    public int[] getConfigSpec()
    {
        // We don't need a depth buffer, and don't care about our
        // colour depth.
        int[] configSpec = { EGL10.EGL_DEPTH_SIZE, 0, EGL10.EGL_NONE };

        return configSpec;
    }
	
}
