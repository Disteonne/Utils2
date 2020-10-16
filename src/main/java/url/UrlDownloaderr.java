package url;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDownloaderr {
    private String url = null;
    private String path = null;
    private String open = null;
    private URL webUrl;
    private String encoding="utf-8";
    BufferedWriter bf;
    BufferedReader br;
    InputStreamReader isr;
    URLConnection connection;
    private  boolean isHtmlPage=false;
    File file;
    public void input() {
        System.out.println("Если не желаете вводить данные ,то введите NO");
        Scanner scanner = new Scanner(System.in);
        System.out.println("URL:");
        this.url = scanner.nextLine();
        if (this.url.equals("")) {
            throw new IllegalStateException();
        }
        System.out.println("Path:");
        this.path = scanner.nextLine();
        System.out.println("Open:");
        this.open = scanner.nextLine();
        //scanner.close();
        try {
            webUrl = new URL(url);
            connection = webUrl.openConnection();
        } catch (IOException e) {
        }
    }

    public String fileName() throws IOException {
        String strForLength = url.substring(url.indexOf("//")+2);
        strForLength=strForLength.substring(0,strForLength.indexOf("/"));
        int l=strForLength.length();
        int ll=url.length();
        if (strForLength.length() +9 == url.length() ) {
            if(isHtmlPage) {
                return "saveUrl.html";
            }else {
                if(!url.endsWith(getLast())) {
                    return "saveUrl"+getLast();//+getLast();
                }else
                    return "saveUrl";
            }
        } else {
                String tmp= url;
                if(isHtmlPage) {
                    if (tmp.contains("?")) {
                        tmp = tmp.substring(0, tmp.lastIndexOf("?"));
                        tmp = tmp.substring(tmp.lastIndexOf("/") + 1);
                    } else {
                        tmp = tmp.substring(tmp.lastIndexOf("/") + 1);
                    }
                    return tmp+".html";
                } else {
                    return tmp.substring(tmp.lastIndexOf("/")+1);
            }
        }
    }

    public void saveDefault() throws IOException {
        file=new File(fileName());
        connection.connect();
        String str = "";
        if(isHtmlPage) {
            isr = new InputStreamReader(connection.getInputStream(),encoding);
            br = new BufferedReader(isr);
            bf = new BufferedWriter(new FileWriter(file));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
            isr.close();
            br.close();
            bf.close();
        }else {
            InputStream is=webUrl.openStream();
            OutputStream os=new FileOutputStream(file);
            byte[] bytes=new byte[2048];
            int length;
            while ((length=is.read(bytes))!=-1){
                os.write(bytes,0,length);
            }
            is.close();
            os.close();
        }

    }

    public boolean isHtml() throws IOException {
        HttpURLConnection urlc = (HttpURLConnection) webUrl.openConnection();
        urlc.setAllowUserInteraction(false);
        urlc.setDoInput(true);
        urlc.setDoOutput(false);
        urlc.setUseCaches(true);
        urlc.setRequestMethod("HEAD");
        urlc.connect();
        String mime = urlc.getContentType();
        boolean b=false;
        b=mime.startsWith("text/html") ? true : false;
        if(b) {
            this.encoding = mime.substring(mime.lastIndexOf("=") + 1, mime.length());
        }
        return b;
    }
    public String getLast(){
        String tmp=url;
        return  tmp.substring(tmp.lastIndexOf('.'));
    }
    public void savePath() throws IOException {
        connection.connect();
        isr = new InputStreamReader(connection.getInputStream(),encoding);//,encoding);
        br = new BufferedReader(isr);
        String str="";
        file = new File(path);
        file.mkdir();
        if (!file.exists()) {
            bf=new BufferedWriter(new FileWriter(file));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
        }
        if(file.exists() && file.isFile()){
            System.out.println("Замена файла 'R'. Переименовать файл 'N'");
            Scanner scanner=new Scanner(System.in);
            String tmpStr=scanner.nextLine();
            if (tmpStr.equals("R")) {
                if(isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(file, false));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                }else {
                    InputStream is=webUrl.openStream();
                    OutputStream os=new FileOutputStream(file,false);
                    byte[] bytes=new byte[2048];
                    int length;
                    while ((length=is.read(bytes))!=-1){
                        os.write(bytes,0,length);
                    }
                    is.close();
                    os.close();
                }
            } else if (tmpStr.equals("N")) {
                System.out.println("Введите новое имя файла");
                String tmp=scanner.nextLine();
                String editPath=path.substring(0,path.lastIndexOf("\\")+1);
                String getEd=path.substring(path.lastIndexOf("."));
                String res=editPath+tmp+getEd;
                file=new File(res);
                if(isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(file));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                }else {
                    InputStream is=webUrl.openStream();
                    OutputStream os=new FileOutputStream(file);
                    byte[] bytes=new byte[2048];
                    int length;
                    while ((length=is.read(bytes))!=-1){
                        os.write(bytes,0,length);
                    }
                    is.close();
                    os.close();
                }
            }
        }
        if (file.isDirectory() && !file.isFile()){
            file=new File(path+"\\"+fileName());
            if(isHtmlPage) {
                bf = new BufferedWriter(new FileWriter(file));
                while ((str = br.readLine()) != null) {
                    bf.write(str);
                }
                bf.close();
                br.close();
                isr.close();
            }else {
                InputStream is=webUrl.openStream();
                OutputStream os=new FileOutputStream(file);
                byte[] bytes=new byte[2048];
                int length;
                while ((length=is.read(bytes))!=-1){
                    os.write(bytes,0,length);
                }
                is.close();
                os.close();
            }
        }
    }
    public void openResource(){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();;
                desktop.open(file);
            } catch (IOException ex) {}
        }
    }

    public void actions() throws IOException {
        input();//вводим данные
        this.isHtmlPage=isHtml();
        if(path.toLowerCase().equals("no") && open.toLowerCase().equals("no")){
            saveDefault();
        }
        if(!path.toLowerCase().equals("no")){
            savePath();
        }
        if(!path.toLowerCase().equals("no") && !open.toLowerCase().equals("no")){
            savePath();
            openResource();
        }
    }

    public static void main(String[] args) throws IOException {
        UrlDownloaderr urlDownloaderr=new UrlDownloaderr();
        urlDownloaderr.actions();
    }
}
