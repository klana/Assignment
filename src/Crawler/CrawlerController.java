/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import static Crawler.SharedFunction.countCommas;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ellen
 */
public class CrawlerController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    public void setUp(String[] args) throws Exception {

        if (args.length < 2) {
            return;
        }

        //Setting before Crawling (default)
        String setStorageFolder = "data";
        int setNumberOfCrawler = 30;
        int setPolitenessDelay = 1000;
        int setMaxDepth = -1;
        int setMaxPages = -1;
        boolean setIncludeBinary = true;

        String tempDomain = args[0];
        int commasCount = countCommas(tempDomain);

        String[] setCrawlDomains = new String[commasCount];
        setCrawlDomains = tempDomain.split(",");

        boolean setResumableCrawling = Boolean.parseBoolean(args[1]);

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(setStorageFolder);
        config.setPolitenessDelay(setPolitenessDelay);
        config.setMaxDepthOfCrawling(setMaxDepth);
        config.setMaxPagesToFetch(setMaxPages);
        config.setIncludeBinaryContentInCrawling(setIncludeBinary);
        config.setResumableCrawling(setResumableCrawling);
        config.setMaxDownloadSize(30000000);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        for (String domainName : setCrawlDomains) {
            controller.addSeed(domainName);
        }

        controller.start(CrawlerClass.class, setNumberOfCrawler);

    }

}
