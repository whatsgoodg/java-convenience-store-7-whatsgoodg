package store.front.parser.input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidProductInputException;

public class PurchaseProductInfoParser {
    private static final String PURCHASE_PRODUCTS_REGEX = "^\\[([가-힣]+)-([1-9]\\d*)](,\\[([가-힣]+)-([1-9]\\d*)])*$";
    private static final Pattern PURCHASE_PRODUCT_PATTERN = Pattern.compile(",?\\[([가-힣]+)-([1-9]\\d*)]");

    public static List<PurchaseProductInfo> parse(final String input) {
        validate(input);
        Matcher matcher = PURCHASE_PRODUCT_PATTERN.matcher(input);

        List<PurchaseProductInfo> purchaseProductInfos = new ArrayList<>();
        while (matcher.find()) {
            String name = matcher.group(1);
            Integer quantity = Integer.valueOf(matcher.group(2));
            purchaseProductInfos.add(new PurchaseProductInfo(name, quantity));
        }
        return purchaseProductInfos.stream().toList();
    }

    private static void validate(final String input) {
        if (!input.matches(PURCHASE_PRODUCTS_REGEX)) {
            throw new InvalidProductInputException();
        }
    }
}
