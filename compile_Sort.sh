
#hdfs dfs -mkdir /user/hduser/input/file2
hdfs dfs -copyFromLocal -p /home/hduser/input/file  /user/hduser/input/file2
#ihdfs dfs -copyFromLocal -p $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/input2 /user/$USER/input







javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs Sort.java

jar -cvf Sort.jar *.class

#start hadoop  if not started

hadoop jar Sort.jar Sort  /user/hduser/input/file2 /user/hduser/output/result2


