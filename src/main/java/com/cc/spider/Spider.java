package com.cc.spider;


import com.jtchen.thread.MultiDownload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
/************************************************
 *
 * @author jtchen
 * @date 2020/12/22 23:16
 * @version 1.0
 ************************************************/
public class Spider {
    public static String spider(String url) {
        try {
            Document doc = Jsoup.connect(url).
                    /*proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080))).*/get();
            //根据key为id,value以line_u开头得到元素
            Elements container = doc.getElementsByAttributeValueStarting("id", "line_u");
            //重新生成doc以便dom操作
            Document containerDoc = Jsoup.parse(container.toString());
            //All I want!
            Elements LinksAndTitles = containerDoc.select("a[href]");
            Elements Times = containerDoc.select("span[class]");
            if (LinksAndTitles.size() != Times.size())
                throw new IllegalArgumentException("时间和标题数目不同, 抓包异常! ");

            //遍历爬取到的元素 进行处理
            for (int i = 0; i < Times.size(); i++) {
                //生成地址
                String urlTail = LinksAndTitles.get(i).attr("href");
                String time = Times.get(i).toString().substring(19, 29);
                if (urlTail.indexOf('.') == 0)
                    urlTail = urlTail.substring(urlTail.indexOf('/') + 1);

                String theUrl = "http://news.gzhu.edu.cn/" + urlTail;
                //得到标题
                String theTitle = LinksAndTitles.get(i).attr("title");
                System.out.println("时间: " + time);
                System.out.println("标题: " + theTitle);
                //下载
//                GetSource.download(theUrl, theTitle + ".html");
                Runnable runnable = new MultiDownload(theUrl, theTitle + ".html");
                new Thread(runnable).start();
            }

            //获取下一页
            Elements nextPage = doc.getElementsByClass("Next");
            String nextTail = nextPage.attr("href");
            String nextP;
            if (nextTail.startsWith("ttgd"))
                nextP = "http://news.gzhu.edu.cn/" + nextTail;
            else
                nextP = "http://news.gzhu.edu.cn/ttgd/" + nextTail;
            return nextP;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "http://news.gzhu.edu.cn/ttgd.htm";

        do {
            url = spider(url);
        } while (url != null);


        /*
        for (int i = 0; i < 50; i++) {
            url = spider(url);
        }

         */
    }

}