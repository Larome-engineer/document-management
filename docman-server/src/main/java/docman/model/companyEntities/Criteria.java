package docman.model.companyEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "criteria")
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
    @Id
    @Column(name = "criteria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ccId;
    @Column(name = "criteria_responsible_is")
    private int responsibleIs;

    @Column(name = "criteria_created_date")
    private Date createDate;
    @Column(name = "criteria_responsible_for_protect")
    private boolean responsibleForProtect;
    @Column(name = "criteria_num_of_territories")
    private int numOfTerritories;
    @Column(name = "criteria_personal_data_business_process" )
    private boolean personalDataBusinessProcess;
    @Column(name = "criteria_num_of_employees")
    private int numOfEmployees;
    @Column(name = "criteria_num_of_clients")
    private int numOfClients;
    @Column(name = "criteria_publicly_info")
    private boolean publiclyInfo;
    @Column(name = "criteria_biometric_info")
    private boolean biometricInfo;
    @Column(name = "criteria_special_info")
    private boolean specialInfo;
    @Column(name = "criteria_employee_processing")
    private boolean employeeProcessing;
    @Column(name = "criteria_clients_processing")
    private boolean clientsProcessing;
    @Column(name = "criteria_other_processing")
    private boolean otherProcessing;
    @Column(name = "criteria_num_of_servers")
    private int numOfServers;
    @Column(name = "criteria_ARM")
    private int arm;
    @Column(name = "criteria_skzi")
    private boolean skzi;
    @Column(name = "criteria_threat_model")
    private boolean threatModel;
    @Column(name = "criteria_personal_data_protect_policy")
    private boolean personalDataProtectPolicy;
    @Column(name = "criteria_other_documents_of_pd_protect")
    private boolean otherDocumentsOfPdProtect;
    @Column(name = "criteria_security_level")
    private boolean securityLevel;
    @Column(name = "criteria_degree_of_documentation")
    private int degreeOfDocumentation;
}
