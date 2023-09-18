package docman.model.companyEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "reports")
@AllArgsConstructor
@NoArgsConstructor
public class Reports {

    @Id
    @Column(name = "reports_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crId;

    @Column(name = "reports_path")
    private String reportsPath;

    @Column(name = "reports_created_date")
    private Date createDate;
}
