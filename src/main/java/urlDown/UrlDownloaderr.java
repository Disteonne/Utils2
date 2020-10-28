package urlDown;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class UrlDownloaderr {
/*
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


    public void savePath() throws IOException {
        connection.connect();
        isr = new InputStreamReader(connection.getInputStream(), encoding);
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
            System.out.println("To replace 'R'. To rename 'N'");
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
                System.out.println("Input new name");
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

    public void actions() throws IOException {
        if (!flag) {
            input();
        }
        getConnection();
        this.isHtmlPage = isHtml();
        if (path.toLowerCase().equals("no")) {
            saveDefault();
            if (!isNew) {
                if (isHtmlPage) {
                    savePicturesAndLink("<img","src=");
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
                    savePicturesAndLink("<img","src=");
                    savePicturesAndLink("<link","href=");
                }
            }
            if(!open.toLowerCase().equals("no")){
                openResource();
            }
        }
    }
    public void savePicturesAndLink(String start,String startLink) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        file.mkdirs();
        String tmp = "";
        ArrayList<String> list = new ArrayList<>();
        while ((tmp = bufferedReader.readLine()) != null) {
            String newTmp = tmp;
            while (newTmp.contains(start)) {
                String img = newTmp.substring(newTmp.indexOf(start));
                img=img.substring(0,img.indexOf(">")+1);//<img/link...src="....>
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
                url.UrlDownloaderr urlDown = new url.UrlDownloaderr();
                urlDown.flag = true;
                urlDown.url = newURL;
                urlDown.isNew = true;
                urlDown.open = "NO";
                if (file.isDirectory() && !file.isFile()) {
                    urlDown.path = this.path + "\\" + fileName() + "_" + "files";
                } else {
                    urlDown.path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")) + "\\" + fileName() + "_" + "files";
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
        url.UrlDownloaderr urlDownloaderr = new url.UrlDownloaderr();
        urlDownloaderr.actions();
    }
}
*/
}