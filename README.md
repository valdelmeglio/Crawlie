# Crawlie

Steps:

* fetches the content for a given start URL
* extracts the links from the content.
* goes on to crawl the extracted links (back to step 1)
* the crawler stops after 1000 found URLs

## Methods

```Java
public static List<URL> crawl(URL starting_url, int limit)
```
Crawl takes two arguments: the first URL to crawl with
and the urls number to stop the execution.
It returns a List<URL> of the urls crawled from the 
starting one.

```Java
private static LinkedHashSet<URL> extractUrls(URL url)
```
It takes an URL as input and extracts all the links from 
it using regular expressions.
It returns them as a LinkedHashSet<URL>.

```Java
 private static String fetchContent(URL url)
```
It fetches the given URL and returns it as a string.

```Java
private static String normalizeUrl(String url)
```
This is a simple URL normalizer that takes the URL given  
as input and makes easy to transform it to an URl object.

## Running
You can both run the code with your preferred IDE or running the compiled Jar.
Open a terminal window and move to the project folder, then type:

``` Bash
$ java -jar crawler.jar
``` 
Enter a starting url to begin the crawling:
``` Bash
$ Please enter a starting url:
``` 
And specify how many results of the thousand do you want to show:
``` Bash
$ Please specify how many results do you want to show:
``` 
Then just wait for the result you wanted the crawler to show.

Eg:
``` Bash
$ java -jar crawler.jar
Please enter a starting url:
www.repubblica.it
Please specify how many results do you want to show:
5
Please wait while I'm crawling...

Crawler discovered 1000 urls in 55861 ms.
Showing first 5 results: 
1) http://www.repubblica.it
2) http://cdn.download.repubblica.it/cless/main/nazionale/2013-v1/img/common/favicon/favicon-152.png
3) http://www.repubblica.it/statickpm3/common/xml/opensearch_desc.xml
4) http://cdn.download.repubblica.it/cless/main/nazionale/2013-v1/img/common/favicon/favicon.ico
5) https://plus.google.com/+repubblica
``` 