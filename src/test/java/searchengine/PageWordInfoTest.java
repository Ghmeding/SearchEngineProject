package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PageWordInfoTest{
    private static PageWordInfo pageWordInfo;

    @BeforeAll static void setUp(){
        pageWordInfo = new PageWordInfo(null, null);
    }

    @Test void incrementWordCountByOne(){
        pageWordInfo.incrementWordCountByOne();
        assertEquals(2, pageWordInfo.getWordCount());
    }



    

}