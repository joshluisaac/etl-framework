LOG_FILE_DATE_FORMAT=%Y%m%d%H%M%S
SUFF=`date +"$LOG_FILE_DATE_FORMAT"`
LOG_FILE=logs/SpringApp$SUFF.log

nohup mvn -f etl-server/pom.xml spring-boot:run 1>$LOG_FILE 2>&1 &
