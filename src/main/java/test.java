

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
import java.net.URLEncoder;

/**
 * ʹ��Jsoup�����������������
 *
 * @tag: url:https://algs4.cs.princeton.edu/home/
 * Created by monster on 2015/12/11.
 */
public class test {
    public static String spider(String url){
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
                String urlTail = linkAndTitle.attr("href");
                if(urlTail.indexOf('.')==0)
                    urlTail = urlTail.substring(urlTail.indexOf('/')+1);
                String theUrl = "http://news.gzhu.edu.cn/"+urlTail;
                //�õ�����
                String theTitle = linkAndTitle.attr("title");
                System.out.println("���⣺" + theTitle);
                //����
                source.download(theUrl,theTitle+".html");
            }

            //��ȡ��һҳ
            Elements nextPage = doc.getElementsByClass("Next");
            String nextTail = nextPage.attr("href");
            String nextP;
            if(nextTail.startsWith("ttgd"))
                nextP = "http://news.gzhu.edu.cn/"+nextTail;
            else
                nextP = "http://news.gzhu.edu.cn/ttgd/"+nextTail;
            return nextP;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
            String url = "http://news.gzhu.edu.cn/ttgd.htm";
            do{
                url = spider(url);
            }while (url!=null);
    }

}
