package digfinderserver;
import java.util.List;



public class Result_Item {
	
	Address address;
	String title,
		   hyperlink,
		   email,
		   date,
		   price,
		   bedrm,
		   phone;
    List<String> photos;
    
	
    public Result_Item(Address address, String title, String hyperlink, String email, String date,
    		String price, String bedrm, String phone, List<String> photos){
    	
    	this.address = address;
    	this.title = title;
    	this.hyperlink = hyperlink;
    	this.email = email;
    	this.date = date;
    	this.price = price;
    	this.bedrm = bedrm;
    	this.phone = phone;
    	this.photos = photos;
    }
    
    
    public void printItem(){
    	System.out.println("_______________________________________________");
    	 	System.out.println(title);
    	    System.out.println("price: "+price);
    	    System.out.println("bedrm: "+bedrm);
    	    System.out.println("date: "+date);
    	    System.out.println("email: "+email);
    	    System.out.println("phone: "+phone);
    	    System.out.println("photos: "+ photos);
    	    
    	    if(address != null)
    	    System.out.println("address: "+ address.getAddressAsString());
        
    }
    
    public Address getAddress(){ return address;}
    public List<String> getPhotoLinks(){ return photos;}
    public String getTitle(){return title;}
    public String getLink() {return hyperlink;}
    public String getDate() {return date;}
    public String getPrice() {return price;}
    public String getPhone() {return phone;}
    public String getBedrm() {return bedrm;}
    public String getEmail() {return email;}
    
    
    	   	 
	   	
	
}
