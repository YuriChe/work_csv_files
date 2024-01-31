package appCSV.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Arrays;

/** Символ запятой (,) имеет десятичный ASCII-код 44.
Символ двойных кавычек (") имеет десятичный ASCII-код 34.
*/
@Entity
@Table (name = "invalid_lines")
public class InvalidLine {

    public Long beginPosition;

    public Long endPosition;

    public Long numberLine;

    public int commaAmount;

    public int quotesAmount;

    public Integer[] stringByInt;
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < stringByInt.length; i++) {
            str.append(Character.toString(stringByInt[i]));
        }

        return "Errors{" +
                "begin=" + beginPosition +
                ", end=" + endPosition +
                ", line=" + numberLine +
                ", numberComma(44)=" + commaAmount +
                ", numberQuotes(34)=" + quotesAmount +
                ", text=" + str +
                ", stringInt=" + Arrays.toString(stringByInt) +
                "}\n";
    }
}
