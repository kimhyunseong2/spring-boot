package du.ac.kr.sb1031.event;

import du.ac.kr.sb1031.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final CustomEventPublisher customEventPublisher;

    @GetMapping("/event/{msg}")
    public void event(@PathVariable Order msg) {
        customEventPublisher.doStuffAndPublishAnEvent(msg);
    }
}
