package comp7507.api_server.Entity;

import java.util.ArrayList;

/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.18
 */

public class CurrentNewsBean
{
    private int id;
    private String pubDate;
    private String pubDateStr;
    private String title;
    private String summary;
    private String infoSourse;
    private String sourceUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDateStr() {
        return pubDateStr;
    }

    public void setPubDateStr(String pubDateStr) {
        this.pubDateStr = pubDateStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInfoSourse() {
        return infoSourse;
    }

    public void setInfoSourse(String infoSourse) {
        this.infoSourse = infoSourse;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

}

