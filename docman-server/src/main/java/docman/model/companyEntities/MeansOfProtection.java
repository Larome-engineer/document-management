package docman.model.companyEntities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Data
@Entity
@Table(name = "means_of_protection")
@AllArgsConstructor
@NoArgsConstructor
public class MeansOfProtection {

    @Id
    @Column(name = "mop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mopId;
    @Column(name = "mop_antivirus")
    private String antivirus;
    @Column(name = "mop_against_unauth_access")
    private String againstUnauthorizedAccess;
    @Column(name = "mop_inter_network_shielding")
    private String interNetworkShielding;
    @Column(name = "mop_siem")
    private String siem;
    @Column(name = "mop_dlp")
    private String dlp;
    @Column(name = "mop_crypto")
    private String crypto;
    @Column(name = "mop_security_monitoring_tool")
    private String securityMonitoringTool;

}
