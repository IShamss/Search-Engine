package Crawler;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.DB;
import utils.Constants;

public class WebCrawler {

	public static void main(String[] args) throws Exception {
		// Connect to the Database
		DB db = new DB();
		db.clearDb(); //clear the values of the DB	
		
		// Fill the initial seed into an arraylist
		ArrayList<String> urls = new ArrayList<String>();
		//read the initial seed from file
		File file = new File(Constants.CRAWLING_SEED_PATH);
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
            	//split the line into the 3 different attributes
            	urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		//get the number of threads from the user
		System.out.println("Enter the number of threads for the web crawler : ");
		Scanner scanner = new Scanner(System.in);
		int threads = scanner.nextInt();
		//create an array of threads
		WebCrawlerThread[] crawlerThreads = new WebCrawlerThread[threads];
		int tasks = urls.size() / threads;
		// execute the threads
		int start=0;
		for(int i=1;i<=threads;i++) {


			int end = i*tasks;
			if(end>=urls.size()) {
				end=urls.size();
			}
			crawlerThreads[i-1]= new WebCrawlerThread(new ArrayList<String>(urls.subList(start, end)),db);
			crawlerThreads[i-1].start();
				
			System.out.print(urls.subList(start, end));
			start=i*tasks;
			System.out.println();
		}
		//then wait for the threads to join as the main thread closes the connection to Db
		
		for(WebCrawlerThread currentThread : crawlerThreads) {
			currentThread.join();
		}
		
		
		
		scanner.close();
		db.close();
	}

}
