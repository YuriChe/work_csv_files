package appCSV.writers;

import java.util.List;

public interface WriteListToTable<T>{

    /** записывает в таблицу PostgresSQL с индекса begin до end включительно
     *
     * @param list<T>
     * @param begin
     * @param end
     * @return
     */
    long writeListToTable(List<T> list, int begin, int end);

}
