package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for handling queries
 */
public class QueryHandler {
  private HashMap<String, HashMap<String, PageWordInfo>> index;
  InvertedIndex invertedIndex;

  public QueryHandler(String filename) {
    // Creates an index of the relevant words in the dataset as a hashmap
    this.invertedIndex = new InvertedIndex(filename);
    this.index = invertedIndex.getIndex();
  }

  /**
   * Gets the web pages matching a search term (no possibility for AND and OR
   * searches)
   * 
   * @param searchTerm The search term to find matching pages for
   * @return ArrayList<PageWordInfo> The ArrayList containing the matching pages
   */
  public ArrayList<PageWordInfo> getMatchingWebPagesForOneSearchTerm(String searchTerm) {
    ArrayList<PageWordInfo> response = new ArrayList<PageWordInfo>();
    HashMap<String, PageWordInfo> indexResult;
    if (index.containsKey(searchTerm)) {
      indexResult = index.get(searchTerm);
    } else {
      return response;
    }
    for (Map.Entry<String, PageWordInfo> entry : indexResult.entrySet()) {
      entry.getValue().calFinalFreq_TfIdf(invertedIndex.getWordCountInPage().get(entry.getValue().getUrl()),
          indexResult.size());
      response.add(entry.getValue());
    }
    return response;
  }

  /**
   * Gets the web pages matching a search query (Possibility for AND searches but
   * not for OR searches)
   * 
   * @param searchTerm The search term to find matching pages for
   * @return ArrayList<PageWordInfo> The ArrayList containing the matching pages
   */
  public ArrayList<PageWordInfo> getMatchingWebPagesFor_AND_Searches(String query) {
    String[] searchTerms = query.split("%20"); // %20 is equal to space
    if (searchTerms.length == 1) {
      return getMatchingWebPagesForOneSearchTerm(searchTerms[0]);
    }
    ArrayList<PageWordInfo> matchingWebPages = new ArrayList<PageWordInfo>(); // For final results
    ArrayList<PageWordInfo> checkingForMatchingResponses = new ArrayList<>(); // Temporary storage for matches for one
                                                                              // or more search terms
    for (String searchTerm : searchTerms) {
      ArrayList<PageWordInfo> searchTermMatches = new ArrayList<>();
      searchTermMatches = getMatchingWebPagesForOneSearchTerm(searchTerm);
      for (PageWordInfo searchTermMatch : searchTermMatches) {
        if (checkingForMatchingResponses.contains(searchTermMatch)) {
          matchingWebPages.add(searchTermMatch);
          continue;
        }
        checkingForMatchingResponses.add(searchTermMatch);
      }
    }
    return matchingWebPages;
  }

  /**
   * Gets the web pages matching a search query (Possibility for both AND and OR
   * searches)
   * 
   * @param searchTerm The search term to find matching pages for
   * @return ArrayList<PageWordInfo> The ArrayList containing the matching pages
   */
  public ArrayList<PageWordInfo> getMatchingWebPagesFor_AND_and_OR_Searches(String query) {
    ArrayList<PageWordInfo> matchingWebPages = new ArrayList<PageWordInfo>(); // For final results
    if (query == null) {
      return matchingWebPages;
    }
    String[] searchTerms = query.split("%20or%20"); // %20 is equal to space
    if (searchTerms.length == 1) {
      return getMatchingWebPagesFor_AND_Searches(searchTerms[0]);
    }
    for (String searchTerm : searchTerms) {
      ArrayList<PageWordInfo> searchTermMatches = new ArrayList<>();
      searchTermMatches = getMatchingWebPagesFor_AND_Searches(searchTerm);
      for (PageWordInfo searchTermMatch : searchTermMatches) {
        if (matchingWebPages.contains(searchTermMatch)) {
          continue;
        }
        matchingWebPages.add(searchTermMatch);
      }
    }
    return matchingWebPages;
  }
}