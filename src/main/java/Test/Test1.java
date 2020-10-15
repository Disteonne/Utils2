package Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test1 {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\huawei\\Desktop\\taxt.txt");
        System.out.println(file.isFile());
        URL pageURL = null;
        URLConnection connection = null;
        String pathUrl = "https://javadevblog.com/kak-proverit-sushhestvovanie-fajla-v-java.html";
        pageURL = new URL(pathUrl);
        connection = pageURL.openConnection();
        connection.connect();

        InputStreamReader ReadIn = new InputStreamReader(connection.getInputStream());
        BufferedReader BufData = new BufferedReader(ReadIn);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("text.txt"));
        String urlData = "";
        while ((urlData = BufData.readLine()) != null) {
            bufferedWriter.write(urlData);
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
        ReadIn.close();
        BufData.close();
    }
}
