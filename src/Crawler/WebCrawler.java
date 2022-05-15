package Crawler;


import java.util.ArrayList;

import utils.DB;

public class WebCrawler {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DB db = new DB();
		db.clearDb();
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("https://www.javatpoint.com/java-get-current-date");
		urls.add("https://www.geeksforgeeks.org/queue-interface-java/");
		WebCrawlerThread t1 = new WebCrawlerThread(urls,db);
		t1.start();
		t1.join();
		
		db.close();
	}

}
