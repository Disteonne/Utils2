package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test8 {
    public static void main(String[] args) {
        String str="<link rel=\"alternate\" type=\"application/rss+xml\"title=\" \n" +
                "Статьи с сайта htmlbook.ru\" href=\"http://htmlbook.ru/rss.xml\">";
        Pattern pattern=Pattern.compile("<link (.([\\n]+[\\t]+))+>");
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        String strk=str.substring(str.indexOf("<link rel=")+1);
        strk=strk.substring(0,strk.indexOf(">"));
        System.out.println(strk);
        System.out.println(str.indexOf("<link rel="));
    }
}
