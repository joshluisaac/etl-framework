#!/bin/sh


#clean compile
mvn clean compile

#execute main class
mvn exec:java -Dexec.mainClass=com.kollect.etl.util.PropertiesUtils

mvn exec:java -Dexec.mainClass=test.Main -Dexec.args="arg1 arg2 arg3"

mvn clean compile exec:java -Dexec.mainClass=com.kollect.etl.service.email.impl.DataConnectorEmailSender -Dexec.args="/home/joshua/martian/ptrworkspace/etl_implementation_yyc/logs/Server.log arg2 arg3"

mvn clean compile exec:java -Dexec.mainClass=com.kollect.etl.service.email.impl.DataConnectorEmailSender -Dexec.args="/home/joshua/desktop/Server.log arg2 arg3"

mvn clean compile exec:java -Dexec.mainClass=com.kollect.etl.util.FileConcatenator


mvn exec:java -Dexec.mainClass=test.Main -f folder/pom.xml

#execute main class specified in POM file
mvn clean compile exec:java
