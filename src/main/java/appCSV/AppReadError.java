package appCSV;

import appCSV.config.Config;
import appCSV.readers.ReadErrorFiles;

import java.io.File;
import java.util.List;

public class AppReadError {
    public static void main(String[] args) {
        final File dir = new File(Config.path);
        final File[] files = dir.listFiles();
        if (files == null) {
            throw new RuntimeException("Нет файлов с данными");
        }

        ReadErrorFiles readErrorFiles = new ReadErrorFiles();
//        for (File file : files) {
            List<String> listRes = readErrorFiles.reader(String.valueOf(files[1]));

            System.out.println("Файл = " + files[1]);
            listRes.stream()
                    .limit(5)
                    .forEach(System.out::println);
//        }
        System.out.println();
        System.out.println("  Errors:");

        readErrorFiles.invalidLineArrayList.stream()
                .limit(20)
                .forEach(System.out::println);
        System.out.println();
//        for (int i : readErrorFiles.invalidLineArrayList.get(2).stringByInt) {
//            System.out.print(Character.toString(i));
//        }
    }
}
