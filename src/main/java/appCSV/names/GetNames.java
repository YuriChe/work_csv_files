package appCSV.names;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface GetNames {

    Set<String> listUniqueNames(List<String[]> list);

    HashMap<String, Long> countNames(List<String[]> list);

    List<String> namesList(List<String[]> list);

    Set<String> namesSet(List<String[]> list);
}
