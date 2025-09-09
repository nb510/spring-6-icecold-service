package guru.springframework.spring_6_icecold_service.listener;

import guru.springframework.spring6restmvcapi.event.DrinkPreparedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static guru.springframework.spring_6_icecold_service.config.KafkaConfig.DRINK_PREPARED_TOPIC;

@Component
public class DrinkPreparedKafkaConsumer {
    public AtomicInteger messageCount = new AtomicInteger(0);

    @KafkaListener(groupId = "IntegrationTest", topics = DRINK_PREPARED_TOPIC)
    public void listen(DrinkPreparedEvent event) {
        System.out.println("Processing DrinkPreparedEvent in DrinkPreparedKafkaConsumer");
        messageCount.incrementAndGet();
    }
}
