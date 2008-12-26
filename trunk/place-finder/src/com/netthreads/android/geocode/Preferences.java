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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Application preferences.
 * 
 * Note the defaults values are returned the first time the 'getters' are accessed, this then
 * creates the appropriate entry in the preferences structure.
 * 
 */
public class Preferences 
{
	private static final String PREFERENCES_TEXT = "preferences";
	
	private static final String TEXT_CURRENT_LOCATION = "currentLocation";
	private static final String TEXT_CURRENT_LATITUDE = "currentLatitude";
	private static final String TEXT_CURRENT_LONGITUDE = "currentLongitude";

	private static final String TEXT_CURRENT_LOCATION_DEFAULT = "Glasgow, Glasgow,GB";
	private static final String TEXT_CURRENT_LATITUDE_DEFAULT = "55.8656274";
	private static final String TEXT_CURRENT_LONGITUDE_DEFAULT = "-4.2572227";
	
	public static final int UNCHANGED = 0;
	public static final int CHANGED = 1;

	private static Preferences instance = null;

    private SharedPreferences settings = null;
    
	public static Preferences instance(Context context)
	{
		if (instance==null)
		{
			instance = new Preferences(context);
		}
		
		return instance;
	}

	private Preferences(Context context) 
	{
        settings = context.getSharedPreferences(PREFERENCES_TEXT, Activity.MODE_PRIVATE);
	}
	
    /**
     * Return location.
     * 
     * @return Location string
     */
    public String getLocation()
    {
		String locationValue = settings.getString(TEXT_CURRENT_LOCATION, TEXT_CURRENT_LOCATION_DEFAULT);

		return locationValue;
    }
    
    /**
     * Set the location.
     * 
     * @return Location string
     */
    public void setLocation(String location)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TEXT_CURRENT_LOCATION, location);
        editor.commit();
    }
    
    /**
     * Return location latitude
     * 
     * @return Location string
     */
    public double getLatitude()
    {
		String latitude = settings.getString(TEXT_CURRENT_LATITUDE, TEXT_CURRENT_LATITUDE_DEFAULT);

		return Double.parseDouble(latitude);
    }
    
    /**
     * Set the location latitude.
     * 
     * @return Location string
     */
    public void setLatitude(double latitude)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TEXT_CURRENT_LATITUDE, Double.toString(latitude));
        editor.commit();
    }	

    /**
     * Return location longitude
     * 
     * @return Location string
     */
    public double getLongitude()
    {
		String latitude = settings.getString(TEXT_CURRENT_LONGITUDE, TEXT_CURRENT_LONGITUDE_DEFAULT);

		return Double.parseDouble(latitude);
    }
    
    /**
     * Set the location longitude.
     * 
     * @return Location string
     */
    public void setLongitude(double longitude)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TEXT_CURRENT_LONGITUDE, Double.toString(longitude));
        editor.commit();
    }    
}
