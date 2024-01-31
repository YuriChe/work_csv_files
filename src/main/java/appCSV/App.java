package appCSV;

import appCSV.readers.ReadCSV_ByLine;
import appCSV.search.SearchCSV;
import appCSV.search.SearchCSVImpl;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.Semaphore;

public class App implements Runnable {

    private final String file;

    public List<String[]> getResultSet() {
        return resultSet;
    }

    private List<String[]> resultSet;

    public App(String file, List<String[]> resultSet) {
        this.file = file;
        this.resultSet = resultSet;
    }

    @Override
    public void run() {

        ReadCSV_ByLine readCSVByLine = new ReadCSV_ByLine();
        SearchCSV<String[]> searchCSV = new SearchCSVImpl();
        List<String[]> tempList = new ArrayList<>();
        tempList = readCSVByLine.reader(String.valueOf(file), 1, 0);
        WeakReference<List<String[]>> weakReference = new WeakReference<>(tempList);

// !!! НЕОБХОДИМО ПРОВЕРИТЬ класс ConditionPredicate где прописаны условия отбора/поиска
        Collection<String[]> resIntermediate = searchCSV.selectSet(tempList, 0);
        for (String[] str : resIntermediate) {
            resultSet.add(Arrays.copyOf(str, str.length));
        }
        tempList = null;
        System.gc();
        if (weakReference.get() == null) {
            System.out.println("tempList Зачищен");
        } else System.out.println("tempList не null");
    }

    public Runnable run2(Semaphore semaphore) {
        return () -> {

            try {
                // Получение разрешения от Semaphore
                semaphore.acquire();

                ReadCSV_ByLine readCSVByLine = new ReadCSV_ByLine();
                SearchCSV<String[]> searchCSV = new SearchCSVImpl();
                List<String[]> tempList = new ArrayList<>();
                tempList = readCSVByLine.reader(String.valueOf(file), 1, 0);
                WeakReference<List<String[]>> weakReference = new WeakReference<>(tempList);

// !!! НЕОБХОДИМО ПРОВЕРИТЬ класс ConditionPredicate где прописаны условия отбора/поиска
                Collection<String[]> resIntermediate = searchCSV.selectSet(tempList, 0);
                for (String[] str : resIntermediate) {
                    resultSet.add(Arrays.copyOf(str, str.length));
                }
                tempList = null;
                System.gc();
                if (weakReference.get() == null) {
                    System.out.println("tempList Зачищен");
                } else System.out.println("tempList не null");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Освобождение разрешения после выполнения задачи
                semaphore.release();
            }
        };
    }
}

