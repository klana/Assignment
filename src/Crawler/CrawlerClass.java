/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.http.Header;

/**
 *
 * @author Ellen
 */
public class CrawlerClass extends WebCrawler {

    private static final Pattern SKIP_CRAWL_PATTERN = Pattern.compile(".*\\.(bmp|gif|jpg|png|ppt|pptx)$");
    private static final Pattern TO_CRAWL_PATTERN = Pattern.compile(".*\\.(txt|docx|doc|html|aspx|php|css|js|pdf|ps|)$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches defined skipCrawlPattern.
        if (SKIP_CRAWL_PATTERN.matcher(href).matches()) {
            return false;
        }

        // Do not ignore the url if it has an extension that matches defined toCrawlPattern
        if (TO_CRAWL_PATTERN.matcher(href).matches()) {
            return true;
        }

        //Do not ignore the url to those without extension.
        return true;
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        String domain = page.getWebURL().getDomain();
        String URL = page.getWebURL().getURL();
        ContentType contentType;
        Charset charset = null;
        PageDetails pageDetails;

        System.out.println("Domain: " + domain);
        System.out.println("URL: " + URL);
        
        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            System.out.println("Response headers:");
            logger.debug("Response headers:");

            for (Header header : responseHeaders) {
                //System.out.println(header.getName() + ":" + header.getValue());
                logger.debug("\t{}: {}", header.getName(), header.getValue());

                if ("Content-Type".equals(header.getName())) {
                    if (header.getName() == null) {

                    } else {
                        contentType = SharedFunction.getContentTypeHeader(header.getValue());
                        charset = SharedFunction.getCharset(contentType);
                    }
                }
            }
        }

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            pageDetails = SharedFunction.getPageTitle(html, charset);
            System.out.println("Page Title: " + pageDetails.getPageTitle());
            
            pageDetails = SharedFunction.getKeywords(html, charset);
            pageDetails = SharedFunction.getDescription(html, charset);

          
            System.out.println(text);
            //System.out.println(html);

            System.out.println("Text length:" + text.length());
            System.out.println("Html length:" + html.length());
            System.out.println("Number of outgoing links:" + links.size());
            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());
        }

        logger.debug("=============");
    }
}
/*public static void main(String[] args) 
 {
        
 // TODO code application logic here
        
 /*Connection conx = null;
 Statement stmt = null;
 //String Driver="";
        
 String URL = "jdbc:mysql://localhost:3306/";
 String db ="contentdb";
 String u_name = "root";
 String u_pwd="";
 String query ="";
        
 try
 {
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 conx = DriverManager.getConnection(URL+db,u_name,u_pwd);
            
            
 if(conx!= null)
 {
 stmt = conx.createStatement();
                
 }
            
 }
 catch(Exception ex)
 {
        
 }
 finally
 {
 try
 {
 conx.close();
 }
 catch(Exception ex)
 {
                
 }
 }*/
    //}
