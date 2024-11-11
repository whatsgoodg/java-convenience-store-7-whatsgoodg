package store.back.facade.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.repository.ProductRepository;
import store.global.dto.request.purchase.PurchaseCheckRequestDTO;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.response.purchase.NonPromotionalProductInfo;
import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.dto.response.purchase.PurchaseCheckResponseDTO;
import store.global.exception.InvalidProductNameException;
import store.global.exception.OutOfStockException;

class PurchaseCheckFacadeTest {
    private final PurchaseCheckFacade purchaseCheckFacade = new PurchaseCheckFacade();

    @AfterEach
    void setUp(){
        ProductRepository.loadAgain();
    }

    @ParameterizedTest
    @MethodSource("productFacadeParams")
    @DisplayName("사용자가 구매 목록을 전달하면 이에 검증, 따른 증정 가능 상품, 정가 구매 상품을 보여줌")
    void 사용자_구매_확인_통합_테스트(final PurchaseCheckRequestDTO purchaseCheckRequestDTO,
                          final PurchaseCheckResponseDTO expecTedPurchaseCheckResponseDTO) {
        //give
        //when
        PurchaseCheckResponseDTO purchaseCheckResponseDTO = purchaseCheckFacade.checkPurchaseConditions(
                purchaseCheckRequestDTO);
        assertThat(purchaseCheckResponseDTO).usingRecursiveComparison().isEqualTo(expecTedPurchaseCheckResponseDTO);

    }

    private static Stream<Arguments> productFacadeParams() {
        return Stream.of(
                Arguments.of(
                        new PurchaseCheckRequestDTO(
                                List.of(
                                        new PurchaseProductInfo("콜라", 8),
                                        new PurchaseProductInfo("사이다", 9))
                        ),
                        new PurchaseCheckResponseDTO(
                                List.of(
                                        new PromotionalProductInfo("콜라", 1)),
                                List.of(
                                        new NonPromotionalProductInfo("사이다", 3))
                        )
                ),
                Arguments.of(
                        new PurchaseCheckRequestDTO(
                                List.of(
                                        new PurchaseProductInfo("콜라", 9),
                                        new PurchaseProductInfo("사이다", 6))
                        ),
                        new PurchaseCheckResponseDTO(
                                List.of(),
                                List.of()
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("productFacadeExceptionParams")
    @DisplayName("사용자가 구매 목록을 전달했을 때 상품이 존재하지 않거나 수량이 부족한 경우 예외")
    void 사용자_구매_예외_테스트(final PurchaseCheckRequestDTO purchaseCheckRequestDTO,
                       final IllegalArgumentException exception) {
        //give
        //when
        assertThatThrownBy(() -> purchaseCheckFacade.checkPurchaseConditions(
                purchaseCheckRequestDTO)).isInstanceOf(exception.getClass());

    }

    private static Stream<Arguments> productFacadeExceptionParams() {
        return Stream.of(
                Arguments.of(
                        new PurchaseCheckRequestDTO(
                                List.of(
                                        new PurchaseProductInfo("콜라", 16),
                                        new PurchaseProductInfo("사이다", 16))),
                        new OutOfStockException()
                ),
                Arguments.of(
                        new PurchaseCheckRequestDTO(
                                List.of(
                                        new PurchaseProductInfo("햄버거", 9))
                        ),
                        new InvalidProductNameException()
                )
        );
    }
}