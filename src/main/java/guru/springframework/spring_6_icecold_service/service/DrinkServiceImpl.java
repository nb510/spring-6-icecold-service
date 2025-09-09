package guru.springframework.spring_6_icecold_service.service;

import guru.springframework.spring6restmvcapi.BeerOrderLineDto;
import guru.springframework.spring6restmvcapi.event.DrinkPreparedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static guru.springframework.spring_6_icecold_service.config.KafkaConfig.DRINK_PREPARED_TOPIC;

@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void prepareDrink(BeerOrderLineDto orderLine) {
        orderLine.setQuantityAllocated(orderLine.getOrderQuantity());

        kafkaTemplate.send(DRINK_PREPARED_TOPIC, new DrinkPreparedEvent(orderLine));
    }
}
