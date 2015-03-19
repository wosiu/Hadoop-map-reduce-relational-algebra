mvn clean install

# put data into hdfs
rm sample-input/*~
hdfs dfs -rm -r -f /user/mwos/sample-input
hdfs dfs -put sample-input /user/mwos/sample-input
hdfs dfs -rm -r -f /user/mwos/result-*


# run
#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Union /user/mwos/sample-input /user/mwos/result-union/
echo "UNION: "
#hdfs dfs -cat /user/mwos/result-union/part*

hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Intersection /user/mwos/sample-input /user/mwos/result-intersection/
#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar WordCount2 /user/mwos/sample-input /user/mwos/result-intersection/
echo "INTERSECTION: "
hdfs dfs -cat /user/mwos/result-intersection/part*

