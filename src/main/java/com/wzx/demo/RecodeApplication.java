package com.wzx.demo;

import com.wzx.demo.pojo.NetWorth;
import com.wzx.demo.service.CollectionMission;
import com.wzx.demo.util.ExcelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RecodeApplication {

    private static final String  prefix= "http://fund.eastmoney.com/f10/jjjz_";
    private static final String  suffix= ".html";

    public static void main(String[] args) {
        if (args.length==0){
            System.out.println("请输入查询的基金编码,以英文状态的逗号隔开");
        }

        String arg = args[0];
        String[] split;
        if (arg.contains(",")){
            split = arg.split(",");
        }else {
            split=new String[]{arg};
        }

        if (split.length>0){
            CountDownLatch latch = new CountDownLatch(split.length);
            List<NetWorth> list = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();
            for (String code : split) {
                CollectionMission mission = new CollectionMission(code, prefix + code + suffix, latch, list);
                threads.add(new Thread(mission));
            }
            threads.forEach(Thread::start);
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ExcelUtil.creatTableAndSave(list);

            list.forEach(s->{
                System.out.println("基金编码: "+s.getCode()+" \t基金名称: "+s.getFundName()+"\t更新时间: "
                        +s.getCreateDate()+"\t单元净值: "+s.getUnitValue()
                        +"\t日增长: "+s.getDayGrowValue()+"\t日增长率: "+s.getDayGrowPercent());
            });
            System.out.println("执行结束");


        }

    }
}
