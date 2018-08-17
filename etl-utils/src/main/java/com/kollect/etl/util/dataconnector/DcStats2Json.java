package com.kollect.etl.util.dataconnector;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.PropertiesUtils;

public class DcStats2Json {

	public void dcLogtoJson(String[] args) throws IOException {
	  
	    //Inverse the dependencies. Let the caller or the client inject whatever it needs
	  // This should also be moved into DcStats2Json constructor
		Regex r = new Regex();
		JsonUtils j = new JsonUtils();
		Properties prop = new PropertiesUtils().loadPropertiesFileResource("server.properties");
		
		//this should go into the main method
		String daysAgo = prop.getProperty("email.daysAgo");
		List<TotalLoaded> bodyList = r.getMatchedTokens(args[0], Integer.parseInt(daysAgo));
		
		//This should be serialized to a JSON formatted output and written to disk. 
		//PowerETL notification engine will pick up that JSON fie, process it and the send an email using thyme leaf templates
		j.toJson(bodyList);
	}
}
