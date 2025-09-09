package guru.springframework.spring_6_icecold_service.service;

import guru.springframework.spring6restmvcapi.BeerOrderLineDto;

public interface DrinkService {

    void prepareDrink(BeerOrderLineDto orderLine);
}
