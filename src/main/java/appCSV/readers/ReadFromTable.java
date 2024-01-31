package appCSV.readers;


import java.util.List;

public interface ReadFromTable <T> {
    public List<T> readFromTableToList();
}
