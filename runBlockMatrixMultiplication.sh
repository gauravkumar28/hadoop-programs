javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*  BlockMatrixMultiplication.java

hdfs dfs -rm -r /user/hduser/output/resultMat
jar -cvf BlockMatrixMultiplication.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
hadoop jar ${HADOOP_HOME}/programs/BlockMatrixMultiplication.jar BlockMatrixMultiplication  /user/hduser/input/mat.txt  /user/hduser/output/resultMat
hadoop fs -cat '/user/hduser/output/resultMat/part-r-00000' > output.txt
