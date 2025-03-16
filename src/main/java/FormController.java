import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class FormController {
    @PostMapping(value = "/validate-form",headers = "HX-Request=true")
    public String validateForm(@RequestParam Map<String, String> params, Model model) {
        boolean isFormValid = params.get("name") != null && !params.get("name").isEmpty()
                && params.get("email") != null && !params.get("email").isEmpty()
                && params.get("password") != null && !params.get("password").isEmpty();

        //System.out.println(" the sent params ==="+params);
        model.addAttribute("enabled", isFormValid);
        return "fragments/submit-button :: button";
    }
}
