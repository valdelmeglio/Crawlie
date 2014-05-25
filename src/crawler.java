import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

/* --------------------------------------------------------
 * Java crawler:
 * 
 * - Fetches the content for a given start URL.
 * - Extracts the links from the content.
 * - Goes on to crawl the extracted links (back to step 1).
 * - The crawler should stop after 1000 found URLs.
 * 
 * Author: Valerio Del Meglio (valerio.delmeglio@gmail.com)
 * --------------------------------------------------------
 */
public class crawler {

    /* --------------------------------------------------------
     * Crawl takes two arguments: the first URL to crawl with
     * and the urls number to stop the execution.
     * 
     * It returns a List<URL> of the urls crawled from the 
     * starting one.
     * --------------------------------------------------------
     */
    public static List<URL> crawl(URL starting_url, int limit) {
        List<URL> urls = new ArrayList<URL>(limit);
        urls.add(starting_url);
        
        int i = 0;
        while (urls.size() < limit && i < urls.size()) {
            URL currentUrl = urls.get(i);
            for (URL url : extractUrls(currentUrl)) {
            	if (!urls.contains(url)) { // Checking for already added urls
                    urls.add(url);
                    if (urls.size() == limit) {
                        break;
                    }
                }
            }
            i++;
        }
        return urls;
    }


    
    /* --------------------------------------------------------
  	 * It takes an URL as input and extracts all the links from 
  	 * it using regular expressions.
  	 * 
  	 * It returns them as a LinkedHashSet<URL>
     * --------------------------------------------------------
     */  
    private static LinkedHashSet<URL> extractUrls(URL url) {
        LinkedHashSet<URL> links = new LinkedHashSet<URL>();
        Pattern p = Pattern.compile("href=\"((http://|https://|www).*?)\"", Pattern.DOTALL);
        Matcher m = p.matcher(fetchContent(url));

        while (m.find()) {
            String linkStr = normalizeUrl(m.group(1));
            try {
                URL link = new URL(linkStr);
                links.add(link);
            }
            catch (MalformedURLException e) {
                System.err.println("Page at " + url + " has a invalid link : " + linkStr + ".");
            }
        }
        return links;
    }

    /* --------------------------------------------------------
     * It fetches the given URL and returns it as a string
     * --------------------------------------------------------
     */
    private static String fetchContent(URL url) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = inputStream.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            inputStream.close();
        }
        catch (IOException e) {
            System.err.println("Something went wrong while fetching " + url);
        }
        return stringBuilder.toString();
    }

    /* --------------------------------------------------------
     * This is a simple URL normalizer that takes the URL given  
     * as input and makes easy to transform it to an URl object.
     * --------------------------------------------------------
     */
    private static String normalizeUrl(String url) {
    	
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        return url;
        
    	
        /*
    	String urlString = new String();
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
    	try {
        	URL urlToNormalize = new URL(url);
        	URI uri = new URI(urlToNormalize.getProtocol(), urlToNormalize.getUserInfo(), urlToNormalize.getHost(), urlToNormalize.getPort(), urlToNormalize.getPath(), urlToNormalize.getQuery(), urlToNormalize.getRef());
			urlString = uri.toString();
			
		} 
    	  catch (URISyntaxException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.err.println("You provided an invalid URL.");
			
		}
    	return urlString;	*/
    }


    
    public static void main(String[] args) {
        try {
        
        	
        	Scanner user_input = new Scanner( System.in );
        	URL starting_url;
        	System.out.println("Please enter a starting url:");
        	starting_url = new URL(normalizeUrl(user_input.next()));
        	
        	// Trying to connect to the given URL, discarding URLs without domain
            URLConnection conn = starting_url.openConnection();
            conn.connect();

        	int results;
        	System.out.println("Please specify how many results do you want to show:");
        	results = Integer.parseInt(user_input.next());
        	
          
            int limit = 1000;
            System.out.println("Please wait while I'm crawling...");
            System.out.println("");	
            
            long start = System.currentTimeMillis();
            List<URL> discovered = crawler.crawl(starting_url, limit);
            long finish = System.currentTimeMillis();
            System.out.println("Crawler discovered " + discovered.size() + " urls in " + (finish - start) + " ms.");

            if (discovered.size() < results) {
              System.out.println(results + " is bigger than the discovered links, showing first " + discovered.size() + " results: ");
              results = discovered.size();	
            } else {
              System.out.println("Showing first " + results + " results: ");	
            }
            

            int i = 1;
            Iterator<URL> iterator = discovered.iterator();
            while (iterator.hasNext() && i <= results) {     
              System.out.println(i + ") " + iterator.next());	           	
              i++;
            }
        } 
        // Multiple exceptions in one block are only supported in java 1.7
        catch (MalformedURLException e) {
            System.err.println("You provided an invalid URL.");
        }
        
        catch (NumberFormatException e){
        	System.err.println("You provided an invalid number.");
        }
        
        catch (IOException e) {
        	System.err.println("The connection couldn't be established."); 
        } 
        
    }
    
    
}

