package Ranker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Indexer.Indexer;
import utils.DB;
// Main class for testing 
public class RankerMain {
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        DB db = new DB();
        Indexer indexer = new Indexer();
        HashMap<String, HashMap<Integer, List<Integer>>> index = indexer.retreiveIndexer(db);
        List<String> query = new ArrayList<String>();
        query.add("egypt");
        Integer c = db.getPagesCount();
        Ranker ranker = new Ranker(index, query, c);
        for (Integer word : ranker.Rank())
        {
            System.out.println(word);
        }
    }
}
