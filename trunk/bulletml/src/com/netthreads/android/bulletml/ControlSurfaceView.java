/**
 * Copyright (C) 2009 Alistair Rutherford, Glasgow, Scotland, UK, www.netthreads.co.uk
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.netthreads.android.bulletml;

import android.content.Context;
import android.view.MotionEvent;

import com.netthreads.android.bulletml.data.StateData;

/**
 * Surface view with input handling. 
 *
 */
public class ControlSurfaceView extends CanvasSurfaceView 
{
	private StateData state = null;
	
    public ControlSurfaceView(Context context, StateData stateData) 
    {
        super(context);
        
        this.state = stateData;
    }


    /**
     * Triggered on touch event. Note we defer the processing by queueing it.
     * 
     * @param The touch event.
     */
    public boolean onTouchEvent(final MotionEvent event) 
    {
        queueEvent(new Runnable()
        {
            public void run() {
                state.controlX = event.getX();
                state.controlY = event.getY();
            }});
        
        return true;
    }

}