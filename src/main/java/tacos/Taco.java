package tacos;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Taco {
    @Id
    private Long id;

    private @NonNull String name;

    // Transient annotation indicates this field is not persisted in the database
    @Transient
    private Set<Long> ingredientIds = new HashSet<>();

    // This field will be stored in the database as a TEXT column
    private String ingredientIdsSerialized;

    public Set<Long> getIngredientIds() {
        if (ingredientIds.isEmpty() && ingredientIdsSerialized != null && !ingredientIdsSerialized.isEmpty()) {
            // Deserialize ingredientIdsSerialized to ingredientIds
            ingredientIds = deserializeIngredientIds(ingredientIdsSerialized);
        }
        return ingredientIds;
    }

    public void setIngredientIds(Set<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
        // Serialize ingredientIds to ingredientIdsSerialized
        this.ingredientIdsSerialized = serializeIngredientIds(ingredientIds);
    }

    public void addIngredient(Ingredient ingredient) {
        getIngredientIds().add(ingredient.getId());
        setIngredientIds(this.ingredientIds);
    }

    private Set<Long> deserializeIngredientIds(String serializedIds) {
        return Set.of(serializedIds.split(",")).stream()
            .map(Long::parseLong)
            .collect(Collectors.toSet());
    }

    private String serializeIngredientIds(Set<Long> ids) {
        return ids.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
    }
}
