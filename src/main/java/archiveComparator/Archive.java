package archiveComparator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Archive {
    ArrayList<File> fileList = new ArrayList<>(2);
    ArrayList<String> changesFirstZip = new ArrayList<>();
    ArrayList<String> changesSecondZip = new ArrayList<>();

    public Archive() {
        for (int i = 0; i < 2; i++) {
            fileList.add(new File("-1"));
        }
    }

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
                throw new NoSuchElementException("Не был введен путь одного из архивов.");
            }
        }
        return fileList;
    }


    public Map<String, Long> addMap(File file) {
        Map<String, Long> tempMap = new HashMap<>();
        try {
            ZipFile zip = new ZipFile(file.getAbsolutePath());
            for (Enumeration<? extends ZipEntry> iter = zip.entries(); iter.hasMoreElements(); ) {
                ZipEntry zipElement = iter.nextElement();
                tempMap.put(zipElement.getName(), zipElement.getSize());
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return tempMap;
    }

    public void compareAndAddToChangesTxt(Map<String, Long> mapOne, Map<String, Long> mapTwo) {
        for (Map.Entry<String, Long> pair : mapOne.entrySet()
        ) {
            for (Map.Entry<String, Long> pair1 : mapTwo.entrySet()
            ) {
                if (pair.getKey().equals(pair1.getKey()) && !pair.getValue().equals(pair1.getValue())) {
                    changesFirstZip.add("* update " + pair.getKey());
                    changesSecondZip.add("* update " + pair1.getKey());
                }
                if (!pair.getKey().equals(pair1.getKey()) && pair.getValue().equals(pair1.getValue())) {
                    changesFirstZip.add("? rename " + pair.getKey());
                    changesSecondZip.add("? rename " + pair1.getKey());
                }
            }
        }
        ArrayList<String> m1 = new ArrayList<>();
        ArrayList<String> m2 = new ArrayList<>();
        String[] mOne = new String[mapOne.size()];
        String[] mTwo = new String[mapTwo.size()];

        for (Map.Entry<String, Long> pair : mapOne.entrySet()
        ) {
            m1.add(pair.getKey());
            for (int i = 0; i < mOne.length; i++) {
                mOne[i] = pair.getKey();
            }
        }
        for (Map.Entry<String, Long> pair1 : mapTwo.entrySet()
        ) {
            m2.add(pair1.getKey());
            for (int i = 0; i < mTwo.length; i++) {
                mTwo[i] = pair1.getKey();
            }
        }

        if (mapOne.size() < mapTwo.size()) {
            boolean b = false;
            for (int i = 0; i < m2.size(); i++) {
                for (int j = 0; j < m1.size(); j++) {
                    if (m2.get(i).equals(m1.get(j))) {
                        b = true;
                    }
                }
                if (!b) {
                    changesFirstZip.add("- remove " + m1.get(i));
                    changesSecondZip.add("+ addNew " + m1.get(i));
                }
            }
        } else {
            boolean b = false;
            for (int i = 0; i < m1.size(); i++) {
                for (int j = 0; j < m2.size(); j++) {
                    if (m1.get(i).equals(m2.get(j))) {
                        b = true;
                    }
                }
                if (!b) {
                    changesFirstZip.add("+ addNew " + m1.get(i));
                    changesSecondZip.add("- remove " + m1.get(i));
                }
            }

            /*
            for (int i = 0; i < mTwo.length; i++) {
                int search = Arrays.binarySearch(mOne, mTwo[i]);
                if (search == -1) {
                    changesFirstZip.add("- remove " + mTwo[i]);
                    changesSecondZip.add("+ addNew " + mTwo[i]);
                }
            }

             */
        }
    }

    /*
    public void readyToLaunch(){
        Archive archive=new Archive();
        ArrayList<File> file= archive.fileList;
        Map<String,Long> mmm1=archive.addMap(file.get(0));
        Map<String,Long> mmm2=archive.addMap(file.get(1));
        archive.compareAndAddToChangesTxt(mmm1,mmm2);
        for (String s:archive.changesFirstZip
             ) {
            System.out.println(s);
        }
    }

     */
    public static void main(String[] args) {
        /*
        Archive archive = new Archive();
        ArrayList<File> file = archive.getFile();
        for (File f : file
        ) {
            System.out.println(f.getAbsoluteFile());
        }
         */
        Archive archive = new Archive();
        ArrayList<File> file = archive.getFile();
        Map<String, Long> mmm1 = archive.addMap(file.get(0));
        Map<String, Long> mmm2 = archive.addMap(file.get(1));
        archive.compareAndAddToChangesTxt(mmm1, mmm2);
        for (String s : archive.changesFirstZip
        ) {
            System.out.println(s);
        }

    }
}
