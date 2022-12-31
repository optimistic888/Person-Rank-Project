import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PersonRank {
  public static void main(String[] args) {
   

    Map<String, Integer> personRank = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("tweets.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("    ");
        String handle = parts[0];
        String tweet = parts[1];
        int likes = Integer.parseInt(parts[2]);

        int handleRank = personRank.getOrDefault(handle, 0) + likes * 10;
        personRank.put(handle, handleRank);

        String[] words = tweet.split(" ");

        for (String word : words) {
          if (word.startsWith("@")) {
            String mention = word.substring(1);

            int mentionRank = personRank.getOrDefault(mention, 0) + likes * 5 + 50;
            personRank.put(mention, mentionRank);
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      return;
    }

    personRank.entrySet().stream()
      .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
      .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
  }
}