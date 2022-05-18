package Indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import utils.Constants;
import utils.DB;

public class Indexer {
    public static void main(String[] args) throws Exception 
    {
        try {
            Indexer indexer = new Indexer();
            DB db = new DB();
            // if the indexer is already created, then read the indexer from db and load the indexer
            if (db.getIndexCount() != 0)
            {
                System.out.println("index already created");
                System.out.println("checking for updates");
                // check if there are any new html documents to index
                HashMap<String, HashMap<Integer, List<Integer>>> index;
                ResultSet idsAndFilenames = db.getUnindexed();
                HashMap<Integer, String> idsAndFilenamesMap = new HashMap<Integer, String>();
                while (idsAndFilenames.next()) {
                    String filename = idsAndFilenames.getString("filename");
                    int id = idsAndFilenames.getInt("id");
                    System.out.println("indexing " + filename);
                    idsAndFilenamesMap.put(id, filename);
                }
                List<HtmlDocument> docs = new ArrayList<HtmlDocument>();
                for (int id : idsAndFilenamesMap.keySet()) {
                    String filename = idsAndFilenamesMap.get(id);
                    docs.add(new HtmlDocument(filename+".html", id));
                }
                indexer.processDocs(docs);
                index = indexer.buildIndexer(docs);
                indexer.saveIndexer(index, db);

                index = indexer.retreiveIndexer(db);
                for (String word : index.keySet()) {
                    System.out.println(word + ": " + index.get(word));
                }
               
            }
            else
            {
                // get ids and filenames from db
                ResultSet idsAndFilenames = db.getIdsAndFilenames();
                HashMap<Integer, String> idsAndFilenamesMap = new HashMap<Integer, String>();
                while (idsAndFilenames.next())
                {
                    String docId = idsAndFilenames.getString("id");
                    String fileName = idsAndFilenames.getString("fileName");
                    System.out.println("indexing " + fileName);
                    idsAndFilenamesMap.put(Integer.parseInt(docId), fileName);
                    // read the file and create the indexer
                }
                List<HtmlDocument> docs = new ArrayList<HtmlDocument>();
                for (Integer docId : idsAndFilenamesMap.keySet())
                {
                    String fileName = idsAndFilenamesMap.get(docId);
                    docs.add(new HtmlDocument(fileName+".html", docId));
                }
                // read the files and create the indexer
                // process docs
                indexer.processDocs(docs);
                // build index
                HashMap<String, HashMap<Integer, List<Integer>>> index = indexer.buildIndexer(docs);
                // print index
                for (String word : index.keySet()) {
                    System.out.println(word + ": " + index.get(word));
                }
                // save index to db
                indexer.saveIndexer(index, db);


            }
            // else create the indexer
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void processDocs(List<HtmlDocument> docs) throws FileNotFoundException
    {
        Scanner sc;
            sc = new Scanner(new File(Constants.STOP_WORDS_PATH));
            List<String> stopWords = new ArrayList<String>();
            while (sc.hasNextLine()) {
                stopWords.add(sc.nextLine());
            } 
        for (HtmlDocument d : docs) {
            d.processDocument(stopWords);
        }
    }

    public HashMap<String, HashMap<Integer, List<Integer>>> buildIndexer(List<HtmlDocument> docs)
    {
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
        return index;
    }

    public boolean saveIndexer(HashMap<String, HashMap<Integer, List<Integer>>> index, DB db) throws Exception
    {
        for (String word : index.keySet()) {
            HashMap<Integer, List<Integer>> docIds = index.get(word);
            for (Integer docId : docIds.keySet()) {
                List<Integer> positions = docIds.get(docId);
                db.insertIndexEntry(docId, word, positions);
                db.setIndexed(docId);
            }
        }
        return true;
    }

    public HashMap<String, HashMap<Integer, List<Integer>>> retreiveIndexer(DB db) throws NumberFormatException, SQLException
    {
        ResultSet index = db.getIndex();
        HashMap<String, HashMap<Integer, List<Integer>>> indexer = new HashMap<String, HashMap<Integer, List<Integer>>>();
        while (index.next()) {
            String word = index.getString("word");
            Integer docId = index.getInt("docId");
            List<Integer> positions = new ArrayList<Integer>();
            positions.add(index.getInt("total"));
            positions.add(index.getInt("title"));
            positions.add(index.getInt("h1"));
            positions.add(index.getInt("h2"));
            positions.add(index.getInt("h3"));
            positions.add(index.getInt("h4"));
            positions.add(index.getInt("h5"));
            positions.add(index.getInt("normal"));
            if (!indexer.containsKey(word)) {
                indexer.put(word, new HashMap<Integer, List<Integer>>());
            }
            indexer.get(word).put(docId, positions);
        }
        return indexer;
    }
}
