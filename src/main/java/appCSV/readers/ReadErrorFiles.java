package appCSV.readers;

import appCSV.entity.InvalidLine;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadErrorFiles implements IReadCSV<String> {
    public ArrayList<InvalidLine> invalidLineArrayList = new ArrayList<>();// 0 - begin, 1 - end, 2 - line, 3 - string by num

    public ReadErrorFiles(int skipLines) {
        this.skipLines = skipLines;
    }

    private boolean isValidUnicodeCodePoint(int codePoint) {
        return codePoint >= 0x0000 && codePoint <= 0x10FFFF;
    }

    public boolean validate(List<Integer> stringByInt) {
        /*List<Integer> check = new ArrayList<>(List.of(new Integer[]{10, 13}));
        if (stringByInt.lastIndexOf(check) == stringByInt.size() - 3) return false;
        int countDoubleQuotes = 0;
        StringBuilder strForSearch = new StringBuilder(stringByInt.size());
        for (int sym : stringByInt) {
            if (sym == 34) { //Символ двойных кавычек (")
                countDoubleQuotes++;
            }
            strForSearch.append(sym);

        }*/
        for (int i = 0; i < stringByInt.size(); i++) {
            if (stringByInt.get(i) == 119) {
                if (stringByInt.get(i + 1) == 122) {
                    if (stringByInt.get(i + 2) == 116) {
                        if (stringByInt.get(i + 3) == 118) {
                            if (stringByInt.get(i + 4) == 54) {
                                if (stringByInt.get(i + 5) == 57) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        /*String strB = new String("wztv69");
        if (strForSearch.indexOf(strB) != -1) return false;
        if (countDoubleQuotes % 2 != 0) return false;*/
        return true;
    }

    public int skipLines;

    //    Читает строку до сочетания переноса строки 1310. эти символы в массив не добавляет
    @Override
    public List<String> reader(String file) {

        List<String> listCorrectRead = new ArrayList<>();

        long beginLine;
        long endLine;
        long countSymbols = 0;
        long countLines = 0;
        int symbol;
        int flag = 0;

        try (FileReader fileReader = new FileReader(file)) {
            ArrayList<Integer> currentStrByInt = new ArrayList<>();
            while (flag == 0) {
//            WeakReference<List<Integer>> weakReference = new WeakReference<>(currentStrByInt);
                beginLine = countSymbols + 1;
                while (countLines < skipLines) {
                    symbol = fileReader.read();
                    countSymbols++;
                    switch (symbol) {
                        case -1: {
                            throw new RuntimeException("Строк меньше чем задано пройти");
                        }
                        case 10: {
                            countLines++;
                            break;
                        }
                    }
                }
//    чтение строки и проверка на целостность
                while ((symbol = fileReader.read()) != 10) {
                    countSymbols++;
                    if (symbol == -1) {
                        flag = -1;
                        break;
                    }
                    if (!isValidUnicodeCodePoint(symbol)) {
                        String str1 = "символ неверный! " + symbol + " line: " + countLines + " символ по счету " + countSymbols;
                        throw new RuntimeException(str1);
                    }
                    currentStrByInt.add(symbol);
//                    if (symbol != 13) currentStrByInt.add(symbol);
                }
                if (symbol != -1) currentStrByInt.add(symbol);
                countSymbols++;//символ 10
                endLine = countSymbols;
                countLines++;

                if (validate(currentStrByInt) && flag == 0) {
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < currentStrByInt.size(); i++) {
                        str.append(Character.toString(currentStrByInt.get(i)));
                    }
                    listCorrectRead.add("LINE: " + countLines + " TEXT: " + str);

                } else {
                    InvalidLine invalidLine = new InvalidLine();
                    invalidLine.beginPosition = beginLine;
                    invalidLine.endPosition = endLine;
                    invalidLine.numberLine = countLines;
                    invalidLine.stringByInt = currentStrByInt.toArray(new Integer[0]);
                    int countComma = 0;
                    int countDoubleQuotes = 0;
                    for (int sym : invalidLine.stringByInt) {
                        if (sym == 44) { //Символ запятой (,)
                            countComma++;
                        }
                        if (sym == 34) { //Символ двойных кавычек (")
                            countDoubleQuotes++;
                        }
                    }
                    invalidLine.commaAmount = countComma;
                    invalidLine.quotesAmount = countDoubleQuotes;
                    invalidLineArrayList.add(invalidLine);
                }
                currentStrByInt.clear();
//                if (countLines > (skipLines + 20)) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listCorrectRead;
    }
}
