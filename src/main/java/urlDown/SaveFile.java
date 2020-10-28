package urlDown;

import java.io.*;
import java.util.Scanner;

public class SaveFile {
    private Input object;
    private GetFileName name;
    private ActionsWithURL actionsWithURL;

    public SaveFile(Input object, GetFileName name) {
        this.object = object;
        this.name = name;
        this.actionsWithURL = name.getActions();
    }

    public void save() throws IOException {
        try {
            actionsWithURL.getConnection().connect();
        } catch (IOException exception) {
            System.out.println("Connect error");
        }
        if (object.getPath() == null) {
            File localFile = createFile(name.getName());
            treadInpOut(localFile);
        } else {
            File newFile = createFile(object.getPath());
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
                    newFile = new File(res);
                    treadInpOut(newFile);
                }
            }
            if (newFile.isDirectory() && !newFile.isFile()) {
                newFile = new File(object.getPath() + "\\" + name.getName());
                treadInpOut(newFile);
            }
        }
    }

    private File createFile(String pathOrLocalName) {
        File file = new File(pathOrLocalName);
        //file.mkdirs();
        return file;
    }

    public void treadInpOut(File file) throws IOException {
        //actionsWithURL.getConnection().connect();
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
                System.out.println("Tread error");
            }
        }
    }
}
