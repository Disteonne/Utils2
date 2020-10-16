package url;

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
    private String encoding;
    BufferedWriter bf;
    BufferedReader br;
    InputStreamReader isr;
    URLConnection connection;
    private  boolean isHtmlPage=false;

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
        scanner.close();
        try {
            webUrl = new URL(url);
            connection = webUrl.openConnection();
        } catch (IOException e) {
        }
    }

    public String fileName() throws IOException {
        String strForLength = url.substring(8,url.length());
        //String[] strTmpArray=strForLength.split("/");
        strForLength=strForLength.substring(0,strForLength.indexOf("/"));
        //strForLength=strTmpArray[0];
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
            if (tmp.contains("?")) {
                tmp=tmp.substring(0,tmp.lastIndexOf("?"));
                tmp=tmp.substring(tmp.lastIndexOf("/")+1);
                int i=5;
                //tmp=tmp.substring(1,tmp.length()-1);
                //tmp=tmp.substring(tmp.lastIndexOf("/"));
                //tmp = tmp.substring(1, tmp.lastIndexOf("?"));
            }else {
                tmp=tmp.substring(tmp.lastIndexOf("/")+1);
            }
            if(isHtmlPage) {
                return tmp + ".html";
            }else {
                String getl=getLast();
                //if(url.endsWith(getLast())) {
                 //   return tmp;//+getLast();
                //}else {
                    return tmp+getLast();
                //}
            }
        }
    }

    public void saveDefault() throws IOException {
        connection.connect();

        String str = "";
        if(isHtmlPage) {
            isr = new InputStreamReader(connection.getInputStream(),encoding);
            br = new BufferedReader(isr);
            bf = new BufferedWriter(new FileWriter(fileName()));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
            isr.close();
            br.close();
            bf.close();
        }else {
            InputStream is=webUrl.openStream();
            OutputStream os=new FileOutputStream(fileName());
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
        this.encoding = mime.substring(mime.lastIndexOf("=")+1, mime.length());
        return mime.startsWith("text/html") ? true : false;
    }
    public String getLast(){
        String tmp=url;
        return  tmp.substring(tmp.lastIndexOf('.'));
    }
    public void savePath() throws IOException {
        connection.connect();
        isr = new InputStreamReader(connection.getInputStream());//,encoding);
        br = new BufferedReader(isr);

        String str="";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
            bf=new BufferedWriter(new FileWriter(path));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
        }
        if(file.exists() && file.isFile()){
            System.out.println("Замена файла 'R'. Переименовать файл 'N'");
            Scanner scanner = new Scanner(System.in);
            String tmpStr = scanner.nextLine().toUpperCase();
            if (tmpStr.equals("R")) {
                if(isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(path, false));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                }else {
                    InputStream is=webUrl.openStream();
                    OutputStream os=new FileOutputStream(path,false);
                    byte[] bytes=new byte[2048];
                    int length;
                    while ((length=is.read(bytes))!=-1){
                        os.write(bytes,0,length);
                    }
                    is.close();
                    os.close();
                }
            } else if (tmpStr.equals("N")) {
                System.out.println("Введите название файла");
                String tmp=scanner.nextLine();
                if(isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(tmp));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                }else {
                    InputStream is=webUrl.openStream();
                    OutputStream os=new FileOutputStream(fileName());
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
            String name=fileName();
            //C:\Users\huawei\Desktop\TEST\java_tests
            if(isHtmlPage) {
                bf = new BufferedWriter(new FileWriter(path + "\\" + name));
                while ((str = br.readLine()) != null) {
                    bf.write(str);
                }
                bf.close();
                br.close();
                isr.close();
            }else {
                InputStream is=webUrl.openStream();
                OutputStream os=new FileOutputStream(path + "\\" + name);
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

    public void actions() throws IOException {
        input();//вводим данные
        this.isHtmlPage=isHtml();
        if(path.toLowerCase().equals("no") && open.toLowerCase().equals("no")){
            saveDefault();
        }
        if(!path.toLowerCase().equals("no")){
            savePath();
        }
    }

    public static void main(String[] args) throws IOException {
        UrlDownloaderr urlDownloaderr=new UrlDownloaderr();
        urlDownloaderr.actions();
    }
}
