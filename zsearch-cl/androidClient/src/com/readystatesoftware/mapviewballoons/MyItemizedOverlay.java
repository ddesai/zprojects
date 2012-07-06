/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.readystatesoftware.mapviewballoons;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.digfinder.client.DetailView;
import com.digfinder.client.MapResult;
import com.digfinder.client.Result_Item;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	
	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView, List<Result_Item> rItems) {
		super(boundCenter(defaultMarker), mapView, rItems);
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap (Result_Item resultItem){   //(int index, OverlayItem item) {
	//	Toast.makeText(c, "onBalloonTap for overlay index " + index,
	//			Toast.LENGTH_LONG).show();
		
		Intent i = new Intent(c, DetailView.class);
    	Bundle bundle = new Bundle();
    	List<String> photoLinks = resultItem.getPhotoLinks();
    	String[] sArray = photoLinks.toArray(new String[photoLinks.size()]);
    	bundle.putStringArray("imageURLs", sArray );
    	bundle.putString("item_title", resultItem.getTitle());
    	bundle.putString("item_snippet", MapResult.formSnippet(resultItem));
    	bundle.putString("hyperlink", resultItem.getLink());
    	bundle.putString("phone", resultItem.getPhone());
    	bundle.putString("email", resultItem.getEmail());
    	
    	i.putExtras(bundle);
     	c.startActivity(i); 
     	
		return true;
	}
	
}
