
Java file explanation: 
The provided Java code implements a Hadoop MapReduce program to analyze a movie script by extracting word frequency, dialogue length per character, and unique words spoken. The CharacterWordMapper processes each line of the script, extracts dialogue words, normalizes them, and emits (word, 1) pairs. The CharacterWordReducer then aggregates these counts to determine the most frequently spoken words. The DialogueLengthMapper identifies the character speaking and calculates the length of their dialogue, emitting (character, length), which is summed up in the DialogueLengthReducer to determine total dialogue length per character. The UniqueWordsReducer collects and stores distinct words spoken by each character using a HashSet, formatting the output as a list. Additionally, Hadoop counters track total lines, words, unique words, and the number of characters speaking, ensuring a comprehensive analysis of the script.

## Setup and Execution

### 1. **Start the Hadoop Cluster**

Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. **Build the Code**

Build the code using Maven:

```bash
mvn install
```

### 3. **Move JAR File to Shared Folder**

Move the generated JAR file to a shared folder for easy access:
I moved it manually 
```

### 4. **Copy JAR to Docker Container**

Copy the JAR file to the Hadoop ResourceManager container:

```bash
 docker cp input/code/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. **Move Dataset to Docker Container**

Copy the dataset to the Hadoop ResourceManager container:

```bash
docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. **Connect to Docker Container**

Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. **Set Up HDFS**

Create a folder in HDFS for the input dataset:

```bash
hadoop fs -mkdir -p /input/dataset1
```

Copy the input dataset to the HDFS folder:

```bash
hadoop fs -put ./movie_dialogues.txt /input/dataset1
```

### 8. **Execute the MapReduce Job**

Run your MapReduce job using the following command:

```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar   com.movie.script.analysis.MovieScriptAnalysis /input/dataset1/movie_dialogues.txt  /output1
```

### 9. **View the Output**

To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output1/*
```

### 10. **Copy Output from HDFS to Local OS**

To copy the output from HDFS to your local machine:

1. Use the following command to copy from HDFS:
    ```bash
    hdfs dfs -get /output1 /opt/hadoop-3.2.1/share/hadoop/mapreduce/
    ```

2. use Docker to copy from the container to your local machine:
   ```bash
   exit 
   ```
    ```bash
   docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output1/ output
    ```
3. Commit and push to your repo so that we can able to see your output