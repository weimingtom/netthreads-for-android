/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.opengl.support;

import java.nio.CharBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * A 2D rectangular mesh. Can be drawn textured or untextured.
 * This version is modified from the original Grid.java (found in
 * the SpriteText package in the APIDemos Android sample) to support hardware
 * vertex buffers.
 */
public class Grid 
{
    private FloatBuffer mVertexBuffer;
    private float[] mVertexArray;

    private FloatBuffer mTexCoordBuffer;
    private float[] mTexCoordArray;

    private CharBuffer mIndexBuffer;

    private int mW;
    private int mH;
    private int mIndexCount;
    private int mVertBufferIndex;
    private int mIndexBufferIndex;
    private int mTextureCoordBufferIndex;
    
    public Grid(int vertsAcross, int vertsDown) 
    {
        if (vertsAcross < 0 || vertsAcross >= 65536) {
            throw new IllegalArgumentException("vertsAcross");
        }
        if (vertsDown < 0 || vertsDown >= 65536) {
            throw new IllegalArgumentException("vertsDown");
        }
        if (vertsAcross * vertsDown >= 65536) {
            throw new IllegalArgumentException("vertsAcross * vertsDown >= 65536");
        }

        mW = vertsAcross;
        mH = vertsDown;
        int size = vertsAcross * vertsDown;
        mVertexArray = new float[size * 3];
        mVertexBuffer = FloatBuffer.wrap(mVertexArray);

        mTexCoordArray = new float[size * 2];
        mTexCoordBuffer = FloatBuffer.wrap(mTexCoordArray);

        int quadW = mW - 1;
        int quadH = mH - 1;
        int quadCount = quadW * quadH;
        int indexCount = quadCount * 6;
        mIndexCount = indexCount;
        char[] indexArray = new char[indexCount];

        /*
         * Initialize triangle list mesh.
         *
         *     [0]-----[  1] ...
         *      |    /   |
         *      |   /    |
         *      |  /     |
         *     [w]-----[w+1] ...
         *      |       |
         *
         */

        {
            int i = 0;
            for (int y = 0; y < quadH; y++) {
                for (int x = 0; x < quadW; x++) {
                    char a = (char) (y * mW + x);
                    char b = (char) (y * mW + x + 1);
                    char c = (char) ((y + 1) * mW + x);
                    char d = (char) ((y + 1) * mW + x + 1);

                    indexArray[i++] = a;
                    indexArray[i++] = b;
                    indexArray[i++] = c;

                    indexArray[i++] = b;
                    indexArray[i++] = c;
                    indexArray[i++] = d;
                }
            }
        }

        mIndexBuffer = CharBuffer.wrap(indexArray);
        
        mVertBufferIndex = 0;
    }

    public void set(int i, int j, float x, float y, float z, float u, float v) 
    {
        if (i < 0 || i >= mW) {
            throw new IllegalArgumentException("i");
        }
        if (j < 0 || j >= mH) {
            throw new IllegalArgumentException("j");
        }

        int index = mW * j + i;

        int posIndex = index * 3;
        mVertexArray[posIndex] = x;
        mVertexArray[posIndex + 1] = y;
        mVertexArray[posIndex + 2] = z;

        int texIndex = index * 2;
        mTexCoordArray[texIndex] = u;
        mTexCoordArray[texIndex + 1] = v;
    }

    public static void beginDrawing(GL10 gl, boolean useTexture) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        if (useTexture) {
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glEnable(GL10.GL_TEXTURE_2D);
        } else {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
    }
    
    
    public void draw(GL10 gl, boolean useTexture) {
        if (mVertBufferIndex == 0) {
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
    
            if (useTexture) {
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
            } 
    
            gl.glDrawElements(GL10.GL_TRIANGLES, mIndexCount,
                    GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
        } else {
            GL11 gl11 = (GL11)gl;
            // draw using hardware buffers
            gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
            gl11.glVertexPointer(3, GL10.GL_FLOAT, 0, 0);
            
            gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mTextureCoordBufferIndex);
            gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
            
            gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferIndex);
            gl11.glDrawElements(GL11.GL_TRIANGLES, mIndexCount,
                    GL11.GL_UNSIGNED_SHORT, 0);
            
            gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
            gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);


        }
    }
    
    public static void endDrawing(GL10 gl) {
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
    public boolean usingHardwareBuffers() {
        return mVertBufferIndex != 0;
    }
    
    /** 
     * When the OpenGL ES device is lost, GL handles become invalidated.
     * In that case, we just want to "forget" the old handles (without
     * explicitly deleting them) and make new ones.
     */
    public void forgetHardwareBuffers() {
        mVertBufferIndex = 0;
        mIndexBufferIndex = 0;
        mTextureCoordBufferIndex = 0;
    }
    
    /**
     * Deletes the hardware buffers allocated by this object (if any).
     */
    public void freeHardwareBuffers(GL10 gl) {
        if (mVertBufferIndex != 0) {
            if (gl instanceof GL11) {
                GL11 gl11 = (GL11)gl;
                int[] buffer = new int[1];
                buffer[0] = mVertBufferIndex;
                gl11.glDeleteBuffers(1, buffer, 0);
                
                buffer[0] = mTextureCoordBufferIndex;
                gl11.glDeleteBuffers(1, buffer, 0);
                
                buffer[0] = mIndexBufferIndex;
                gl11.glDeleteBuffers(1, buffer, 0);
            }
            
            forgetHardwareBuffers();
        }
    }
    
    /** 
     * Allocates hardware buffers on the graphics card and fills them with
     * data if a buffer has not already been previously allocated.  Note that
     * this function uses the GL_OES_vertex_buffer_object extension, which is
     * not guaranteed to be supported on every device.
     * @param gl  A pointer to the OpenGL ES context.
     */
    public void generateHardwareBuffers(GL10 gl) {
        if (mVertBufferIndex == 0) {
            if (gl instanceof GL11) {
                GL11 gl11 = (GL11)gl;
                int[] buffer = new int[1];
                
                // Allocate and fill the vertex buffer.
                gl11.glGenBuffers(1, buffer, 0);
                mVertBufferIndex = buffer[0];
                gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
                final int vertexSize = mVertexBuffer.capacity() * Float.SIZE; 
                gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertexSize, 
                        mVertexBuffer, GL11.GL_STATIC_DRAW);
                
                // Allocate and fill the texture coordinate buffer.
                gl11.glGenBuffers(1, buffer, 0);
                mTextureCoordBufferIndex = buffer[0];
                gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 
                        mTextureCoordBufferIndex);
                final int texCoordSize = 
                    mTexCoordBuffer.capacity() * Float.SIZE;
                gl11.glBufferData(GL11.GL_ARRAY_BUFFER, texCoordSize, 
                        mTexCoordBuffer, GL11.GL_STATIC_DRAW);    
                
                // Unbind the array buffer.
                gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
                
                // Allocate and fill the index buffer.
                gl11.glGenBuffers(1, buffer, 0);
                mIndexBufferIndex = buffer[0];
                gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 
                        mIndexBufferIndex);
                // A char is 2 bytes.
                final int indexSize = mIndexBuffer.capacity() * 2;
                gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indexSize, mIndexBuffer, GL11.GL_STATIC_DRAW);
                
                // Unbind the element array buffer.
                gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
                
            
            }
        }
    }

}
