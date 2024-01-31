package appCSV.search;

import java.util.Collection;
import java.util.List;

public interface SearchCSV<T>  {

    //формирует выборку согласно условию
    /** list of possible values of flag:
     *          < -1 output Set limit by value
     *          > 0 output List limit by value
     * DEFAULT  0 - output unlimited List
     *          -1 - output unlimited Set
     */

    Collection<T> selectSet(List<T> data, int flag);
    Collection<T> selectSet(List<T> data);
}
