package tacos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.OrderRepository;
import tacos.TacoRepository;

@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {
    private final TacoRepository tacoRepo;
    private final OrderRepository orderRepo;

    public Mono<TacoOrder> save(TacoOrder tacoOrder) {
        return Mono.just(tacoOrder)
            .flatMap(order -> {
                return tacoRepo.saveAll(order.getTacos())
                    .collectList()
                    .map(tacos -> {
                        order.setTacos(tacos);
                        return order;
                    });
            })
            .flatMap(orderRepo::save);
    }

    public Mono<TacoOrder> findById(String id) {
        return orderRepo.findById(id)
            .flatMap(order -> {
                List<String> tacoIds = order.getTacos().stream()
                    .map(Taco::getId)
                    .collect(Collectors.toList());
                return tacoRepo.findAllById(tacoIds)
                    .collectList()
                    .map(tacos -> {
                        order.setTacos(tacos);
                        return order;
                    });
            });
    }
    
    public Flux<TacoOrder> findAllTacoOrders() {
        return orderRepo.findAll();
    }
}
