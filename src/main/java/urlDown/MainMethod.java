package urlDown;

import java.io.IOException;

public class MainMethod {
    public static void main(String[] args) {
        try {
            Input input = new Input();
            String[] argumentForValid = {"https://i.artfile.ru/3000x2000_844381_%5Bwww.ArtFile.ru%5D.jpg"};
            input.input(argumentForValid);
            ActionsWithURL actionsWithURL = new ActionsWithURL(input.getUrl());
            //actionsWithURL.getConnectionUrl();
            GetFileName getFileName = new GetFileName(input.getUrl(), actionsWithURL);
            SaveFile saveFile = new SaveFile(input, getFileName);
            saveFile.save();
        }catch (IOException exception){
            System.out.println("error main");
        }

    }
}
