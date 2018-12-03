package com.kollect.etl.util.load.config.xml;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kollect.etl.util.parser.BooleanParser;
import com.kollect.etl.util.parser.IntegerParser;

public class DcConfigGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(DcConfigGenerator.class);

  List<Object> src;
  
  public DcConfigGenerator(List<Object> src) {
    this.src = src;
  }
  
  public DcXml generate() throws IOException {
    XmlTagBuilder xmlTagBuilder = new XmlTagBuilder();
    IntegerParser intParser = new IntegerParser();
    BooleanParser boolParser = new BooleanParser();
    StringBuilder sb = new StringBuilder();
    List<Object> fileList = this.src;
    
    Map<String, String> listMap = (Map) fileList.get(0);

    String delimiter = listMap.get("delimiter");
    String destinationTable = listMap.get("destinationTable");
    String generatedKey = listMap.get("generatedKey");
    String generatedKeySequence = listMap.get("generatedKeySequence");
    String loadName = listMap.get("fileName") +"_"+ System.currentTimeMillis();
    String dateFormat = listMap.get("dateFormat");

    sb.append(xmlTagBuilder.generateHeader(delimiter, destinationTable, generatedKey, generatedKeySequence));

    for (Object map : fileList) {
      
      Map<String, String> xMap = (Map) map;
      
      String config = "";
      Boolean isExternal = boolParser.parseBoolean(xMap.get("isExternal"));
      Boolean isKey = boolParser.parseBoolean(xMap.get("isKey"));
      Boolean isOptional = boolParser.parseBoolean(xMap.get("isOptional"));
      String name = xMap.get("fieldName");
      String defaultValue = xMap.get("defaultValue");
      String locationStart = xMap.get("locationStart");
      String locationEnd = xMap.get("locationEnd");
      String handlerName = xMap.get("handlerName");
      String lookUpQuery = xMap.get("lookUpQuery");
      String lookUpInsert = xMap.get("lookUpInsert");
      String lookUpInsertKey = xMap.get("lookUpInsertKey");
      Boolean isLookUp = boolParser.parseBoolean(xMap.get("isLookUp"));
      
      if(handlerName.trim().equals("date")) {
        config = xMap.get("dateFormat");
      } else if (handlerName.trim().equals("currency")) {
        config = "0";
      }

      String fieldStartTag = xmlTagBuilder.generateFieldStartTag(isExternal, isKey, isOptional);
      String nameTag = xmlTagBuilder.generateNameTag(name);
      String defaultTag = xmlTagBuilder.generateDefaultTag(defaultValue);
      String locationTag = xmlTagBuilder.generateLocationTag(locationStart, locationEnd);
      String handlerTag = xmlTagBuilder.generateHandlerTag(handlerName, config);
      String queryLookUpTag = xmlTagBuilder.generateQueryLookUpTag(lookUpQuery, isLookUp, lookUpInsert, lookUpInsertKey);
      String fieldEndTag = xmlTagBuilder.generateFieldEndTag();
      xmlTagBuilder.buildXmlBody(sb, fieldStartTag, nameTag, defaultTag, locationTag, handlerTag, queryLookUpTag,
          fieldEndTag);

    }
    sb.append(xmlTagBuilder.generateLoadExecFragment());
    sb.append(xmlTagBuilder.generateFooter());
    
    return new DcXml(loadName + ".xml", sb.toString().getBytes());
  }
  
  
  public static void main(String[] args) throws IOException {
    MockData data = new MockData();
    DcConfigGenerator xmlGen = new DcConfigGenerator(data.mockList());
    xmlGen.generate();
    
  }

}
