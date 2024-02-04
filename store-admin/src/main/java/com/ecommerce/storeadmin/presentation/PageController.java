package com.ecommerce.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {

    /**
     * 메인 페이지에 대한 요청 처리
     * 루트 경로("/")와 "/main" 경로로 들어오는 요청 처리
     * "main"에 해당하는 뷰(main.html) 반환
     *
     * @return ModelAndView 객체("main" 뷰 포함)
     */
    @RequestMapping(path = {"", "/main"})
    public ModelAndView main(){
        return new ModelAndView("main");
    }
}
