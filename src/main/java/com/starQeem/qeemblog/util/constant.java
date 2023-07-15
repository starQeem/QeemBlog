package com.starQeem.qeemblog.util;


/**
 * @Date: 2023/5/6 9:14
 * @author: Qeem
 */
public class constant {
    public final static int PAGE_SIZE = 7;  //数据条数
    public final static int MESSAGE_PAGE_SIZE = 10; //留言板一页的数据条数
    public final static int SEARCH_PAGE_SIZE = 10000000; //搜索的默认数据条数
    public final static int PAGE_NUM = 1;  //默认页码
    public final static int ZERO = 0;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String BLOG_DETAIL_KEY = "QeemBlog:blogDetail:";
    public static final String TAG_KEY = "QeemBlog:Tag";
    public static final String TYPE_KEY = "QeemBlog:Type";
    public static final int BLOG_DETAIL_TTL = 60*60*24*7;
    public static final int TAG_TTL = 60*60*24*7;
    public static final int TYPE_TTL = 60*60*24*7;
}
