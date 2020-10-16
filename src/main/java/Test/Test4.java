package Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test4 {
    public static void main(String[] args) throws IOException {
        //String str="C:\\Users\\huawei\\Desktop\\utils2\\text.txt";
        //BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("default.txt"));
        String str="http://espressocode.top/file-mkdir-method-in-java-with-examples/";
        System.out.println(str.substring(0,str.lastIndexOf("/")+1));
    }
}
