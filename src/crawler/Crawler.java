/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Ellen
 */
public class Crawler {

    /**
     * @param args the command line arguments
     */
    private static int maxNoOfPage = 20;
    private Set<String> setOfVisitedPage = new HashSet<String>();
    private List<String> listOfPage = new LinkedList<String>();
    
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Document htmlDocument;
    private List<String> listOflinks = new LinkedList<String>();
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void searchPage(String URL)
    {
        while (setOfVisitedPage.size() < maxNoOfPage)
        {
            String currentURL;
            
            //If the List is Empty
            if (listOfPage.isEmpty())
            {
                // Add page to Set
                currentURL = URL;
                setOfVisitedPage.add(URL);
            }
            else
            {
                currentURL = getNextURL();
            }
            
            
            
        }
    }
    
    private String getNextURL()
    {
        String nextURL = "";
     
        while(listOfPage.contains(nextURL))
        {
            nextURL = listOfPage.remove(0);
        }
        
        setOfVisitedPage.add(nextURL);
        return nextURL;
    }
    
    private boolean crawl(String URL)
    {
        try
        {
            Connection jSoupconnection = Jsoup.connect(URL).userAgent(USER_AGENT);
            //Make HTTP request and get the response
            Document htmlDocument = jSoupconnection.get();
            this.htmlDocument = htmlDocument;
            
            if(jSoupconnection.response().statusCode() == 200)
            {
                 Elements linksOnPage = htmlDocument.select("a[href]");
                
                 for(Element listOflinks : linksOnPage)
                 {
                    this.listOflinks.add(listOflinks.absUrl("href"));
                }
                 
                 return true;
            }
            
            
            if(!jSoupconnection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            
        }
         catch(IOException ioe)
        {
            return false;
        }
    }
}
