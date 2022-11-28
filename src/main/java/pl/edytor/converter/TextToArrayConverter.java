package pl.edytor.converter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TextToArrayConverter {

    public static void main(String[] args) {
        Path pathToString;
        if (args.length > 0) {
            pathToString = obtainPathToFileForConversionFromArgs(args[0]);
        } else {
            pathToString = obtainPathToFileForConversionFromResources();
        }
        String textToConvert = readContentOfFile(pathToString);
        parseTextIntoTwoDimSortedArray(textToConvert);
    }

    private static Path obtainPathToFileForConversionFromArgs(final String arg) {
        return Paths.get(arg);
    }

    private static Path obtainPathToFileForConversionFromResources() {
        final Path pathToString;
        try {
            pathToString = Paths.get(ClassLoader.getSystemResource("text.txt").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("No file found");
        }
        return pathToString;
    }

    private static String readContentOfFile(final Path pathToString) {
        try {
            return Files.readString(pathToString);
        } catch (IOException e) {
            throw new RuntimeException("Can not read file!");
        }
    }

    public static void sort(String[] family) {
        int result;
        String temp;
        for (int repeat = 0; repeat < family.length - 1; repeat++) {
            for (int member = 0; member < family.length - 1; member++) {
                result = family[member].compareTo(family[member + 1]);
                if (result > 0) {
                    temp = family[member];
                    family[member] = family[member + 1];
                    family[member + 1] = temp;
                }
            }
        }
    }

    public static void parseTextIntoTwoDimSortedArray(final String textToParse) {
        final String[] table1D = textToParse.toLowerCase()
                .replace("\n", " ")
                .replaceAll("[^a-zA-Z śźżęąćłńó]", "")
                .trim()
                .split(" ");

        sort(table1D);
        final String[] tableWithoutDuplicates = newLengthTableWithoutDuplicates(table1D);
        final int numberOfRows = findNumberOfRows(tableWithoutDuplicates);
        final String[][] table2D = new String[numberOfRows][];
        createRowsFrom(table2D, tableWithoutDuplicates);
        splitOneDimTableIntoTwoDim(tableWithoutDuplicates, table2D);
        printTwoDimTable(table2D);

    }

    private static String[] newLengthTableWithoutDuplicates(String[] table1D) {
        int j = 1;
        for (int i = 0; i < table1D.length - 1; i++) {
            if (table1D[i].equals("")) {
                continue;
            }
            if (!table1D[i].equals(table1D[i + 1])) {
                j++;
            }
        }
        String[] tableWithoutDuplicates = new String[j];
        int k = 0;
        for (int element = 0; element < table1D.length - 1; element++) {
            if (table1D[element].equals("")) {
                continue;
            }
            if (!table1D[element].equals(table1D[element + 1])) {
                tableWithoutDuplicates[k] = table1D[element];
                k++;
            }
        }
        if (!table1D[table1D.length - 1].equals(table1D[table1D.length - 2])) {
            tableWithoutDuplicates[j - 1] = table1D[table1D.length - 1];
        }
        return tableWithoutDuplicates;
    }

    private static int findNumberOfRows(String[] tableWithoutDuplicates) {
        int result = 0;
        char previousLetter = 0;
        for (String word : tableWithoutDuplicates) {
            if (previousLetter != word.charAt(0)) {
                result++;
                previousLetter = word.charAt(0);
            }
        }
        return result;
    }

    private static void createRowsFrom(String[][] table2D, String[] words) {
        char actualLetter;
        short lettersForRow = 0;
        int row = 0;
        for (int i = 0; i < words.length; i++) {
            actualLetter = words[i].charAt(0);
            lettersForRow++;
            if (isLastLetter(i, words.length) || isNextLetterIsDifferent(actualLetter, words[i + 1].charAt(0))) {
                table2D[row] = new String[lettersForRow];
                row++;
                lettersForRow = 0;
            }
        }
    }

    private static boolean isLastLetter(int numberOfElement, int length) {
        return numberOfElement + 1 == length;
    }

    private static void splitOneDimTableIntoTwoDim(String[] words, String[][] table2D) {
        char actualLetter;
        int row = 0;
        int indexOfRow = 0;
        for (int j = 0; j < words.length; j++) {
            actualLetter = words[j].charAt(0);
            table2D[row][indexOfRow] = words[j];
            indexOfRow++;
            if (j + 1 < words.length && actualLetter != words[j + 1].charAt(0)) {
                row++;
                indexOfRow = 0;
            }
        }
    }

    private static void printTwoDimTable(String[][] table2D) {
        for (String[] oneDimArray : table2D) {
            System.out.println(Arrays.toString(oneDimArray));
        }
    }

    private static boolean isNextLetterIsDifferent(char actualLetter, char nextLetter) {
        return actualLetter != nextLetter;
    }
}
