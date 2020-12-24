package com.jtchen.thread;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/************************************************
 *
 * @author jtchen
 * @date 2020/12/24 21:37
 * @version 1.0
 ************************************************/
public class MultiDownload implements Runnable{
    private final String url;
    private final String filename;

    public MultiDownload(String url, String filename) {
        this.filename = filename;
        this.url = url;
    }

    @Override
    public void run() {
        try (var out = new FileOutputStream("./src/main/resources/" + filename)) {
            URLConnection connection = new URL(url).openConnection
                    (/*new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080))*/);
            Reader r = new InputStreamReader
                    (new BufferedInputStream(connection.getInputStream()), StandardCharsets.ISO_8859_1);
            int c;
            while ((c = r.read()) != -1) {
                out.write((char) c);
            }
            r.close();
        } catch (MalformedURLException e) {
            System.err.println("is not a URL");
        } catch (IOException e) {
            System.err.println("下载错误！");
            System.err.println(e.toString());
        }
    }
}
