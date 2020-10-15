package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test3 {
    public static void main(String[] args) throws IOException {
        String url="https://html5book.ru/hyperlinks-in-html/";
        String url1="https://www.nastol.com.ua/download.php?img=201705/2560x1600/nastol.com.ua-225408.jpg";
        String url2="https://sun9-5.userapi.com/CAcMytJovrbh_QJmEYjwBSoB10xnUguvjGLHWg/j_uQlTKsP9Y.jpg";
        String str1=url1.substring(url1.lastIndexOf("/"),url1.length());
        System.out.println(str1);

        URL URL=new URL(url);
        HttpURLConnection urlc=(HttpURLConnection)URL.openConnection();
        urlc.setAllowUserInteraction( false );
        urlc.setDoInput( true );
        urlc.setDoOutput( false );
        urlc.setUseCaches( true );
        urlc.setRequestMethod("HEAD");
        urlc.connect();
        String mime=urlc.getContentType();
        System.out.println(mime);

        //urlc.setRequestMethod("GET");
         //mime=urlc.getRequestMethod();
        //System.out.println(mime);
        /*
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());


         */
    }
}
