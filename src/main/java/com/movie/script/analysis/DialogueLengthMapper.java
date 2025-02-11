package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DialogueLengthMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable dialogueLength = new IntWritable();
    private Text character = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split(":", 2);

        if (parts.length == 2) {
            character.set(parts[0].trim());
            int length = parts[1].trim().length();
            dialogueLength.set(length);
            context.write(character, dialogueLength);
        }
    }
}
