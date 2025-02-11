package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DialogueLengthReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int totalLength = 0;

        // Sum up dialogue lengths for the character
        for (IntWritable val : values) {
            totalLength += val.get();
        }

        // Emit the final output (Character, Total Dialogue Length)
        context.write(key, new IntWritable(totalLength));

        // Increment counter for the number of characters speaking
        context.getCounter("CustomCounters", "Number of Characters Speaking").increment(1);
    }
}
