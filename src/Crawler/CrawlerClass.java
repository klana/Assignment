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
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.tika.io.IOUtils;
/**
         *
         * @author Ellen
         */

public class CrawlerClass extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
    private static final Pattern toCrawl = Pattern.compile(".*\\.(txt|docx|doc|html|aspx|php|css|js)$");
    
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            return false;
        }
        
        if (toCrawl.matcher(href).matches()) {
            //return href.startsWith(url);
            return true;
        }

        return true;
        //return href.startsWith("http://www.sim.edu.sg/");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        //int docid = page.getWebURL().getDocid();
        //String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        //String pageName = page.getWebURL().
        //String path = page.getWebURL().getPath();
        //String subDomain = page.getWebURL().getSubDomain();
        //String parentUrl = page.getWebURL().getParentUrl();
        //String anchor = page.getWebURL().getAnchor();

        if (page.getWebURL().getURL().contains("txt")) {
            try {
                URL urls = new URL(page.getWebURL().getURL());
                InputStream webIS = urls.openStream();

                System.out.println(IOUtils.toString(webIS));

                webIS.close();

            } catch (Exception ex) {

            }
        }

        //System.out.println("Docid: " + docid);
        //System.out.println("URL: " + url);
        System.out.println("Domain: " + domain);
        //System.out.println("Sub-domain: " + subDomain);
        //System.out.println("Path: " + path);
        //System.out.println("Parent page: " + parentUrl);
        //System.out.println("Anchor text: " + anchor);
        //logger.debug("Docid: {}", docid);
        //logger.info("URL: {}", url);
        //logger.debug("Domain: '{}'", domain);
        //logger.debug("Sub-domain: '{}'", subDomain);
        //logger.debug("Path: '{}'", path);
        //logger.debug("Parent page: {}", parentUrl);
        //logger.debug("Anchor text: {}", anchor);

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            System.out.println(text);
            System.out.println(html);

            System.out.println("Text length:" + text.length());
            System.out.println("Html length:" + html.length());
            System.out.println("Number of outgoing links:" + links.size());
            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());

        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            System.out.println("Response headers:");
            logger.debug("Response headers:");

            for (Header header : responseHeaders) {
                System.out.println(header.getName() + ":" + header.getValue());
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
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
