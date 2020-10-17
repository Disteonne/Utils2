package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test6 {
    public static void main(String[] args) {
        String str="<img src=\"fvjkf bl.a\"";
        Pattern   pattern=Pattern.compile("<img src=\".+\"");
        Matcher matcher=pattern.matcher(str);
        //int tmp=str.indexOf("img src=\"");
        //System.out.println(str.substring(tmp,str.length()));
        //String kek="";
        while (matcher.find()){
            System.out.println(matcher.group());
            String[] kek=matcher.group().split("\"");
            String s="";
        }
    }
}
