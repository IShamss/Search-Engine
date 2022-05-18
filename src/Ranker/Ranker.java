package Ranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.Constants;
import utils.Tuple;

public class Ranker {
    HashMap<String, HashMap<Integer, List<Integer>>> index;
    Integer totalDocsCount;
    List<String> query;
    HashMap<String, List<Tuple<Integer, Double>>> query_weight = new HashMap<String, List<Tuple<Integer, Double>>>();

    public Ranker(HashMap<String, HashMap<Integer, List<Integer>>> index, List<String> query, Integer totalDocsCount) {
        this.index = index;
        this.query = query;
        this.totalDocsCount = totalDocsCount;
    }
    public List<Integer> Rank()
    {
        for (String word : query) {
            HashMap<Integer, List<Integer>> docs = index.get(word);
            if (docs == null) {
                continue;
            }
            for (Integer doc_id : docs.keySet()) {
                List<Integer> wordInfo = docs.get(doc_id);
                Integer titleFreq = wordInfo.get(Constants.TITLE);
                Integer h1Freq = wordInfo.get(Constants.H1);
                Integer h2Freq = wordInfo.get(Constants.H2);
                Integer h3Freq = wordInfo.get(Constants.H3);
                Integer h4Freq = wordInfo.get(Constants.H4);
                Integer h5Freq = wordInfo.get(Constants.H5);
                Integer normalFreq = wordInfo.get(Constants.NORMAL);
                Integer wordCountInDoc = wordInfo.get(Constants.WORD_COUNT);
                Double tf = (4 * titleFreq + 3 * h1Freq + 2 * h2Freq + h3Freq + h4Freq + h5Freq +normalFreq)/wordCountInDoc * 1.0;
                Double idf =  Math.log10(totalDocsCount/docs.size());
                Double tf_idf = tf * idf;
                Tuple<Integer, Double> tuple = new Tuple<Integer, Double>(doc_id, tf_idf);
                if (query_weight.containsKey(word)) {
                    query_weight.get(word).add(tuple);
                } else {
                    List<Tuple<Integer, Double>> list = new ArrayList<Tuple<Integer, Double>>();
                    list.add(tuple);
                    query_weight.put(word, list);
                }
            }
        }
        // get score of each doc
        HashMap<Integer, Double> doc_score = new HashMap<Integer, Double>(); // will hold score of each doc aggregating common scores
        // foreach word get its idf_tf 
        for (String word : query_weight.keySet()) {
            List<Tuple<Integer, Double>> list = query_weight.get(word);
            for (Tuple<Integer, Double> tuple : list) {
                if (doc_score.containsKey(tuple.x)) {
                    doc_score.put(tuple.x, doc_score.get(tuple.x) + tuple.y);
                } else {
                    doc_score.put(tuple.x, tuple.y);
                }
            }
        }
        // sort the docs by score
        List<Integer> docs = new ArrayList<Integer>();
        List<Double> scores = new ArrayList<Double>();
        for (Integer doc_id : doc_score.keySet()) {
            docs.add(doc_id);
            scores.add(doc_score.get(doc_id));
        }
        // sort the docs by score descendingly
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(i) < scores.get(j)) {
                    Double temp = scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, temp);
                    Integer temp_doc = docs.get(i);
                    docs.set(i, docs.get(j));
                    docs.set(j, temp_doc);
                }
            }
        }
        return docs;
    }
}
