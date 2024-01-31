package appCSV.writers;

import java.util.List;

public interface ReorganizeList<T> {
    public List<T> toUniqueList(List<T> list);
}
