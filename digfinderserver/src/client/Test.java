package client;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.thoughtworks.xstream.XStream;

import digfinderserver.Result_Item;

public class Test {
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		// Fluent interfaces
		System.out.println(service.path("rest").path("digfinder/area=sby&nh=santa clara&bedrooms=4").accept(
				MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
		
		
		// Get search results as JSON string
		String resultsJSON = service.path("rest").path("digfinder/area=sby&nh=santa clara&bedrooms=4").accept(
				MediaType.APPLICATION_JSON).get(String.class);
					
		System.out.println("\n\n*************************JSON text***************************\n\n");
		System.out.println(resultsJSON);
		
		System.out.println("\n\n**************************OUTPUT FROM READING JSON*****************\n\n");
		//deserialise JSON string
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Result_Item>>(){}.getType();
		List<Result_Item> resultsJSON_d = gson.fromJson(resultsJSON, collectionType);
		for(int i=0; i<resultsJSON_d.size(); i++)		
			resultsJSON_d.get(i).printItem();
		
		
		// Get search result as XML string
		String resultsXML = service.path("rest").path("digfinder/area=sby&nh=santa clara&bedrooms=4").accept(
				MediaType.TEXT_XML).get(String.class);
				
		System.out.println("\n\n*************************************XML text****************************\n\n");
		System.out.println(resultsXML);

		System.out.println("\n\n**********************************OUTPUT FROM READING XML************************\n\n");
		//deserialize XML
		XStream xstream = new XStream();
		@SuppressWarnings("unchecked")
		List<Result_Item> resultsXML_d = (List<Result_Item>)xstream.fromXML(resultsXML);
				for(int i=0; i<resultsXML_d.size(); i++)		
			resultsXML_d.get(i).printItem();
		
		// The HTML
	/*	System.out.println(service.path("rest").path("hello").accept(
				MediaType.TEXT_HTML).get(String.class));
*/
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/digfinderserver").build();
	}

}
