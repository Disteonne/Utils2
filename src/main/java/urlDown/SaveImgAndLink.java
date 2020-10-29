package urlDown;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveImgAndLink {
    Pattern newSrc = Pattern.compile("<img.+?>");
    Pattern newLink = Pattern.compile("<link.+?\\/>");
    private SaveFile saveFile;

    public SaveImgAndLink(SaveFile saveFile) {
        this.saveFile = saveFile;
    }

    public void saveSRC(String teg) {
        Pattern newPattern;
        if(teg=="src=\""){
            newPattern=newSrc;
        }else
            newPattern=newLink;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile.getNewFile()))) {
            BufferedWriter bufferedWriter = null;
            String tegImgLink;
            while ((tegImgLink = bufferedReader.readLine()) != null) {
                Matcher matcher = newPattern.matcher(tegImgLink);
                String newLine = "";
                while (matcher.find()) {
                    newLine = matcher.group();//найденный тег <...>
                    if (newLine.contains("http")) {
                        String newUrlSrc = newLine.substring(newLine.indexOf(teg) + teg.length());
                        if (newUrlSrc.indexOf("\"") != -1) {
                            newUrlSrc = newUrlSrc.substring(0, newUrlSrc.indexOf("\""));//url нового объекта
                            String nameFile = saveFile.getName().getName();//имя старого объекта
                            String pathNewFileSrc = saveFile.getNewFile().getAbsolutePath().substring(0, saveFile.getNewFile().getAbsolutePath().lastIndexOf("\\")) + "\\" + nameFile + "_" + "files";
                            String[] arrayArgs = {newUrlSrc, pathNewFileSrc};
                            Start startForNewObj = new Start();
                            startForNewObj.isNew = true;
                            startForNewObj.start(arrayArgs);
                            String localAddress = saveFile.getNewFile().getAbsolutePath().substring(saveFile.getNewFile().getAbsolutePath().lastIndexOf("\\") + 1) + nameFile + "_" + "files" + "\\" + startForNewObj.name;
                            tegImgLink = tegImgLink.replace(newUrlSrc, localAddress);
                        }
                    } else
                        continue;
                }
                bufferedWriter = new BufferedWriter(new FileWriter(saveFile.getNewFile()));
                bufferedWriter.write(tegImgLink);
            }
            bufferedWriter.close();
        } catch (IOException exception) {
            System.out.println("error file");
        }
    }
}
