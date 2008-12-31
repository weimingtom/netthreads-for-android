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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

/**
 * Implementation of classic command processor design pattern.
 * 
 */

public class CommandProcessor implements Runnable
{
	private boolean running = false;

	private BlockingQueue<Command> commandsPending = null;

	private static CommandProcessor instance = null;

	/**
	 * Singleton constructor.
	 * 
	 * @return instance.
	 */
	public synchronized static CommandProcessor instance()
	{
		if (instance == null)
		{
			instance = new CommandProcessor();
		}

		// Always make sure it's running
		if (!instance.getRunning())
		{
			instance.start();
		}
		
		return (instance);
	}
    	
	/**
	 * Create list of pending commands.
	 * 
	 */
    private CommandProcessor()
    {
        commandsPending = new LinkedBlockingQueue<Command>();
    }

    /**
     * Runs in it's own thread.
     * 
     */
    public void run()
    {
        process();
    }
    
    /**
     * Process pending commands by removing them from pending list and placing them on executable list.
     *    
     */
    public void process()
    {
        running = true;

        try
        {
            while (running)    // keep going
            {
            	Log.d("process","waiting for command to execute");
            	
                // Block on waiting for command 
                Command command = commandsPending.take();

                if (command!=null)
                {
	                if (command.isThreaded())
	                {
	                    // command executes in its own thread
	                    ThreadManager.instance().add(command.getRunnable());
	                }
	                else
	                {
	                	// Run the command directly without spinning a thread.
	                	command.getRunnable().run();
	                }
                }
            } 
        }
        catch (IllegalThreadStateException  e)
        {
            // Thread stopped
			running = false;
        } 
        catch (InterruptedException e) 
        {
            // Thread stopped
			running = false;
		}
        catch (Throwable t)
        {
			running = false;
            throw new RuntimeException("An error occured while executing command", t);
        }
        
        Log.d("CommandProcessor", "process end");
    }

    /**
     * Add command to pending queue.
     * 
     * @param cmd
     */
    public synchronized void add(UICommand command)
    {
        commandsPending.add(command);
    }

    /**
     * Running state.
     * 
     * @return state
     */
    public synchronized boolean getRunning()
    {
        return (running);
    }
 
    /**
     * Start processing commands.
     * 
     */
    private synchronized void start()
    {
        ThreadManager.instance().add(this);
    }
    
    /**
     * Set running state
     * 
     * @param running
     */
	public synchronized void stop() 
	{
		this.running = false;
		
		// Clear queue and add empty command to indicate end
		commandsPending.clear();
		commandsPending.add(new Command(false));
	}
    
}