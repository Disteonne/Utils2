package archiveComparator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Archive {
    ArrayList<File> fileList = new ArrayList<>(2);
    ArrayList<String> changesFirstZip = new ArrayList<>();
    ArrayList<String> changesSecondZip = new ArrayList<>();


    /**
     * Initializing the list in the constructor.
     */
    public Archive() {
        initFileList();
    }


    /**
     * If the paths are zero, an exception is thrown.
     * In a favorable case, two files are created with the specified paths.
     * For each of the files, all information is collected in maps and then all information
     * is saved in a report compareAndAddToChangesTxt(mmm1, mmm2).
     *
     * @param pathOne   Path of the first archive
     * @param pathTwo   Path of the second archive
     */
    public Archive(String pathOne, String pathTwo) {
        if (pathOne == null || pathTwo == null) {
            throw new IllegalStateException();
        }
        File fileOne = new File(pathOne);
        File fileTwo = new File(pathTwo);
        if (fileOne.exists() && fileTwo.exists()) {
            fileList.add(fileOne);
            fileList.add(fileTwo);
            Map<String, Long> mmm1 = addMap(fileList.get(0));
            Map<String, Long> mmm2 = addMap(fileList.get(1));
            compareAndAddToChangesTxt(mmm1, mmm2);
        } else
            throw new IllegalStateException();

    }

    public void initFileList() {
        for (int i = 0; i < 2; i++) {
            fileList.add(new File("-1"));
        }
    }

    /**
     * Using a custom window. The selection of only those documents that have the extension .zip.
     * Write documents to the list.
     * @return fileList
     */
    public ArrayList<File> getFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".zip", "zip");
        jFileChooser.setFileFilter(filter);
        int count = 0;
        while (count < 2) {
            int ret = jFileChooser.showDialog(null, "Open zip" + (count + 1));
            if (ret == JFileChooser.APPROVE_OPTION) {
                fileList.remove(count);
                fileList.add(count, jFileChooser.getSelectedFile());
            }
            count++;
        }
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getName().equals("-1")) {
                throw new NoSuchElementException("The path of one of the archives was not entered");
            }
        }
        return fileList;
    }


    /**
     * Record information about documents in the archive.
     * @param file
     * @return tempMap  Stores file names and sizes
     */
    public Map<String, Long> addMap(File file) {
        String f = file.getName();
        Map<String, Long> tempMap = new HashMap<>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(file), StandardCharsets.ISO_8859_1);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                tempMap.put(entry.getName(), entry.getSize());
            }
            zip.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return tempMap;
    }

    /**
     * If the files have the same names and different sizes then each column in the report is written '* update'.
     * If the files have different names but the same size, then each column in the report is written to '? rename'.
     * In the right column, all deleted items are recorded , that is, those files that are in the second archive,
     * but not in the first. The left column contains only all the added elements,i.e. those elements that
     * are not in the first column but are in the second one.
     * Text alignment is used when writing to the report.
     * The report is called 'changes.txt'.
     *
     * @param mapOne    Information about files in the first archive
     * @param mapTwo    Information about files in the second archive
     */
    public void compareAndAddToChangesTxt(Map<String, Long> mapOne, Map<String, Long> mapTwo) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("changes.txt"))) {
            bufferedWriter.write("Archive 1" + "\t\t\t\t\t" + "Archive 2\n");
            ArrayList<String> allChanges = new ArrayList<>();
            for (Map.Entry<String, Long> pair : mapOne.entrySet()
            ) {
                for (Map.Entry<String, Long> pair1 : mapTwo.entrySet()
                ) {
                    if (pair.getKey().equals(pair1.getKey()) && !pair.getValue().equals(pair1.getValue())) {
                        changesFirstZip.add("* update " + pair.getKey());
                        changesSecondZip.add("* update " + pair1.getKey());
                        bufferedWriter.write(String.format("%-30.50s","* update "+pair.getKey())+" | "+String.format("%-30.50s","* update "+pair1.getKey())+"\n");
                    }
                    if (!pair.getKey().equals(pair1.getKey()) && pair.getValue().equals(pair1.getValue())) {
                        changesFirstZip.add("? rename " + pair.getKey());
                        changesSecondZip.add("? rename " + pair1.getKey());
                        allChanges.add(pair.getKey());
                        allChanges.add(pair1.getKey());
                        bufferedWriter.write(String.format("%-30.40s","? rename "+pair.getKey())+" | "+String.format("%-30.40s","? rename "+pair1.getKey())+"\n");
                    }
                }
            }
            ArrayList<String> m1 = new ArrayList<>();
            ArrayList<String> m2 = new ArrayList<>();
            for (Map.Entry<String, Long> pair : mapOne.entrySet()
            ) {
                m1.add(pair.getKey());
            }
            for (Map.Entry<String, Long> pair1 : mapTwo.entrySet()
            ) {
                m2.add(pair1.getKey());
            }

            for (int i = 0; i < m2.size(); i++) {
                boolean b1 = false;
                for (int j = 0; j < m1.size(); j++) {
                    if (m2.get(i).equals(m1.get(j))) {
                        b1 = true;
                        break;
                    }
                }
                if (b1) {
                   continue;
                }
                boolean b = false;
                for (int j = 0; j < allChanges.size(); j++) {
                    if (allChanges.get(j).equals(m2.get(i))) {
                        b = true;
                    }
                }
                if (!b) {
                    bufferedWriter.write(String.format("%-30.40s","")+" | "+String.format("%-30.40s","+ addNew "+m2.get(i))+"\n");
                }
            }

            for (int i = 0; i < m2.size(); i++) {
                boolean b1=false;
                for (int j = 0; j < m1.size(); j++) {
                    if (m1.get(j).equals(m2.get(i))) {
                        b1=true;
                        break;
                    }
                }
                if(b1){
                    continue;
                }
                boolean b = false;
                for (int k = 0; k < allChanges.size(); k++) {
                    if (allChanges.get(k).endsWith(m2.get(i))) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    String str="- remove "+m2.get(i);
                    bufferedWriter.write(String.format("%-30.40s",str)+" | "+String.format("%-30.40s","")+"\n");
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    public static void readyToLaunch() {
        Archive archive = new Archive();
        ArrayList<File> file = archive.getFile();
        Map<String, Long> mmm1 = archive.addMap(file.get(0));
        Map<String, Long> mmm2 = archive.addMap(file.get(1));
        archive.compareAndAddToChangesTxt(mmm1, mmm2);
    }

    public static void main(String[] args) {
        Archive.readyToLaunch();
        //Archive archive = new Archive("Example.zip", "Example - Copy.zip");
    }
}
