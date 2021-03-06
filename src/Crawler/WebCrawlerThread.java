package Crawler;
// to download html pages
import org.jsoup.Jsoup;   
import org.jsoup.nodes.Document;   
import org.jsoup.nodes.Element;   
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
//	UrlNormalizer normalizer = new UrlNormalizer();
//	static public int pagesCount = 0;
	static AtomicInteger pagesCount = new AtomicInteger(0);
	DB db;
	public WebCrawlerThread(ArrayList<String> urls, DB db) {   //this is the constructor of the crawler
	    this.urlDepthLinks = new HashSet<String>();   
	    this.robotExclusion = new RobotExclusion();
	    this.db=db;
	    urlLinks = new LinkedList<String>(urls);
    }   
	
	public void crawlPages(String downloadUrl, int depth) throws MalformedURLException, SQLException, UnsupportedEncodingException, URISyntaxException {
		
		//normalize the url
//		URL = URLEncoder.encode(URL,"UTF-8");
//		URL url;
//		URI uri;
		downloadUrl = UrlNormalizer.getNormalizedURL(downloadUrl);
		
		URI uri = new URI(downloadUrl);		
				
		int currentPagesCount = this.db.getPagesCount();
		
		
		//we use the conditional statement to check whether we have already crawled the URL or not.  
		// we also check whether the depth reaches to MAX_DEPTH or not  
		boolean insert = (!urlDepthLinks.contains(downloadUrl)) && 
				(depth < Constants.MAX_DEPTH)&& 
				(robotExclusion.allows(uri.toURL(), Constants.AGENT))&&
				(this.pagesCount.get() < Constants.MAX_CRAWLED_PAGES)&&
				(currentPagesCount < Constants.MAX_CRAWLED_PAGES)&&
				(!this.db.isUrlInDb(downloadUrl));

	    if (insert) {
	        
	        // use try catch block for recursive process  
	        try {
	            
	            // fetch the HTML code of the given URL by using the connect() and get() method and store the result in Document  
	            Document doc = Jsoup.connect(downloadUrl).get();
	            String docContentType = doc.documentType().name();
	            if(docContentType.equals("html")) {
	            	// if the URL is not present in the set, we add it to the set  
		            urlDepthLinks.add(downloadUrl);
		            System.out.println(">> Depth: " + depth + " [" + downloadUrl + "]"+ " --  "+Thread.currentThread().getName());
		            // download the webpage
		            LocalDateTime now = LocalDateTime.now();  
		            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-HH-mm-ss-SS");  
		            downloadPage(doc,dtf.format(now).toString(),downloadUrl);
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
	            System.err.println("For '" + downloadUrl + "': " + e.getMessage());
	        }
	    }
	}
	
	synchronized void downloadPage(Document doc, String fileName,String url) throws SQLException {
			try {
				//save to DB
				if(this.pagesCount.get()< Constants.MAX_CRAWLED_PAGES) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CRAWLED_WEB_PAGES_PATH+fileName+".html"));
					writer.write(doc.html());
					writer.close();
					
					int result = this.db.insertPage(fileName, url);
					if(result==1) {
						this.pagesCount.addAndGet(1);
					}
				}else {
					return;
				}
				//add to order of crawling
			} catch (IOException e) {
				System.out.println("Failed to download page" + e);
			}
			
	}
	
	
	public void run() {
		
				
		try {
			int currentCount = this.db.getPagesCount();
			while(!this.urlLinks.isEmpty() && this.pagesCount.get() < Constants.MAX_CRAWLED_PAGES && currentCount< Constants.MAX_CRAWLED_PAGES) {
				this.crawlPages(urlLinks.remove(), 0);
			}
		} catch (SQLException e) {
			
		} catch (MalformedURLException e) {
			
		}  catch (UnsupportedEncodingException e) {
			
		} catch (URISyntaxException e) {
			
		}catch(NullPointerException e){}
		finally {
			System.out.println(Thread.currentThread().getName()+" terminated !");
		}
			
		
	}
}
