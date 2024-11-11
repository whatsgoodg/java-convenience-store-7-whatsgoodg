package store.back.controller;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

// 해당 테스트는 한번에 실행해야한다.
class ConvenienceStoreControllerTest extends NsTest {

    private final ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();

    @Test
    void 상품_구매_테스트1() {
        assertSimpleTest(() -> {
            run("[사이다-8],[비타민워터-5],[감자칩-3]", "Y", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈16,500");
        });
    }

    @Test
    void 상품_구매_테스트2() {
        assertSimpleTest(() -> {
            run("[콜라-10],[컵라면-10],[탄산수-3]", "Y", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈21,000");
        });
    }

    @Test
    void 상품_구매_테스트3() {
        assertSimpleTest(() -> {
            run("[콜라-10],[컵라면-1],[초코바-3]", "N", "Y", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈12,400");
        });
    }

    @Test
    void 상품_구매_예외_테스트3() {
        assertSimpleTest(() -> {
            runException("[콜라-20],[컵라면-1],[초코바-3]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[오렌지주스-9],[탄산수-2],[초코바-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈21,000");
        }, LocalDate.of(2023, 2, 1).atStartOfDay());
    }

    @Override
    protected void runMain() {
        convenienceStoreController.run();
    }
}