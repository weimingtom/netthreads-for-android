/**
 * Copyright (C) 2008 Alistair Rutherford, Glasgow, Scotland, UK, www.netthreads.co.uk
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
package com.netthreads.android.command.processor;

import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Composite class wraps runnable in a FutureTask.
 *
 * Note: The FutureTask requires a result. I have just dummied this
 * up with a counter for now. I need to have a think about if this
 * can be used for something useful.
 * 
 */
public class UITask extends FutureTask<Number>
{
    private final static AtomicInteger count = new AtomicInteger(1);
    
	public UITask(Runnable runnable) 
	{
		super(runnable, count);
	}
	
	@Override
	protected void done() 
	{
		super.done();
		
		count.incrementAndGet();
	}
}
