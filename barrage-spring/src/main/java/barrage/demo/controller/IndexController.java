package barrage.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController("/")
public class IndexController {
    @GetMapping("/index")
    public ModelAndView showIndex() {

        return new ModelAndView("index");
    }

    //TODO 一级请求响应后，请求被拦截，不再向下传递
//    @RequestMapping(name = "")
//    public ModelAndView nonePathRequest() {
//
//        return new ModelAndView("index");
//    }
}
