package du.ac.kr.map.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @GetMapping("/")
    public String showMap(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map"; // map.html 파일을 반환
    }
}
