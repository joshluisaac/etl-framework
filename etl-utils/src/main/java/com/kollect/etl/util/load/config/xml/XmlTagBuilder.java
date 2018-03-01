package com.kollect.etl.util.load.config.xml;

import java.text.MessageFormat;

public class XmlTagBuilder {

  public String generateLoadExecFragment() {
    return "<field cache=\"true\" isexternal=\"false\" iskey=\"false\" isoptional=\"false\">\n"
        + "  <name>load_execution_id</name>\n" + "  <default>_</default>\n" + "  <location end=\"0\" start=\"0\"/>\n"
        + "  <handler config=\"\" name=\"com.profitera.dc.handler.LongHandler\"/>\n" + "  <nulldefinition/>\n"
        + "  <filteron/>\n" + "  <lookup isoptional=\"false\">\n"
        + "   <query>select ID from kvload_execution where #load_execution_id# = null</query>\n" + "   <fullcache/>\n"
        + "   <insert>insert into kvload_execution (ID) values (#ID#)</insert>\n"
        + "   <insertkey>select nextval('kvload_execution_id')</insertkey>\n" + "  </lookup>\n" + " </field>";
  }

  public String generateHeader(final String delimiter, final String tableName, final String generatedKey,
      final String generatedKeySeqName) {
    String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
        + "<load delimiter=\"{0}\" fullcache=\"false\" mode=\"mixed\" padline=\"true\" type=\"del\" verifyfilekey=\"false\">"
        + "<doc></doc>" + "<table>{1}</table>" + "<recordindicatortag/>" + "<xquery/>"
        + "<generatedkey>{2}</generatedkey>" + "<generatedkeyseqname>{3}</generatedkeyseqname>"
        + "<querywhere><select/><update/></querywhere>" + "<post>" + "<insertinsertion/>" + "<insertupdate/>"
        + "<updateinsertion/>" + "<updateupdate/>" + "</post>" + "<header/>" + "<trailer/>"
        + "<refreshdata key=\"false\" timeout=\"-1\"/>";
    return MessageFormat.format(header,
        new Object[] { delimiter.trim(), tableName.trim(), generatedKey.trim(), generatedKeySeqName.trim() });
  }

  public String generateFooter() {
    return MessageFormat.format("</load>", new Object[] {});
  }

  public String generateFieldStartTag(Boolean isExternal, Boolean isKey, Boolean isOptional) {
    return MessageFormat.format("<field isexternal=\"{0}\" iskey=\"{1}\" isoptional=\"{2}\">",
        new Object[] { isExternal, isKey, isOptional }).replaceAll("null", "");
  }

  public String generateNameTag(final String name) {
    return MessageFormat.format("<name>{0}</name>", new Object[] { name.trim().toUpperCase() }).replaceAll("null", "");
  }

  public String generateDefaultTag(String defaultValue) {
    return MessageFormat.format("<default>{0}</default>", new Object[] { defaultValue }).replaceAll("null", "");

  }

  public String generateLocationTag(final String locationStart, final String locationEnd) {
    return MessageFormat.format("<location end=\"{0}\" start=\"{1}\"/>", new Object[] { locationStart, locationEnd })
        .replaceAll("null", "");
  }

  public String generateHandlerTag(String handlerName, String config) {
    String messageFormat = "<handler config=\"{0}\" name=\"{1}\"/>";
    if (handlerName.trim().equals("string")) {
      config = "";
      handlerName = "com.profitera.dc.handler.StringHandler";
    } else if (handlerName.trim().equals("long")) {
      config = "";
      handlerName = "com.profitera.dc.handler.LongHandler";
    } else if (handlerName.trim().equals("date")) {
      handlerName = "com.profitera.dc.handler.DefaultDateHandler";
    } else if (handlerName.trim().equals("int")) {
      config = "";
      handlerName = "com.profitera.dc.handler.IntegerHandler";
    }
    else if (handlerName.trim().equals("bool")) {
      config = "";
      handlerName = "com.profitera.dc.handler.YNBooleanHandler";
    }
    else if (handlerName.trim().equals("currency")) {
      handlerName = "com.profitera.dc.handler.CurrencyHandler";
    }
    else if (handlerName.trim().equals("pass")) {
      config = "";
      handlerName = "com.profitera.dc.handler.PassThroughHandler";
    }
    return MessageFormat.format(messageFormat, new Object[] { config, handlerName.trim() }).replaceAll("null", "");
  }

  public String generateQueryLookUpTag(String lookUpQuery, boolean isLookUp, String lookUpInsert,
      String lookUpInsertKey) {
    if (!lookUpQuery.isEmpty() && !lookUpInsert.isEmpty() && !lookUpInsertKey.isEmpty()) {
      return MessageFormat.format("<lookup><query>{0}</query><insert>{1}</insert><insertkey>{2}</insertkey></lookup>",
          lookUpQuery, lookUpInsert, lookUpInsertKey);
    } else if (!lookUpQuery.isEmpty()) {
      return MessageFormat.format("<lookup><query>{0}</query></lookup>", lookUpQuery);
    } else {
      return MessageFormat.format("<lookup />", "");
    }
  }

  public String generateFieldEndTag() {
    return MessageFormat.format("</field>", new Object[] {}).replaceAll("null", "");
  }

  public void buildXmlBody(StringBuilder sb, String fieldStartTag, String nameTag, String defaultTag,
      String locationTag, String handlerTag, String queryLookUpTag, String fieldEndTag) {
    sb.append(fieldStartTag);
    sb.append(nameTag);
    sb.append(defaultTag);
    sb.append(locationTag);
    sb.append(handlerTag);
    sb.append(queryLookUpTag);
    sb.append(fieldEndTag);
  }

}
