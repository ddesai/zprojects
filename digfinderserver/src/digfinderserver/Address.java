package digfinderserver;


public class Address{
		
		private String street,
			   crossStreet,
			   city,
			   state;
		
		public boolean useIntersection; //"true" if intersection should be used instead of full address
		//(when no street number is provided)
		
		
	public Address(String[] address){
		
		   if(address[0]!=null){
		   
		  	            	       
	       street = address[0];
	       crossStreet = address[1];
	       city = address[2];
	       state = address[3];
	       
	       if (crossStreet==null)
	    	   useIntersection = false;
	       else useIntersection = true;
		   }
		   
		   else {street = null; crossStreet=null;city=null; state=null;}
		   
		  
	        
		}
		
	public String getAddressAsString(){
			
			return (street+", "+city+", "+state);
			
		}
	
	public String getCrossStreetAsString()
	{
		return (crossStreet+", "+city+", "+state);
	}
	
	public String getStreet(){return street;}
	public String getCrossStreet(){return crossStreet;}
	public String getCity() {return city;}
	public String getState() {return state;}
		
	
	/* method to parse scraped address 
	craigslist scrape result has the form: 
	xstreet0=streetName and/or number
	xstreet1=nearest cross street
	city=city name
    region=state                         */
	public static String[] parseAddress(String[] address){
		
		//process address data
	    String[] adrTemp;	
	

	if(address.length == 4){
	 	
		if(address[0]!=null){
		
	    for(int k=0;k<4;k++)
	    	
	    	if(address[k] != null){
	        adrTemp = address[k].split("=");
	        if(adrTemp.length>1)
	        	address[k] = adrTemp[1].trim();
	        else address[k]=null;
	    	
	    }
	    
	   
	    //check that street contains number
	   
	    adrTemp = address[0].split("\\s"); 	  
	    	if (isNumber(adrTemp[0]))
	    			address[1] = null;  //nearest cross street not needed if full address provided
	    
		} 
		
	    
	} else throw new IllegalArgumentException("Argument requires String array of length 4");
        		
		return address;
		
	}
	

	 // Check if given string is digits-only
	  public static boolean isNumber(String string) {
	      return string.matches("^\\d+$");
	  }	
	
		
	}
