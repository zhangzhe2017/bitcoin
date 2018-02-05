#!/usr/bin/env bash
ulimit -n 110000
#JDK home
JAVA_HOME=$JAVA_HOME

#executing user
RUNNING_USER=root

#APP home
APP_HOME=$(cd ..;pwd)
if [ ${APP_HOME:-4} == "/bin" ]; then
    APP_HOME=$(cd ..;pwd)
    echo $APP_HOME
fi


#main class
APP_MAINCLASS=bitcoin.Application

#classpath
CLASSPATH=$APP_HOME/classes
for i in "$APP_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

CLASSPATH="$CLASSPATH":"$APP_HOME"/conf

#jvm options
JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -Xms1024m -Xmx1024m -Xmn512M -XX:ParallelGCThreads=20 -Djava.awt.headless=true -XX:MaxPermSize=128m -server -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=85 -XX:+DisableExplicitGC -Xnoclassgc -Xverify:none"

#psid
psid=0

checkpid() {
   javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS`

   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}

###################################
#startup
###################################
start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_MAINCLASS already started! (pid=$psid)"
      echo "================================"
   else
      echo -n "Starting $APP_MAINCLASS ..."
      JAVA_CMD="$JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS &"
      su - $RUNNING_USER -c "$JAVA_CMD"
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}

###################################
#stop
###################################
stop() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_MAINCLASS ...(pid=$psid) "
      su - $RUNNING_USER -c "kill -9 $psid"
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Failed]"
      fi

      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_MAINCLASS is not running"
      echo "================================"
   fi
}

###################################
#status
###################################
status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_MAINCLASS is running! (pid=$psid)"
   else
      echo "$APP_MAINCLASS is not running"
   fi
}

###################################
#info
###################################
info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAINCLASS=$APP_MAINCLASS"
   echo "****************************"
}

###################################
#access only 1 argument:{start|stop|restart|status|info}
###################################
case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
esac
exit 0
