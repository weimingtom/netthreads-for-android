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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic thread manager.
 * 
 * Note you should tighten this up with thread pool limit if you intend to spawn lots of child threads.
 *
 */
public class ThreadManager
{
	private static final ThreadFactory threadFactory = new ThreadFactory()
	{
	    private final AtomicInteger count = new AtomicInteger(1);
	
	    public Thread newThread(Runnable r)
	    {
	        return new Thread(r, "Managed Thread #" + count.getAndIncrement());
	    }
	};
	
    private ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
	
	private static ThreadManager instance = null;

	/**
	 * Singleton constructor.
	 * 
	 * @return instance.
	 */
	public synchronized static ThreadManager instance()
	{
		if (instance == null)
		{
			instance = new ThreadManager();
		}

		return (instance);
	}
	
	private ThreadManager()
	{
		// Can't instantiate this directly.
	}
	
    /**
     * Add to thread-pool queue.
     * 
     * @param item
     */
    public void add(Runnable item)
    {
        executor.execute(item);
    }

}