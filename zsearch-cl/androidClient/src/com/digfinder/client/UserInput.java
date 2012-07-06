package com.digfinder.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserInput extends Activity {
	private Spinner area_spinner;
	private Spinner nh_spinner;
	private EditText maxPrice_textbox;
	private SeekBar bedrooms_seekbar;
	private CheckBox cats_checkbox;
	private CheckBox dogs_checkbox;
	private Button search_button;
	private TextView nh_textview;
	private HashMap <String, ArrayList<String>> nhAreaMap;
	private HashMap <String, String> areaNameMap;
	private List <String> nh_arraylist;
	private static final String TAG = "Debug";
	TextView seekbar_value;
	
	public class OnAreaSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //Log.e(TAG, "onselect");
        /*
          Toast.makeText(parent.getContext(), "The area is " +
              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();*/
          nh_spinner = (Spinner) findViewById(R.id.nh_spinner);
          nh_textview= (TextView) findViewById(R.id.nh_textview);
    
          
          //params= new ArrayList();
          //Log.e(TAG, parent.getItemAtPosition(pos).toString());
          if (!parent.getItemAtPosition(pos).toString().equals("all areas"))
          {
        	  nh_arraylist= nhAreaMap.get(areaNameMap.get(parent.getItemAtPosition(pos).toString()));
          }
          else{
        	  nh_arraylist= new ArrayList<String>();
        	  nh_arraylist.add("all neighborhoods");
          }
          //Log.e(TAG, "userinputarray size:" + nh_arraylist.size());
          
          
          ArrayAdapter<String> adapter=new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item, nh_arraylist);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          nh_spinner.setAdapter(adapter);
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input);

        areaNameMap= new HashMap<String,String>();
        areaNameMap.put("san francisco", "sfc");
        areaNameMap.put("southbay", "sby");
        areaNameMap.put("eastbay", "eby");
        areaNameMap.put("penninsula", "pen");
        areaNameMap.put("northbay", "nby");
        areaNameMap.put("santa cruz", "scz");
      
        
        //Log.e(TAG, "before setmap");
        DigfinderClient test= new DigfinderClient();
        
        nhAreaMap= test.populateNeighborhoods();
        
        //Log.e(TAG, "set map");
        nh_textview= (TextView) findViewById(R.id.nh_textview);
        nh_spinner = (Spinner) findViewById(R.id.nh_spinner);
        
        area_spinner = (Spinner) findViewById(R.id.area_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.areas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area_spinner.setAdapter(adapter);
        area_spinner.setOnItemSelectedListener(new OnAreaSelectedListener());
        
      //  Toast.makeText(UserInput.this, "Selected", Toast.LENGTH_SHORT).show();
        maxPrice_textbox = (EditText) findViewById(R.id.maxPrice_textbox);
        
        seekbar_value= (TextView) findViewById(R.id.seekbar_value);
        bedrooms_seekbar = (SeekBar) findViewById(R.id.bedrooms_seekbar);
        
        bedrooms_seekbar.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
        	{
        		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        		{
        			Context context = getApplicationContext();/*
        		//	Toast.makeText(context, "seekbar value: " +
        		 //             progress, Toast.LENGTH_LONG).show();
        			// TODO Auto-generated method stub
        			
        			 final int drawableId = R.drawable.one; 
                     final Drawable d = getResources().getDrawable(drawableId); 
                     d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), 
     d.getIntrinsicHeight()));   
                     ShapeDrawable thumb = new ShapeDrawable( new RectShape() );
                     thumb.getPaint().setColor( 0x00FF00 );
                     thumb.setIntrinsicHeight( 80 );
                     thumb.setIntrinsicWidth( 30 );
        			int xPos = ((bedrooms_seekbar.getRight() - bedrooms_seekbar.getLeft()) * bedrooms_seekbar.getProgress()) / bedrooms_seekbar.getMax();
        			if (progress==1)
        				bedrooms_seekbar.setThumb(thumb);
        			if (progress==2)
        				bedrooms_seekbar.setThumb(context.getResources().getDrawable(R.drawable.two));*/
                    seekbar_value.setText(( String.valueOf(progress) + "+").toCharArray() , 0, String.valueOf(progress).length()+1);
                }

                public void onStartTrackingTouch(SeekBar seekBar)
                {
                    // TODO Auto-generated method stub
                }

                public void onStopTrackingTouch(SeekBar seekBar)
                {
                    // TODO Auto-generated method stub
                }
        	});
        cats_checkbox = (CheckBox) findViewById(R.id.cats_checkbox);
        dogs_checkbox = (CheckBox) findViewById(R.id.dogs_checkbox);
       
        
        search_button = (Button)findViewById(R.id.search_button);
        search_button.setOnClickListener( 
        		new OnClickListener(){
        			public void onClick(View viewParam){
        				launchMap();
        			} 
        		}
        ); 
        
    }
    
    
    protected void launchMap() {
    	Intent i = new Intent(this, MapResult.class);
    	Bundle bundle = new Bundle();
    	
    	String areaResult = (String) area_spinner.getSelectedItem();
    	String nhResult= (String) nh_spinner.getSelectedItem();
    	////Log.e(TAG, "middle params");
    	if (areaResult != null && !areaResult.equals("all areas"))
    	{
    		////Log.e(TAG, "area:" + area_spinner.getSelectedItem() );
    		bundle.putString("area", areaNameMap.get((String)area_spinner.getSelectedItem()));
    	}
    	//Log.e(TAG, "first pass");
    	if (nhResult!=null && !nhResult.equals("all neighborhoods"))
    	{
    		////Log.e(TAG, "second if");
    		bundle.putString("nh",(String)nh_spinner.getSelectedItem());	
    	}
    	//Log.e(TAG, "painful debug");
    	if (!maxPrice_textbox.getText().toString().equals("")) //can later check numeric > 0
    	{
    		//Log.e(TAG, "maxAsk from userinput:"+maxPrice_textbox.getText().toString());
    		bundle.putString("maxAsk",maxPrice_textbox.getText().toString());
    		//params.add(new BasicNameValuePair("maxAsk",));
    	}
    	if (bedrooms_seekbar.getProgress()>0)
    	{
    		bundle.putString("bedrooms",Integer.toString(bedrooms_seekbar.getProgress()));
    		
    	}
    	
    	if (cats_checkbox.isChecked())
    	{
    		bundle.putString("addTwo", "purrr");
    	}
    	
    	if (dogs_checkbox.isChecked())
    	{
    		bundle.putString("addThree", "wooof");
    	}
    	////Log.e(TAG, "completed params");
	    //bundle.putSerializable("params", params);
	   // //Log.e(TAG, "put serializable");
	    i.putExtras(bundle);
    	startActivity(i);
    	//Log.e(TAG, "end launch");
    }
}