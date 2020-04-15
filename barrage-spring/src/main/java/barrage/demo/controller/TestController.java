package barrage.demo.controller;

import barrage.demo.entity.UseridToLoginname;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TomShiDi
 * @Since 2019/5/22
 * @Version 1.0
 */
@RestController
public class TestController {


    @ModelAttribute(value = "param_1")
    public String modelAttributeTest() {
        return "hello world  -------";
    }

    @GetMapping("/class-autowire")
    public String doClassAutowire(@ModelAttribute("param_1") String param_1) {
        return param_1;
    }
}
