package com.digfinder.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailView extends Activity {
   private Gallery gallery;
   private ImageView imgView;
   private Bitmap bmImages[];
   private TextView item_title;
   private TextView item_snippet;
   private ImageView dial_button,
   					 email_button;
   private TextView phoneView,
   					emailView;
   
    


   @Override

   public void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);

       setContentView(R.layout.detail_view2);
       
       
       Bundle b = getIntent().getExtras(); 
       
       String[] imageURLs = b.getStringArray("imageURLs");
       final String item_title_s = b.getString("item_title");
       String item_snippet_s = b.getString("item_snippet");
       final String hyperlink = b.getString("hyperlink");
       final String phone = b.getString("phone");
       final String email = b.getString("email");
       
       
       
                        
       
       imgView = (ImageView)findViewById(R.id.ImageView01);

     //  imgView.setImageResource(Imgid[0]);
       
   /*    
       DownloadImagesTask task1 = new DownloadImagesTask();
       task1.setImageId(R.id.ImageView01);
       task1.execute("http://farm3.static.flickr.com/2772/5803226991_bc39d3060c_z.jpg");
      
     */  
    /*   String[] imageURLs0 = {"http://farm3.static.flickr.com/2772/5803226991_bc39d3060c_z.jpg",
    		   "http://farm6.static.flickr.com/5085/5312055642_1acd7cc5e5_m.jpg"
    		 // , "http://farm6.static.flickr.com/5053/5397828507_26403a94d8_m.jpg"
       };
    */   
       
       
   /*    String[] imageURLs ={
       "http://images.craigslist.org/3k33mc3pd5O05W65P0b8175783cae0b3a18bf.jpg",
       " http://images.craigslist.org/3m63p23of5O55P25S0b81e7817e4229051ca2.jpg",
       " http://images.craigslist.org/3n13k63of5Y55T05P0b8157975bb7b991185c.jpg",
       "http://images.craigslist.org/3p33o13lf5V35Q15U5b811f0e52f751a5165d.jpg" };
	*/

       
       item_title = (TextView) findViewById(R.id.item_title);
       item_title.setText(item_title_s);
       item_title.setClickable(true);
       item_title.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				launchURL(hyperlink);
			}
		});
       
       item_snippet = (TextView) findViewById(R.id.item_snippet);
       item_snippet.setText(item_snippet_s);
       
       if(phone != null){
       phoneView = (TextView) findViewById(R.id.phone);
       phoneView.setText(phone);
       
       phoneView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dial(phone);
			}
		});
       
     /*  dial_button = (Drawable) findViewById(R.id.dial_button);
       dial_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dial(phone);
			}
		}); */
       }
       
       if(email!=null){
       emailView = (TextView) findViewById(R.id.email);
       emailView.setText(email);
       emailView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendEmail(email,item_title_s, hyperlink);
			}
		});
     /*  email_button = (ImageView) findViewById(R.id.email_button);
       email_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendEmail(email,"subj","body");
			}
		}); */
       }
       
       
       
       bmImages = downloadImages(imageURLs);
       
     //set bmImages to "no photos available"
       if (imageURLs.length == 0){
    	   Log.e("no image", item_title_s);
    	   imgView.setImageResource(R.drawable.no_photos);  	   
       	}
       else  	  
    	   imgView.setImageBitmap(bmImages[0]);
       
       
   
       
       
 
        gallery = (Gallery) findViewById(R.id.examplegallery);

        gallery.setAdapter(new AddImgAdp(this));


        gallery.setOnItemClickListener(new OnItemClickListener() {

           public void onItemClick(AdapterView parent, View v, int position, long id) {

               imgView.setImageBitmap(bmImages[position]);
               

           }

       });


   }


   public class AddImgAdp extends BaseAdapter {

       int GalItemBg;

       private Context cont;


       public AddImgAdp(Context c) {

           cont = c;

           TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);

           GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);

           typArray.recycle();

       }


       public int getCount() {

           return bmImages.length;

       }


       public Object getItem(int position) {

           return position;

       }


       public long getItemId(int position) {

           return position;

       }


       public View getView(int position, View convertView, ViewGroup parent) {

           ImageView imgView = new ImageView(cont);


           imgView.setImageBitmap(bmImages[position]);

           imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));

           imgView.setScaleType(ImageView.ScaleType.FIT_XY);

           imgView.setBackgroundResource(GalItemBg);


           return imgView;

       }

   }
   
   class DownloadImagesTask extends AsyncTask<String, Integer, Bitmap[]> {

	   private int imageViewID;

	       protected void onPostExecute(Bitmap[] bitmaps) {
	    	   ImageView  img = (ImageView) findViewById(imageViewID);
	    	   //img.setImageBitmap(bitmap1);
	    	   img.setImageBitmap(bitmaps[0]);
	   }

	       public void setImageId(int imageViewID) {
	           this.imageViewID = imageViewID;
	       }

	       @Override
	       protected Bitmap[] doInBackground(String... url) {
	           Bitmap[] bitmaps = downloadImages(url);
	          // bmImages = bitmaps;
	           return bitmaps;
	       }
	   }
   
   
   Bitmap[] downloadImages(String[] fileUrl){
	   URL myFileUrl =null; 
	   Bitmap[] bmImg = new Bitmap[fileUrl.length];
	 
	   for(int i=0; i<fileUrl.length;i++){	   	   
		
	   //bmImg[i] = null;
	   
	   try {
	   myFileUrl= new URL(fileUrl[i]);
	   } catch (MalformedURLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	   }
	   try {
	   HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
	   conn.setDoInput(true);
	   conn.connect();
	  
	   InputStream is = conn.getInputStream();

	   bmImg[i] = BitmapFactory.decodeStream(is);
	   
	 //  imView.setImageBitmap(bmImg);
	   } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	   }
	   
	   }
	   return bmImg;
	   }

   public void launchURL(String url){
	   Uri uriUrl = Uri.parse(url); 
	   Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);  
	   startActivity(launchBrowser);  
   }
   
   public void dial(String phoneNum){
	   Intent callIntent = new Intent(Intent.ACTION_CALL);
       callIntent.setData(Uri.parse("tel:"+phoneNum));
       startActivity(callIntent);
   }
   
   public String sendEmail(String email, String subj, String body) {
   	Intent i = new Intent(Intent.ACTION_SEND);
   	i.setType("text/plain");
   	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
   	i.putExtra(Intent.EXTRA_SUBJECT, "Re: "+subj);
   	i.putExtra(Intent.EXTRA_TEXT   , body);
   	try {
   	    startActivity(Intent.createChooser(i, "Send mail..."));
   	    return "success";
   	} catch (ActivityNotFoundException e) {
   	    Log.e("There are no email clients installed.", "email failed",e);
   	    return "Fail";
   	}
   }
   

}