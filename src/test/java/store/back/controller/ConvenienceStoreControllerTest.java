package store.back.controller;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.back.repository.ProductRepository;

class ConvenienceStoreControllerTest extends NsTest {

    private final ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();

    @BeforeAll
    static void setUp() {
        ProductRepository.loadAgain();
    }

    @AfterEach
    void afterEach() {
        ProductRepository.loadAgain();
    }

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
            run("[콜라-10],[컵라면-10],[탄산수-3]", "Y", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈26,400");
        });
    }

    @Test
    void 상품_구매_테스트3() {
        assertSimpleTest(() -> {
            run("[콜라-10],[컵라면-1],[초코바-3]", "N", "Y", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,400");
        });
    }

    @Test
    void 상품_구매_예외_테스트3() {
        assertSimpleTest(() -> {
            runException("[콜라-21],[컵라면-1],[초코바-4]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용1() {
        assertNowTest(() -> {
            run("[오렌지주스-9],[탄산수-2],[초코바-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈21,000");
        }, LocalDate.of(2023, 2, 1).atStartOfDay());
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용2() {
        assertNowTest(() -> {
            run("[오렌지주스-4],[사이다-7],[초코바-2]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈11,620");
        }, LocalDate.of(2023, 2, 1).atStartOfDay());
    }

    @Test
    void 프로모션_미적용_상품_제외시_구매수량이_0일경우_제외() {
        assertSimpleTest(() -> {
            run("[콜라-10],[컵라면-10]", "Y", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈7,000");
        });
    }

    @Test
    void 프로모션_미적용_상품_제외시_구매_물건이_아예없으면_다시_입력_받기() {
        assertSimpleTest(() -> {
            run("[컵라면-10]", "N", "[콜라-10]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈7,000");
        });
    }

    @Override
    protected void runMain() {
        convenienceStoreController.run();
    }
}