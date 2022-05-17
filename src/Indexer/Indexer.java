package Indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import utils.Constants;

public class Indexer {
    public static void main(String[] args) 
    {
        Scanner sc;
        try {
            sc = new Scanner(new File(Constants.STOP_WORDS_PATH));
            List<String> stopWords = new ArrayList<String>();
            while (sc.hasNextLine()) {
                stopWords.add(sc.nextLine());
            } 
            HtmlDocument doc = new HtmlDocument("05-17-00-52-27-33.html", 1);
            HtmlDocument doc2 = new HtmlDocument("05-17-00-52-28-02.html", 2);
            List<HtmlDocument> docs = new ArrayList<HtmlDocument>();
            docs.add(doc);
            docs.add(doc2);
            for (HtmlDocument d : docs) {
                d.processDocument(stopWords);
            }
            HashMap<String, HashMap<Integer, List<Integer>>> index = new HashMap<String, HashMap<Integer, List<Integer>>>();
            for (HtmlDocument d : docs) {
                HashMap<String, Integer> words = d.words;
                for (String word : words.keySet()) {
                    if (!index.containsKey(word)) {
                        index.put(word, d.getWords(word));
                    } else {
                        HashMap<Integer, List<Integer>> docIds = index.get(word);
                        if (!docIds.containsKey(d.docId)) {
                            docIds.put(d.docId, d.getWords(word).get(d.docId));
                        }
                    }
                }
            }
            // print the index
            for (String word : index.keySet()) {
                System.out.println(word + ": " + index.get(word));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
