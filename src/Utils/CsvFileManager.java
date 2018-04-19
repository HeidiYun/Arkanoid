package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvFileManager {
    private static String[][] csv = new String[100][13];

    public static void readFile(String fileName) {
        String line;
        int row = 0;

        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){
                String[] token = line.split(",");
                for (int i = 0; i < token.length; i++) {
                    csv[row][i] = token[i];
                }
               row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] getCsvArray(String fileName) {
        readFile(fileName);
        return csv;
    }
}
