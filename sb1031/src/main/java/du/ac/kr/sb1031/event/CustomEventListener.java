package du.ac.kr.sb1031.event;

import du.ac.kr.sb1031.order.Order;
import du.ac.kr.sb1031.shipment.Shipment;
import du.ac.kr.sb1031.shipment.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class CustomEventListener {

    private final ShipmentRepository shipmentRepository;

    @EventListener
    public void handleCustomEvent(CustomEvent customEvent) {
        log.info("event message: {}",customEvent.getMessage());
        Order order = customEvent.getMessage();
        Shipment shipment = Shipment.builder()
                .orderId(order.getId())
                .price(order.getPrice())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .status("주문접수")
                .build();
        shipmentRepository.save(shipment);
    }
}
