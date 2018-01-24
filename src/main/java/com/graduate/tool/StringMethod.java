package com.graduate.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMethod {
    public static String strictTrim(String str){
        String regEx="[`~!@#$%^&*()+=|{}:;\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？" +
                "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789 ]";
        Pattern c = Pattern.compile(regEx);
        Matcher mc=c.matcher(str);
        String result =  mc.replaceAll("").trim();
        return result;
    }
}
