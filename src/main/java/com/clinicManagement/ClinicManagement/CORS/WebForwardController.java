package com.clinicManagement.ClinicManagement.CORS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebForwardController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        // Trả về index.html để cho React Router xử lý
        return "forward:/";
    }
}
