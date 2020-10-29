package urlDown;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for finding links or images ,
 * writing them to a specific folder (according to the task) ,
 * and changing their path to a local path.
 */
public class SaveImgAndLink {
    Pattern newSrc = Pattern.compile("<img.+?>");
    Pattern newLink = Pattern.compile("<link.+?\\/>");
    private SaveFile saveFile;

    public SaveImgAndLink(SaveFile saveFile) {
        this.saveFile = saveFile;
    }

    /**
     * Search for the corresponding attribute in the text.
     * Extracting the url of the link from it and then saving it with overwriting
     * the path in the source file to the local path of the saved file.
     * @param attribute  Attribute for searching in the text (src/href)
     */
    public void saveSrcAndLink(String attribute) {
        Pattern newPattern;
        if(attribute=="src=\""){
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
                        String newUrlSrc = newLine.substring(newLine.indexOf(attribute) + attribute.length());
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
