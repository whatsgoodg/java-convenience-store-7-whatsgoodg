package store.back.repository.loader;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileRowLoaderTest {

    @Test
    @DisplayName("product.md 파일의 모든 row를 가져오는 테스트")
    void 파일_로드_테스트1() {
        //given
        String filePath = "products.md";
        // 맨 윗줄과 마지막 줄을 포함하는지
        List<String> expectedRow1 = List.of("콜라", "1000", "10", "탄산2+1");
        List<String> expectedRow2 = List.of("컵라면", "1700", "10", "null");
        //when
        List<List<String>> loadedRows = FileRowLoader.loadFileToRows(filePath);
        //then
        assertThat(loadedRows.size()).isEqualTo(16);
        assertThat(loadedRows).contains(expectedRow1);
        assertThat(loadedRows).contains(expectedRow2);
    }

    @Test
    @DisplayName("promotions.md 의 모든 row를 가져오는 테스트")
    void 파일_로드_테스트2() {
        //given
        String filePath = "promotions.md";
        // 맨 윗줄과 마지막 줄을 포함하는지
        List<String> expectedRow1 = List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31");
        //when
        List<List<String>> loadedRows = FileRowLoader.loadFileToRows(filePath);
        //then
        assertThat(loadedRows.size()).isEqualTo(3);
        assertThat(loadedRows).contains(expectedRow1);
    }

}