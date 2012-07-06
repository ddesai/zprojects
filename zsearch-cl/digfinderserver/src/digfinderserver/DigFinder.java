package digfinderserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/")
public class DigFinder {


	// Defines that the next path parameter after todos is
		// treated as a parameter and passed to the TodoResources
		// Allows to type http://localhost:8080/de.vogella.jersey.todo/rest/todos/1
		// 1 will be treaded as parameter todo and passed to TodoResource
		@Path("search/{query}")
		@GET
		//@Produces(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String search( @PathParam("query") String query) {
		
			
			Scraper scraper = new Scraper();
			Gson gson = new Gson();
			String json;
			
			//result of search
			List<Result_Item> result_items = scraper.Search(query);

			//print out search results
			//String searchRes = "" ;
			/*for(int i=0; i<result_items.size(); i++){								
				result_items.get(i).printItem();
				
			}*/
			
		
			
			//convert search results to json as string
			json = gson.toJson(result_items);
			
			
			 //deserialize JSON
	   
			Type collectionType = new TypeToken<List<Result_Item>>(){}.getType();
			List<Result_Item> resultsJSON_d = gson.fromJson(json, collectionType);
			for(int i=0; i<resultsJSON_d.size(); i++)		
				resultsJSON_d.get(i).printItem();
			
						
			return json; 
			
			
		}
	
			
	// This method is called if XML is request
	@Path("search/xml/{query}")
	@GET
	@Produces(MediaType.TEXT_XML)
	public String search1( @PathParam("query") String query) {
	
		
		Scraper scraper = new Scraper();
		
		//result of search
		List<Result_Item> result_items = scraper.Search(query);
	
		XStream xstream = new XStream();
		String xml = xstream.toXML(result_items);
		
		return xml;
	
	}
	
	
	@GET
	@Path("search")
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String search2() {
	
		
		Scraper scraper = new Scraper();
		Gson gson = new Gson();
		String json;
		
		//result of search
		List<Result_Item> result_items = scraper.Search(null);

		//print out search results
		//String searchRes = "" ;
		for(int i=0; i<result_items.size(); i++){
			System.out.println(i);
			result_items.get(i).printItem();
			
		}
		
	
		
		//convert search results to json as string
		json = gson.toJson(result_items);
					
		return json; 
		
		
	}	
	
	@Path("nhoods")
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String nhoods() {
		Scraper scraper = new Scraper();
		Gson gson = new Gson();
		String json;
		
		HashMap<String, ArrayList<String>> nhAreaMap= scraper.getNeighborhoodsInAreaMap();
		json = gson.toJson(nhAreaMap);
		
		//System.out.println("calling nhoods: " + json);
		return json;
	}
 
	@Path("xml/nhoods")
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_XML)
	public String nhoodsXML() {
		
		Scraper scraper = new Scraper();
			
		HashMap<String, ArrayList<String>> nhAreaMap= scraper.getNeighborhoodsInAreaMap();
		XStream xstream = new XStream();
		String xml = xstream.toXML(nhAreaMap);
		
		return xml;
	
	}
	
	

	// This method returns API info
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String api_info() throws java.io.IOException{
		
		String filePath = "http://digfinder.googlecode.com/hg/digfinderserver/API_info.html";
		//String filePath = "API_info.html";	
	   	
			//InputStream fileInStream = getClass().getResourceAsStream("API_info.html");
	    	//URL url = getClass().getResource(filePath);
	    	//File file = new File(url.getPath());
		
		 	URL url = new URL(filePath);  
		 
		    //BufferedReader fileInStream = new BufferedReader(new InputStreamReader(url.openStream()));
		
									    	
	        StringBuffer fileData = new StringBuffer();
	        BufferedReader reader = new BufferedReader(
	        		new InputStreamReader(url.openStream()));
	               // new FileReader(filePath));
	        char[] buf = new char[1024];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	            fileData.append(buf, 0, numRead);
	        }
	        reader.close();
	        
	        String html= fileData.toString();
	     
	        return html;
	        
		    
	}
	
	
	

}

		
	
