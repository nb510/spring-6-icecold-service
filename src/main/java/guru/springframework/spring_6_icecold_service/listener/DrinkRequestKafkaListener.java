package guru.springframework.spring_6_icecold_service.listener;

import guru.springframework.spring6restmvcapi.event.DrinkRequestEvent;
import guru.springframework.spring_6_icecold_service.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static guru.springframework.spring_6_icecold_service.config.KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC;

@Component
@RequiredArgsConstructor
public class DrinkRequestKafkaListener {

    private final DrinkService drinkService;

    @KafkaListener(groupId = "IceColdService", topics = DRINK_REQUEST_ICE_COLD_TOPIC)
    public void listen(DrinkRequestEvent event) {
        drinkService.prepareDrink(event.getBeerOrderLineDTO());
    }
}
