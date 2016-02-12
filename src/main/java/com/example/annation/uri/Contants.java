package com.example.annation.uri;


/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class Contants {

    public static final String APP_KEY = "3744475598";// 应用的APP_KEY
    public static final String SECRET_KEY = "03adbac738c601470df87550d679c6a5";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 应用的回调页
    public static final String SCOPE =                               // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static class API {
        public static final String HOME_BASE_URL = "https://api.weibo.com/2/";

        public static final String PUBLIC_TIMELINE = HOME_BASE_URL + "public_timeline.json";

        /**
         * 首页微博
         */
        public static final String HOME_TIME_LINE = HOME_BASE_URL + "statuses/home_timeline.json";

        /**
         * 我的首页微博
         */
        public static final String USER_TIME_LINE = HOME_BASE_URL + "statuses/user_timeline.json";

        /**
         * 转发微博
         */
        public static final String STATUS_REPOST = HOME_BASE_URL + "statuses/repost.json";

        /**
         * 评论
         */
        public static final String COMMENT_CREATE = HOME_BASE_URL + "comments/create.json";

        /**
         * 数据展示
         */
        public static final String COMMENT_SHOW = HOME_BASE_URL + "comments/show.json";

        /**
         * 评论微博
         */
        public static final String MENTIONS = HOME_BASE_URL + "statuses/mentions.json";

        /**
         * 我的评论
         */
        public static final String COMMENTS_BY_ME = HOME_BASE_URL + "comments/by_me.json";

        /**
         * 收藏微博
         */
        public static final String FAVOURITES = HOME_BASE_URL + "favorites.json";

        /**
         * 用户信息
         */
        public static final String USER_INFO = HOME_BASE_URL + "users/show.json";
    }
}

