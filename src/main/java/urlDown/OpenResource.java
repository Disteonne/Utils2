package urlDown;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Opening a file using the original url
 */
public class OpenResource {
    /**
     * @param file  File received from the input url
     */
    public static void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException ex) {
                System.out.println("Error opening the file");
            }
        }
    }
}
