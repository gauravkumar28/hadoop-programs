javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*:${HOME}/hadoop2/programs/JCuda-All-0.5.5-bin-linux-x86_64/jcuda-0.5.5.jar:${HOME}/hadoop2/programs/JCuda-All-0.5.5-bin-linux-x86_64/*.jar  OneStepMatrixMultiplicationJCuda.java

hdfs dfs -rm -r /user/hduser/output/resultMat
jar -cvf OneStepMatrixMultiplicationJCuda.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
hadoop jar ${HADOOP_HOME}/programs/OneStepMatrixMultiplicationJCuda.jar OneStepMatrixMultiplication  -libjars ${HADOOP_HOME}/programs/jcuda-0.5.5.jar /user/hduser/input/mat.txt  /user/hduser/output/resultMat
~                                                                             
~                                                                             

