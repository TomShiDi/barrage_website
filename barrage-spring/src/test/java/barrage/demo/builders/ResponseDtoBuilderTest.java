package barrage.demo.builders;

import barrage.demo.controller.SubCommonDto;
import barrage.demo.dao.AuthResponseDto;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
public class ResponseDtoBuilderTest {

    @Test
    public void doCommonTest() {
        ResponseDtoBuilder<AuthResponseDto> builder = new ResponseDtoBuilder<>(AuthResponseDto.class);
        AuthResponseDto authResponseDto = (AuthResponseDto) builder.message("111")
                .code(11)
                .data(new Object())
                .build();
        assertNotNull(authResponseDto);
    }

    @Test
    public void doExTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResponseDtoBuilder<SubCommonDto> builder = new ResponseDtoBuilder<>(SubCommonDto.class);
        SubCommonDto subCommonDto = (SubCommonDto) builder.exBuildMethod("name", "TomShiDi", String.class).build();
        assertNotNull(subCommonDto);
    }
}