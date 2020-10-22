package com.wzx.demo.service;

import com.wzx.demo.pojo.NetWorth;
import com.wzx.demo.util.DateUtil;
import com.wzx.demo.util.WebUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class CollectionMission implements Runnable {

    private String code;
    private String url;
    private CountDownLatch latch;
    private List<NetWorth> list;

    public CollectionMission(String code, String url, CountDownLatch latch, List<NetWorth> list) {
        this.code = code;
        this.url = url;
        this.latch = latch;
        this.list = list;
    }

    public void run() {
        Document webPage = WebUtil.getWebPage(url);

        Element jztable = webPage.getElementById("jztable");
        NetWorth netWorth = null;
        if (jztable != null) {
            String fundName = webPage.getElementsByTag("h4").first().text().trim();
            Element tbody = jztable.getElementsByTag("tbody").get(0);
            Elements trs = tbody.getElementsByTag("tr");
            Element tr = trs.get(0);
            netWorth = new NetWorth();
            netWorth.setCode(code);
            Elements tds = tr.getElementsByTag("td");
            if (tds.size() != 0) {
                String text = tds.get(0).text();
                //没有当天数据则跳过
                if (DateUtil.getYesterDayTime().equals(text)) {
                    netWorth.setCreateDate(text);
                    netWorth.setFundName(fundName);
                    String todayValue = tds.get(1).text();
                    String yesterdayValue = trs.get(1).getElementsByTag("td").get(1).text();
                    BigDecimal valueNow = new BigDecimal(todayValue);
                    BigDecimal value = new BigDecimal(yesterdayValue);
                    netWorth.setUnitValue(todayValue);
                    netWorth.setDayGrowValue(valueNow.subtract(value).setScale(4, RoundingMode.HALF_UP).doubleValue() + "");
                    netWorth.setDayGrowPercent(tds.get(3).text());
                    list.add(netWorth);
                } else {
                    System.out.println("基金: " + fundName + " 没有数据更新");
                }
            } else {
                System.out.println("该指标查询结果为空 code:" + code);
            }

        }
        latch.countDown();

    }


}
