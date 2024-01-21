package tacos;

//import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import java.io.Serializable;
//import java.util.List;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
//import org.springframework.data.cassandra.core.mapping.Table;
//import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("ingredients")
public class Ingredient {
    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String type;

   
}