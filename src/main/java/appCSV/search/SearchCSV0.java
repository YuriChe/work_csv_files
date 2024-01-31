package appCSV.search;

import appCSV.ConditionsPredicate;

import java.util.*;

public class SearchCSV0 implements SearchCSV<String[]> {

    @Override
    /** list of possible values of flag:
     *          < -1 output Set limit by value
     *          > 0 output List limit by value
     * DEFAULT  0 - output unlimited List
     *          -1 - output unlimited Set
     */
    public Collection<String[]> selectSet(List<String[]> data, int flag) {
        ConditionsPredicate conditionsPredicate = new ConditionsPredicate();

        List<String[]> resList;
        long startTime = System.currentTimeMillis();
        if (flag == 0 || flag == -1) {
            resList = data.parallelStream()
                    .skip(1)
                    .filter(conditionsPredicate)
                    .map(x -> new String[]{x[1], x[2], x[7]})
                    .toList();
        } else {
            resList = data.parallelStream()
                    .skip(1)
                    .filter(conditionsPredicate)
                    .limit(flag)
                    .map(x -> new String[]{x[1], x[2], x[7]})
                    .toList();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("EXECUTION SelectSet TIME WAS " + (endTime - startTime));
        if (flag < 0) {
            return new HashSet<>(resList);
        }
        return resList;
    }

    @Override
    public Collection<String[]> selectSet(List<String[]> data) {

        return selectSet(data, -1);
    }
}

