package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexTest{
    static HashMap<String, HashMap<String, PageWordInfo>> index;
    static InvertedIndex invertedIndex;

    @BeforeAll
    static void setUp() {
        invertedIndex = new InvertedIndex("data/test-file.txt");
        index = invertedIndex.getIndex();
    }

    //wordExist test - word exist in the datafile
    @Test 
    void wordExist(){
        HashMap<String, PageWordInfo> result = index.get("word1");
        assertTrue(result.size() > 0);
        assertEquals("title2", result.get("*PAGE:http://page2.com").getTitle());
    }

    //wordExist test - word exist in the datafile
    @Test 
    void specificWordCountWorks(){
        HashMap<String, PageWordInfo> result = index.get("word1");
        assertEquals(1, result.get("*PAGE:http://page2.com").getWordCount());
    }

    //wordExist test - word exist in the datafile
    @Test 
    void pageWordCount(){
        assertEquals(2, invertedIndex.getWordCountInPage().get("*PAGE:http://page2.com"));
    }

    //wordExist test - word exist in the datafile
    @Test 
    void wordDoesNotExist(){
        HashMap<String, PageWordInfo> result = index.get("word5");
        assertEquals(null, result);
    }

    //wordExist test - word exist in the datafile
    @Test 
    void equalsWorkForPageInfo(){
        PageWordInfo pageInfo1 = new PageWordInfo("test.com", "test");
        PageWordInfo pageInfo1Duplicate = new PageWordInfo("test.com", "test");
        PageWordInfo pageInfo2 = new PageWordInfo("test2.com", "test2");
        assertFalse(pageInfo1.equals(pageInfo2));
        assertTrue(pageInfo1.equals(pageInfo1Duplicate));
    }
}