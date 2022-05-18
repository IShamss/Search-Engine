package QueryProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Indexer.Stemmer;

public class QueryProcessor {
	String query;
	public List<String> processedQuery = new ArrayList<String>();
	
	public QueryProcessor(String q)
	{
		query = q;
	}
	
	// function that splits the query to words then removes stop words from the words of the query
    public void filter(List<String> stopWords)
    {
    	String[]words = query.split("\\s");  // split the query to words
    	processedQuery.addAll(Arrays.asList(words));
    	
    	//turn all the words to lower case
        for (int i = 0; i < processedQuery.size(); i++) {
        	processedQuery.set(i, processedQuery.get(i).toLowerCase());
        }
        
      //remove the stop words from the words of the query
        for (int i = 0; i < processedQuery.size(); i++) {

            if (stopWords.contains(processedQuery.get(i))) {
            	processedQuery.set(i, "");
            }
            //remove non alphanumeric characters
            processedQuery.set(i, processedQuery.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }

    }
    
   public void stemm()
    {
        Stemmer stemmer = new Stemmer();
        //stemming the words of the query
        List<String> finalWords = new ArrayList<>();
        for (int i = 0; i < processedQuery.size(); i++) {
            if (!processedQuery.get(i).equals("")) {
                stemmer.add(processedQuery.get(i).toCharArray(), processedQuery.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                finalWords.add(stemmed);
            }
        }
        processedQuery = finalWords;
    }
       
}
