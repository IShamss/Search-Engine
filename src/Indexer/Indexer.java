package Indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            HtmlDocument doc = new HtmlDocument("05-17-00-52-27-33.html");
            doc.filter(stopWords);
            doc.stemm();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
