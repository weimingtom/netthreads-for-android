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
package com.netthreads.android.command;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.widget.BaseAdapter;

import com.netthreads.android.command.processor.UICommand;

/**
 * Geocode text command. Runs in a background thread, updates supplied list and notifies change
 * to list adapter. 
 * 
 */
public class GeoCodeCommand extends UICommand 
{
    private BaseAdapter adapter = null;
    private List<Address> locations = null;
    private Geocoder geocoder = null;
    private String locationText = "";

    public GeoCodeCommand(String locationText, BaseAdapter adapter, List<Address> locations, Geocoder geocoder) 
    {
    	// Interestingly we could have made this non-threaded. Don't forget the command processor
    	// runs in it's own thread so the UI would not be blocked. For this type of operation this
    	// might be preferable as the user is typing away and we want to queue up the operations
    	// performed in response to their actions. The operations still get executed in the command
    	// processor thread except now they block the queue until finished.
    	// The problem is that you don't really want to send every single keypress to the geocoder
    	// because it takes a wee while to respond and if you type real fast you will see your
    	// actions executing away long after you've stopped typing.
    	
    	super(true); // TRUE=THREADED
    	
    	this.locationText = locationText;

    	this.adapter = adapter;
    	this.locations = locations;
    	this.geocoder = geocoder;
	}
    
    /**
     * Execute task
     * 
     */
    @Override
    public void execute() 
	{
    	// Geocode text
    	try 
    	{
    		List<Address> search = geocoder.getFromLocationName(locationText, 20);
    		
			setLocations(search);
		} 
    	catch (IOException e) 
    	{
			// Do not crash out
		}
    }

    @Override
    public void onPostExecute() 
    {
    	adapter.notifyDataSetChanged();
    }

    /**
     * Update results list.
     * 
     * @param locations
     */
	public void setLocations(List<Address> locations) 
	{
		this.locations.clear();
		
		for (Address address : locations)
		{
			String countryCode = address.getCountryCode();
			if (countryCode!=null)
			{
				this.locations.add(address);
			}
		}
	}

	/**
	 * Return location list
	 * 
	 * @return
	 */
	public List<Address> getLocations() 
	{
		return locations;
	}

}
