package url;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenFileTest {
    public static void main(String[] args) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                File myFile = new File("example.txt");
                desktop.open(myFile);
            } catch (IOException ex) {}
        }
    }
}
