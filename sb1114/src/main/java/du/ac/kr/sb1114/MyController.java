package du.ac.kr.sb1114;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class MyController {
    @GetMapping("/")
    public String index() {
        return "index";
    }


}
