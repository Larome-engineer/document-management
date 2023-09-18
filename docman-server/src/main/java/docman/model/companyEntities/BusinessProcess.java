package docman.model.companyEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "business_process")
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProcess {

    @Id
    @Column(name = "bp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBusinessProcess;

    @Column(name = "bp_name")
    private String businessProcess;

}
