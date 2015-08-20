/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.io.IOUtils;

/**
 *
 * @author PLian7156538
 */
public class SharedFunction {

    private static final Pattern CHARSET_HEADER = Pattern.compile("charset=([-_a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern TITLE_HEADER = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    public static int countCommas(String CrawlDomains) {
        int noOfCommas = 0;

        for (int i = 0; i < CrawlDomains.length(); i++) {
            if (CrawlDomains.charAt(i) == ',') {
                noOfCommas++;
            }
        }
        return noOfCommas;
    }

    public static PageDetails getPageTitle(String html, Charset charsetName) {
        Charset charset;
        int noOfChar = 0, totalReadChar = 0;
        char[] buffer = new char[1024];
        StringBuilder str = new StringBuilder();
        PageDetails pageDetails = new PageDetails();
        
        try {
            if (charsetName == null) {
                charset = Charset.defaultCharset();
            } else {
                charset = charsetName;
            }

            InputStream inputStream = IOUtils.toInputStream(html, charset.toString());
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream, charset));

            // read until EOF or first 8192 characters
            while (totalReadChar < 8192 && (noOfChar = buffReader.read(buffer, 0, buffer.length)) != -1) {
                str.append(buffer, 0, noOfChar);
                totalReadChar += noOfChar;
            }
            buffReader.close();

            // extract the title
            Matcher matcher = TITLE_HEADER.matcher(str);
            if (matcher.find()) {
                /* replace any occurrences of whitespace (which may
                 * include line feeds and other uglies) as well
                 * as HTML brackets with a space */
                //return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                pageDetails.setPageTitle(matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim());
            } else {
                pageDetails.setPageTitle(null);
                //return null;
            }

        } catch (Exception ex) {

        }
        return pageDetails;
    }

    public static ContentType getContentTypeHeader(String headerValue) {
        ContentType contentType = new ContentType();
        int charaSetCount = headerValue.indexOf(";");

        if (charaSetCount != -1) {
            contentType.setContentType(headerValue.substring(0, charaSetCount));
            Matcher matcher = CHARSET_HEADER.matcher(headerValue);
            if (matcher.find()) {
                contentType.setCharsetName(matcher.group(1));
            }
        } else {
            contentType.setContentType(headerValue);
        }

        return contentType;
    }

    public static Charset getCharset(ContentType contentType) {
        if (contentType != null && contentType.charsetName != null && Charset.isSupported(contentType.charsetName)) {
            return Charset.forName(contentType.charsetName);
        } else {
            return null;
        }
    }

}
