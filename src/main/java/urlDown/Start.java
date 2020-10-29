package urlDown;

import java.io.IOException;

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
              overrideWithNewSRC.saveSRC("src=\"");
                overrideWithNewSRC.saveSRC("href=\"");
                if(input.getOpen()!=null){
                    OpenResource.openFile(saveFile.getNewFile());
                }
            }
        }catch (IOException exception){
            System.out.println("error main");
        }

    }
}
