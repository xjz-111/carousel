package com.leslie.carousel_view;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 11:39
 */
public class Utils {
    public static String[] imgs = new String[]
            {       "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnews.chinaxiaokang.com%2Fuploads%2Fimage%2F20170426%2F1493170290679914.jpg&refer=http%3A%2F%2Fnews.chinaxiaokang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624176889&t=1eb6ccc333dd23b433bc9f6f15330e31",
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F10572014182%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624176889&t=21a66caba42d1a5d5b2f7d88506cb088",
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201903%2F12%2F20190312205108_4UwWX.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624176889&t=66fc164d5ca548b021af524427d60627",
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fupload.trends.com.cn%2F2019%2F0604%2F1559619714257.jpg&refer=http%3A%2F%2Fupload.trends.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624176889&t=54ca457e17d67fd2319a05d202ef44cc",
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinakd10123%2F706%2Fw1075h1231%2F20200401%2F906b-irpunai4320158.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624176889&t=99fdfbceab6a6ea2555498bade3adf64",};
    public static Integer[][] params = new Integer[][]{new Integer[]{422, 300}, new Integer[]{653, 748}, new Integer[]{1600, 2097}, new Integer[]{600, 780}, new Integer[]{1075, 1231}};

    public static List<String> getList1(){
        List<String> list = getList();
        for (int i = 0; i < 2; i++){
            list.add(imgs[i % imgs.length]);
        }
        return list;
    }

    public static List<String> getList2(){
        List<String> list = getList();
        for (int i = 0; i < 2; i++){
            list.add(imgs[i % imgs.length]);
        }
        return list;
    }


    private static <T> List<T> getList(){
        return new ArrayList<>();
    }
}
