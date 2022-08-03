package Task6.FakePersonGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping({"/","/home", "/app"})
    public String app() {
        return "app";
    }
}
