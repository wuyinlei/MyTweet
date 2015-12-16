package com.example.annation.status;

/**
 * Created by ruolan on 2015/12/16.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class StatusEntity {


    /**
     * created_at : Wed Dec 16 16:36:21 +0800 2015
     * id : 3920715919443716
     * mid : 3920715919443716
     * idstr : 3920715919443716
     * text : 很好。
     * source_allowclick : 0
     * source_type : 1
     * source : <a href="http://app.weibo.com/t/feed/2trInO" rel="nofollow">红米Note 4G</a>
     * favorited : false
     * truncated : false
     * in_reply_to_status_id :
     * in_reply_to_user_id :
     * in_reply_to_screen_name :
     */

    private String created_at;
    private long id;
    private String mid;
    private String idstr;
    private String text;
    private int source_allowclick;
    private int source_type;
    private String source;
    private boolean favorited;
    private boolean truncated;
    private String in_reply_to_status_id;
    private String in_reply_to_user_id;
    private String in_reply_to_screen_name;

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource_allowclick(int source_allowclick) {
        this.source_allowclick = source_allowclick;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getMid() {
        return mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getText() {
        return text;
    }

    public int getSource_allowclick() {
        return source_allowclick;
    }

    public int getSource_type() {
        return source_type;
    }

    public String getSource() {
        return source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }
}
