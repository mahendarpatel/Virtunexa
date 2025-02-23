import java.io.*;
import java.util.*;

class TextFileAnalyzer{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();
        scanner.close();
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("File not found.");
            return;
        }
        analyzeFile(file);
    }
    private static void analyzeFile(File file){
        int lines = 0, words = 0, characters = 0;
        Map<String, Integer> wordCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                lines++;
                characters += line.length();
                String[] wordArray = line.split("\\s+");
                words += wordArray.length;
                for (String word : wordArray){
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } 
          catch (IOException e){
            System.out.println("Error reading file.");
            return;
        }
        System.out.println("Lines: " + lines);
        System.out.println("Words: " + words);
        System.out.println("Characters: " + characters);
        System.out.println("Top words:");
        wordCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
