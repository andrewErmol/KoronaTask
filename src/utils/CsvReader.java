package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvReader {
    public static <T> List<T> read(String filePath, Function<String[], T> parser) {
        return read(filePath, parser, false);
    }

    public static <T> List<T> read(String filePath, Function<String[], T> parser, boolean hasHeader) {
        return read(filePath, parser, hasHeader, ",");
    }

    public static <T> List<T> read(String filePath, Function<String[], T> parser, boolean hasHeader, String separator) {
        List<T> entities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (hasHeader) {
                    hasHeader = false;
                    continue;
                }

                String[] fields = line.split(separator);

                T entity = parser.apply(fields);

                if (entity != null) {
                    entities.add(entity);
                }
            }
        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        }

        return entities;
    }
}