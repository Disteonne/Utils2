package url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDownloaderr {
    private String url=null;
    private String path=null;
    private String open=null;
    private URL webUrl;
    private String encoding="";
    BufferedWriter bf;
    BufferedReader br;
    InputStreamReader isr;
    URLConnection connection;
    public void  input(){
        System.out.println("Если не желаете вводить данные ,то введите NO");
        Scanner scanner=new Scanner(System.in);
        System.out.println("URL:");
        this.url=scanner.nextLine();
        if(this.url.equals("")){
            throw new IllegalStateException();
        }
        System.out.println("Path:");
        this.path=scanner.nextLine();
        System.out.println("Open:");
        this.open=scanner.nextLine();
        scanner.close();
        try {
            webUrl = new URL(url);
            connection=webUrl.openConnection();
        }catch (IOException e){
        }
    }
    public String fileName(){
        Pattern pattern=Pattern.compile("([/]([a-zA-Z]*[а-яА-Я]*[.]?[0-9])+[/])");
        Matcher matcher= pattern.matcher(url);
        String strForLength="";
        while (matcher.find()){
            strForLength= matcher.group();
        }
        if (strForLength.length()+6==url.length()) {
            return  "saveUrl.txt";
        }else {
            String[] splUrlString=url.split(strForLength);
            String[] splUrlStrRight=splUrlString[1].split("[?]");
            return splUrlString[0]+".txt";
        }
    }
    public void saveDefault() throws IOException{
        connection.connect();
        isr=new InputStreamReader(connection.getInputStream());
        br=new BufferedReader (isr);
        String str="";
        bf=new BufferedWriter(new FileWriter("default.txt"));
        while ((str=br.readLine())!=null){
            bf.write(str);
        }
    }
    public boolean isHtml() throws IOException {
        HttpURLConnection urlc=(HttpURLConnection)webUrl.openConnection();
        urlc.setAllowUserInteraction( false );
        urlc.setDoInput( true );
        urlc.setDoOutput( false );
        urlc.setUseCaches( true );
        urlc.setRequestMethod("HEAD");
        urlc.connect();
        String mime=urlc.getContentType();
        this.encoding=mime.substring(mime.lastIndexOf("=",mime.length()));
        return mime.startsWith("text/html") ? true:false;
    }
}
