javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*  OneStepMatrixMultiplication.java

hdfs dfs -rm -r /user/hduser/output/resultMat
jar -cvf OneStepMatrixMultiplication.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
hadoop jar ${HADOOP_HOME}/programs/OneStepMatrixMultiplication.jar OneStepMatrixMultiplication  /user/hduser/input/mat.txt  /user/hduser/output/resultMat
~                                                                               
~                                                                               
~                                               
