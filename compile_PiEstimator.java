

javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs own_wordcount.java

jar -cvf own_wordcount.jar *.class

#start hadoop  if not started

hadoop jar own_wordcount.jar own_wordcount -m 2 -r 2 /user/hduser/input/file /user/hduser/output/result


