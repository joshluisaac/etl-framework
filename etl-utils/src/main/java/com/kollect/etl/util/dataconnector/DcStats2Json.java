package com.kollect.etl.util.dataconnector;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.PropertiesUtils;

public class DcStats2Json {

	public void dcLogtoJson(String[] args) throws IOException {
		Regex r = new Regex();
		JsonUtils j = new JsonUtils();
		Properties prop = new PropertiesUtils().loadPropertiesFileResource("server.properties");
		String daysAgo = prop.getProperty("email.daysAgo");
		List<TotalLoaded> bodyList = r.getMatchedTokens(args[0], Integer.parseInt(daysAgo));
		j.toJson(bodyList);
	}
}
