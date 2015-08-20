/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import java.util.HashSet;

/**
 *
 * @author PLian7156538
 */
public class PageDetails {

    //Get Page Title
    String pageTitle;
    String[] pageKeywords;
    String description;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPageKeywords() {
        return pageKeywords;
    }

    public void setPageKeywords(String[] pageKeywords) {
        this.pageKeywords = pageKeywords;
    }

}
