package appCSV.readers;

import java.io.IOException;
import java.util.List;

public interface IReadCSV<T> {

    //считывает из файла в список
    List<T> reader(String file) throws IOException;
}
