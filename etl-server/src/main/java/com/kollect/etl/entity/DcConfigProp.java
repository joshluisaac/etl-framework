package com.kollect.etl.entity;

//dto - data transfrer object
public class DcConfigProp {

  String destinationTable;
  String tableSequenceName;
  String generatedKey;
  int columnDelimiter;
  int dateFormat;
  int id;
  String sourceFileName;
  String description;
  String descriptorFileName;
  boolean includeLoadExecution;
  boolean delOption;

  public DcConfigProp(String destinationTable, String tableSequenceName, String generatedKey, int columnDelimiter,
      int dateFormat, String sourceFileName, String description, String descriptorFileName, boolean includeLoadExecution, boolean delOption) {
    this.destinationTable = destinationTable;
    this.tableSequenceName = tableSequenceName;
    this.generatedKey = generatedKey;
    this.columnDelimiter = columnDelimiter;
    this.dateFormat = dateFormat;
    this.sourceFileName = sourceFileName;
    this.description = description;
    this.descriptorFileName = descriptorFileName;
    this.includeLoadExecution = includeLoadExecution;
    this.delOption = delOption;
  }
  
  
  public void setId(int id) {
    this.id = id;
  }

}
