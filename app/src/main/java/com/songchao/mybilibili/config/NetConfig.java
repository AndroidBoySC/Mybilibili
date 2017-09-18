package com.songchao.mybilibili.config;

/**
 * Author: SongCHao
 * Date: 2017/8/23/14:48
 * Email: 15704762346@163.com
 */

public interface NetConfig {
    //糗事百科接口
    String GET_PATH = "http://m2.qiushibaike.com/article/list/text?page=";
    //下面这种处理利用占位符
    //String.format(NetConfig.URL_USER_ICON,user.getId()/10000,user.getId(),user.getIcon()
    String URL_USER_ICON="http://pic.qiushibaike.com/system/avtnew/%d/%d/thumb/%s";
    // 专享
    String URL_HOT_SUGGEST = "http://m2.qiushibaike.com/article/list/suggest?page=";
    // 视频
    String URL_ETLITE_DAYYY = "http://m2.qiushibaike.com/article/list/video?page=";
    // 纯图
    String URL_ETLITE_DAY = "http://m2.qiushibaike.com/article/list/image?page=";
    // 最新
    String URL_HOT_LATEST = "http://m2.qiushibaike.com/article/list/latest?page=";
    //评论
    String URL_COMMENT = "http://m2.qiushibaike.com/article/%d/comments?page=2";
    //新闻
    String URL_DOUBLENEWS = "http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=20&callback=?&justList=1";
    //
    String URL_NEW = "http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=100";
}
