/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import java.util.Scanner;

/**
 *
 * @author PLian7156538
 */
public class Index {

    public static void main(String[] args) throws Exception {
        Scanner cin = new Scanner(System.in);
        String setCrawlDomains;
        boolean setResumableCrawling;

        System.out.println("Some Setup Required! ");
        System.out.println("Please Enter the parameter : \n");

        System.out.println("Enter Crawl Domain(eg http://sim.edu.sg/,http://https://moodle.uowplatform.edu.au/login/index.php)");
        setCrawlDomains = cin.nextLine();

        System.out.println("Enter Resumable Crawling (True to resume crawling from previousl crawl.)");
        setResumableCrawling = cin.nextBoolean();

        args = new String[2];
        args[0] = setCrawlDomains;
        args[1] = String.valueOf(setResumableCrawling);

        CrawlerController config = new CrawlerController();
        config.setUp(args);

    }

}
