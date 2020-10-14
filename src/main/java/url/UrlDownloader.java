package url;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDownloader {
    public  UrlDownloader(String pathURL){
        writeToFile(pathURL,null);
    }
    public UrlDownloader(String pathURL, String saveFilePath){
    writeToFile(pathURL,saveFilePath);
    }
    public void writeToFile(String pathURL, String saveFilePath) {
        try {

            URL pageUrl = null;
            URLConnection connection = null;
            pageUrl = new URL(pathURL);
            connection=pageUrl.openConnection();
            connection.connect();
            if(saveFilePath==null) {
                Pattern pattern=Pattern.compile("([/]([a-zA-Z]*[а-яА-Я]*[.]?[0-9])+[/])");
                Matcher matcher= pattern.matcher(pathURL);
                String strForLength="";
                while (matcher.find()){
                    strForLength= matcher.group();
                }
                if (strForLength.length()+6==pathURL.length()) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("saveUrl.txt"));
                }else {
                    String[] splUrlString=pathURL.split(strForLength);
                    String[] splUrlStrRight=splUrlString[1].split("[?]");
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(splUrlStrRight[0]+".txt"));
                }
            }else {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFilePath));
            }


        }catch (IOException e){

        }
    }
}
