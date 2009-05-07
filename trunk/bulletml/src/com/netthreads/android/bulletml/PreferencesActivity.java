/**
 * Copyright (C) 2009 Alistair Rutherford, Glasgow, Scotland, UK, www.netthreads.co.uk
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
package com.netthreads.android.bulletml;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

import com.netthreads.android.bulletml.widget.SeekBarPreference;

/**
 * Preferences settings activity.
 *
 *
 */
public class PreferencesActivity extends PreferenceActivity
{
	private SeekBarPreference rankPref = null;
	private SeekBarPreference thicknessPref = null;
	
    /*
     * View Create
     *
     * (non-Javadoc)
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set the name of the preferences this PreferenceActivity will manage
        getPreferenceManager().setSharedPreferencesName(ApplicationPreferences.NAME);

        // Create and note geocode pref screen
        setPreferenceScreen(createPreferenceScreen());
    }
    
    /**
     * On resume.
     *
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        // We can hook listeners for changes in here
    }

    /**
     * On pause we uninstall changed listener.
     *
     */
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    
    /**
     * Build preference view.
     *
     * @return view
     */
    private PreferenceScreen createPreferenceScreen()
    {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // ---------------------------------------------------------------
        // Preferences Category 
        // ---------------------------------------------------------------
        PreferenceCategory inlinePrefCat = new PreferenceCategory(this);
        inlinePrefCat.setTitle(R.string.data_preferences);
        root.addPreference(inlinePrefCat);

        // Persist data slider
        rankPref = new SeekBarPreference(this);
        
        rankPref.setKey(ApplicationPreferences.RANK_TEXT);
        rankPref.setTitle(ApplicationPreferences.RANK_TEXT);
        rankPref.setMax(100);

        inlinePrefCat.addPreference(rankPref);
        
        // Persist data slider
        thicknessPref = new SeekBarPreference(this);
        
        thicknessPref.setKey(ApplicationPreferences.LINE_THICKNESS_TEXT);
        thicknessPref.setTitle(ApplicationPreferences.LINE_THICKNESS_TEXT);
        thicknessPref.setMax(10);
        
        inlinePrefCat.addPreference(thicknessPref);

        return root;
    }
    
}
