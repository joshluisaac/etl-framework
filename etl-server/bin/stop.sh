echo "Attempting to stop PowerIntegrator"

procid=`cat logs/procid`

ps -p $procid > /dev/null
if [ $? -ne 0 ]; then
  echo "Extractor is not running."
else
  kill -9 $procid > /dev/null
  if [ $? -eq 0 ]; then
    echo "Extractor stopped succesfully!"
  else
    echo "Unable to stop Extractor, please stop manually!"
  fi
fi
