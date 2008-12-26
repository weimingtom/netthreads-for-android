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

/**
 * Base Command class.
 * 
 */
public class Command implements Runnable
{
	private boolean threaded = false;

	private Status status = Status.PENDING;
	
	public Command(boolean threaded)
	{
		this.threaded = threaded;
	}

	/**
	 * Execute command.
	 * 
	 */
	public void run()
	{
		status = Status.RUNNING;
		
		execute();
		
		status = Status.FINISHED;
	}
  
	/**
	 * Override in subclasses. I have not made this abstract as it has it's uses like
	 * providing an empty command for signalling blocking queues.
	 * 
	 */
	public void execute()
	{
		// I can connect nothing with nothing.
	}

	/**
	 * Is command threaded.
	 *   
	 * @return threading flag.
	 */
	public boolean isThreaded()
	{
		return (threaded);
	}

	/**
	 * Return command state.
	 * 
	 * @return status
	 */
	public Status getStatus() 
	{
		return status;
	}

	/**
	 * Set run status.
	 * 
	 * @param status
	 */
	public void setStatus(Status status) 
	{
		this.status = status;
	}
	
	/**
	 * We introduce a method to return runnable interface. This is because subclasses may
	 * wrap this command and present a different runnable interface by overloading this 
	 * method.
	 * 
	 * @return Runnable interface
	 */
	public Runnable getRunnable()
	{
		return this;
	}
	
	/**
	 * Status of command.
	 * 
	 */
	public enum Status 
	{
		PENDING,
		RUNNING,
		FINISHED;
	}	
}