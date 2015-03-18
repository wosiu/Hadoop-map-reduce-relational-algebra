# put data into hdfs
hdfs dfs -rm -r /user/mwos/sample-input
hdfs dfs -put sample-input /user/mwos/sample-input
hdfs dfs -rm -r /user/mwos/result-*

# run
hadoop jar target/map-reduce-1.0-SNAPSHOT.jar Sum /user/mwos/sample-input /user/mwos/result-sum/
echo "UNION: "
hdfs dfs -cat /user/mwos/result-sum/part*
