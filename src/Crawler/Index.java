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
        String setStorageFolder;
        int setNumberOfCrawler;
        String setCrawlDomains;
        int setPolitenessDelay;
        int setMaxDepth;
        int setMaxPages;
        boolean setIncludeBinaryContent;
        boolean setResumableCrawling;

        System.out.println("First time Setup Required! ");
        System.out.println("Please enter the Parameter: \n");

        System.out.println("Enter Storage Folder (Foler where intermediate crawl data is stored)");
        setStorageFolder = cin.nextLine();

        System.out.println("Enter Number Of Crawler (Number of concurrent threads)");
        setNumberOfCrawler = cin.nextInt();

        cin.nextLine();

        System.out.println("Enter Crawl Domain(eg http://sim.edu.sg/,http://https://moodle.uowplatform.edu.au/login/index.php)");
        setCrawlDomains = cin.nextLine();
        //String tempCrawlDomains = cin.nextLine();
        //int commasCount = countCommas(tempCrawlDomains);
        //setCrawlDomains = new String[commasCount];
        //setCrawlDomains = tempCrawlDomains.split(",");

        System.out.println("Enter Politeness Delay (Default 1000; So that no more than 1 request per second will be sent.)");
        setPolitenessDelay = cin.nextInt();

        System.out.println("Enter Max Depth (-1 for unlimited)");
        setMaxDepth = cin.nextInt();

        System.out.println("Enter Max Pages (-1 for unlimited)");
        setMaxPages = cin.nextInt();

        System.out.println("Enter Include Binary Content (True to include content of pdf.)");
        setIncludeBinaryContent = cin.nextBoolean();

        System.out.println("Enter Resumable Crawling (True to resume crawling from previousl crawl.)");
        setResumableCrawling = cin.nextBoolean();

        args = new String[8];
        args[0] = setStorageFolder;
        args[1] = String.valueOf(setNumberOfCrawler);
        args[2] = setCrawlDomains;
        args[3] = String.valueOf(setPolitenessDelay);
        args[4] = String.valueOf(setMaxDepth);
        args[5] = String.valueOf(setMaxPages);
        args[6] = String.valueOf(setIncludeBinaryContent);
        args[7] = String.valueOf(setResumableCrawling);

        CrawlerController config = new CrawlerController();
        config.setUp(args);

    }

}
