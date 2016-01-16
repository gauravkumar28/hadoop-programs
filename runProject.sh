javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*:${HADOOP_HOME}/programs/javacv-bin/javacv.jar:${HADOOP_HOME}/programs/javacv-bin/javacpp.jar project.java  -Xlint:deprecation
export LIBJARS=${HOME}/hadoop2/programs/javacv-bin/javacv.jar,${HOME}/hadoop2/programs/javacv-bin/javacpp.jar,${HOME}/hadoop2/programs/javacv-bin/javacv-linux-x86_64.jar
hdfs dfs -rm -r /user/hduser/output/SequencialFile/Result
jar -cvf project.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
hadoop jar ${HADOOP_HOME}/programs/project.jar project -libjars ${LIBJARS} /user/hduser/output/SequencialFile/part-r-00000  /user/hduser/output/SequencialFile/Result
