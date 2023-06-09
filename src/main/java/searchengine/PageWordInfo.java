package searchengine;

/**
 * Used for keeping track of page data, in the inverted index
 */
public class PageWordInfo {
    private String url;
    private String title;
    private int wordCount;
    private double score;

    private Calculator cal = Calculator.getInstance();
    private FileSystem fs = FileSystem.getInstance();

    public PageWordInfo(String url, String title) {
        this.url = url;
        this.title = title;
        this.wordCount = 1;
    }

    public Integer getWordCount() {
        return this.wordCount;
    }

    /**
     * Increments the word count by one.
     */
    public void incrementWordCountByOne() {
        wordCount++;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    /**
     * gets tfidf from the calculator
     * 
     * @param totalNumOfWords
     * @param totalNumOfPagesWithWord
     */
    public void calFinalFreq_TfIdf(int totalNumOfWords, int totalNumOfPagesWithWord) {
        this.score = cal.finalFrequency(wordCount, totalNumOfWords, fs.getNumPageInDataset(), totalNumOfPagesWithWord);
    }

    /**
     * gets tf from the calculator
     * 
     * @param totalNumOfWords
     */
    public void calFinalFreq_Tf(int totalNumOfWords) {
        this.score = cal.termFrequency(wordCount, totalNumOfWords);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double tfidf) {
        this.score = tfidf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /**
     * Checks if an object is equal based on url and title
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageWordInfo other = (PageWordInfo) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        double otherScore = other.getScore();
        setScore((otherScore + score));
        return true;
    }

}