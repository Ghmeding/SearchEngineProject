package searchengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//** Represents an index of the words in the dataset and the matching pages where the word can be found */
public class InvertedIndex {
    HashMap<String, HashMap<String, PageWordInfo>> index;
    FileSystem fs = FileSystem.getInstance();
    HashMap<String, Integer> wordCountInPage = new HashMap<String, Integer>();

    /**
     * Creates an index based on a dataset
     * 
     * @param filename Name of the dataset to be read
     */
    public InvertedIndex(String filename) {
        index = new HashMap<String, HashMap<String, PageWordInfo>>();
        List<List<String>> pages = fs.getPages(filename);
        for (List<String> page : pages) {
            int wordCount = 0;
            for (int i = 2; i < page.size(); i++) {
                wordCount++;
                String word = page.get(i).toLowerCase();
                if (index.containsKey(word)) {
                    if (index.get(word).containsKey(page.get(0))) {
                        index.get(word).get(page.get(0)).incrementWordCountByOne();
                    } else {
                        index.get(word).put(page.get(0), new PageWordInfo(page.get(0), page.get(1)));
                    }
                } else {
                    HashMap<String, PageWordInfo> pageInfo = new HashMap<String, PageWordInfo>();
                    index.put(word, pageInfo);
                    index.get(word).put(page.get(0), new PageWordInfo(page.get(0), page.get(1)));
                }
            }
            wordCountInPage.put(page.get(0), wordCount);
        }
    }

    public HashMap<String, HashMap<String, PageWordInfo>> getIndex() {
        return index;
    }

    public HashMap<String, Integer> getWordCountInPage() {
        return wordCountInPage;
    }

    /**
     * Used for testing, get a word in the index.
     * 
     * @param word
     * @return a hashmap containing the pages which contains the word
     */
    public HashMap<String, PageWordInfo> getWordInIndex(String word) {
        return index.get(word);
    }

}
