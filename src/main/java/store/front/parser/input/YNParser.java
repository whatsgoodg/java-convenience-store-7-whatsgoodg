package store.front.parser.input;

import store.global.exception.InvalidInputException;

public class YNParser {
    private static final String YES = "Y";
    private static final String NO = "N";

    public static Boolean parse(String input) {
        validate(input);

        return input.equals("Y");
    }

    private static void validate(String input) {
        if (!input.equals(YES) && !input.equals(NO)) {
            throw new InvalidInputException();
        }
    }
}
