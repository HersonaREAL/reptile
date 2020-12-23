

/************************************************
 *
 * @author jtchen
 * @date 2020/12/22 23:16
 * @version 1.0
 ************************************************/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * ʹ��Jsoup�����������������
 *
 * @tag: url:https://algs4.cs.princeton.edu/home/
 * Created by monster on 2015/12/11.
 */
public class test {

    public static void main(String[] args) {
        final String url = "http://news.gzhu.edu.cn/ttgd.htm";
        try {

            Document doc = Jsoup.connect(url).
                    proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080))).get();

            //����keyΪid,value��line_u��ͷ�õ�Ԫ��
            Elements container = doc.getElementsByAttributeValueStarting("id","line_u");
            //��������doc�Ա�dom����
            Document containerDoc = Jsoup.parse(container.toString());
            //AllIwant!
            Elements ALLIwant = containerDoc.select("a[href]");

            //������ȡ����Ԫ�� ���д���
            for(Element linkAndTitle:ALLIwant){
                //���ɵ�ַ
                String theUrl = "http://news.gzhu.edu.cn/"+linkAndTitle.attr("href");
                //�õ�����
                String theTitle = linkAndTitle.attr("title");
                System.out.println("���⣺" + theTitle);
                //����
                source.download(theUrl,theTitle+".html");
            }



            //String articleTitle = containerDoc.getElementsByAttribute("title").text();
//            String authorName = containerDoc.getElementById("authorName").text();
//            String time = containerDoc.select("span").first().text();
//            String imgphotoUrl=containerDoc.select("img").get(1).attr("src");
            //System.out.println("���⣺" + articleTitle); //����
//            System.out.println("���ߣ�"+authorName); //����
//            System.out.println("����ʱ�䣺"+time); //����ʱ��
//            System.out.println("����ͷ���url��"+imgphotoUrl); //����ʱ��

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
