javac -cp ${HADOOP_HOME}/share/hadoop/common/*:${HADOOP_HOME}/share/hadoop/mapreduce/*:${HADOOP_HOME}/share/hadoop/common/lib/*:${HADOOP_HOME}/share/hadoop/hdfs/*:${HADOOP_HOME}/share/hadoop/tools/*:${HADOOP_HOME}/share/hadoop/yarn/*:${HADOOP_HOME}/share/hadoop/httpfs/*  *.java

#hdfs dfs -rm -r /user/hduser/output/resultMat
#jar -cvf BlockMatrixMultiplication.jar *.class
#hdfs dfs -rm -r /user/gauravk/output/SequencialFile
#hdfs dfs -mkdir /user/gauravk/output/SequencialFile
#hadoop jar my_jar.jar classname -libjars JTS.jar inputFiles outputFiles
#hadoop jar ${HADOOP_HOME}/programs/BlockMatrixMultiplication.jar BlockMatrixMultiplication  /user/hduser/input/mat.txt  /user/hduser/output/resultMat
#hadoop fs -cat '/user/hduser/output/resultMat/part-r-00000' > output.txt
#javac -classpath $HADOOP_HOME/hadoop-core.jar *.java
rm -f algorithm.jar
jar -cvf algorithm.jar *.class

rm -f output*.txt

hadoop fs -rmr '/user/hduser/outputAlgorithm*'
hadoop jar algorithm.jar AlgorithmDriver input/shortest_path.txt outputAlgorithm
rm -f output.txt
hadoop fs -cat '/user/hduser/outputAlgorithm/part-*' > output.txt
more output.txt

<<COMMENT
maximumIterations=6
next=1;
for ((  i = 1 ;  i < $maximumIterations;  i++  ))
do
  next=$((next + 1))
  echo "MapReduce Run: $next"
  hadoop fs -rmr inputAlgorithm/shortest_path_$i.txt
  hadoop fs -put output_$i.txt inputAlgorithm/shortest_path_$i.txt
  hadoop fs -rmr outputAlgorithm
  hadoop jar algorithm.jar AlgorithmDriver inputAlgorithm/shortest_path_$i.txt outputAlgorithm
  rm -f output_$next.txt
  hadoop fs -cat 'outputAlgorithm/part-*' > output_$next.txt
  more output_$next.txt
done

echo "Intermediate Output Files"
for ((  i = 1 ;  i <= $maximumIterations;  i++  ))
do
  ls -al output_$i.txt
  more output_$i.txt
done
COMMENT
