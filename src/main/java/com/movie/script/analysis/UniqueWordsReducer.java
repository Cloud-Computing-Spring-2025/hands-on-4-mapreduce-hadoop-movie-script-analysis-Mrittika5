package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringJoiner;

public class UniqueWordsReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> uniqueWords = new HashSet<>();

        // Collect unique words spoken by the character
        for (Text word : values) {
            uniqueWords.add(word.toString());
        }

        // Format the unique words as a list
        StringJoiner wordList = new StringJoiner(", ", "[", "]");
        for (String word : uniqueWords) {
            wordList.add(word);
        }

        // Emit the final output (Character, [Unique Words])
        context.write(key, new Text(wordList.toString()));

        // Increment counter for total unique words identified
        context.getCounter("CustomCounters", "Total Unique Words Identified").increment(uniqueWords.size());
    }
}
