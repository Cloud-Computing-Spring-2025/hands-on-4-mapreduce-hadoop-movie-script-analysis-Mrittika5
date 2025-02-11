package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CharacterWordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text characterWord = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();

        // Ignore empty lines
        if (line.isEmpty()) {
            return;
        }

        // Split character name and dialogue
        int colonIndex = line.indexOf(":");
        if (colonIndex == -1) {
            return; // Ignore malformed lines
        }

        String character = line.substring(0, colonIndex).trim();
        String dialogue = line.substring(colonIndex + 1).trim();

        // Tokenize the dialogue
        StringTokenizer tokenizer = new StringTokenizer(dialogue, " .,!?\"'();:-");

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase(); // Normalize to lowercase
            characterWord.set(character + ":" + word);
            context.write(characterWord, one);
            
            // Increment counters
            context.getCounter("CustomCounters", "Total Words Processed").increment(1);
        }

        // Increment total lines processed counter
        context.getCounter("CustomCounters", "Total Lines Processed").increment(1);
    }
}
