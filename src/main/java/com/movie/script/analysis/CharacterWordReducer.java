package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CharacterWordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int totalCount = 0;

        // Sum occurrences of each word
        for (IntWritable val : values) {
            totalCount += val.get();
        }

        // Emit the final output (Word, Frequency)
        context.write(key, new IntWritable(totalCount));

        // Increment counter for total unique words identified
        context.getCounter("CustomCounters", "Total Unique Words Identified").increment(1);
    }
}
