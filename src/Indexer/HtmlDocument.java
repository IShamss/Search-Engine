package Indexer;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.Constants;


public class HtmlDocument {
    HashMap<String, Integer> wordCount;
    List<String> paragraphs;
    List<String> headings1;
    List<String> headings2;
    List<String> headings3;
    List<String> headings4;
    List<String>headings5;
    List<String> listTitles;
    List<String> Title;

    public HtmlDocument(String documentName)
    {
        // open the file and read the content
        // read the file and store the paragraphs, headings, list titles and title
        // store the word count in a hashmap
        File file = new File(Constants.CRAWLED_WEB_PAGES_PATH + documentName);
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            String li = doc.getElementsByTag("li").text();
            String[] listTitles = li.split("\\s+");
            for (int i = 0; i < listTitles.length; i++) {
                listTitles[i] = listTitles[i].replaceAll("[^\\w]", "");
            }
            this.listTitles = Arrays.asList(listTitles);
            String h1 = doc.getElementsByTag("h1").text();
            String[] headings1 = h1.split("\\s+");
            for (int i = 0; i < headings1.length; i++) {
                headings1[i] = headings1[i].replaceAll("[^\\w]", "");
            }
            this.headings1 = Arrays.asList(headings1);
            String h2 = doc.getElementsByTag("h2").text();
            String[] headings2 = h2.split("\\s+");
            for (int i = 0; i < headings2.length; i++) {
                headings2[i] = headings2[i].replaceAll("[^\\w]", "");
            }
            this.headings2 = Arrays.asList(headings2);
            String h3 = doc.getElementsByTag("h3").text();
            String[] headings3 = h3.split("\\s+");
            for (int i = 0; i < headings3.length; i++) {
                headings3[i] = headings3[i].replaceAll("[^\\w]", "");
            }
            this.headings3 = Arrays.asList(headings3);
            String h4 = doc.getElementsByTag("h4").text();
            String[] headings4 = h4.split("\\s+");
            for (int i = 0; i < headings4.length; i++) {
                headings4[i] = headings4[i].replaceAll("[^\\w]", "");
            }
            this.headings4 = Arrays.asList(headings4);
            String h5 = doc.getElementsByTag("h5").text();
            String[] headings5 = h5.split("\\s+");
            for (int i = 0; i < headings5.length; i++) {
                headings5[i] = headings5[i].replaceAll("[^\\w]", "");
            }
            this.headings5 = Arrays.asList(headings5);
            String title = doc.title();
            String[] Title = title.split("\\s+");
            for (int i = 0; i < Title.length; i++) {
                Title[i] = Title[i].replaceAll("[^\\w]", "");
            }
            this.Title = Arrays.asList(Title);
            String[] paragraphs = doc.getElementsByTag("p").text().split("\\s+");
            for (int i = 0; i < paragraphs.length; i++) {
                paragraphs[i] = paragraphs[i].replaceAll("[^\\w]", "");
            }
            this.paragraphs = Arrays.asList(paragraphs);
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
        for (int i = 0; i < Title.size(); i++) {
            Title.set(i, Title.get(i).toLowerCase());
        }
        for (int i = 0; i < listTitles.size(); i++) {
            listTitles.set(i, listTitles.get(i).toLowerCase());
        }
        //remove the stop words from the words in the document
        for (int i = 0; i < paragraphs.size(); i++) {

            if (stopWords.contains(paragraphs.get(i))) {
                paragraphs.set(i, "");
            }
        }
        for (int i = 0; i < listTitles.size(); i++) {
            if (stopWords.contains(listTitles.get(i))) {
                listTitles.set(i, "");
            }
        }
        for (int i = 0; i < headings1.size(); i++) {
            if (stopWords.contains(headings1.get(i))) {
                headings1.set(i, "");
            }
        }
        for (int i = 0; i < headings2.size(); i++) {
            if (stopWords.contains(headings2.get(i))) {
                headings2.set(i, "");
            }
        }
        for (int i = 0; i < headings3.size(); i++) {
            if (stopWords.contains(headings3.get(i))) {
                headings3.set(i, "");
            }
        }
        for (int i = 0; i < headings4.size(); i++) {
            if (stopWords.contains(headings4.get(i))) {
                headings4.set(i, "");
            }
        }
        for (int i = 0; i < headings5.size(); i++) {
            if (stopWords.contains(headings5.get(i))) {
                headings5.set(i, "");
            }
        }
        for (int i = 0; i < Title.size(); i++) {
            if (stopWords.contains(Title.get(i))) {
                Title.set(i, "");
            }
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
        for (int i = 0; i < Title.size(); i++) {
            if (!Title.get(i).equals("")) {
                stemmer.add(Title.get(i).toCharArray(), Title.get(i).length());
                stemmer.stem();
                String stemmed = stemmer.toString();
                FinalTitle.add(stemmed);
            }
        }

        Title = FinalTitle;
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
}
