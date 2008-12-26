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

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/**
 * User Interface Command.
 * 
 * This command implements a mechanism where the command thread can trigger
 * overridden calls from within the thread body by sending the predefined 
 * message types [start, progress, end, cancelled]. 
 *
 */
public class UICommand extends Command 
{
    private static final int MESSAGE_POST_START = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;
    private static final int MESSAGE_POST_END = 0x3;
    private static final int MESSAGE_POST_CANCELLED = 0x4;

    private static final InternalHandler handler = new InternalHandler();

    // The runnable component is wrapped in a FutureTask.
    private UITask task = new UITask(this);
    
	public UICommand(boolean threaded) 
	{
		super(threaded);
	}

	/**
	 * Cancel the task and signal cancelled state.
	 * 
	 */
	public void cancel()
	{
		task.cancel(true);
		
		publishCancelled();
	}
	
	/**
	 * This saves us having to provide a reference to the composite task.
	 * 
	 * @return cancelled state.
	 */
	public boolean isCancelled()
	{
		return task.isCancelled();
	}
	
	/**
	 * This is overridden version of the main runnable method. 
	 * 
	 * Note the calls to signal the start and end of the task.
	 * 
	 */
	@Override
	public void run() 
	{
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

		publishStart();
		
		execute();
		
		publishEnd();
	}

	/**
	 * Because we are wrapping the command runnable in a FutureTask we present that
	 * interface to the the execution mechanism.
	 * 
	 * @return Runnable interface
	 */
	@Override
	public Runnable getRunnable()
	{
		return task;
	}
	
    protected final void publishStart()
    {
		setStatus(Status.RUNNING);
		
        handler.obtainMessage(MESSAGE_POST_START, new CommandResult(this)).sendToTarget();
    }
    
    protected final void publishProgress()
    {
        handler.obtainMessage(MESSAGE_POST_PROGRESS, new CommandResult(this)).sendToTarget();
    }
    
    protected final void publishEnd()
    {
		setStatus(Status.FINISHED);
		
        handler.obtainMessage(MESSAGE_POST_END, new CommandResult(this)).sendToTarget();
    }

    protected final void publishCancelled()
    {
        handler.obtainMessage(MESSAGE_POST_CANCELLED, new CommandResult(this)).sendToTarget();
    }
    
    // -------------------------------------------------------------------
    // Override these in subclasses to handle situation signalled from thread
    // -------------------------------------------------------------------
    
    public void onPreExecute()
    {
    	Log.d("onPreExecute", "called");
    }
    
    public void onProgressUpdate()
    {
    	Log.d("onProgressUpdate", "called");
    }

    public void onPostExecute()
    {
    	Log.d("onPostExecute", "called");
    }

    public void onCancelled()
    {
    	Log.d("onPostCancelled", "called");
    }

    // -------------------------------------------------------------------
    // Define message object and message handler 
    // -------------------------------------------------------------------
    
    private static class CommandResult
    {
        final UICommand command;

        CommandResult(UICommand task)
        {
            this.command = task;
        }
    }    
    
    /**
     * Message handler for signalling out of the command thread.
     * 
     */
    private static class InternalHandler extends Handler
    {
        @Override
        public void handleMessage(Message message)
        {
            CommandResult result = (CommandResult)message.obj;

            switch (message.what)
            {
		        case MESSAGE_POST_START:
		            result.command.onPreExecute();
		            break;
		        case MESSAGE_POST_PROGRESS:
		            result.command.onProgressUpdate();
		            break;
		        case MESSAGE_POST_END:
		            result.command.onPostExecute();
		            result.command.task.done();
		            break;
		        case MESSAGE_POST_CANCELLED:
		            result.command.onCancelled();
		            break;
		        default:
		        	break;
            }
        }
    }	

}
