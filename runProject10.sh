javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*:${HADOOP_HOME}/programs/javacv-bin/javacv.jar:${HADOOP_HOME}/programs/javacv-bin/javacpp.jar project10.java  -Xlint:deprecation
export LIBJARS=${HOME}/hadoop2/programs/javacv-bin/javacv.jar,${HOME}/hadoop2/programs/javacv-bin/javacpp.jar,${HOME}/hadoop2/programs/javacv-bin/javacv-linux-x86_64.jar
hdfs dfs -rm -r /user/hduser/output/Result
jar -cvf project10.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
hadoop jar ${HADOOP_HOME}/programs/project10.jar project10 -libjars ${LIBJARS} /user/hduser/input/inputpath.txt  /user/hduser/output/Result
