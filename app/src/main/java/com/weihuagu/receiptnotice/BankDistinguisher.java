package com.weihuagu.receiptnotice;
import android.app.Notification;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;


public class BankDistinguisher{
        public BankDistinguisher(){
        }
        public String distinguishByNum(String num){
                Map <String, String> map=ExternalInfoUtil.getBanksMessageNum();
                String whatsback=map.get(num);
                if(whatsback!=null)
                        return whatsback;
                else
                        return "";

        }

        public String distinguishByMessageContent(String content){
                if(content.contains("银行")&&ExternalInfoUtil.containsBankmessageFeature(content))
                {
                        Stack<String> alternativebank = new Stack<String>();
                        Map <String,String> map=ExternalInfoUtil.getAllBanksNameMap();
                        for (String key : map.keySet()) {
                                if(content.contains(key))
                                        alternativebank.push(key);
                        }
                        if(alternativebank.isEmpty())
                                return "";
                        else
                                return map.get(alternativebank.peek());
                }
                else
                        return null;



        }

        public  String extractMoney(String content){
                Pattern pattern = Pattern.compile("(收入|存入|转入|入账)(\\(.*\\))?(\\d{1,3}(,\\d{2,3})*(\\.\\d{0,2})?)元?");
                Matcher matcher = pattern.matcher(content);
                if(matcher.find()){
                        String tmp=matcher.group();
                        Pattern patternnum = Pattern.compile("((\\d{1,3}(,\\d{2,3})*(\\.\\d{0,2})?))");
                        Matcher matchernum = patternnum.matcher(tmp);
                        if(matchernum.find())
                                return matchernum.group();
                        return null;
                }else
                        return null;


        }

        public String extractCardNum(String content){
                String pattern = "(尾号\\d{4}的?(卡|账号|账户))";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(content);
                if(m.find())
                        return m.group();
                else
                        return "";


        }














}

