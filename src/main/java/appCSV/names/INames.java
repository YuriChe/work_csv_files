package appCSV.names;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface INames {

    Set<String> listUniqueNames(List<String[]> list);
    Map<String, Long> countNames(List<String[]> list);
    List<String> namesList(List<String[]> list);
    Set<String> namesSet(List<String[]> list);




}
