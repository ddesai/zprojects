package digfinderserver;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;


public class API_tester {
	
	public static void main(String[] args){
		
	Scraper scraper = new Scraper();
	Gson gson = new Gson();
	String json;
	
	//result of search
	List<Result_Item> result_items = scraper.Search("area=sby&nh=santa clara&bedrooms=1");

	//print out search results
/*	for(int i=0; i<result_items.size(); i++)		
		result_items.get(i).printItem(); */
	    
/*	//convert java object to JSON format
	for(int i=0; i<result_items.size(); i++)
	{
		json = gson.toJson(result_items.get(i));
		System.out.println(json);
	}
*/
	
	//serialize to json as string
	json = gson.toJson(result_items);
	//System.out.println(json);
	
	
	//deserialise json string
	Type collectionType = new TypeToken<List<Result_Item>>(){}.getType();
	List<Result_Item> resDe = gson.fromJson(json, collectionType);
	
	for(int i=0; i<resDe.size(); i++)		
		resDe.get(i).printItem();
	
	

	XStream xstream = new XStream();
	String xml = xstream.toXML(result_items);
	System.out.println(xml);
	
	
	
	
	
	
			
	//String[] toobjectString = ""{"address":{"street":"Lick Mill Blvd","crossStreet":"Montague Exp","city":"Santa Clara","state":"CA","useIntersection":true},"title":"Luxury At Miraval**Pride of Owner**Exceptionally Nice!","hyperlink":"http://sfbay.craigslist.org/sby/apa/2507421558.html","email":"hous-fgptx-2507421558@craigslist.org","date":"2011-07-31","price":"$2100","bedrm":" 1br ","phone":"408-985-7089","photos":["http://images.craigslist.org/3ne3p43lc5O25T05W2b7m73c8de3b30e217ae.jpg","http://images.craigslist.org/3nc3m83p75O55Q15S5b7m03c5413cf0b61947.jpg","http://images.craigslist.org/3ma3p03l35Q45U15X0b7m6b4fee8431d812ab.jpg","http://images.craigslist.org/3n93k33o25Y05Z65T3b7m07d0b27e027111d9.jpg"]}""

	
	}
	
}