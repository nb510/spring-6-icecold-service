package guru.springframework.spring_6_icecold_service.service;

import guru.springframework.spring6restmvcapi.BeerOrderLineDto;
import guru.springframework.spring_6_icecold_service.config.KafkaConfig;
import guru.springframework.spring_6_icecold_service.listener.DrinkPreparedKafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = {KafkaConfig.DRINK_PREPARED_TOPIC}, partitions = 1, kraft = true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DrinkServiceImplTest {

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    DrinkService drinkService;
    @Autowired
    DrinkPreparedKafkaConsumer drinkPreparedKafkaConsumer;

    @BeforeEach
    void setUp() {
        kafkaListenerEndpointRegistry.getListenerContainers().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, 1);
        });
    }

    @Test
    void testPrepareDrink() {
        BeerOrderLineDto orderLine = BeerOrderLineDto.builder()
                .orderQuantity(10)
                .build();

        drinkService.prepareDrink(orderLine);

        await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
                .until(drinkPreparedKafkaConsumer.messageCount::get, greaterThan(0));
    }
}