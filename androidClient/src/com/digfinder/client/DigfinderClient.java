package com.digfinder.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;



public class DigfinderClient extends Activity {
    /** Called when the activity is first created. */
	
	String result="";
	static String tag = "Your Logcat tag: ";
	TextView t;
	EditText e;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
   /* 
    public void gotoSu(View v) {
    	e = (EditText)findViewById(R.id.editText1);   	
    	t = (TextView)findViewById(R.id.textView1);
    	//String s2 = WSClient(e.getText().toString());
    	//String s2 = digfinderClient(e.getText().toString());
    	//String s2 = HelloClient(e.getText().toString());
    	//String s2 = makePhoneCall(e.getText().toString());
    	//String s2 = sendEmail(e.getText().toString());
    	//t.setText(s2);
    }

    public String sendEmail(String s1) {
    	Intent i = new Intent(Intent.ACTION_SEND);
    	i.setType("text/plain");
    	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{s1});
    	i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
    	i.putExtra(Intent.EXTRA_TEXT   , "body of email");
    	try {
    	    startActivity(Intent.createChooser(i, "Send mail..."));
    	    return "success";
    	} catch (ActivityNotFoundException e) {
    	    //Log.e("There are no email clients installed.", "email failed",e);
    	    return "Fail";
    	}
    }
    
    public String makePhoneCall(String s1) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+s1));
            startActivity(callIntent);
            return "Success";
        } catch (ActivityNotFoundException e) {
            //Log.e("helloandroid dialing example", "Call failed", e);
            return "Fail";
        }
    }
    public List<Result_Item> WSClient(List<NameValuePair> params) {
    	HttpClient httpclient = new DefaultHttpClient();  
        HttpGet request = new HttpGet(formUrl(params));
        InputStream content = null;
        BufferedReader in = null;
        String page=null;
        HttpResponse response;
        //ResponseHandler<String> handler = new BasicResponseHandler();
        RequestLine rl = request.getRequestLine();
        //Log.i(tag, "Request Line: " + rl.getUri());
        try {  
            response = httpclient.execute(request);  
            content = response.getEntity().getContent();
            in = new BufferedReader(new InputStreamReader(content), 8192);
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
                System.out.println(line);
            }
            in.close();
            page = sb.toString();
            System.out.println("Now I will print the page");
            System.out.println(page);
        } catch (ClientProtocolException e) {
        	//Log.i(tag,"Problem");
            e.printStackTrace();  
        } catch (IOException e) {
        	//Log.i(tag,"Problem");        	
            e.printStackTrace();  
        }  
        httpclient.getConnectionManager().shutdown(); 
        
        
        //deserialize JSON
        Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Result_Item>>(){}.getType();
		List<Result_Item> resultsJSON_d = gson.fromJson(page, collectionType);
	/*	for(int i=0; i<resultsJSON_d.size(); i++)
			if(resultsJSON_d.get(i) != null)
				resultsJSON_d.get(i).printItem();
     */   
        
  /*    //deserialize XML
      		XStream xstream = new XStream();
      		@SuppressWarnings("unchecked")
      		List<Result_Item> resultsXML_d = (List<Result_Item>)xstream.fromXML(page);
      				for(int i=0; i<resultsXML_d.size(); i++)		
      			resultsXML_d.get(i).printItem();
        
    
        //Log.i(tag, page);
        System.out.println("You should see the page by now");
        return resultsJSON_d;
        }
*/
    public List<Result_Item> digfinderClient(List<NameValuePair> params) {
    	//Log.e(tag, "in the client");
    	HttpClient httpclient = new DefaultHttpClient();  
        HttpGet request = new HttpGet(formUrl(params));
        String result=null;
        ResponseHandler<String> handler = new BasicResponseHandler();
        RequestLine rl = request.getRequestLine();
        List<Result_Item> resultsJSON_d = new ArrayList();
        //Log.i(tag, "Request Line: " + rl.getUri());
        try {  
        	for (NameValuePair param: params)
        	{
        		//Log.e(tag, param.getName() + " " + param.getValue());
        	}
            result = httpclient.execute(request, handler);  
            System.out.println("Result should be here: "+result+"Do you see it");
        } catch (ClientProtocolException e) {
        	//Log.i(tag,"Problem");
            e.printStackTrace();  
        } catch (IOException e) {
        	//Log.i(tag,"Problem");        	
            e.printStackTrace();  
        }  
        httpclient.getConnectionManager().shutdown();  
        //deserialize JSON
        Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Result_Item>>(){}.getType();
		//Log.e(tag,"result:" + result);
		if (result!=null)
		{
			//Log.e(tag,"into the else");
		resultsJSON_d = gson.fromJson(result, collectionType);
		//Log.e(tag,"results size: " + resultsJSON_d.size());
		for(int i=0; i<resultsJSON_d.size(); i++)
		{
			//Log.e(tag,"in the look");
			/*if(resultsJSON_d.get(i) != null)
				resultsJSON_d.get(i).printItem();*/
			/*print func strangely not working here when it works on the server-side
			 * for the case of all elements of a Result_Item being null
			 */
			
		}
        //Log.i(tag, result);
        //Log.e(tag,"about to return client");
		}
    	return resultsJSON_d;
    }
    
    public HashMap<String, ArrayList<String>> populateNeighborhoods() {
    	HttpClient httpclient = new DefaultHttpClient();  
        HttpGet request = new HttpGet( getBaseURI() + "nhoods");
        String result=null;
        ResponseHandler<String> handler = new BasicResponseHandler();
        RequestLine rl = request.getRequestLine();
        //Log.e(tag, "Request Line: " + rl.getUri());
        try {  
            result = httpclient.execute(request, handler);  
            //Log.e(tag, result);
            System.out.println("Result should be here: "+result+"Do you see it");
        } catch (ClientProtocolException e) {
        	//Log.i(tag,"Problem");
            e.printStackTrace();  
        } catch (IOException e) {
        	//Log.i(tag,"Problem");        	
            e.printStackTrace();  
        }  
        httpclient.getConnectionManager().shutdown();  
        //deserialize JSON
        Gson gson = new Gson();
		Type collectionType = new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType();
		HashMap<String, ArrayList<String>> resultsJSON_d = gson.fromJson(result, collectionType);
		     
        ////Log.i(tag, result);
    	return resultsJSON_d;
    }
/*
    public String HelloClient(String s1) {
    	HttpClient httpclient = new DefaultHttpClient();
    	List<NameValuePair> params = new LinkedList<NameValuePair>();
    	params.add(new BasicNameValuePair("name",s1));
    	String paramString = URLEncodedUtils.format(params, "utf-8");
    	String finalUrl = getHelloBaseURI() + paramString;
        HttpGet request = new HttpGet(finalUrl);
        String result=null;
        ResponseHandler<String> handler = new BasicResponseHandler();
        RequestLine rl = request.getRequestLine();
        //Log.i(tag, "Request Line: " + rl.getUri());
        try {  
            result = httpclient.execute(request, handler);  
            System.out.println(result);
        } catch (ClientProtocolException e) {
        	//Log.i(tag,"Problem");
            e.printStackTrace();  
        } catch (IOException e) {
        	//Log.i(tag,"Problem");        	
            e.printStackTrace();  
        }  
        httpclient.getConnectionManager().shutdown();  
        //Log.i(tag, result);
    	return result;
    }*/
     
    private static String formUrl(List<NameValuePair> params) {
    	String url = getBaseURI();
    	//List<NameValuePair> params = new LinkedList<NameValuePair>();

    	/*
    	params.add(new BasicNameValuePair("area","sby"));
    	params.add(new BasicNameValuePair("nh","santa clara"));
    	params.add(new BasicNameValuePair("bedrooms","4"));
    	*/
        String paramString = URLEncodedUtils.format(params, "utf-8");
        
        url += "search/" + paramString;
        
        //Log.e(tag, "url: " + url);
        return url;
    }

	private static String getBaseURI() {
	    return "http://10.0.2.2:8080/digfinderserver/";
		//return "http://10.0.2.2:8080/digfinderserver1/rest/hello/";
		//return "http://localhost:8080/digfinderserver1/rest/hello/darshan";
	}
/*
	private static String getHelloBaseURI() {
		return "http://10.0.2.2:8080/digfinderserver/";
	}
*/
}

