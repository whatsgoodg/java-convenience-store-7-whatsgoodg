package store.back.repository.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileRowLoader {

    static List<List<String>> loadFileToRows(final String filePath) {
        try (InputStream inputStream = FileRowLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return readAllRows(bufferedReader);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }

    private static List<List<String>> readAllRows(final BufferedReader bufferedReader) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        String line;
        bufferedReader.readLine(); // 첫 줄을 날림
        while ((line = bufferedReader.readLine()) != null) {
            List<String> row = Arrays.stream(line.split(",")).toList();
            rows.add(row);
        }
        return rows;
    }
}
