package com.wzx.demo.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;



public class WebUtil {


    public static Document getWebPage(String url) {
        //Htmlunit模拟的浏览器，设置css,js等支持及其它的一些简单设置
        WebClient browser = new WebClient();
        browser.getOptions().setCssEnabled(false);
        browser.getOptions().setJavaScriptEnabled(true);
        browser.getOptions().setThrowExceptionOnScriptError(false);

        //获取页面
        HtmlPage htmlPage = null;
        try {
            htmlPage = browser.getPage(url);
        } catch (IOException e) {
            System.out.println("网页: " + url + "获取失败,请检查网络,或者稍后再试");
        }
        //设置等待js的加载时间
        browser.waitForBackgroundJavaScript(1000);
        //使用xml的方式解析获取到jsoup的document对象
        if (htmlPage==null){
            System.out.println("当前网页获取为空 url:"+url);
        }
        return Jsoup.parse(htmlPage.asXml());
    }
}
