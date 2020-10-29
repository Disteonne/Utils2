package urlDown;

import java.io.IOException;

/**
 * Task implementation logic:
 * -The setting of parameters;
 * -Working with URLs;
 * -Getting the file name;
 * -Writing this file;
 * -Saving all images and links inside this file;
 * -Opening the source file.
 */
public class Start {
    public String name;
    public boolean isNew=false;
    public void start(String[] args){
        try {
            Input input = new Input();
            input.input(args);
            ActionsWithURL actionsWithURL = new ActionsWithURL(input.getUrl());
            GetFileName getFileName = new GetFileName(input.getUrl(), actionsWithURL);
            SaveFile saveFile = new SaveFile(input, getFileName);
            this.name=getFileName.getName();
            saveFile.save();
            if(!isNew && actionsWithURL.isHtmlPage()){
              SaveImgAndLink overrideWithNewSRC=new SaveImgAndLink(saveFile);
              overrideWithNewSRC.saveSrcAndLink("src=\"");
                overrideWithNewSRC.saveSrcAndLink("href=\"");
                if(input.getOpen()!=null){
                    OpenResource.openFile(saveFile.getNewFile());
                }
            }
        }catch (IOException exception){
            System.out.println("error main");
        }

    }
}
