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
package com.netthreads.android.geocode;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.netthreads.android.command.GeoCodeCommand;
import com.netthreads.android.command.processor.Command;
import com.netthreads.android.command.processor.CommandProcessor;
import com.netthreads.android.command.processor.UICommand;

/**
 * Implements Geocode activity where we can enter an address and the geocoder will attempt to 
 * return an list of targets.
 * 
 * @author Alistair Rutherford
 *
 */
public class GeoCodeActivity extends ListActivity
{
	// Constants
	public static final String DATA_LATITUDE = "latitude";
	public static final String DATA_LONGITUDE = "longitude";
	public static final String DATA_LOCATION = "location";
	
	private Geocoder geocoder = null;

    private EditText editText = null;

    private final int MIN_TEXT = 2;

    private List<Address> list = null;
    
    private LocationListAdapter adapter = null;
    
    private UICommand geocodeCommand = null;
    
    private boolean pending = false;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        
    	// ---------------------------------------------------------------
        // Model 
		// ---------------------------------------------------------------

		// Empty list
		list = new ArrayList<Address>();
		
		// Create geocoder
        geocoder = new Geocoder(this);

        // Results list adapter
        adapter = new LocationListAdapter(this, list);
        
    	// ---------------------------------------------------------------
        // View
        // ---------------------------------------------------------------
        setContentView(R.layout.geocode);

        setListAdapter(adapter);

        // Get reference to input box
        editText = (EditText) this.findViewById(R.id.edit);
        
    	// ---------------------------------------------------------------
        // Control
        // ---------------------------------------------------------------
        editText.addTextChangedListener(new TextWatcher()
        {
        	@Override
        	public void afterTextChanged(Editable s) 
        	{
        		String text = s.toString();
                if (text.length()>MIN_TEXT)
        		{
                	processText(text);
        		}
                else
                {
                	list.clear();
                	adapter.notifyDataSetChanged();
                }
        	}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) 
			{
				// Nowt
			}

			@Override
    		public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				// Nowt
			}
        });

        // Register a listener to detect when list is updated, this is to catch any 
        // keypresses that weren't included in the last geocode query.
        adapter.registerDataSetObserver(new DataSetObserver()
        {
        	@Override
        	public void onChanged()
        	{
        		// If we missed keypresses then requery
        		if (pending)
        		{
        	        processText(editText.getText().toString());
        	        
        			pending = false;
        		}
        	}
        });
	}

    /**
     * Called when activity is destroyed. Stops geocode task if it's running.
     * 
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    
        // Stop weather task (if it's running)
        if (geocodeCommand!= null && geocodeCommand.getStatus()==Command.Status.RUNNING) 
        {
        	geocodeCommand.cancel();
        }

        // Kill the command processor
        CommandProcessor.instance().stop();
    }
	
    /**
     * On list item selected. Set data in result and return to calling activity.
     * 
     */
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) 
    {
        Address item = list.get(position);
        
		Bundle bundle = new Bundle();
	      
    	if (item!=null)
    	{
    		Log.d("onListItemClick", adapter.getDescription(item));
    		Log.d("onListItemClick", Double.toString(item.getLatitude()));
    		Log.d("onListItemClick", Double.toString(item.getLongitude()));
    		
			bundle.putString(DATA_LOCATION, adapter.getDescription(item));
			bundle.putDouble(DATA_LATITUDE, item.getLatitude());
			bundle.putDouble(DATA_LONGITUDE, item.getLongitude());
		
	        Intent mIntent = new Intent();
	        mIntent.putExtras(bundle);
	
	        setResult(Preferences.CHANGED, mIntent);
	        
	        finish();
    	}
    	
    }

	/**
	 * Process key.
	 * 
	 * Note: Since we can't process keypresses when the geocoder is running we have to implement
	 * a means of catching them. The pending flag is set when keypresses arrive that we can't
	 * process. When the geocoder task completes it will update the list accordingly, at that
	 * point we test the pending flag and if true kick the geocoder task off again.
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public boolean processText(String text) 
	{
        if (text.length()>MIN_TEXT)
		{
            if (geocodeCommand!=null)
            {
            	// If previous command finished then requery
            	if (geocodeCommand.getStatus()==Command.Status.FINISHED)
            	{
            		issueQuery(text);
            	}
            	else
            	{
            		Log.w("processText", "pending, "+text);
            		
            		pending = true;
            	}
            }
            else
        	{
            	issueQuery(text);
        	}
		}
		
		return false;
	}

	/**
	 * Issue geocode query with given text.
	 * 
	 * @param text
	 */
	private void issueQuery(String text)
	{
		geocodeCommand = new GeoCodeCommand(text, adapter, list, geocoder);
		
		Log.d("GeoCodeActivity", "issueQuery, "+text);
		
		CommandProcessor.instance().add(geocodeCommand);
	}
	
	
}
