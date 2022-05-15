package utils;

public class Constants {
	public static final int MAX_DEPTH = 2; //this is the max depth of the crawler when crawling
	public static final int MAX_CRAWLED_PAGES = 10; // the max number of crawled pages
	public static final String AGENT = "ShamsAgent";
	
	public static final String CRAWLED_WEB_PAGES_PATH = "data/pages/";
	public static final String CRAWLING_SEED_PATH = "data/initial_seed.txt";
	public static final String CRAWLED_WEB_PAGES_FILE_PATH = "data/pages/";
	public static final String DB_CONNECTION_STRING = "jdbc:mysql://localhost:3306/searchengine";
	
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "root";
	
}
