package com.digfinder.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.digfinder.client.DigfinderClient;
import com.digfinder.client.Result_Item;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import com.readystatesoftware.mapviewballoons.*;



public class MapResult extends MapActivity{
	
	Drawable drawable;
	Drawable drawable2;
	MyItemizedOverlay itemizedOverlay;
	MyItemizedOverlay itemizedOverlay2;
	
	
	  private List <NameValuePair> params;
	  private List<GeoPoint> gPoints;
	  private List<Result_Item> results;
	  private int minLat;
	  private int maxLat;
	  private int minLon;
	  private int maxLon;
	  private static final String TAG = "Debug";
	  
	  class MarkerOverlay extends Overlay
	    {
		  	private GeoPoint gPt;
		  	
		  	public MarkerOverlay(GeoPoint point)
		  	{
		  		gPt=point;
		  	}
		  	
	        @Override
	        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
	        {
	            super.draw(canvas, mapView, shadow);                   
	 
	            //---translate the GeoPoint to screen pixels---
	            Point screenPts = new Point();
	            mapView.getProjection().toPixels(gPt, screenPts);
	 
	            //---add the marker---
	            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bluemarker);            
	            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-33, null);         
	            return true;
	        }
	    } 
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_result); 
	    MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        MapController mapController = mapView.getController();
        List<Overlay> mapOverlays = mapView.getOverlays(); 
		params= new ArrayList();
		//////Log.e(TAG, "start mapresult");
	//	params= (ArrayList <NameValuePair>)getIntent().getSerializableExtra("params");
		Bundle b = getIntent().getExtras(); 
		if (b.getString("area")!=null)
		{
			//////Log.e(TAG, "area:" +  b.getString("area"));
			params.add(new BasicNameValuePair("area", b.getString("area")));
			//////Log.e(TAG, "area finish");
		}
		if (b.getString("nh")!=null)
		{
			//////Log.e(TAG, "nh start");
			//////Log.e(TAG, "nh:" +  b.getString("nh"));
			params.add(new BasicNameValuePair("nh", b.getString("nh")));
		}
		if (b.getString("maxAsk")!=null)
		{
			//////Log.e(TAG, "maxAsk:" +  b.getString("maxAsk"));
			params.add(new BasicNameValuePair("maxAsk", b.getString("maxAsk")));
		}
		if (b.getString("bedrooms")!=null)
		{
			//////Log.e(TAG, "bedrooms:" +  b.getString("bedrooms"));
			params.add(new BasicNameValuePair("bedrooms", b.getString("bedrooms")));
		}
		if (b.getString("cats") != null)
		{
			//////Log.e(TAG, "cats:" +  b.getString("cats"));
			params.add(new BasicNameValuePair("cats", b.getString("cats")));
		}
		
		if (b.getString("dogs")!=null)
		{
			//////Log.e(TAG, "dogs:" +  b.getString("dogs"));
			params.add(new BasicNameValuePair("dogs", b.getString("dogs")));
		}
		
		DigfinderClient test= new DigfinderClient();
		//////Log.e(TAG, "started client call");
		results=test.digfinderClient(params);
		//////Log.e(TAG, "returnedclient call");
		if (results.size()==0)
		{
			//////Log.e(TAG,"map result say results =null");
			AlertDialog.Builder alert = new AlertDialog.Builder(MapResult.this);
			alert.setMessage("No results available. Enter new search criteria.")
		       .setCancelable(false)
		       .setTitle("Warning")
		       .setIcon(android.R.drawable.ic_dialog_alert)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		        	   launchSearch();
		           }
		       });
			alert.create().show();
		}
		////////Log.e(TAG, "#results:" + results.size());
		////////Log.e(TAG, "finish");
		////////Log.e(TAG, "name:" + params.get(0).getName());
		////////Log.e(TAG, "value:" + params.get(0).getValue());
		//client call query to get search results based on user input
		//result should be a list of objects containing: address, phone number, listing info etc.
		//assumption for now that result is array of address strings
		
		//Start processing results
		else if (results.size() > 0){
		
		List<Result_Item> mappedResults = new ArrayList<Result_Item>();
		
		List<String> addressesList = new ArrayList<String>();
		int nullCount=0;
		for (int i=0; i<results.size(); i++)
		{
			 //////Log.e(TAG, "result:" + i);
			 
			 
			 if (!(results.get(i).getAddress().getCity()==null || results.get(i).getAddress().getState()==null))
			 { //this is ok,  .getAddress() will never return null
					 //////Log.e(TAG, "into the if");
				    if(results.get(i).getAddress().useIntersection)
				    	addressesList.add(results.get(i).getAddress().getCrossStreetAsString());
				    else
				    	addressesList.add(results.get(i).getAddress().getAddressAsString());
				    //////Log.e(TAG, results.get(i).getAddress().getAddressAsString());
				    
				    mappedResults.add(results.get(i)); //collect Result_Items that are mappable
			 }
		
		
			 else
					nullCount++; //count mappable results
			 
		}
		
		if (nullCount < results.size()) //start of map result display
			 {
				 String [] addressArray = addressesList.toArray(new String[addressesList.size()]);
				 gPoints=convertAddressesToGeoPoints(addressArray);
				 minLat=Integer.MAX_VALUE;
				 maxLat=Integer.MIN_VALUE;
				 minLon=Integer.MAX_VALUE;
				 maxLon=Integer.MIN_VALUE;
		    
			
			drawable = getResources().getDrawable(R.drawable.bluemarker);
	 		itemizedOverlay = new MyItemizedOverlay(drawable, mapView, mappedResults);
			
	 		int i=0; //loop over geoPoints and add markers
		    for (GeoPoint point: gPoints)
		    {
		    	maxLat = Math.max(point.getLatitudeE6(), maxLat);
		        minLat = Math.min(point.getLatitudeE6(), minLat);
		        maxLon = Math.max(point.getLongitudeE6(), maxLon);
		        minLon = Math.min(point.getLongitudeE6(), minLon);
		    //	MarkerOverlay markerOverlay = new MarkerOverlay(point);
		    //	mapOverlays.add(markerOverlay); 
		    	
		      
		    	
		        Result_Item item =  mappedResults.get(i);
		        
		        if(item.getTitle() != null){ //Can get null title when listing still shows up on craigs' rss but has been marked for deletion
		        	String snippetStr = formSnippet(item);	//form pop-up text	            	        
		        	//add pop-up
		        	OverlayItem overlayItem = new OverlayItem(point,item.getTitle(), snippetStr);
		        	itemizedOverlay.addOverlay(overlayItem);
		        	mapOverlays.add(itemizedOverlay);
		        }
		        
	            i++;
		    }   //end of loop over geoPoints
			
				 mapController.zoomToSpan(Math.abs(maxLat - minLat), Math.abs(maxLon - minLon));
				 mapController.animateTo(new GeoPoint( (maxLat + minLat)/2, (maxLon + minLon)/2 )); 
		}//end of map display 
		
			 else{ // no mappable address, show alert
				 AlertDialog.Builder alert = new AlertDialog.Builder(MapResult.this);
					alert.setMessage("No valid addresses. Enter new search criteria.")
				       .setCancelable(false)
				       .setTitle("Warning")
				       .setIcon(android.R.drawable.ic_dialog_alert)
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   dialog.cancel();
				        	   launchSearch();
				           }
				       });
					alert.create().show();
		
				} //end of alert
			
		}//end of processing results
		
		//////Log.e(TAG, "finished results");
		/*
		String [] addresses= {  "1600 Amphitheatre Parkway, Mountain View, CA", 
				  "400 Castro St Mountain View, CA 94041",
				  "635 W Dana St Mountain View, CA 94040",
				  "311 Moffett Blvd Mountain View, CA 94043",
				  "3535 Truman Ave Mountain View, CA 94040",
				  "Mountain View,CA"};
*/
	    
	  
	}
	
	public List<GeoPoint> convertAddressesToGeoPoints(String [] addresses)
	{
		List<GeoPoint> gPoints= new ArrayList<GeoPoint>();
		Geocoder gc= new Geocoder(this);
		double lat;
		double lon;
		
		try {
			for (int i=0; i<addresses.length; i++)
			{
				//////Log.e(TAG, "trying call get from location name");
				List<Address> foundAddresses = gc.getFromLocationName(addresses[i], 1);
				
	              if (foundAddresses.size() == 0) { //if no address found, display an error
	            	  /*
	            	  Dialog locationError = new AlertDialog.Builder(MapResult.this)
	            	  .setIcon(0)
	            	  .setTitle("Error")
	            	  .setPositiveButton("Okay", null)
	            	  .setMessage("Sorry, your address doesn't exist.")
	            	  .create();
	            	  locationError.show();*/
	              }
	            
	              
	            if (foundAddresses.size() >0)
	            {
	            	  //get first address from foundAddresses list
	            	  Address x = foundAddresses.get(0);
	            	  lat =  x.getLatitude();
	            	  lon =  x.getLongitude();
	            	  GeoPoint point = new GeoPoint((int)(lat * 1000000), (int)(lon * 1000000));
	            	  gPoints.add(point);
	            }
			}
				
		}
		catch (IOException e) {
	          //@todo: Show error message
	    }
		return gPoints;
	}
	
	// form text snippet of a Result_Item's address, bedrm, and price if available
	public static String formSnippet(Result_Item item){
		StringBuffer snippetBuf = new StringBuffer("");
        if(item.getAddress().getStreet() != null)
        	snippetBuf.append(item.getAddress().getStreet());
        if(item.getBedrm() != null || item.getPrice() != null){
        	snippetBuf.append("\n");
        	if (item.getBedrm() != null)
        		snippetBuf.append("Bedrm: "+item.getBedrm()+"  ");
        	if (item.getPrice() != null)
        		snippetBuf.append("Price: "+item.getPrice());
        }
        String snippetStr = snippetBuf.toString();
        
        return snippetStr;
		
	}
	
	 protected void launchSearch() {
	    	Intent i = new Intent(this, UserInput.class);
	    	startActivity(i);
	 }
	    /*
	    public HashMap<String, ArrayList<String>> getNeighborhoodsInAreaMap()
		{
			HashMap<String,ArrayList<String>> nhAreaMap= new HashMap<String, ArrayList<String>>();
			try{
				    String [] areas= {"sby","eby","pen","sfc", "nby", "scz"};
				    for (int i=0; i<areas.length; i++)
					{
				    	ArrayList<String> nhArray=  new ArrayList <String>();
				    	Document doc = Jsoup.connect("http://sfbay.craigslist.org/" + areas[i] + "/apa/").get();
				    	Element outerSelect = doc.getElementsByAttributeValue("name", "nh").first();
				    	Elements nhOptions = outerSelect.getElementsByTag("option");
			
				    	for (Element nh : nhOptions) 
				    	{
				    		String innerH = nh.html(); 
				    		nhArray.add(innerH);
				    	}
				    	nhAreaMap.put(areas[i], nhArray);
					}
			}
			catch(IOException e)
			{
				
			}
		   // //////Log.e(TAG, "in scraper array size:"+nhArray.size());
			return nhAreaMap;
		}*/
	

}


