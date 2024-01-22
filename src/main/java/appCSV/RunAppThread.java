package appCSV;

import appCSV.config.Config;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RunAppThread {


    public static void main(String[] args) {

        final File dir = new File(Config.path);
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
        List<Thread> threadList = new ArrayList<>();
        List<String[]> finalResultThread = new CopyOnWriteArrayList<>();

        for (File file1 : files) {
            String file = String.valueOf(file1);

            Thread myThread = new Thread(new App(file, finalResultThread), "MyThread " + file1);
            myThread.start();
            threadList.add(myThread);
            System.out.println(myThread.getName());
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        finalResultThread.stream().limit(10).forEach(x -> System.out.println(Arrays.toString(x)));
        System.out.println("TOTAL RECORDS " + finalResultThread.size());
        System.out.println("Elapsed time: " + (endTime - startTime));

        /*Comparator<String[]> compStrArr = new ComparatorListStr();
        finalResultThread.sort(compStrArr);
        finalResultByOnes.sort(compStrArr);


        long withoutRepeat = finalResultThread.stream()
                .flatMap(Arrays::stream)
                .distinct()
                .count();
        long withoutRepeat2 = finalResultThread.stream()
                .flatMap(Arrays::stream)
                .count();
        System.out.println("Размер без повторов " + withoutRepeat);
        System.out.println("Размер с повторами " + withoutRepeat2);

        System.out.println("Проверка по equals = " + finalResultByOnes.equals(finalResultThread));
//        finalResultByOnes.removeAll(finalResultThread);

        finalResultByOnes.forEach(x -> System.out.println(" РАЗНИЦА === " + Arrays.toString(x)));
        System.out.println("СКОЛЬКО несовпадений " + finalResultByOnes.size());
        System.out.println("СКОЛЬКО в list1 " + finalResultByOnes.size());
        System.out.println("СКОЛЬКО в list2 Thread " + finalResultThread.size());*/

        /*for (int i = 0; i < list3.size(); i++) {
            if (!list4.contains(list3.get(i))) {
                System.out.println(list4.get(i));
            }*/
            /*if (!list3.get(i).equals(list4.get(i))) {
                System.out.println(list3.get(i) + " \t" + list4.get(i));
            }
        }*/

        /*System.out.println("ЕСТЬ ЛИ ? " + finalResultByOnes.stream()
                .flatMap(Arrays::stream)
                .skip(1)
//                .filter(x -> x.equals("Черникова Елена Викторовна"))
                .anyMatch(x -> x.equals("Мадестова Тамара Викторовна")));

        System.out.println("ЕСТЬ ЛИ 2 ? " + finalResultThread.stream()
                .flatMap(Arrays::stream)
                .skip(1)
//                .filter(x -> x.equals("Черникова Елена Викторовна"))
                .anyMatch(x -> x.equals("Мадестова Тамара Викторовна")));*/


//        System.out.println(list1.get(0).length);
//        System.out.println();
//        list2.stream().filter(list1::contains).forEach(System.out::println);

        /*HashSet<String[]> setDiff = new HashSet<>();
        finalResultThread.removeAll(setDiff);
        finalResultThread.forEach(x -> System.out.println(Arrays.toString(x)));*/


        // Запись данных в файл CSV
        /*WriteToFile writeToFile = new WriteToFile("C:\\Users\\asus\\Desktop\\result\\res0501-2.csv");
        if (!writeToFile.writeToFile(resultSetAll)) {
            throw new RuntimeException("Ошибка записи в файл!");
        }*/

//        Iterator<String[]> strI = resultSetAll.iterator();
//        while (strI.hasNext()) {
//            System.out.println(Arrays.toString(strI.next()));
//        }
//        System.out.println("TOTAL RECORDS " + resultSetAll.size());
    }
}

