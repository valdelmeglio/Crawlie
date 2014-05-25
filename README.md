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
