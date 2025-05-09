package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UniqueWordsMapper extends Mapper<Object, Text, Text, Text> {

    private Text character = new Text();
    private Text word = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split(":", 2);

        if (parts.length == 2) {
            character.set(parts[0].trim());
            String dialogue = parts[1].trim().toLowerCase().replaceAll("[^a-zA-Z ]", "");

            HashSet<String> uniqueWords = new HashSet<>();
            StringTokenizer tokenizer = new StringTokenizer(dialogue);
            while (tokenizer.hasMoreTokens()) {
                uniqueWords.add(tokenizer.nextToken());
            }

            for (String uniqueWord : uniqueWords) {
                word.set(uniqueWord);
                context.write(character, word);
            }
        }
    }
}
