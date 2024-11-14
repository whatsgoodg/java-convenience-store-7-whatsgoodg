package store.front.parser.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.InvalidInputException;

class YNParserTest {

    @Test
    @DisplayName("Y, N에 대해 Y일 경우 true를 반환하는 파싱 테스트")
    void Y_N_파싱_테스트1() {
        //given
        String input = "Y";
        //when
        Boolean parse = YNParser.parse(input);
        //then
        assertThat(parse).isTrue();
    }

    @Test
    @DisplayName("Y, N에 대해 N일 경우 false를 반환하는 파싱 테스트")
    void Y_N_파싱_테스트2() {
        //given
        String input = "N";
        //when
        Boolean parse = YNParser.parse(input);
        //then
        assertThat(parse).isFalse();
    }

    @Test
    @DisplayName("Y, N가 아닌 문자열에 대해 예외를 발생하는 테스트")
    void Y_N_파싱_테스트_예외_테스트() {
        //given
        String input = "a";
        //when
        //then
        assertThatThrownBy(() -> YNParser.parse(input)).isInstanceOf(InvalidInputException.class);
    }

}