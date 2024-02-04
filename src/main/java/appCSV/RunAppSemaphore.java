package appCSV;

import appCSV.config.Config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class RunAppSemaphore {

    public static void main(String[] args) {

        final File dir = new File(Config.PATH);
        final File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Нет файлов с данными");
            return;
        }
//        запуск блока с Thread
        /*Comparator<String[]> arrayComparator = Comparator.comparing((String[] array) -> array[0]);
        Set<String[]> finalResultByOnes = new Concurrent   SkipListSet<>(arrayComparator);
        Hashtable*/
        long startTime = System.currentTimeMillis();
        List<String[]> finalResultThread = new CopyOnWriteArrayList<>();

        Semaphore semaphore = new Semaphore(4);
        ExecutorService executorService = Executors.newFixedThreadPool(4);


        for (File file1 : files) {
            String file = String.valueOf(file1);
            App app = new App(file, finalResultThread);
            executorService.submit(app.run2(semaphore));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Ожидание завершения
        }
        long endTime = System.currentTimeMillis();

        finalResultThread.stream().limit(10).forEach(x -> System.out.println(Arrays.toString(x)));
        System.out.println("TOTAL RECORDS " + finalResultThread.size());
        System.out.println("Elapsed time: " + (endTime - startTime));

//        Iterator<String[]> strI = resultSetAll.iterator();
//        while (strI.hasNext()) {
//            System.out.println(Arrays.toString(strI.next()));
//        }
//        System.out.println("TOTAL RECORDS " + resultSetAll.size());
    }
}

