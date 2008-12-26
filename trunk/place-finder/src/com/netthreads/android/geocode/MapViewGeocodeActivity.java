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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * This application show map and currently selected location. The user can pull up a
 * geocoder screen which will let them input an a location name which the geocoder will
 * attempt to convert into location. The map is then centred in the location.
 *
 */
public class MapViewGeocodeActivity  extends MapActivity 
{
    private static final int REQUEST_GEOCODE_ACTIVITY = 1;
    
	private MapView mapView = null;
	private MapController mapController = null;
	
    /**
     *  Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        // ---------------------------------------------------------------
        // MapView
        // ---------------------------------------------------------------
        mapView = (MapView)findViewById(R.id.map_view);

        LinearLayout zoomLayout =(LinearLayout)findViewById(R.id.layout_zoom);
        
        View zoomView = mapView.getZoomControls(); 
        zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        // ---------------------------------------------------------------
        // MapController
        // ---------------------------------------------------------------
        mapController = mapView.getController();
    }

    /**
     * No routing.
     * 
     */
	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}
	
    /**
     * Create options menu.
     * 
     * @param The activity context menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Once views have been created then setup view elements.
     * 
     */
    @Override
    protected void onResume() 
    {
    	super.onResume();

    	setView();
    }

    /**
     * Executed when a sub-activity (e.g. geocoder) returns.
     * 
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        // The preferences returned if the request code is what we had given
        // earlier in startSubActivity
        if (requestCode == REQUEST_GEOCODE_ACTIVITY) 
        {
        	if (data!=null)
        	{
	    		// Extract results
	            Bundle extras = data.getExtras();
            
	            String location = extras.getString(GeoCodeActivity.DATA_LOCATION);
	            double longitude = extras.getDouble(GeoCodeActivity.DATA_LONGITUDE);
	            double latitude  = extras.getDouble(GeoCodeActivity.DATA_LATITUDE);
	
	            // We're going to use the preference to store our settings.
	            Preferences preferences = Preferences.instance(this);
	            preferences.setLocation(location);
	            preferences.setLatitude(latitude);
	            preferences.setLongitude(longitude);
            }
        }
    }

    /**
     * Setup view elements from preferences.
     *
     */
    private void setView()
    {
    	// Centre map on current location.
        Preferences preferences = Preferences.instance(this);
        
        Double latitude = preferences.getLatitude()*1E6;
        Double longitude = preferences.getLongitude()*1E6;
        
        GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
        
        mapController.setCenter(point);
        mapController.animateTo(point);
        mapController.setZoom(15);
        
        // Title
        TextView textView = (TextView)this.findViewById(R.id.location_name);
        textView.setText(preferences.getLocation());
    }
    
    /**
     * On options item.
     *
     * @param The selected item.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch(item.getItemId()) 
        {
        case R.id.show_geocoder:
            showGeocode();
            break;

        case R.id.show_about:
            showAbout();
            break;
        default:
        	break;
        }

        return true;
    }
    
    /**
     * Show about view.
     * 
     */
    public void showAbout()
    {
        // Launch about activity
        startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * Show geocoder view
     * 
     */
    public void showGeocode()
    {
		// Get location value to pass to the geocoder (as a possible initial setting).
        Intent geocodeIntent = new Intent().setClass(this, GeoCodeActivity.class);
        
        // Make it a sub-activity so we know when it returns
        startActivityForResult(geocodeIntent, REQUEST_GEOCODE_ACTIVITY);
    }
    
}
