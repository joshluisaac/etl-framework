package com.kollect.etl.util.dctemporary;


import com.google.gson.*;
import com.google.gson.stream.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Convertor {
    /**
     * Convert a result set into a JSON Array
     * @param resultSet
     * @return a JSONArray
     * @throws Exception
     */
	
	private static final Gson gson = new Gson();
    public static void convertToJSON(JsonWriter json,ResultSet resultSet)
            throws IOException {     		
    		try {
    				
    	            ResultSetMetaData resMeta = resultSet.getMetaData();
    	            int columnCount = resMeta.getColumnCount();
    	
    	            json.beginArray();
    	            while (resultSet.next()) {
    	                json.beginObject();
    	                for (int i = 1; i <= columnCount; i++) {
    	                	json.name(resMeta.getColumnName(i));
    	                	Class<?> type = Class.forName(resMeta.getColumnClassName(i));
    	                	//System.out.println("classs name: "+resMeta.getColumnClassName(i));
    	                	//System.out.println("data is: "+ resMeta.getColumnName(i)+" and: "+resultSet.getObject(i));
    	                    gson.toJson(resultSet.getObject(i), type, json);
    	                    //writer.value(rs.getString(i));
    	                }
    	                json.endObject();
    	            }
    	            json.endArray();
            } catch (SQLException e) {
                throw new RuntimeException(e.getClass().getName(), e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getClass().getName(), e);
            }
    }
}
    