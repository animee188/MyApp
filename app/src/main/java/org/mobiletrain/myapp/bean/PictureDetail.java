package org.mobiletrain.myapp.bean;

import java.io.Serializable;

/**
 * Created by liusihui on 2015／12／8.
 */
public class PictureDetail implements Serializable{
    private int id;
    private String title;
    private String authorbrief;
    private String text1;
    private String image1;
    private String text2;
    private int times;
    private long publishtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorbrief() {
        return authorbrief;
    }

    public void setAuthorbrief(String authorbrief) {
        this.authorbrief = authorbrief;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(long publishtime) {
        this.publishtime = publishtime;
    }

    @Override
    public String toString() {
        return "PictureDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorbrief='" + authorbrief + '\'' +
                ", text1='" + text1 + '\'' +
                ", image1='" + image1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", times=" + times +
                ", publishtime=" + publishtime +
                '}';
    }
}
