package docman.model.companyEntities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "information_system")
@AllArgsConstructor
@NoArgsConstructor
public class InformationSystem {

    @Id
    @Column(name = "is_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cisId;

    @Column(name = "is_system")
    private String system;

    @Column(name = "is_foreign")
    private boolean isForeign;

}
