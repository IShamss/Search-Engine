package Indexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.Constants;

public class HtmlDocument {
    public HashMap<String, Integer> words;
    public HashMap<String, Integer> wordsInParagraph;
    public HashMap<String, Integer> wordsInHeadings1;
    public HashMap<String, Integer> wordsInHeadings2;
    public HashMap<String, Integer> wordsInHeadings3;
    public HashMap<String, Integer> wordsInHeadings4;
    public HashMap<String, Integer> wordsInHeadings5;
    public HashMap<String, Integer> wordsInTitle;
    List<String> paragraphs = new ArrayList<String>();
    List<String> headings1= new ArrayList<String>();
    List<String> headings2= new ArrayList<String>();
    List<String> headings3= new ArrayList<String>();
    List<String> headings4= new ArrayList<String>();
    List<String> headings5= new ArrayList<String>();
    List<String> listTitles= new ArrayList<String>();
    List<String> title= new ArrayList<String>();
    public double wordCount;
    int docId;

    public HtmlDocument(String documentName, int id)
    {
        // open the file and read the content
        // read the file and store the paragraphs, headings, list titles and title
        // store the word count in a hashmap
        docId = id;
        File file = new File(Constants.CRAWLED_WEB_PAGES_PATH + documentName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            // get all elements from doc and store them in a list
            List<org.jsoup.nodes.Element> elements = doc.getAllElements();
            for (org.jsoup.nodes.Element element : elements) {
                if (element.text().equals(" ")) {
                    continue;
                }
                if (element.text().equals("")) {
                    continue;
                }
                if (element.tagName().equals("p")) {
                    String[]words = element.text().split("\\s");
                    paragraphs.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("h1")) {
                    String[]words = element.text().split("\\s");
                    headings1.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("h2")) {
                    String[]words = element.text().split("\\s");
                    headings2.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("h3")) {
                    String[]words = element.text().split("\\s");
                    headings3.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("h4")) {
                    String[]words = element.text().split("\\s");
                    headings4.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("h5")) {
                    String[]words = element.text().split("\\s");
                    headings5.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("title")) {
                    String[]words = element.text().split("\\s");
                    title.addAll(Arrays.asList(words));
                } else if (element.tagName().equals("li")) {
                    String[]words = element.text().split("\\s");
                    listTitles.addAll(Arrays.asList(words));
                }
            }
        } catch (IOException e) {
            //print error message
            System.out.println("Error reading file: " + documentName + e);
            e.printStackTrace();
        }
    }

    // function that removes stop words from the words in the document
    void filter(List<String> stopWords)
    {
        //turn all the words in the document to lower case
        for (int i = 0; i < paragraphs.size(); i++) {
            paragraphs.set(i, paragraphs.get(i).toLowerCase());
        }
        for (int i = 0; i < headings1.size(); i++) {
            headings1.set(i, headings1.get(i).toLowerCase());
        }
        for (int i = 0; i < headings2.size(); i++) {
            headings2.set(i, headings2.get(i).toLowerCase());
        }
        for (int i = 0; i < headings3.size(); i++) {
            headings3.set(i, headings3.get(i).toLowerCase());
        }
        for (int i = 0; i < headings4.size(); i++) {
            headings4.set(i, headings4.get(i).toLowerCase());
        }
        for (int i = 0; i < headings5.size(); i++) {
            headings5.set(i, headings5.get(i).toLowerCase());
        }
        for (int i = 0; i < title.size(); i++) {
            title.set(i, title.get(i).toLowerCase());
        }
        for (int i = 0; i < listTitles.size(); i++) {
            listTitles.set(i, listTitles.get(i).toLowerCase());
        }
        //remove the stop words from the words in the document
        for (int i = 0; i < paragraphs.size(); i++) {

            if (stopWords.contains(paragraphs.get(i))) {
                paragraphs.set(i, "");
            }
            //remove all non alphabetic characters
            paragraphs.set(i, paragraphs.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < listTitles.size(); i++) {
            if (stopWords.contains(listTitles.get(i))) {
                listTitles.set(i, "");
            }
            //remove all non alphabetic characters
            listTitles.set(i, listTitles.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < headings1.size(); i++) {
            if (stopWords.contains(headings1.get(i))) {
                headings1.set(i, "");
            }
            //remove all non alphabetic characters
            headings1.set(i, headings1.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < headings2.size(); i++) {
            if (stopWords.contains(headings2.get(i))) {
                headings2.set(i, "");
            }
            //remove all non alphabetic characters
            headings2.set(i, headings2.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < headings3.size(); i++) {
            if (stopWords.contains(headings3.get(i))) {
                headings3.set(i, "");
            }
            //remove all non alphabetic characters
            headings3.set(i, headings3.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < headings4.size(); i++) {
            if (stopWords.contains(headings4.get(i))) {
                headings4.set(i, "");
            }
            //remove all non alphabetic characters
            headings4.set(i, headings4.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < headings5.size(); i++) {
            if (stopWords.contains(headings5.get(i))) {
                headings5.set(i, "");
            }
            //remove all non alphabetic characters
            headings5.set(i, headings5.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }
        for (int i = 0; i < title.size(); i++) {
            if (stopWords.contains(title.get(i))) {
                title.set(i, "");
            }
            //remove all non alphabetic characters
            title.set(i, title.get(i).replaceAll("[^a-zA-Z0-9]", ""));
        }


    }
    void stemm()
    {
        Stemmer stemmer = new Stemmer();
        //stemming the words in the document
        List<String> FinalParagarphs = new ArrayList<>();
        for (int i = 0; i < paragraphs.size(); i++) {
            if (!paragraphs.get(i).equals("")) {
                stemmer.add(paragraphs.get(i).toCharArray(), paragraphs.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalParagarphs.add(stemmed);
            }
        }
        paragraphs = FinalParagarphs;

        List<String> FinalHeadings1 = new ArrayList<>();
        for (int i = 0; i < headings1.size(); i++) {
            if (!headings1.get(i).equals("")) {
                stemmer.add(headings1.get(i).toCharArray(), headings1.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalHeadings1.add(stemmed);
            }
        }
        headings1 = FinalHeadings1;

        List<String> FinalHeadings2 = new ArrayList<>();
        for (int i = 0; i < headings2.size(); i++) {
            if (!headings2.get(i).equals("")) {
                stemmer.add(headings2.get(i).toCharArray(), headings2.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalHeadings2.add(stemmed);
            }
        }
        headings2 = FinalHeadings2;

        List<String> FinalHeadings3 = new ArrayList<>();
        for (int i = 0; i < headings3.size(); i++) {
            if (!headings3.get(i).equals("")) {
                stemmer.add(headings3.get(i).toCharArray(), headings3.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalHeadings3.add(stemmed);
            }
        }
        headings3 = FinalHeadings3;

        List<String> FinalHeadings4 = new ArrayList<>();
        for (int i = 0; i < headings4.size(); i++) {
            if (!headings4.get(i).equals("")) {
                stemmer.add(headings4.get(i).toCharArray(), headings4.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalHeadings4.add(stemmed);
            }
        }
        headings4 = FinalHeadings4;


        List<String> FinalHeadings5 = new ArrayList<>();
        for (int i = 0; i < headings5.size(); i++) {
            if (!headings5.get(i).equals("")) {
                stemmer.add(headings5.get(i).toCharArray(), headings5.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalHeadings5.add(stemmed);
            }
        }

        headings5 = FinalHeadings5;
        List<String> FinalTitle = new ArrayList<>();
        for (int i = 0; i < title.size(); i++) {
            if (!title.get(i).equals("")) {
                stemmer.add(title.get(i).toCharArray(), title.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalTitle.add(stemmed);
            }
        }

        title = FinalTitle;
        List<String> FinalListTitles = new ArrayList<>();
        for (int i = 0; i < listTitles.size(); i++) {
            if (!listTitles.get(i).equals("")) {
                stemmer.add(listTitles.get(i).toCharArray(), listTitles.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalListTitles.add(stemmed);
            }
        }
        listTitles = FinalListTitles;
    }
    void indexWords()
    {
        // get the word count of the document
        // loop through each list of string and add words to hashmap
        // if the word is already in the hashmap, increment the count
        // if not, add the word to the hashmap with count 1
        // after the loop, the hashmap contains the word count of the document
        // the key is the word and the value is the count

        wordCount = paragraphs.size()+ headings1.size()+ headings2.size()+ headings3.size()+ headings4.size()+ headings5.size()+ title.size()+ listTitles.size();

        words = new HashMap<String, Integer>();
        wordsInParagraph = new HashMap<String, Integer>();
        wordsInHeadings1 = new HashMap<String, Integer>();
        wordsInHeadings2 = new HashMap<String, Integer>();
        wordsInHeadings3 = new HashMap<String, Integer>();
        wordsInHeadings4 = new HashMap<String, Integer>();
        wordsInHeadings5 = new HashMap<String, Integer>();
        wordsInTitle = new HashMap<String, Integer>();

        for (String word : listTitles) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInParagraph.put(word, wordsInParagraph.getOrDefault(word, 0) + 1);
        }
        for (String word : paragraphs) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInParagraph.put(word, wordsInParagraph.getOrDefault(word, 0) + 1);
        }
        for (String word : headings1) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInHeadings1.put(word, wordsInHeadings1.getOrDefault(word, 0) + 1);
        }
        for (String word : headings2) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInHeadings2.put(word, wordsInHeadings2.getOrDefault(word, 0) + 1);
        }
        for (String word : headings3) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInHeadings3.put(word, wordsInHeadings3.getOrDefault(word, 0) + 1);
        }
        for (String word : headings4) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInHeadings4.put(word, wordsInHeadings4.getOrDefault(word, 0) + 1);
        }
        for (String word : headings5) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInHeadings5.put(word, wordsInHeadings5.getOrDefault(word, 0) + 1);
        }
        for (String word : title) {
            words.put(word, words.getOrDefault(word, 0) + 1);
            wordsInTitle.put(word, wordsInTitle.getOrDefault(word, 0) + 1);
        }
    }

    void processDocument(List<String> stopWords)
    {
        this.filter(stopWords);
        this.stemm();
        this.indexWords();
    }
     
    HashMap<Integer, List<Integer>> getWords(String word)
    {
        List<Integer> indexInfo = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0);
        Integer frequency = 0;
        if (words.containsKey(word))
        {
            frequency = words.get(word);
            indexInfo.set(Constants.TOTAL, frequency);
        }
        if (wordsInParagraph.containsKey(word))
        {
            frequency = wordsInParagraph.get(word);
            indexInfo.set(Constants.NORMAL, frequency);
        }
        if (wordsInHeadings1.containsKey(word))
        {
            frequency = wordsInHeadings1.get(word);
            indexInfo.set(Constants.H1, frequency);
        }
        if (wordsInHeadings2.containsKey(word))
        {
            frequency = wordsInHeadings2.get(word);
            indexInfo.set(Constants.H2, frequency);
        }
        if (wordsInHeadings3.containsKey(word))
        {
            frequency = wordsInHeadings3.get(word);
            indexInfo.set(Constants.H3, frequency);
        }
        if (wordsInHeadings4.containsKey(word))
        {
            frequency = wordsInHeadings4.get(word);
            indexInfo.set(Constants.H4, frequency);
        }
        if (wordsInHeadings5.containsKey(word))
        {
            frequency = wordsInHeadings5.get(word);
            indexInfo.set(Constants.H5, frequency);
        }
        if (wordsInTitle.containsKey(word))
        {
            frequency = wordsInTitle.get(word);
            indexInfo.set(Constants.TITLE, frequency);
        }
        HashMap<Integer, List<Integer>> indexInfoMap = new HashMap<Integer, List<Integer>>();
        indexInfoMap.put(getDocId(), indexInfo);
        return indexInfoMap;
    }
    int getDocId()
    {
        return docId;
    }
}
