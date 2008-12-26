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

import android.content.Context;

import android.location.Address;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.List;


/**
 * Location list adapter builds views for each row of list.
 * 
 * The view description is a filtered version of the location description
 * returned from the geocoder.
 *
 */
public class LocationListAdapter extends BaseAdapter
{
    private List<Address> list = null;

	/**
     * Remember our context so we can use it when constructing views.
     */
    private Context mContext;

    public LocationListAdapter(Context context, List<Address> list)
    {
        mContext = context;
        this.list = list;
    }

    /**
     * The number of items in the list.
     *
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount()
    {
        return list.size();
    }

    /**
     * Return item index.
     */
    public Object getItem(int position) 
    {
    	return list.get(position);
    }

    /**
     * Use the array index as a unique id.
     *
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	LocationView sv;

    	Address address = list.get(position);
    	

    	String description = getDescription(address);
    	
        if (convertView == null)
        {
            sv = new LocationView(mContext, description);
        }
        else
        {
            sv = (LocationView)convertView;
            sv.setTitle(description);
        }

        return sv;
    }

    public List<Address> getList() 
    {
		return list;
	}

	public void setList(List<Address> list) 
	{
		this.list = list;
	}
    
    /**
     * We will use a LocationView to display each item.
     *
     */
    private class LocationView extends LinearLayout
    {
        private TextView locationText;

        public LocationView(Context context, String description)
        {
            super(context);

            // Assign our xml layout
            addView(inflate(context, R.layout.geocode_row, null));
            
            this.setOrientation(HORIZONTAL);
            
            locationText = (TextView)this.findViewById(R.id.location);
            locationText.setText(description);
        }

        /**
         * Convenience method to set the title of a SpeechView
         */
        public void setTitle(String title)
        {
            locationText.setText(title);
        }
    }

    /**
     * Build description string for address. Note we have to be selective about what we show as
     * some fields get set by the process, others do not. Some fields are the same value. The 
     * following rtn is what I have discovered gives the best descriptions IMHO.
     * 
     * @param address
     * 
     * @return String description.
     */
    public String getDescription(Address address)
    {
    	String description = "";
    	
    	String addressLine = address.getAddressLine(0);
    	String adminArea = address.getAdminArea();
    	String locality = address.getLocality();
    	String countryCode = address.getCountryCode();
    	
    	String feature = address.getFeatureName();
    	String thoroughfare = address.getThoroughfare();

    	// If you would like to see the full list of attributes returned from the geocoder then
    	// uncomment these and examine the DDMS log.
//    	String countryName = address.getCountryName();
//    	String postalCode = address.getPostalCode();
//    	String subAdminArea = address.getSubAdminArea();
//    	String subPhone = address.getPhone();
    	
//    	if (countryName!=null) Log.d("countryName", countryName);
//    	if (feature!=null)Log.d("feature", feature);
//    	if (locality!=null)Log.d("locality", locality);
//    	if (postalCode!=null)Log.d("postalCode", postalCode);
//    	if (subAdminArea!=null)Log.d("subAdminArea", subAdminArea);
//    	if (subPhone!=null)Log.d("subPhone", subPhone);
//    	if (thoroughfare!=null)Log.d("thoroughfare", thoroughfare);
    	
    	if (locality!=null) 
    	{
    		if (adminArea!=null)
    		{
    			description = locality+", "+adminArea;
    		}
    		else
    		{
    			description = locality;
    		}
    	}
    	else
    	{
    		if (adminArea!=null)
    		{
            	description = addressLine+", "+adminArea;
    		}
    		else
    		{
    			description = addressLine;
    		}
    	}

    	if (feature!=null)
    	{
    		description = feature+", "+description;
    	}
    	else
	    	if (thoroughfare!=null)
	    	{
	    		description = thoroughfare+", "+description;
	    	}
    	
    	description +=","+countryCode;

    	return description;
    }
}
