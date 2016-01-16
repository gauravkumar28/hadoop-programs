javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs PiEstimator.java

jar -cvf PiEstimator.jar *.class

#start hadoop  if not started

hadoop jar PiEstimator.jar PiEstimator  10 10 
