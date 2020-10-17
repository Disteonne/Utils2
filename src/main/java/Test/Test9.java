package Test;

import java.io.File;

public class Test9 {
    public static void main(String[] args) {

        String str="fvjfvnjkdnvf <img class=\"fit-picture\"\n" +
                "     src=\"/media/cc0-images/grapefruit-slice-332-332.jpg\"\n" +
                "     alt=\"Grapefruit slice atop a pile of other slices\"> jdcajnbd kjafd";
        String img=str.substring(str.indexOf("<img"));
        System.out.println(img);
        img=img.substring(0,img.indexOf(">")+1);
        System.out.println(img);
        img=img.substring(img.indexOf("src="));
        System.out.println(img);
        img=img.substring(img.indexOf("\"")+1);
        System.out.println(img);
        img=img.substring(0,img.indexOf("\""));
        System.out.println(img);
    }
}
