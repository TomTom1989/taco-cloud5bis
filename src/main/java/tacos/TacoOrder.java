package tacos;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

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
    private List<Long> tacoIds = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    public Long getTacoId() {
        return id;
    }
}
