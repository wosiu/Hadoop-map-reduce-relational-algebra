import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Sum {

	public static class SumMapper
			extends Mapper<Object, Text, Text, Text> {

		private Text word = new Text();

		public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, word);
			}
		}
	}


	public static class SumCombiner
			extends Reducer<Text, Iterable<Text>, Text, Iterable<Text>> {

		public void reduce(Text key, Iterable<Text> values,
						   Context context
		) throws IOException, InterruptedException {
				List<Text> result = new ArrayList<Text>();
				result.add(key);
				context.write(key, result);
		}
	}

	// can't use as combiner because input != output
	public static class SumReducer
			extends Reducer<Text, Iterable<Text>, Text, Text> {

		public void reduce(Text key, Iterable<Text> values,
						   Context context
		) throws IOException, InterruptedException {
				context.write(key, key);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "Word sum");
		job.setJarByClass(Sum.class);
		job.setMapperClass(SumMapper.class);
		//job.setCombinerClass(SumCombiner.class);
		job.setReducerClass(SumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		Path input = new Path(args[0]);
		Path output = new Path(args[1]);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}