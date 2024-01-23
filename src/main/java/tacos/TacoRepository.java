package tacos;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, String> {
}
