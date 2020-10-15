package url;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
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
            BufferedWriter bufferedWriter;
            InputStreamReader readIn = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(readIn);
            String data="";

            if(saveFilePath==null) {
                String nameFile=nameFile(pathURL);
                while ((data=bufferedReader.readLine())!=null){
                    bufferedWriter=new BufferedWriter(new FileWriter(nameFile));
                }
            }else {
                File file = new File(saveFilePath);
                if (!file.exists()) {
                    bufferedWriter = new BufferedWriter(new FileWriter(saveFilePath));
                }
                if (file.exists() && file.isFile()) {
                    System.out.println("Замена файла 'R'. Переименовать файл 'N'");
                    Scanner scanner = new Scanner(System.in);
                    String tmpStr = scanner.nextLine().toUpperCase();
                    if (tmpStr.equals("R")) {
                        bufferedWriter = new BufferedWriter(new FileWriter(saveFilePath, false));
                    } else if (tmpStr.equals("N")) {
                        bufferedWriter = new BufferedWriter(new FileWriter(tmpStr));
                    }
                }
                if (file.isDirectory() && !file.isFile()) {
                    bufferedWriter = new BufferedWriter(new FileWriter(nameFile(pathURL)));
                }
            }


        }catch (IOException e){

        }
    }
    public  String  nameFile(String pathURL){
        Pattern pattern=Pattern.compile("([/]([a-zA-Z]*[а-яА-Я]*[.]?[0-9])+[/])");
        Matcher matcher= pattern.matcher(pathURL);
        String strForLength="";
        while (matcher.find()){
            strForLength= matcher.group();
        }
        if (strForLength.length()+6==pathURL.length()) {
            return  "saveUrl.txt";
        }else {
            String[] splUrlString=pathURL.split(strForLength);
            String[] splUrlStrRight=splUrlString[1].split("[?]");
            return splUrlString[0]+".txt";
        }
    }
}
