package tacos;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TacoOrder {
    @Id
    private Long id;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    @Transient
    private transient List<Taco> tacos = new ArrayList<>();

    // This field will be stored in the database as a TEXT column
    private String tacoIdsSerialized;

    public List<Long> getTacoIds() {
        if (tacoIdsSerialized != null && !tacoIdsSerialized.isEmpty()) {
            // Deserialize tacoIdsSerialized to a List<Long>
            return deserializeTacoIds(tacoIdsSerialized);
        }
        return new ArrayList<>();
    }

    public void setTacoIds(List<Long> tacoIds) {
        // Serialize tacoIds to tacoIdsSerialized
        this.tacoIdsSerialized = serializeTacoIds(tacoIds);
    }

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
        List<Long> tacoIds = getTacoIds();
        tacoIds.add(taco.getId());
        setTacoIds(tacoIds);
    }

    private List<Long> deserializeTacoIds(String serializedIds) {
        return List.of(serializedIds.split(",")).stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

    private String serializeTacoIds(List<Long> ids) {
        return ids.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
    }
}
