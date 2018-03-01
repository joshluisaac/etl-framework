@echo off

title %TITLE%



set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;..\config
set CLASSPATH=%CLASSPATH%;..\lib\dataextractor.jar
set CLASSPATH=%CLASSPATH%;..\lib\etlutil.jar
set CLASSPATH=%CLASSPATH%;..\lib\slf4j-api-1.7.5.jar
set CLASSPATH=%CLASSPATH%;..\lib\slf4j-log4j12-1.7.5.jar
set CLASSPATH=%CLASSPATH%;..\lib\log4j-1.2.17.jar


java -classpath %CLASSPATH% com.kollect.etl.util.DiffResolver