rm -rf *.class
rm -rf *.jar
rm -f output.txt
hadoop fs -rm -r outputPrimeNumber
javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*  *.java

#hdfs dfs -rm -r /user/hduser/output/resultMat
jar -cvf prime.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
#hadoop jar ${HADOOP_HOME}/programs/prime_number/BlockMatrixMultiplication.jar BlockMatrixMultiplication  /user/hduser/input/mat.txt  /user/hduser/output/resultMat

#javac -classpath /usr/lib/hadoop/hadoop-core.jar *.java
#jar cvf prime.jar *.class
hadoop jar prime.jar PrimeNumberDriver /user/hduser/outputPrimeNumber
hadoop fs -cat '/user/hduser/outputPrimeNumber/part-*' > output.txt
more output.txt
