#javac createMatrixDataFile.java
#java createMatrixDataFile $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/input1 A 8 8 4
#java createMatrixDataFile $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/input2 B 8 8 4

#hdfs dfs -mkdir input
#hdfs dfs -copyFromLocal -p $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/input1 /user/$USER/input
#hdfs dfs -copyFromLocal -p $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/input2 /user/$USER/input

#javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs -d $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/blockMatMul hadoopBlkMatMatMul.java -Xlint:deprecation

#jar -cvf hadoopBlkMatMatMul.jar *.class

#hadoop jar $HOME/hadoop/hadoop-programs/blockMatrixMul_Hadoop/blockMatMul/hadoopBlkMatMatMul.jar hadoopBlkMatMatMul /user/$USER/input /user/$USER/output 8 8 4

javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs own_wordcount.java

jar -cvf own_wordcount.jar *.class
hadoop jar ${HADOOP_HOME}/programs/own_wordcount.jar own_wordcount /user/hduser/input/file /user/hduser/output/result100
#start hadoop  if not started





