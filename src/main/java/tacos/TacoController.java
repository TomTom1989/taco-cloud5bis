package tacos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    // Create a new taco
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    // Get a taco by ID
    @GetMapping("/{id}")
    public Mono<Taco> getTacoById(@PathVariable Long id) {
        return tacoRepo.findById(id);
    }

    // Update a taco
    @PutMapping("/{id}")
    public Mono<Taco> updateTaco(@PathVariable Long id, @RequestBody Taco taco) {
        return tacoRepo.findById(id)
            .map(existingTaco -> {
                existingTaco.setName(taco.getName());
                existingTaco.setIngredientIds(taco.getIngredientIds());
                return existingTaco;
            })
            .flatMap(tacoRepo::save);
    }

    // Delete a taco
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTaco(@PathVariable Long id) {
        return tacoRepo.deleteById(id);
    }

    // Create a new taco order
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postTacoOrder(@RequestBody TacoOrder tacoOrder) {
        return tacoOrderService.save(tacoOrder);
    }

    // Get a taco order by ID
    @GetMapping("/order/{id}")
    public Mono<TacoOrder> getTacoOrderById(@PathVariable Long id) {
        return tacoOrderService.findById(id);
    }

    // List all tacos
    @GetMapping
    public Flux<Taco> getAllTacos() {
        return tacoRepo.findAll();
    }

    // Additional methods can be added for more functionality...
}
