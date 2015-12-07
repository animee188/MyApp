package org.mobiletrain.myapp.bean;

/**
 * Created by liusihui on 2015/12/6.
 */
public class Text_detail_info {
    private String title;       //题目
    private String author;      //作者
    private String times;       //浏览次数
    private String summary;     //前记
    private String text;        //内容
    private String authorbrief; //作者简介
    private String image;       //图片地址

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorbrief() {
        return authorbrief;
    }

    public void setAuthorbrief(String authorbrief) {
        this.authorbrief = authorbrief;
    }
}
