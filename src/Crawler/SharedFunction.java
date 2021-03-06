/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.tika.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author PLian7156538
 */
public class SharedFunction {

    private static final Pattern CHARSET_HEADER = Pattern.compile("charset=([-_a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern TITLE_HEADER = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    //private static final Pattern KEYWORDS_TAG = Pattern.compile("<meta name\\s?=\\s?\"keywords\"\\scontent\\s?=\\s?\".*\"/?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern KEYWORDS_TAG = Pattern.compile("\\<meta name=\"keywords\" content=\"(.*)\"/>", Pattern.CASE_INSENSITIVE);
    private static final Pattern DESCRIPTION_TAG = Pattern.compile("\\<meta name=\"description\" content=\"(.*)\"/>", Pattern.CASE_INSENSITIVE);
    public final static int size = 1024;

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
        char[] buffer = new char[size];
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

    public String downloadFile(String url) {
        InputStream inputStream = null;
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        byte[] buffer;
        try {
            URL fileURL = new URL(url);
            inputStream = fileURL.openStream();
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            //System.out.println("Reading File...");
            int length = -1;
            buffer = new byte[SharedFunction.size];// buffer for portion of data from
            // connection
            while ((length = inputStream.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            inputStream.close();
            //System.out.println("File was Downloaded");

        } catch (Exception ex) {
            //throw ex;
        }
        return fileName;
    }

    public String stripPDFToText(String fileName) {
        File fileReader;
        StringWriter stringWriter = new StringWriter();
        PDDocument pdfDoc;

        try {
            fileReader = new File(fileName);

            pdfDoc = PDDocument.load(fileReader);
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setStartPage(1);
            textStripper.setEndPage(pdfDoc.getNumberOfPages());
            textStripper.writeText(pdfDoc, stringWriter);

            //System.out.println(stringWriter.toString());
            if (pdfDoc != null) {
                pdfDoc.close();
            }
            fileReader.delete();
            //bufferWriter.close();
            stringWriter.close();

        } catch (Exception ex) {
            //throw ex;
        }

        return stringWriter.toString();
    }

    public String readTextFile(String fileName) {
        FileReader fileReader;
        File file;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        try {
            file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            fileReader.close();
            bufferedReader.close();
            file.delete();
        } catch (Exception ex) {

        }
        return stringBuilder.toString();
    }

    public void getAllImage(DBConnection Conn, Document doc, String url) {
        String[] altImageList = new String[]{};

        try {
            //ocument doc = Jsoup.parse(html, charsetName.toString());
            Elements elements = doc.getElementsByTag("IMG");
            for (int imageCount = 0; imageCount < elements.size(); imageCount++) {
                //System.out.println("Sources of " + imageCount + ":" + elements.get(imageCount));
                //System.out.println(url);
                if (elements.get(imageCount).attr("alt").trim().length() > 0) {
                    //altImageList[imageCount + 1] = elements.get(imageCount).attr("alt");
                    InsertToImageDb(Conn, url, elements.get(imageCount).attr("alt"));
                }
            }

        } catch (SQLException | IOException ex) {

        }

    }

    public void getAllHeader(DBConnection Conn, Document doc, String url) {

        String tagContent;
        Elements elements = doc.select("h1,h2,h3,h4,h5");
        try {
            for (Element element : elements) {
                tagContent = Jsoup.parse(element.toString()).text();
                StringBuilder sb = new StringBuilder(tagContent);

                Element next = element.nextElementSibling();
                while (next != null && next.tagName().startsWith("h")) {
                    tagContent = Jsoup.parse(next.toString()).text();
                    sb.append(tagContent).append("\n");
                    next = next.nextElementSibling();
                }
                //System.out.println(sb);
                String[] lines = sb.toString().split("\\n");
                InsertToHeaderDb(Conn, url, lines);
            }
        } catch (Exception ex) {

        }

    }

    public void InsertToHeaderDb(DBConnection Conn, String url, String[] headerList) throws SQLException, IOException {

        String sql = "select * from contentdb where URL = '" + url + "'";
        ResultSet rs = Conn.executeStatement(sql);

        if (rs.next()) {
            //store the URL to database to avoid parsing again
            int ID = rs.getInt("ID");
            sql = "INSERT INTO `headerdb`(`ID`, `headerText`) "
                    + "VALUES(?,?);";
            PreparedStatement stmt = Conn.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (String headerText : headerList) {
                stmt.setInt(1, ID);
                stmt.setString(2, headerText.trim());
            }
            stmt.execute();

        }
    }

    public void InsertToImageDb(DBConnection Conn, String url, String altImage) throws SQLException, IOException {

        String sql = "select * from contentdb where URL = '" + url + "'";
        ResultSet rs = Conn.executeStatement(sql);

        if (rs.next()) {
            //store the URL to database to avoid parsing again
            int ID = rs.getInt("ID");
            sql = "INSERT INTO `imagedb`(`ID`, `altText`) "
                    + "VALUES(?,?);";
            PreparedStatement stmt = Conn.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, ID);
            stmt.setString(2, altImage.trim());

            stmt.execute();

        }
    }

    public static PageDetails getExtension(String url) {
        String extension = "";
        PageDetails pageDetails = new PageDetails();

        try {

            extension = url.substring(url.lastIndexOf('.') + 1);

            if (extension.indexOf('?') > 0) {
                extension = extension.substring(0,extension.indexOf('?'));
            }

            if ("".equals(extension)) {
                pageDetails.setDescription(null);
            } else {
                pageDetails.setExtension(extension);
            }

        } catch (Exception ex) {

        }
        return pageDetails;
    }

    public static PageDetails getKeywords(String html, Charset charsetName) {
        Charset charset;
        int noOfChar = 0, totalReadChar = 0;
        char[] buffer = new char[size];
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

            Matcher matcher = KEYWORDS_TAG.matcher(str);
            if (matcher.find()) {
                String tempKeywords = matcher.group(1);
                int noOfCommas = countCommas(tempKeywords);
                String[] Keywords = new String[noOfCommas];
                Keywords = tempKeywords.split("\\s*,\\s*");

                pageDetails.setPageKeywords(Keywords);
            } else {
                pageDetails.setPageKeywords(null);

            }
        } catch (Exception ex) {

        }
        return pageDetails;
    }

    public static PageDetails getExtension(String html, Charset charsetName) {
        Charset charset;
        int noOfChar = 0, totalReadChar = 0;
        char[] buffer = new char[size];
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

            Matcher matcher = KEYWORDS_TAG.matcher(str);
            if (matcher.find()) {
                String tempKeywords = matcher.group(1);
                int noOfCommas = countCommas(tempKeywords);
                String[] Keywords = new String[noOfCommas];
                Keywords = tempKeywords.split("\\s*,\\s*");

                pageDetails.setPageKeywords(Keywords);
            } else {
                pageDetails.setPageKeywords(null);

            }
        } catch (Exception ex) {

        }
        return pageDetails;
    }

    public static PageDetails getDescription(String html, Charset charsetName) {
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

            Matcher matcher = DESCRIPTION_TAG.matcher(str);
            if (matcher.find()) {
                int indexCount = matcher.group(1).indexOf('/');
                String description = matcher.group(1).substring(0, indexCount);

                pageDetails.setDescription(description);
            } else {
                pageDetails.setDescription(null);
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
