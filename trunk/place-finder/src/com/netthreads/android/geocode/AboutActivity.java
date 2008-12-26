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
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * About view with version number.
 * 
 */
public class AboutActivity extends Activity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        
        try 
        {
			PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
			
			String version = packageInfo.versionName;
			
			TextView textView = (TextView)this.findViewById(R.id.app_version);
			
			textView.setText(version);
		} 
        catch (NameNotFoundException e) 
        {
			Log.e("AboutActivity", "Package not found!");
		} 
    }
}

