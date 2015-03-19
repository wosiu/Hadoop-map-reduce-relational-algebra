mvn clean install

# put data into hdfs
rm sample-input/*~
hdfs dfs -rm -r -f /user/mwos/sample-input
hdfs dfs -put sample-input /user/mwos/sample-input
hdfs dfs -rm -r -f /user/mwos/result-*


# run
#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Union /user/mwos/sample-input /user/mwos/result-union/
echo "UNION: "
hdfs dfs -cat /user/mwos/result-union/part*

#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Intersection /user/mwos/sample-input /user/mwos/result-intersection/
echo "INTERSECTION: "
hdfs dfs -cat /user/mwos/result-intersection/part*

#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Difference /user/mwos/sample-input /user/mwos/result-difference/
echo "DIFFERENCE: "
hdfs dfs -cat /user/mwos/result-difference/part*

#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar FirstColumnProjection /user/mwos/sample-input /user/mwos/result-projection/
echo "PROJECTION (1st element): "
hdfs dfs -cat /user/mwos/result-projection/part*

#hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Select /user/mwos/sample-input /user/mwos/result-select/
echo "SELECT (1st element == 'cc'): "
hdfs dfs -cat /user/mwos/result-select/part*

hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Join /user/mwos/sample-input /user/mwos/result-join/
echo "JOIN: "
hdfs dfs -cat /user/mwos/result-join/part*

