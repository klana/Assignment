/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

/**
 *
 * @author PLian7156538
 */
public class SharedFunction {
    public static int countCommas(String CrawlDomains) {
        int noOfCommas = 0;
        
        for (int i = 0; i < CrawlDomains.length(); i++) {
            if (CrawlDomains.charAt(i) == ',') {
                noOfCommas++;
            }
        }
        return noOfCommas;
    }
}
