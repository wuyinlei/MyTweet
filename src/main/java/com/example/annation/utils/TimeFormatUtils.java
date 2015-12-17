package com.example.annation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class TimeFormatUtils {

    public static String parseToYYMMDD(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(time);
            calendar.setTime(date);
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.getTimeInMillis();   //表示从1790-1-1 00:00:00到当前时间总共经过的时间的毫秒数。
            return converToSimpleStrDate(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换成多少分钟之前的格式    ---->    5分钟前    一个小时前    一天前   前天   31天前   一年前
     *
     * @param date
     * @return
     */
    public static String converToSimpleStrDate(long date) {

        long current = System.currentTimeMillis();   //获取到当前的毫秒数
        SimpleDateFormat simpleDateFormat;
        long offSet = (current - date) / 1000;    //当前毫秒数 - 发布当时的毫秒数
        Date dt = new Date(date);
        String returnData;
        if (offSet < 5 * 60) {
            //如果是发布的5分钟之内，则输出  “刚刚”
            returnData = "刚刚";
        } else if (offSet >= 5 * 60 && offSet < 60 * 60) {
            //如果是发布的5-60分钟之内，则输出  “几分钟前”
            returnData = offSet / 60 + "分钟前";
        } else if (offSet >= 60 * 60 && offSet < 60 * 60 * 24) {
            //如果是发布的60分钟--24小时之内，则输出  “几小时前”
            returnData = (offSet / 60 / 60) + "小时前";
        } else if (offSet >= 60 * 60 * 24 && offSet < 60 * 60 * 24 * 2) {
            //如果是发布的24小时-48小时内，则输出  “昨天”
            returnData = "昨天";
        } else if (offSet >= 60 * 60 * 24 && offSet < 60 * 60 * 24 * 30) {
            //前天
            simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            returnData = simpleDateFormat.format(dt);
        } else if (offSet >= 60 * 60 * 24 * 30 && offSet < (60 * 60 * 24 * 30 * 365)) {
            //31天前
            simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            returnData = simpleDateFormat.format(dt);
        } else {
            //一年前
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            returnData = simpleDateFormat.format(dt);
        }
        return returnData;
    }
}
