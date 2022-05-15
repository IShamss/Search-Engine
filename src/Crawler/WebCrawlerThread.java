package Crawler;
// to download html pages
import org.jsoup.Jsoup;   
import org.jsoup.nodes.Document;   
import org.jsoup.nodes.Element;   
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.Constants;
import utils.DB;

import com.trigonic.jrobotx.RobotExclusion;


public class WebCrawlerThread extends Thread {

	private HashSet<String> urlDepthLinks;   //this is a list of unique urls using DFS on a given url
	private Queue<String> urlLinks;
	RobotExclusion robotExclusion;
	static public int pagesCount = 0;
	DB db;
	public WebCrawlerThread(ArrayList<String> urls, DB db) {   //this is the constructor of the crawler
	    this.urlDepthLinks = new HashSet<String>();   
	    this.robotExclusion = new RobotExclusion();
	    this.db=db;
	    urlLinks = new LinkedList<String>(urls);
    }   
	
	public void crawlPages(String URL, int depth) throws URISyntaxException, MalformedURLException, SQLException {
		
		//normalize the url
		URI uri = new URI(URL);
	    URL =  uri.normalize().toString();
	    
	    int currentPagesCount = this.db.getPagesCount();

	    //we use the conditional statement to check whether we have already crawled the URL or not.  
	    // we also check whether the depth reaches to MAX_DEPTH or not  
	    boolean insert = (!urlDepthLinks.contains(URL)) && 
	    		(depth < Constants.MAX_DEPTH)&& 
	    		(robotExclusion.allows(uri.toURL(), Constants.AGENT))&&
	    		(currentPagesCount<Constants.MAX_CRAWLED_PAGES)&&
	    		(!this.db.isUrlInDb(URL));
	    if (insert) {
	        
	        // use try catch block for recursive process  
	        try {
	            
	            // fetch the HTML code of the given URL by using the connect() and get() method and store the result in Document  
	            Document doc = Jsoup.connect(URL).get();
	            String docContentType = doc.documentType().name();
	            if(docContentType.equals("html")) {
	            	// if the URL is not present in the set, we add it to the set  
		            urlDepthLinks.add(URL);
		            System.out.println(">> Depth: " + depth + " [" + URL + "]");
		            // download the webpage
		            LocalDateTime now = LocalDateTime.now();  
		            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-HH-mm-ss-SS");  
		            downloadPage(doc,dtf.format(now).toString(),URL);
		            // we use the select() method to parse the HTML code for extracting links of other URLs and store them into Elements  
		            Elements availableLinksOnPage = doc.select("a[href]");
		            // increase depth  
		            depth++;
		            // for each extracted URL, we repeat above process  
		            for (Element page: availableLinksOnPage) {
		                // call getPageLinks() method and pass the extracted URL to it as an argument  
		                crawlPages(page.attr("abs:href"), depth);
		            }
	            	
	            }
	           
	        }
	        // handle exception  
	        catch (IOException e) {
	            // print exception messages  
	            System.err.println("For '" + URL + "': " + e.getMessage());
	        }
	    }
	}
	
	synchronized void downloadPage(Document doc, String fileName,String url) throws SQLException {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CRAWLED_WEB_PAGES_PATH+fileName+".html"));
				writer.write(doc.html());
				writer.close();
				//save to DB
				int result = this.db.insertPage(fileName, url);
				if(result==1) {
					this.pagesCount++;
				}
				//add to order of crawling
			} catch (IOException e) {
				System.out.println("Failed to download page" + e);
			}
			
	}
	
	
	public void run() {
		
				
		try {
			int currentCount = this.db.getPagesCount();
			while(!this.urlLinks.isEmpty() && currentCount < Constants.MAX_CRAWLED_PAGES) {
				this.crawlPages(urlLinks.remove(), 0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
}
