package tacos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tacos")
public class TacoController {

    private final TacoRepository tacoRepo;
    private final TacoOrderAggregateService tacoOrderService;

    public TacoController(TacoRepository tacoRepo, TacoOrderAggregateService tacoOrderService) {
        this.tacoRepo = tacoRepo;
        this.tacoOrderService = tacoOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @GetMapping("/{id}")
    public Mono<Taco> getTacoById(@PathVariable String id) {
        return tacoRepo.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Taco> updateTaco(@PathVariable String id, @RequestBody Taco taco) {
        return tacoRepo.findById(id)
            .map(existingTaco -> {
                existingTaco.setName(taco.getName());
                existingTaco.setIngredients(taco.getIngredients());
                return existingTaco;
            })
            .flatMap(tacoRepo::save);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTaco(@PathVariable String id) {
        return tacoRepo.deleteById(id);
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postTacoOrder(@RequestBody TacoOrder tacoOrder) {
        return tacoOrderService.save(tacoOrder);
    }

    @GetMapping("/order/{id}")
    public Mono<TacoOrder> getTacoOrderById(@PathVariable String id) {
        return tacoOrderService.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Taco Order not found"))); 
    }

    @GetMapping
    public Flux<Taco> getAllTacos() {
        return tacoRepo.findAll();
    }
    
    @GetMapping("/orders")
    public Flux<TacoOrder> getAllTacoOrders() {
        return tacoOrderService.findAllTacoOrders();
    }
}
