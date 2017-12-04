


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class PortfolioController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    };
}

