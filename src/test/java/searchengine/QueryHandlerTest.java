package searchengine;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class QueryHandlerTest {
    private void assertArrayEquals(ArrayList<String> result, ArrayList<PageWordInfo> matchingWebPagesForOneSearchTerm) {
    }

    @Test
    void getMatchingWebPages_OneSearchTerm_OneResult() {
        QueryHandler queryHandler = new QueryHandler("data/test-file.txt");
        ArrayList<String> result = new ArrayList<String>();
        result.add("{\"url\": \"http: //page1.com\", \"title\": \"title1\"}");
        assertArrayEquals(result, queryHandler.getMatchingWebPagesForOneSearchTerm("word2"));
    }

    @Test
    void getMatchingWebPages_OneSearchTerm_TwoResults() {
        QueryHandler queryHandler = new QueryHandler("data/test-file.txt");
        ArrayList<String> result = new ArrayList<String>();
        result.add("{\"url\": \"http: //page1.com\", \"title\": \"title1\"}");
        result.add("{\"url\": \"http: //page2.com\", \"title\": \"title2\"}");       
        assertArrayEquals(result, queryHandler.getMatchingWebPagesFor_AND_and_OR_Searches("word1"));
    }

    @Test
    void getMatchingWebPages_TwoSearchTerms_OneResult_ForAn_AND_search() {
        QueryHandler queryHandler = new QueryHandler("data/test-file.txt");
        ArrayList<String> result = new ArrayList<String>();
        result.add("{\"url\": \"http: //page1.com\", \"title\": \"title1\"}");    
        assertArrayEquals(result, queryHandler.getMatchingWebPagesFor_AND_and_OR_Searches("word1%20word2"));
    }

    @Test
    void getMatchingWebPages_TwoSearchTerms_TwoResults_ForAn_OR_search() {
        QueryHandler queryHandler = new QueryHandler("data/test-file.txt");
        ArrayList<String> result = new ArrayList<String>();
        result.add("{\"url\": \"http: //page1.com\", \"title\": \"title1\"}");    
        assertArrayEquals(result, queryHandler.getMatchingWebPagesFor_AND_and_OR_Searches("word1%20or%20word2"));
    }
}
