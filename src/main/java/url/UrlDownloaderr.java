package url;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class UrlDownloaderr {
    private String url = null;
    private String path = null;
    private String open = null;
    private URL webUrl;
    private String encoding = "utf-8";
    BufferedWriter bf;
    BufferedReader br;
    InputStreamReader isr;
    URLConnection connection;
    private boolean isHtmlPage = false;
    File file;
    boolean flag = false;
    boolean isNew = false;

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

    }

    public void getConnection() {
        try {
            webUrl = new URL(url);
            connection = webUrl.openConnection();
        } catch (IOException e) {
        }
    }

    public String fileName() throws IOException {
        String strForLength = url.substring(url.indexOf("//") + 2);
        strForLength = strForLength.substring(0, strForLength.indexOf("/"));
        int l = strForLength.length();
        int ll = url.length();
        if (strForLength.length() + 9 == url.length()) {
            if (isHtmlPage) {
                return "index.html";
            } else {
                if (!url.endsWith(getLast())) {
                    return "index" + getLast();//+getLast();
                } else
                    return "index";
            }
        } else {
            String tmp = url;
            if (isHtmlPage) {
                if (tmp.contains("?")) {
                    tmp = tmp.substring(0, tmp.lastIndexOf("?"));
                    tmp = tmp.substring(tmp.lastIndexOf("/") + 1);
                } else {
                    String temp=tmp;
                    if(temp.endsWith("/")){
                        temp=temp.substring(0,temp.lastIndexOf("/"));
                        tmp=temp.substring(temp.lastIndexOf("/")+1);
                    }else {
                        tmp = tmp.substring(tmp.lastIndexOf("/") + 1);
                    }
                }
                return tmp + ".html";
            } else {
                String temp=tmp;
                if(temp.endsWith("/")) {
                    temp = temp.substring(0, temp.lastIndexOf("/"));
                    tmp = temp.substring(temp.lastIndexOf("/") + 1);
                    return tmp;
                }else {
                    return tmp.substring(tmp.lastIndexOf("/") + 1);
                }
            }
        }
    }

    public void saveDefault() throws IOException {
        file = new File(fileName());
        connection.connect();
        String str = "";
        if (isHtmlPage) {
            isr = new InputStreamReader(connection.getInputStream(), encoding);
            br = new BufferedReader(isr);
            bf = new BufferedWriter(new FileWriter(file));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
            isr.close();
            br.close();
            bf.close();
        } else {
            InputStream is = webUrl.openStream();
            OutputStream os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int length;
            while ((length = is.read(bytes)) != -1) {
                os.write(bytes, 0, length);
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
        boolean b = false;
        if (mime == null) {
            return b;
        }
        b = mime.startsWith("text/html") ? true : false;
        if (b) {
            this.encoding = mime.substring(mime.lastIndexOf("=") + 1, mime.length());
        }
        return b;
    }

    public String getLast() {
        String tmp = url;
        return tmp.substring(tmp.lastIndexOf('.'));
    }

    public void savePath() throws IOException {
        connection.connect();
        isr = new InputStreamReader(connection.getInputStream(), encoding);//,encoding);
        br = new BufferedReader(isr);
        String str = "";
        file = new File(path);
        file.mkdirs();
        if (!file.exists()) {
            bf = new BufferedWriter(new FileWriter(file));
            while ((str = br.readLine()) != null) {
                bf.write(str);
            }
        }
        if (file.exists() && file.isFile()) {
            System.out.println("Замена файла 'R'. Переименовать файл 'N'");
            Scanner scanner = new Scanner(System.in);
            String tmpStr = scanner.nextLine();
            if (tmpStr.equals("R")) {
                if (isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(file, false));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                } else {
                    InputStream is = webUrl.openStream();
                    OutputStream os = new FileOutputStream(file, false);
                    byte[] bytes = new byte[2048];
                    int length;
                    while ((length = is.read(bytes)) != -1) {
                        os.write(bytes, 0, length);
                    }
                    is.close();
                    os.close();
                }
            } else if (tmpStr.equals("N")) {
                System.out.println("Введите новое имя файла");
                String tmp = scanner.nextLine();
                String editPath = path.substring(0, path.lastIndexOf("\\") + 1);
                String getEd = path.substring(path.lastIndexOf("."));
                String res = editPath + tmp + getEd;
                file = new File(res);
                if (isHtmlPage) {
                    bf = new BufferedWriter(new FileWriter(file));
                    while ((str = br.readLine()) != null) {
                        bf.write(str);
                    }
                } else {
                    InputStream is = webUrl.openStream();
                    OutputStream os = new FileOutputStream(file);
                    byte[] bytes = new byte[2048];
                    int length;
                    while ((length = is.read(bytes)) != -1) {
                        os.write(bytes, 0, length);
                    }
                    is.close();
                    os.close();
                }
            }
        }
        if (file.isDirectory() && !file.isFile()) {
            file = new File(path + "\\" + this.fileName());
            if (isHtmlPage) {
                bf = new BufferedWriter(new FileWriter(file));
                while ((str = br.readLine()) != null) {
                    bf.write(str);
                }
                String skek = "";

                bf.close();
                br.close();
                isr.close();

            } else {
                InputStream is = webUrl.openStream();
                OutputStream os = new FileOutputStream(file);
                byte[] bytes = new byte[2048];
                int length;
                while ((length = is.read(bytes)) != -1) {
                    os.write(bytes, 0, length);
                }
                is.close();
                os.close();
            }
        }
    }

    public void openResource() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException ex) {
            }
        }
    }

    public void actions() throws IOException {
        if (!flag) {
            input();//вводим данные
        }
        getConnection();
        this.isHtmlPage = isHtml();
        if (path.toLowerCase().equals("no")) {
            saveDefault();
            if (!isNew) {
                if (isHtmlPage) {
                    //savePicturesAndLink("<img","src=");
                    savePicturesAndLink("<link","href=");
                }
            }
            if(!open.toLowerCase().equals("no")){
                openResource();
            }
        }

        if (!path.toLowerCase().equals("no")) {
            savePath();
            if (!isNew) {
                if (isHtmlPage) {
                    //savePicturesAndLink("<img","src=");
                    savePicturesAndLink("<link","href=");
                }
            }
            if(!open.toLowerCase().equals("no")){
                openResource();
            }
        }
    }
                                // <img     //src=
                                // <link    //href=
    public void savePicturesAndLink(String start,String startLink) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        file.mkdirs();
        String tmp = "";
        ArrayList<String> list = new ArrayList<>();
        while ((tmp = bufferedReader.readLine()) != null) {
            String newTmp = tmp;
            while (newTmp.contains(start)) {//|| tmp.contains("<link")) {
                String img = newTmp.substring(newTmp.indexOf(start));
                img=img.substring(0,img.indexOf(">")+1);//<img...src="....>
                String replaceString=img;
                if (!replaceString.contains("http")) {
                    newTmp  = newTmp.substring(newTmp.indexOf(replaceString));
                    newTmp = newTmp.substring(replaceString.length());
                    continue;
                }
                String newURL=img.substring(img.indexOf(startLink)+startLink.length());
                if(newURL.startsWith("\"")) {
                    newURL = newURL.substring(newURL.indexOf("\"") + 1);
                    newURL = newURL.substring(0, newURL.indexOf("\""));
                }else {
                    newTmp  = newTmp.substring(newTmp.indexOf(replaceString));
                    newTmp = newTmp.substring(replaceString.length());
                    continue;
                }
                UrlDownloaderr urlDown = new UrlDownloaderr();
                urlDown.flag = true;
                urlDown.url = newURL;
                urlDown.isNew = true;
                urlDown.open = "NO";
                if (file.isDirectory() && !file.isFile()) {
                    urlDown.path = this.path + "\\" + fileName() + "_" + "files";
                } else {
                    String s=file.getAbsolutePath();
                    urlDown.path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")) + "\\" + fileName() + "_" + "files";
                    String ss= urlDown.fileName();
                }
                urlDown.actions();
                int length = this.path.length();
                String localPath = urlDown.file.getAbsolutePath().substring(length + 1);
                String result = replaceString.replace(newURL, localPath);
                tmp = tmp.replace(replaceString, result);
                newTmp = newTmp.substring(newTmp.indexOf(replaceString));
                newTmp = newTmp.substring(replaceString.length() + 1);
            }
            list.add(tmp);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String s : list
        ) {
            bufferedWriter.write(s);
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        UrlDownloaderr urlDownloaderr = new UrlDownloaderr();
        urlDownloaderr.actions();
    }
}
