package digfinderserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;




public class Scraper {
	
	private HashMap <String, String> nhMap;
	 
	public Scraper(){
		nhMap= new HashMap<String, String>();
		createNeighborhoodMap();
		//printnhMap();
	}
	
	
	public List<Result_Item> Search(String query){
		
		
		return scrape(buildUrl(query));
		
	}
	
	
	
	//extract hyperlinks from xml file (input: url of file)
	private List<String> getLinks(String url_str){
		 
		 	
		    List<String> links = new ArrayList<String>();

		    		//get the factory
		    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		    		try {

		    			//Using factory get an instance of document builder
		    			DocumentBuilder db = dbf.newDocumentBuilder();
		    		
		    			//parse using builder to get DOM representation of the XML file
		    			org.w3c.dom.Document dom = db.parse(url_str);
		    			   						    			
		    			//get the root element
		    			org.w3c.dom.Element docEle = dom.getDocumentElement();

			    		//get a nodelist of  elements
			    		NodeList nl = docEle.getElementsByTagName("rdf:li");
		
			    		
			    		if(nl != null && nl.getLength() > 0) {
			    			for(int i = 0 ; i < nl.getLength();i++) {

			    				
			    				org.w3c.dom.Element el = (org.w3c.dom.Element)nl.item(i);
			    				//System.out.println(el.getAttribute("rdf:resource"));
			    				links.add(el.getAttribute("rdf:resource"));

			    				
			    			}
			    		} 
		    		

		    		}catch(ParserConfigurationException pce) {
		    			pce.printStackTrace();
		    		}catch(SAXException se) {
		    			se.printStackTrace();
		    		}catch(IOException ioe) {
		    			ioe.printStackTrace();
		    		}
		    		
		    		
		return links;    	
		    	
	}
	
	
	public List<Result_Item> scrape(String url_str){
		
		
		List links = getLinks(url_str); //get links of listings
		
		List<Result_Item> result_items = new ArrayList<Result_Item>(); //store info of each listing
		
		// patterns of html tags 
		Pattern TITLE_TAG = Pattern.compile(".*<title>(.+)</title>"), 
				ADR_TAG = Pattern.compile(".*<!-- CLTAG(.+)-->"), //address tags
				PRICE_TAG = Pattern.compile("<h2>(.+)</h2>"), //extracted string contains price,bedrm,title,neighborhood
				DATE = Pattern.compile(".*Date:(.+)<br>"),
				EMAIL = Pattern.compile(".*mailto:(.+)\\?subject.+"), 
				PHONE = Pattern.compile(".*(\\D\\d{3}\\D{1,2}\\d{3}\\D{1,2}\\d{4})\\D.*"),
				PHOTOS = Pattern.compile(".*src=\"(.+\\..{3})\".*");

		
		//extract each link
		for (int i=0; i<links.size(); i++){
			
		//String content = null;
		String title=null,
				price=null,
				bedrm=null, 
				date=null, 
				email=null, 
				phone=null;
		
		List<String> photos= new ArrayList<String>();
		
		String[] address = new String[4]; //holds street, crossStreet, city, state
	
		String hyperlink = (String) links.get(i);
		
		//StringBuffer buffer = new StringBuffer();

    
    try {
    	URL url = new URL(hyperlink);                        
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        
        int j=0;
        Matcher matcher = null;
        while ((line = reader.readLine()) != null) {
        	
        	
        	//match address
        	if (line.contains("CLTAG")) {        		
          		matcher = ADR_TAG.matcher(line);
                if (matcher.matches()) {
                       	//System.out.println("address: " + matcher.group(1));
                	if (j<4)
                	address[j++]=matcher.group(1);   	                	
                } 
        	}
        	
        	//match title
        	if (line.contains("title")){
        		 matcher = TITLE_TAG.matcher(line);
        		 if(matcher.matches()) 
        			 title = matcher.group(1);
        		 
        	}
        	
        	
        	//match price, bedroom#
        	if (line.contains("h2")){
        		//String matchResult = null;
        		matcher = PRICE_TAG.matcher(line);
        		if(matcher.matches()) {
        			//String[] matchResult = SPLITTER.split(matcher.group(1));
        			String[] matchResult = matcher.group(1).split("[/-]");
        			if(matchResult[0].contains("$"))
        				price = matchResult[0].split("\\s")[0];
        			else if(matchResult[0].contains("br"))
    						bedrm = matchResult[0];
        				
        			if(matchResult.length > 1)
        				if(matchResult[1].contains("br"))
        					bedrm = matchResult[1];
        			        			        			
        		}
        		
        		}
   		       	
        
        	//match listing date
        	if (line.contains("Date")){
        	matcher = DATE.matcher(line);
        	if(matcher.matches()){        		        	
        		date = matcher.group(1);
        		//date = COMMA_SPLIT.split(date)[0].trim();
        		date = date.split(",")[0].trim();
        		
        	}
        	}
        	
        
        	//match email
        	if (line.contains("mailto")){
        	matcher = EMAIL.matcher(line);
        	if(matcher.matches())
        		email = matcher.group(1);
        	}
        	
        	//match photos (links)
        	if (line.contains("image")){
        		matcher = PHOTOS.matcher(line);
        		
        	if(matcher.matches())
          		photos.add(matcher.group(1));
        	}
        	
        	//match phone
        	matcher = PHONE.matcher(line);
        	if(matcher.matches())
        		phone = matcher.group(1).trim();
        		
        }
        
        
    }
    catch (IOException e) {
    	e.printStackTrace();
    }
  
    
    Address addressOBJ = new Address(Address.parseAddress(address));
    
    Result_Item resultItem = new Result_Item(addressOBJ, title, hyperlink, email, date,
    		price, bedrm, phone, photos);
   
    
//    resultItem.printItem(); //Print info for each listing
    
    result_items.add(resultItem); 
         
    
		}//end for-loop  
    

return result_items;		

}//end method Scrape()


	
	

	public HashMap getNeighborhoodMap()
	{
		return nhMap;
	}
	
	private void createNeighborhoodMap() 
	{
		try{
			String [] areas= {"sby","eby","pen","sfc", "nby", "scz"};
			for (int i=0; i<areas.length; i++)
			{	
				Document doc = Jsoup.connect("http://sfbay.craigslist.org/" + areas[i] + "/apa/").get();
				Element outerSelect = doc.getElementsByAttributeValue("name", "nh").first();
				Elements nhOptions = outerSelect.getElementsByTag("option");
			
				for (Element nh : nhOptions) 
				{
					String outerH = nh.outerHtml();
					String innerH = nh.html(); 
					int startIndex= outerH.indexOf('"');
					int endIndex= outerH.indexOf('"',startIndex+1);
					//System.out.println(outerH);
				
					if(startIndex+1 != endIndex)
					{
						//System.out.println(startIndex);
						//System.out.println(endIndex);
					 	//System.out.println(innerH);
						String number= outerH.substring(startIndex+1, endIndex);
						//System.out.println(number);
						nhMap.put(innerH, number);
					}
				}
			}
		}
		catch(IOException e)
		{
			
		}
	}
	
	
	//expecting format "area=South Bay&nh=Santa Clara&bedrooms=1"
		public HashMap <String, String> createArgumentMap(String arg)
		{
			HashMap <String, String> argsMap = new HashMap();
			String [] tokens = arg.split("&");
			for (int i = 0; i < tokens.length; i++)
			{
			    System.out.println("HashMap: " + tokens[i]);
			    String [] pair = tokens[i].split("=");
			    if(pair[0].equals("nh"))
			    {
                    if(pair[1].indexOf('+') != -1) {
                        pair[1] = pair[1].replace('+', ' ');
                        System.out.println("Replaced :" + pair[1]);
                    }
                argsMap.put(pair[0], nhMap.get(pair[1]));
            }
			    	
			    else
			    	argsMap.put(pair[0], pair[1]);
			}
			return argsMap;
		}

		public String buildUrl (String arg)
		{
			
			String url= "http://sfbay.craigslist.org/search/apa/";
			
			
			if(arg == null){
			url = "http://sfbay.craigslist.org/apa/index.rss";
			System.out.println("no args");}
			
			
			else{
				
				HashMap <String, String> argsMap = createArgumentMap(arg);
				
			if (argsMap.containsKey("area"))
			{
				url = url+argsMap.get("area");
				//System.out.println(url);
				
			}
			if (argsMap.size()>1)
			{
				url=url+ "?query=";
				Iterator it = argsMap.entrySet().iterator();
			    while (it.hasNext()) {
			    	Map.Entry<String,String> entry = (Map.Entry<String, String>) it.next();
			        //System.out.println(entry.getKey() + "	" + entry.getValue());
			    	if(!entry.getKey().equals("area"))
			    		url= url + "&"+entry.getKey() + "=" + entry.getValue();
			    }
			}
			
			url = url + "&format=rss";
			}
			
			System.out.println(url);
			return url;
			
		}
	
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
		   // Log.e(TAG, "in scraper array size:"+nhArray.size());
			return nhAreaMap;
		}
	 
	public void printnhMap()
	{
		Iterator it = nhMap.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry<String,String> entry = (Map.Entry<String, String>) it.next();
	      System.out.println("PrintMap: " + entry.getKey() + "	" + entry.getValue());
	    }
	}
	
	

	

		
}
	

