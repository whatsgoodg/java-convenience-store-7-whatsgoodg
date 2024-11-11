package store.back.controller;

import store.back.controller.util.Repeater;
import store.back.facade.order.OrderFacade;
import store.back.facade.product.ProductDisplayFacade;
import store.back.facade.product.PurchaseCheckFacade;
import store.front.view.input.InputView;
import store.front.view.output.OutputView;
import store.global.dto.request.purchase.PurchaseCheckRequestDTO;
import store.global.dto.request.order.OrderRequestDTO;
import store.global.dto.response.order.OrderResponseDTO;
import store.global.dto.response.purchase.PurchaseCheckResponseDTO;
import store.global.dto.response.display.ProductDisplayResponseDTO;
import store.global.exception.NoPurchaseProductException;

public class ConvenienceStoreController {

    private final Repeater repeater = new Repeater();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final PurchaseCheckFacade purchaseCheckFacade = new PurchaseCheckFacade();
    private final ProductDisplayFacade productDisplayFacade = new ProductDisplayFacade();
    private final OrderFacade orderFacade = new OrderFacade();

    public void run() {
        do {
            purchaseAndOrder();
        } while (readRetry());
    }

    public void purchaseAndOrder() {
        PurchaseCheckResponseDTO purchaseCheckResponseDTO = checkPurchase();
        Integer purchaseQuantity = checkPromotions(purchaseCheckResponseDTO);
        validatePurchaseQuantity(purchaseQuantity);
        checkMembership();
        orderProducts();
    }

    private void validatePurchaseQuantity(final Integer purchaseQuantity) {
        if (purchaseQuantity < 1) {
            throw new NoPurchaseProductException();
        }
    }

    private void displayAllProductInfos() {
        ProductDisplayResponseDTO productDisplayResponseDTO = productDisplayFacade.getAllProducts();
        outputView.printAllProductInfos(productDisplayResponseDTO);
    }

    private PurchaseCheckResponseDTO checkPurchase() {
        return repeater.executeWithReturn(() -> {
            clearShoppingCart();
            displayAllProductInfos();
            PurchaseCheckRequestDTO purchaseCheckRequestDTO = inputView.readPurchaseProductInfos();
            return purchaseCheckFacade.checkPurchaseConditions(purchaseCheckRequestDTO);

        }, outputView::printErrorMessage);
    }

    private Integer checkPromotions(PurchaseCheckResponseDTO purchaseCheckResponseDTO) {
        return repeater.executeWithReturn(() -> inputView.promptUserForPromotions(purchaseCheckResponseDTO),
                outputView::printErrorMessage);
    }

    private void checkMembership() {
        repeater.executeNoReturn(inputView::promptUserForMemberShip, outputView::printErrorMessage);
    }

    private void orderProducts() {
        repeater.executeNoReturn(() -> {
            OrderRequestDTO orderRequestDTO = inputView.orderProducts();
            OrderResponseDTO orderResponseDTO = orderFacade.orderProducts(orderRequestDTO);
            outputView.printInvoiceInfo(orderResponseDTO);
        }, outputView::printErrorMessage);
    }

    private void clearShoppingCart() {
        inputView.clearShoppingCart();
    }

    public Boolean readRetry() {
        return repeater.executeWithReturn(inputView::promptRetry, outputView::printErrorMessage);
    }

}
