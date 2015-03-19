import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Union {

	private static Text emptyWord = new Text("");
	public static class UnionMapper
			extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {
			context.write(value, emptyWord);
		}
	}

	// can't use as combiner because input != output
	public static class UnionReducer
			extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> _values,
						   Context context
		) throws IOException, InterruptedException {
				context.write(key, key);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "Word sum");
		job.setJarByClass(Union.class);
		job.setMapperClass(UnionMapper.class);
		job.setCombinerClass(UnionReducer.class);
		job.setReducerClass(UnionReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		Path input = new Path( args[0]);
		Path output = new Path(args[1]);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}