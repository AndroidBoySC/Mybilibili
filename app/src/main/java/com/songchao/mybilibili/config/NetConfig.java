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
}
