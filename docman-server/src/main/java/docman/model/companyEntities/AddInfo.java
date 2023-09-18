package docman.model.companyEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "company_add_info")
@AllArgsConstructor
@NoArgsConstructor
public class AddInfo {

    @Id
    @Column(name = "cai_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caiId;

    @Column(name = "cai_desc")
    private String description;

    @Column(name = "cai_contact_person")
    private String contactPerson;

    @Column(name = "cai_email")
    private String email;

    @Column(name = "cai_phone_number")
    private String phoneNumber;

    @Column(name = "cai_date_sheet_was_created")
    private Date dateSheetWasCreated;

    @Column(name = "cai_site")
    private String site;
}
