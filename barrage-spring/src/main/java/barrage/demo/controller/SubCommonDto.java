package barrage.demo.controller;

import barrage.demo.dao.CommonDto;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
public class SubCommonDto extends CommonDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
