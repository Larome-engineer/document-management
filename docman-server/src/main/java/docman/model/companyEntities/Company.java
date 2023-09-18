package docman.model.companyEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Entity
@Builder
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;

    @Column(name = "c_tin")
    private String tin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_add_info")
    private AddInfo addInfo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_criteria",
            joinColumns = @JoinColumn(name = "cc_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "cc_criteria_id")
    )
    private Set<Criteria> criteria;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_reports",
            joinColumns = @JoinColumn(name = "cr_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "cr_reports_id")
    )
    private Set<Reports> reports;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_means_of_protection",
            joinColumns = @JoinColumn(name = "c_mop_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "c_mop_m_id")
    )
    private Set<MeansOfProtection> meansOfProtections;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_info_system",
            joinColumns = @JoinColumn(name = "c_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "c_is_id")
    )
    private Set<InformationSystem> informationSystems;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_addresses",
            joinColumns = @JoinColumn(name = "ca_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "ca_ad_id")
    )
    private Set<Addresses> addresses;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_business_processes",
            joinColumns = @JoinColumn(name = "cbp_comp_id"),
            inverseJoinColumns = @JoinColumn(name = "cbp_bp_id")
    )
    private Set<BusinessProcess> businessProcesses;
}
