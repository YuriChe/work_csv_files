package appCSV.readers;

import appCSV.config.Config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import static appCSV.config.Config.debug;

public class GetArrFilesInDir {

    public static class CsvFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            // Принимаем только файлы с расширением .csv (регистронезависимо)
            return name.toLowerCase().endsWith(".csv");
        }
    }

    public static File[] getArrFiles() {
        File[] files;
        try {
            File dir = new File(Config.path);

            files = dir.listFiles();
            if (files == null) {
                System.out.println("No files found!");
            }
            if (debug) {
                System.out.println(dir);
                System.out.println(Arrays.toString(files));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return files;
    }
}
