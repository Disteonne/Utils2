package url;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pattenrDomen {
    public static void main(String[] args) {
        Pattern pattern=Pattern.compile("([/]{2}([a-zA-Z]*[а-яА-Я]*[.]?[0-9]*)+[/])");
        String pathURL="https://vk.com/dianochka_kek?z=photo365995385_457278379%2Falbum365995385_0%2Frev";
        Matcher matcher=pattern.matcher(pathURL);
        String strForLength="";
        while (matcher.find()){
            strForLength= matcher.group();
        }
        if (strForLength.length()+6==pathURL.length()) {
          System.out.println("Name of file is "+"text.txt");
        }else {
            String[] splUrlString=pathURL.split(strForLength);
            String[] splUrlStrRight=splUrlString[1].split("[?]");
            System.out.println("Name of file is "+splUrlStrRight[0]);
        }
    }
}
