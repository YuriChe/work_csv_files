package appCSV.writers;

import java.util.List;

public interface ReorganizeList<T> {
    List<T> toUniqueList (List<T> list);
    List<String[]> toArrStringForFile (List<T> list);
}
