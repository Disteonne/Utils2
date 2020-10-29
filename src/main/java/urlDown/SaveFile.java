package urlDown;

import java.io.*;
import java.util.Scanner;

/**
 * Class write to a file.
 */
public class SaveFile {
    private Input object;
    private GetFileName name;
    private ActionsWithURL actionsWithURL;
    private File newFile;

    public SaveFile(Input object, GetFileName name) {
        this.object = object;
        this.name = name;
        this.actionsWithURL = name.getActions();
    }

    /**
     * Writing a file according to the task condition:
     * -saving by default, if the path is not specified;
     * -Saving to the specified path, taking into account the
     * correctness of the specified directory or the presence
     * of another file on this path(replacing or changing the name).
     * @throws IOException    If the connection to the url fails
     */
    public void save() throws IOException {
        try {
            actionsWithURL.getConnection().connect();
        } catch (IOException exception) {
            System.out.println("Connect error");
        }
        if (object.getPath() == null) {
           this.newFile= createFile(name.getName());
            treadInpOut(newFile);
        } else {
            this.newFile = createFile(object.getPath());
            newFile.mkdirs();
            if (!newFile.exists()) {
                treadInpOut(newFile);
            }
            if (newFile.exists() && newFile.isFile()) {
                System.out.println("To replace 'R'. To rename 'N'");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();
                if (choice.equals("R")) {
                    treadInpOut(newFile);
                } else if (choice.equals("N")) {
                    System.out.println("Input new name");
                    String newName = scanner.nextLine();
                    String editPath = object.getPath().substring(0, object.getPath().lastIndexOf("\\") + 1);
                    String getEd = object.getPath().substring(object.getPath().lastIndexOf("."));
                    String res = editPath + newName + getEd;
                    this.newFile = new File(res);
                    treadInpOut(newFile);
                }
            }
            if (newFile.isDirectory() && !newFile.isFile()) {
                this.newFile = new File(object.getPath() + "\\" + name.getName());
                treadInpOut(newFile);
            }
        }
    }

    private File createFile(String pathOrLocalName) {
        File file = new File(pathOrLocalName);
        return file;
    }

    /**
     * Method for writing to a file (depends on whether the file is an html page or not).
     * @param file  File to write
     * @throws IOException
     */
    public void treadInpOut(File file) throws IOException {
        if (actionsWithURL.isHtmlPage()) {
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(actionsWithURL.getConnection().getInputStream(), actionsWithURL.getEncoding(actionsWithURL.isHtmlPage())));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                String tmp = "";
                while ((tmp = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(tmp);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            try(InputStream inputStream = actionsWithURL.getUrl().openStream();
                OutputStream outputStream = new FileOutputStream(file)) {
                byte[] bytes = new byte[2048];
                int length;
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                }
            } catch (IOException exception) {
            }
        }
    }

    public GetFileName getName() {
        return name;
    }

    public File getNewFile() {
        return newFile;
    }
}
