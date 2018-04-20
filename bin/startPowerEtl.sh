################################################################################################################
## ONLY CHANGE THESE VARIABLES IN THIS SECTION
## THE REST OF THE SCRIPT SHOULD WORK FINE WITHOUT NEEDING ANY CHANGE FROM THE USER

## SET THE JAVA_HOME FIRST. THIS SHOULD BE AN ABSOLUTE PATH THE DIRECTORY WHERE JAVA FILES ARE INSTALLED
## THIS SHOULD NOT POINT TO THE 'bin' DIRECTORY
##JAVA_HOME=/usr/java/jdk1.5.0_10
#JAVA_HOME=/usr

## MINIMUM MEMORY TO BE ALLOCATED TO THE JVM, IN MEGABYTES
MIN_MEMORY=4096

## MAXIMUM MEMORY TO BE ALLOCATED TO THE JVM, IN MEGABYTES
MAX_MEMORY=8192

## LOG FILE NAME DATE FORMAT
## THIS IS AN ADVANCE SETTING, PLEASE DON'T PLAY WITH IT UNLESS YOU'RE AN EXPERT IN BASH 'date' COMMAND FORMAT
LOG_FILE_DATE_FORMAT=%Y%m%d%H%M%S

## SET THIS IF YOU WANT TO ENABLE JMX
ENABLE_JMX=true
JMX_PORT=8001

## SET THIS IF YOU WANT TO ENABLE CUSTOM DIFFERENT GC
GC=+UseConcMarkSweepGC

## OTHER JAVA OPTIONS
## USE THIS SECTION TO SPECIFY ANY OTHER JAVA COMMAND LINE OPTIONS, SUCH AS -D
## IF THERE ARE NO OTHER OPTIONS, JUST COMMENT OUT THE LINE BELOW
#OTHER_OPTS=

## END OF CHANGEABLE SETTINGS
## PLEASE DON'T CHANGE ANYTHING AFTER THIS LINE
################################################################################################################


if [ -f $JAVA_HOME/bin/java ]; then
 JAVA=$JAVA_HOME/bin/java
else
 JAVA=`which java`
 if [ $? -ne 0 ]; then
  echo "Unable to find Java, plase ensure JAVA_HOME or path to Java is set in your environment!"
  exit 1
 fi
fi

echo $MIN_MEMORY | grep [^0-9] > /dev/null 2>&1
if [ "$?" -eq "0" ]; then
 echo "MIN_MEMORY value must be a valid number"
 exit 1
fi

echo $MAX_MEMORY | grep [^0-9] > /dev/null 2>&1
if [ "$?" -eq "0" ]; then
 echo "MAX_MEMORY value must be a valid number"
 exit 1
fi

CurDir=`dirname $0`
HomeDir=$CurDir/..

##Change the working location to be the directory where the script is located i.e. ServiceEngine/bin
cd $CurDir

CONFIG=$HomeDir/config
LIB=$HomeDir/lib
LOGS=$HomeDir/logs

CLASSPATH=$CONFIG

#POWER ETL LIBS
CLASSPATH=$CLASSPATH:$LIB/etl-core-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-server-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-dataaccess-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-incubation-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-jdbc-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-reports-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-services-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-tests-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-utils-0.0.1-SNAPSHOT.jar

#SPRING LIBS


#OTHERS

SUFF=`date +"$LOG_FILE_DATE_FORMAT"`
LOG_FILE=$LOGS/PowerEtl$SUFF.log

JAVA_OPTS="-classpath $CLASSPATH"
JAVA_OPTS=$JAVA_OPTS" -Xms"$MIN_MEMORY"m"
JAVA_OPTS=$JAVA_OPTS" -Xmx"$MAX_MEMORY"m"
JAVA_OPTS=$JAVA_OPTS" -Djava.awt.headless=true"
JAVA_OPTS=$JAVA_OPTS" -Djava.io.tmpdir="$LOGS/tmp

nohup $JAVA $JAVA_OPTS com.kollect.etl.Server
