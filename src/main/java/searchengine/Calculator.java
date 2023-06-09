package searchengine;

/** Helper class calulates tdidf */
public class Calculator {
    private static final Calculator instance = new Calculator();

    public Calculator() {
    }

    public static Calculator getInstance() {
        return instance;
    }

    /**
     * Used for calculating tf
     * 
     * @param numOfMatchWordInPage
     * @param totalNumOfWords
     * @return double
     */
    public double termFrequency(int numOfMatchWordInPage, int totalNumOfWords) {
        double tf = (double) numOfMatchWordInPage / totalNumOfWords;
        return tf;
    }

    /**
     * used for calculating idf
     * 
     * @param numOfPagesInCorpus
     * @param totalNumOfPagesWithWord
     * @return double
     */
    public double inverseFrequency(int numOfPagesInCorpus, int totalNumOfPagesWithWord) {
        double idf = Math.log((double) numOfPagesInCorpus / totalNumOfPagesWithWord);
        return idf;
    }

    /**
     * used for calculating tfidf
     * 
     * @param numOfMatchWordInPage
     * @param totalNumOfWords
     * @param numOfPagesInCorpus
     * @param totalNumOfPagesWithWord
     * @return double
     */
    public double finalFrequency(int numOfMatchWordInPage, int totalNumOfWords, int numOfPagesInCorpus,
            int totalNumOfPagesWithWord) {
        double finalFreq = termFrequency(numOfMatchWordInPage, totalNumOfWords)
                * inverseFrequency(numOfPagesInCorpus, totalNumOfPagesWithWord);
        return finalFreq;
    }
}