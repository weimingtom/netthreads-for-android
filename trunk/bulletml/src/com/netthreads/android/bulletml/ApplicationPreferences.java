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

import jp.gr.java_conf.abagames.bulletml_demo.noiz.Sample;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class ApplicationPreferences 
{
	public static final String NAME = "preferences";
	
	public static final String RANK_TEXT = "Rank";
	public static final int RANK_DEFAULT = 50;

	public static final String LINE_THICKNESS_TEXT = "Line Thickness";
	public static final int LINE_THICKNESS_DEFAULT = 3;
	
	public static final String SAMPLE_TEXT = "Samples";
	public static final int SAMPLE_DEFAULT = 5; // 0==template

	// We use hex as these are bitmaps
	public static final int UNCHANGED = 0x00;
	public static final int CHANGED_SETTINGS = 0x01;
	
	// Preferences 
	private static ApplicationPreferences instance = null;
    private SharedPreferences settings = null;

	/**
	 * Singleton access.
	 * 
	 * @param context
	 * 
	 * @return The preferences object.
	 */
	public static ApplicationPreferences getInstance(Context context)
	{
		if (instance==null)
		{
			instance = new ApplicationPreferences(context);
		}
		
		return instance;
	}

	private ApplicationPreferences(Context context) 
	{
        settings = context.getSharedPreferences(ApplicationPreferences.NAME, Activity.MODE_PRIVATE);
	}

    /**
     * Return Rank
     * 
     * @return Rank value
     */
    public int getRank()
    {
		int value = settings.getInt(RANK_TEXT, RANK_DEFAULT);

		return value;
    }

    /**
     * Set the rank
     *
     * @param The value string
     */
    public void setRank(int value)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(RANK_TEXT, value);
        editor.commit();
    }

    /**
     * Return line thickness
     * 
     * @return value
     */
    public int getLineThickness()
    {
		int value = settings.getInt(LINE_THICKNESS_TEXT, LINE_THICKNESS_DEFAULT);

		return value;
    }

    /**
     * Set the line thickness
     *
     * @param The value
     */
    public void setLineThickness(int value)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LINE_THICKNESS_TEXT, value);
        editor.commit();
    }
    
    /**
     * Return Sample
     * 
     * @return Rank value
     */
    public Sample getSampleData()
    {
        Sample sml = Sample.samples[getSample()];
		
		return sml;
    }
    
    /**
     * Return Sample
     * 
     * @return Rank value
     */
    public int getSample()
    {
		int value = settings.getInt(SAMPLE_TEXT, SAMPLE_DEFAULT);

		return value;
    }
    
    /**
     * Set the sample setting
     *
     * @param The value string
     */
    public void setSample(int value)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SAMPLE_TEXT, value);
        editor.commit();
    } 
    
}
