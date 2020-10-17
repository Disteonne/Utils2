package url;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test5 {
    /*
    File file=new File("C:\\Users\\huawei\\Desktop\\TEST\\mam\\op\\kek\\saveUrl.html");
    Document page = Jsoup.parse(file);
    Element imageElement = page.select("img").get(0);
    String link = imageElement.attr("src");
     */
    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\huawei\\Desktop\\TEST\\mam\\op\\link.html");
        File filetmp = file;
        String tmp = "";
        //BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(filetmp));
        ArrayList<String> list=new ArrayList<>();
        BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
        while ((tmp=bufferedReader.readLine())!=null){
            System.out.println(tmp);
            System.out.println("kek");
            String res="";
            if(tmp.contains("<img src=")){
                String[] splStr=tmp.split("<img src=\"");
                for (int i = 0+1; i < splStr.length-1; i++) {
                    if(splStr[i]=="\"" && splStr[i-1]=="<img src="){
                        System.out.println(splStr[i+1]);
                        splStr[i+1]="kkkkkkkkkkkkkk";
                        break;
                    }
                }
                String tmppp="";
                for (int i = 0; i < splStr.length; i++) {
                    tmppp+=splStr[i];
                }
                list.add(tmppp);
                continue;
            }
            list.add(tmp);
        }

    }
}
